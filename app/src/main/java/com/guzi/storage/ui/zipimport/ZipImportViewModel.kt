package com.guzi.storage.ui.zipimport

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guzi.storage.data.repository.ItemRepository
import com.guzi.storage.data.repository.PresetOptionRepository
import com.guzi.storage.data.repository.TransactionRepository
import com.guzi.storage.domain.model.Item
import com.guzi.storage.domain.model.Transaction
import com.guzi.storage.domain.model.TransactionType
import com.guzi.storage.util.DateUtils
import com.guzi.storage.util.IdGenerator
import com.guzi.storage.util.ImageFileManager
import com.guzi.storage.util.ZipExtractor
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import javax.inject.Inject

/**
 * ZIP 批量导入 ViewModel
 *
 * ZIP 结构：
 * - items.csv        谷子基本信息（含临时 id 列）
 * - transactions.csv 交易记录（id 列为外键，对应 items.csv 的 id）
 * - image/           图片文件夹，子目录名 = items.csv 中的 id
 *
 * 临时 id 仅用于导入时的关联，不会写入数据库
 */
@HiltViewModel
class ZipImportViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val itemRepository: ItemRepository,
    private val transactionRepository: TransactionRepository,
    private val presetOptionRepository: PresetOptionRepository,
    private val imageFileManager: ImageFileManager
) : ViewModel() {

    sealed class ImportState {
        data object Idle : ImportState()
        data object Loading : ImportState()
        data class Preview(val data: ZipImportData) : ImportState()
        data class Done(
            val itemCount: Int,
            val transactionCount: Int,
            val imageCount: Int,
            val mergedCount: Int,
            val errorLines: List<String>
        ) : ImportState()
        data class Error(val message: String) : ImportState()
    }

    private val _importState = MutableStateFlow<ImportState>(ImportState.Idle)
    val importState: StateFlow<ImportState> = _importState.asStateFlow()

    private var parsedData: ZipImportData? = null
    private var tempDir: File? = null

    fun parseFile(uri: Uri) {
        viewModelScope.launch {
            _importState.value = ImportState.Loading
            try {
                val data = parseZip(uri)
                parsedData = data
                _importState.value = ImportState.Preview(data)
            } catch (e: Exception) {
                _importState.value = ImportState.Error("文件解析失败：${e.javaClass.simpleName}: ${e.message}")
                cleanupTempDir()
            }
        }
    }

    fun confirmImport() {
        val data = parsedData ?: return
        viewModelScope.launch {
            _importState.value = ImportState.Loading
            try {
                // 0. 加载现有物品用于去重匹配
                val existingItems = itemRepository.getAllItems().first()
                val existingIds = existingItems.map { it.id }.toSet()

                val newItems = mutableListOf<Item>()
                val itemsToUpdate = mutableListOf<Item>()
                var mergedCount = 0
                var totalImages = 0
                // 导入物品 id → 实际物品 id 映射（用于交易记录关联）
                val itemIdRemap = mutableMapOf<String, String>()
                // 被合并的导入物品 id 集合（其交易记录和图片不导入）
                val mergedParsedIds = mutableSetOf<String>()

                for (parsedItem in data.items) {
                    val imported = parsedItem.item

                    // 按指定字段查找匹配的现有物品
                    val match = existingItems.find { isDuplicateItem(it, imported) }

                    if (match != null) {
                        // 合并：仅累加数量，不导入交易记录和图片
                        itemsToUpdate.add(match.copy(
                            quantity = match.quantity + imported.quantity
                        ))
                        itemIdRemap[imported.id] = match.id
                        mergedParsedIds.add(imported.id)
                        mergedCount++
                    } else {
                        // 新物品：复制图片
                        val imagePaths = parsedItem.imageFiles.mapNotNull { file ->
                            imageFileManager.copyImageFile(imported.id, file)
                        }
                        newItems.add(imported.copy(imagePaths = imagePaths))
                        itemIdRemap[imported.id] = imported.id
                        totalImages += imagePaths.size
                    }
                }

                // 2. 批量插入新物品
                if (newItems.isNotEmpty()) {
                    itemRepository.addItems(newItems)
                }

                // 3. 更新合并的物品（仅数量）
                for (item in itemsToUpdate) {
                    itemRepository.updateItem(item)
                }

                // 4. 批量插入交易记录
                // - 被合并物品的交易记录：跳过
                // - 新物品的交易记录：使用实际 item ID
                // - 直接引用数据库已有 UUID 的交易记录：导入
                val transactionsToInsert = data.transactions.mapNotNull { parsedTxn ->
                    val txnItemId = parsedTxn.transaction.itemId
                    when {
                        // 被合并物品的交易记录 → 跳过
                        txnItemId in mergedParsedIds -> null
                        // 来自 items.csv 的交易记录 → 使用实际 item ID
                        txnItemId in itemIdRemap -> parsedTxn.transaction.copy(
                            itemId = itemIdRemap[txnItemId]!!
                        )
                        // 直接引用数据库已有 UUID → 导入
                        txnItemId in existingIds -> parsedTxn.transaction
                        // 未知 id → 跳过
                        else -> null
                    }
                }
                if (transactionsToInsert.isNotEmpty()) {
                    transactionRepository.addTransactions(transactionsToInsert)
                }

                // 5. 自动保存预设选项
                autoSavePresets(newItems)

                // 6. 清理临时目录
                cleanupTempDir()

                _importState.value = ImportState.Done(
                    itemCount = newItems.size + mergedCount,
                    transactionCount = transactionsToInsert.size,
                    imageCount = totalImages,
                    mergedCount = mergedCount,
                    errorLines = data.errors
                )
            } catch (e: Exception) {
                _importState.value = ImportState.Error("导入失败：${e.message}")
            }
        }
    }

    fun reset() {
        cleanupTempDir()
        parsedData = null
        _importState.value = ImportState.Idle
    }

    // ===== ZIP 解析 =====

    private fun parseZip(uri: Uri): ZipImportData {
        val errors = mutableListOf<String>()

        val dir = File(context.cacheDir, "zip_import_${System.currentTimeMillis()}")
        dir.mkdirs()
        tempDir = dir

        // 先将 ZIP 复制到临时文件，再解压（支持 GBK 编码回退）
        val tempZip = File(context.cacheDir, "zip_src_${System.currentTimeMillis()}.zip")
        try {
            context.contentResolver.openInputStream(uri).use { input ->
                if (input == null) throw Exception("无法打开文件")
                tempZip.outputStream().use { out -> input.copyTo(out) }
            }
            ZipExtractor.unzipToDir(tempZip, dir)
        } finally {
            tempZip.delete()
        }

        // 查找文件（递归搜索，不区分大小写，兼容 ZIP 内有顶层文件夹的情况）
        val itemsFile = findFileRecursive(dir, "items.csv")
            ?: throw Exception("压缩包中未找到 items.csv")

        val transactionsFile = findFileRecursive(dir, "transactions.csv")

        val imagesDir = findDirRecursive(dir, "image")

        if (transactionsFile == null) {
            errors.add("未找到 transactions.csv，跳过交易记录导入")
        }
        if (imagesDir == null) {
            errors.add("未找到 image 文件夹，跳过图片导入")
        }

        // 解析 items.csv
        val (parsedItems, itemErrors) = parseItemsCsv(itemsFile, imagesDir)
        errors.addAll(itemErrors)

        // 构建 tempId -> realUUID 映射
        val idMap = parsedItems.associate { it.tempId to it.item.id }

        // 解析 transactions.csv
        val transactions = if (transactionsFile != null) {
            val (parsedTxns, txnErrors) = parseTransactionsCsv(transactionsFile, idMap)
            errors.addAll(txnErrors)
            parsedTxns
        } else {
            emptyList()
        }

        return ZipImportData(
            items = parsedItems,
            transactions = transactions,
            errors = errors
        )
    }

    // ===== CSV 解析 =====

    private fun parseItemsCsv(
        file: File,
        imagesDir: File?
    ): Pair<List<ParsedItem>, List<String>> {
        val items = mutableListOf<ParsedItem>()
        val errorLines = mutableListOf<String>()

        val reader = BufferedReader(InputStreamReader(file.inputStream(), "UTF-8"))
        val lines = reader.readLines()
        reader.close()

        if (lines.isEmpty()) return Pair(emptyList(), listOf("items.csv 为空"))

        // 解析表头（剥离 BOM）
        val firstLine = lines[0].removePrefix("\uFEFF")
        val headers = firstLine.split(",").map { it.trim().lowercase() }
        val colIndex = buildItemColMap(headers)

        if (colIndex.isEmpty()) {
            return Pair(emptyList(), listOf("items.csv 表头无法识别"))
        }

        for ((index, line) in lines.withIndex()) {
            if (index == 0 || line.isBlank()) continue

            try {
                val cols = splitCsvLine(line)
                val tempId = getCol(cols, colIndex, "import_id").ifBlank { "${index}" }
                val (item, missing) = buildItem(cols, colIndex)
                if (item != null) {
                    // 查找对应图片
                    val imageFiles = if (imagesDir != null) {
                        val itemImageDir = File(imagesDir, tempId)
                        if (itemImageDir.exists() && itemImageDir.isDirectory) {
                            itemImageDir.listFiles()?.filter { it.isFile }?.sortedBy { it.name }
                                ?: emptyList()
                        } else emptyList()
                    } else emptyList()

                    items.add(ParsedItem(tempId, item, imageFiles))
                } else {
                    errorLines.add("items.csv 第 ${index + 1} 行：缺少必填字段：${missing.joinToString("、")}")
                }
            } catch (e: Exception) {
                errorLines.add("items.csv 第 ${index + 1} 行：${e.message ?: "格式错误"}")
            }
        }

        return Pair(items, errorLines)
    }

    private fun parseTransactionsCsv(
        file: File,
        idMap: Map<String, String>
    ): Pair<List<ParsedTransactionData>, List<String>> {
        val transactions = mutableListOf<ParsedTransactionData>()
        val errorLines = mutableListOf<String>()

        val reader = BufferedReader(InputStreamReader(file.inputStream(), "UTF-8"))
        val lines = reader.readLines()
        reader.close()

        if (lines.isEmpty()) return Pair(emptyList(), emptyList())

        val firstLine = lines[0].removePrefix("\uFEFF")
        val headers = firstLine.split(",").map { it.trim().lowercase() }
        val colIndex = buildTransactionColMap(headers)

        if (colIndex.isEmpty()) {
            return Pair(emptyList(), listOf("transactions.csv 表头无法识别"))
        }

        for ((index, line) in lines.withIndex()) {
            if (index == 0 || line.isBlank()) continue

            try {
                val cols = splitCsvLine(line)
                val tempItemId = getCol(cols, colIndex, "item_ref_id")

                // 先从 idMap 查找（items.csv 的临时 id），找不到则保留原始值（可能是数据库已有 UUID）
                val realItemId = idMap[tempItemId] ?: tempItemId

                val typeStr = getCol(cols, colIndex, "type")
                val type = if (typeStr.contains("卖出")) TransactionType.SELL else TransactionType.BUY
                val nameCn = getCol(cols, colIndex, "name_cn").ifBlank { "交易记录" }
                val price = getCol(cols, colIndex, "price").toDoubleOrNull() ?: 0.0
                val channel = getCol(cols, colIndex, "channel").ifBlank { "未知" }
                val quantity = getCol(cols, colIndex, "quantity").toIntOrNull()?.coerceAtLeast(1) ?: 1
                val date = DateUtils.normalizeYearMonth(getCol(cols, colIndex, "date"))
                val note = getCol(cols, colIndex, "note").ifBlank { null }

                transactions.add(ParsedTransactionData(
                    Transaction(
                        id = IdGenerator.generate(),
                        itemId = realItemId,
                        nameCn = nameCn,
                        type = type,
                        price = price,
                        channel = channel,
                        quantity = quantity,
                        date = date,
                        note = note
                    )
                ))
            } catch (e: Exception) {
                errorLines.add("transactions.csv 第 ${index + 1} 行：${e.message ?: "格式错误"}")
            }
        }

        return Pair(transactions, errorLines)
    }

    // ===== 列名映射 =====

    private fun buildItemColMap(headers: List<String>): Map<String, Int> {
        val map = mutableMapOf<String, Int>()
        headers.forEachIndexed { index, header ->
            when (header) {
                "id" -> map["import_id"] = index
                "中文名称", "中文名" -> map["name_cn"] = index
                "日文英文名称", "日文/英文名称", "日文名称", "英文名称", "日文(英文)名称", "日文（英文）名称" -> map["name_en"] = index
                "ip名", "ip" -> map["ip_name"] = index
                "出品商" -> map["producer"] = index
                "出品地" -> map["origin_places"] = index
                "发售形式" -> map["sale_format"] = index
                "售卖方式" -> map["sell_method"] = index
                "价格" -> map["price"] = index
                "发售日期" -> map["release_date"] = index
                "种类" -> map["category"] = index
                "尺寸" -> map["sizes"] = index
                "材质" -> map["materials"] = index
                "相关角色" -> map["characters"] = index
                "柄图种类" -> map["art_types"] = index
                "状态" -> map["status"] = index
                "伤情详情", "伤情" -> map["damage_details"] = index
                "版本" -> map["version"] = index
                "数量" -> map["quantity"] = index
                "备注" -> map["note"] = index
            }
        }
        return map
    }

    private fun buildTransactionColMap(headers: List<String>): Map<String, Int> {
        val map = mutableMapOf<String, Int>()
        headers.forEachIndexed { index, header ->
            when (header) {
                "id" -> map["item_ref_id"] = index
                "类型", "type" -> map["type"] = index
                "名称", "name_cn", "namecn" -> map["name_cn"] = index
                "价格", "price" -> map["price"] = index
                "渠道", "channel" -> map["channel"] = index
                "数量", "quantity" -> map["quantity"] = index
                "日期", "date" -> map["date"] = index
                "备注", "note" -> map["note"] = index
            }
        }
        return map
    }

    // ===== 辅助方法 =====

    /** 递归搜索指定文件名（不区分大小写） */
    private fun findFileRecursive(dir: File, name: String): File? {
        dir.listFiles()?.forEach { file ->
            when {
                file.isFile && file.name.equals(name, ignoreCase = true) -> return file
                file.isDirectory -> findFileRecursive(file, name)?.let { return it }
            }
        }
        return null
    }

    /** 递归搜索指定目录名（不区分大小写） */
    private fun findDirRecursive(dir: File, name: String): File? {
        dir.listFiles()?.forEach { file ->
            when {
                file.isDirectory && file.name.equals(name, ignoreCase = true) -> return file
                file.isDirectory -> findDirRecursive(file, name)?.let { return it }
            }
        }
        return null
    }

    /** 判断两个物品是否在指定字段上完全相同（用于导入去重） */
    private fun isDuplicateItem(existing: Item, imported: Item): Boolean {
        return existing.nameCn == imported.nameCn &&
               existing.ipName == imported.ipName &&
               (existing.producer ?: "") == (imported.producer ?: "") &&
               existing.originPlaces.toSet() == imported.originPlaces.toSet() &&
               existing.saleFormat.toSet() == imported.saleFormat.toSet() &&
               existing.sellMethod == imported.sellMethod &&
               (existing.category ?: "") == (imported.category ?: "") &&
               existing.sizes.toSet() == imported.sizes.toSet() &&
               existing.characters.toSet() == imported.characters.toSet() &&
               existing.artTypes.toSet() == imported.artTypes.toSet() &&
               existing.status == imported.status &&
               (existing.version ?: "") == (imported.version ?: "") &&
               (existing.releaseDate ?: "") == (imported.releaseDate ?: "")
    }

    private fun getCol(cols: List<String>, colIndex: Map<String, Int>, key: String): String =
        colIndex[key]?.let { if (it < cols.size) cols[it].trim() else "" } ?: ""

    private fun buildItem(cols: List<String>, colIndex: Map<String, Int>): Pair<Item?, List<String>> {
        fun get(key: String): String = getCol(cols, colIndex, key)
        fun getList(key: String): List<String> = get(key).split("|").map { it.trim() }.filter { it.isNotBlank() }

        val nameCn = get("name_cn")
        val ipName = get("ip_name")
        val saleFormat = getList("sale_format")
        val sellMethod = get("sell_method")
        val characters = getList("characters")
        val artTypes = getList("art_types")

        val missing = mutableListOf<String>()
        if (nameCn.isBlank()) missing.add("中文名称")
        if (ipName.isBlank()) missing.add("IP名")
        if (saleFormat.isEmpty()) missing.add("发售形式")
        if (characters.isEmpty()) missing.add("相关角色")
        if (artTypes.isEmpty()) missing.add("柄图种类")
        if (missing.isNotEmpty()) return Pair(null, missing)

        val statusRaw = get("status")
        val damageRaw = get("damage_details")
        // 状态单列模式：空→无伤，有值→有伤+damageDetails
        // 兼容旧格式：status="有伤" + damage_details="底锈|划痕"
        val (status, damageDetails) = when {
            statusRaw.isBlank() || statusRaw == "无伤" -> "无伤" to emptyList()
            statusRaw == "有伤" -> "有伤" to damageRaw.split("|").map { it.trim() }.filter { it.isNotBlank() }
            else -> "有伤" to statusRaw.split("|").map { it.trim() }.filter { it.isNotBlank() }
        }

        val item = Item(
            id = IdGenerator.generate(),
            nameCn = nameCn,
            nameEn = get("name_en").ifBlank { null },
            ipName = ipName,
            producer = get("producer").ifBlank { null },
            originPlaces = getList("origin_places"),
            saleFormat = saleFormat,
            sellMethod = sellMethod,
            price = get("price").ifBlank { null },
            releaseDate = DateUtils.normalizeYearMonth(get("release_date")),
            category = get("category").ifBlank { null },
            sizes = getList("sizes"),
            materials = getList("materials"),
            characters = characters,
            artTypes = artTypes,
            status = status,
            damageDetails = damageDetails,
            version = get("version").ifBlank { null },
            quantity = get("quantity").toIntOrNull()?.coerceAtLeast(1) ?: 1,
            note = get("note").ifBlank { null }
        )
        return Pair(item, emptyList())
    }

    private fun splitCsvLine(line: String): List<String> {
        val result = mutableListOf<String>()
        val current = StringBuilder()
        var inQuotes = false
        for (char in line) {
            when {
                char == '"' -> inQuotes = !inQuotes
                char == ',' && !inQuotes -> {
                    result.add(current.toString())
                    current.clear()
                }
                else -> current.append(char)
            }
        }
        result.add(current.toString())
        return result
    }

    private suspend fun autoSavePresets(items: List<Item>) {
        val ipNames = items.map { it.ipName }.distinct()
        val producers = items.mapNotNull { it.producer }.distinct()
        val originPlaces = items.flatMap { it.originPlaces }.distinct()
        val saleFormats = items.flatMap { it.saleFormat }.distinct()
        val sellMethods = items.map { it.sellMethod }.distinct()
        val categories = items.mapNotNull { it.category }.distinct()
        val sizes = items.flatMap { it.sizes }.distinct()
        val materials = items.flatMap { it.materials }.distinct()
        val characters = items.flatMap { it.characters }.distinct()
        val artTypes = items.flatMap { it.artTypes }.distinct()

        presetOptionRepository.addOptions("ip_name", ipNames)
        presetOptionRepository.addOptions("producer", producers)
        presetOptionRepository.addOptions("origin_places", originPlaces)
        presetOptionRepository.addOptions("sale_format", saleFormats)
        presetOptionRepository.addOptions("sell_method", sellMethods)
        presetOptionRepository.addOptions("category", categories)
        presetOptionRepository.addOptions("sizes", sizes)
        presetOptionRepository.addOptions("materials", materials)
        presetOptionRepository.addOptions("characters", characters)
        presetOptionRepository.addOptions("art_types", artTypes)
    }

    private fun cleanupTempDir() {
        tempDir?.let { dir ->
            if (dir.exists()) dir.deleteRecursively()
        }
        tempDir = null
    }

    override fun onCleared() {
        super.onCleared()
        cleanupTempDir()
    }
}

// ===== 数据类 =====

data class ParsedItem(
    val tempId: String,
    val item: Item,
    val imageFiles: List<File>
)

data class ParsedTransactionData(
    val transaction: Transaction
)

data class ZipImportData(
    val items: List<ParsedItem>,
    val transactions: List<ParsedTransactionData>,
    val errors: List<String>
) {
    val totalImageCount: Int get() = items.sumOf { it.imageFiles.size }
}
