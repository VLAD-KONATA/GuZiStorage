package com.guzi.storage.ui.fileimport

import android.content.Context
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guzi.storage.data.repository.ItemRepository
import com.guzi.storage.data.repository.PresetOptionRepository
import com.guzi.storage.data.repository.TransactionRepository
import com.guzi.storage.domain.model.Item
import com.guzi.storage.util.DateUtils
import com.guzi.storage.util.IdGenerator
import com.guzi.storage.domain.model.Transaction
import com.guzi.storage.domain.model.TransactionType
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

enum class StandaloneImportType {
    ITEMS_CSV,
    TRANSACTIONS_CSV,
    IMAGE_ZIP
}

/**
 * 单独文件导入 ViewModel
 *
 * 支持三种导入模式：
 * - items.csv：导入谷子信息（含去重合并）
 * - transactions.csv：导入交易记录（id 须对应已有谷子）
 * - image.zip：导入图片（子目录名须对应已有谷子 id）
 */
@HiltViewModel
class StandaloneImportViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val itemRepository: ItemRepository,
    private val transactionRepository: TransactionRepository,
    private val presetOptionRepository: PresetOptionRepository,
    private val imageFileManager: ImageFileManager,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val importType: StandaloneImportType = when (savedStateHandle.get<String>("type")) {
        "transactions_csv" -> StandaloneImportType.TRANSACTIONS_CSV
        "image_zip" -> StandaloneImportType.IMAGE_ZIP
        else -> StandaloneImportType.ITEMS_CSV
    }

    sealed class ImportState {
        data object Idle : ImportState()
        data object Loading : ImportState()
        data class Preview(val data: StandalonePreviewData) : ImportState()
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

    private var previewData: StandalonePreviewData? = null
    private var tempDir: File? = null

    fun parseFile(uri: Uri) {
        viewModelScope.launch {
            _importState.value = ImportState.Loading
            try {
                val data = when (importType) {
                    StandaloneImportType.ITEMS_CSV -> parseItemsCsvFile(uri)
                    StandaloneImportType.TRANSACTIONS_CSV -> parseTransactionsCsvFile(uri)
                    StandaloneImportType.IMAGE_ZIP -> parseImageZipFile(uri)
                }
                previewData = data
                _importState.value = ImportState.Preview(data)
            } catch (e: Exception) {
                _importState.value = ImportState.Error("文件解析失败：${e.javaClass.simpleName}: ${e.message}")
                cleanupTempDir()
            }
        }
    }

    fun confirmImport() {
        val data = previewData ?: return
        viewModelScope.launch {
            _importState.value = ImportState.Loading
            try {
                when (importType) {
                    StandaloneImportType.ITEMS_CSV -> confirmItemsCsv(data as StandalonePreviewData.ItemsPreview)
                    StandaloneImportType.TRANSACTIONS_CSV -> confirmTransactionsCsv(data as StandalonePreviewData.TransactionsPreview)
                    StandaloneImportType.IMAGE_ZIP -> confirmImageZip(data as StandalonePreviewData.ImagesPreview)
                }
            } catch (e: Exception) {
                _importState.value = ImportState.Error("导入失败：${e.message}")
            }
        }
    }

    fun reset() {
        cleanupTempDir()
        previewData = null
        _importState.value = ImportState.Idle
    }

    // ===== items.csv 导入 =====

    private suspend fun parseItemsCsvFile(uri: Uri): StandalonePreviewData.ItemsPreview {
        val tempFile = File(context.cacheDir, "import_items_${System.currentTimeMillis()}.csv")
        context.contentResolver.openInputStream(uri).use { input ->
            input?.copyTo(tempFile.outputStream()) ?: throw Exception("无法打开文件")
        }

        val reader = BufferedReader(InputStreamReader(tempFile.inputStream(), "UTF-8"))
        val lines = reader.readLines()
        reader.close()
        tempFile.delete()

        if (lines.isEmpty()) throw Exception("文件为空")

        val firstLine = lines[0].removePrefix("\uFEFF")
        val headers = firstLine.split(",").map { it.trim().lowercase() }
        val colIndex = buildItemColMap(headers)
        if (colIndex.isEmpty()) throw Exception("表头无法识别")

        val items = mutableListOf<ParsedItemData>()
        val errors = mutableListOf<String>()

        for ((index, line) in lines.withIndex()) {
            if (index == 0 || line.isBlank()) continue
            try {
                val cols = splitCsvLine(line)
                val tempId = getCol(cols, colIndex, "import_id").ifBlank { "${index}" }
                val (item, missing) = buildItem(cols, colIndex)
                if (item != null) {
                    items.add(ParsedItemData(tempId, item))
                } else {
                    errors.add("第 ${index + 1} 行：缺少必填字段：${missing.joinToString("、")}")
                }
            } catch (e: Exception) {
                errors.add("第 ${index + 1} 行：${e.message ?: "格式错误"}")
            }
        }

        return StandalonePreviewData.ItemsPreview(items = items, errors = errors)
    }

    private suspend fun confirmItemsCsv(data: StandalonePreviewData.ItemsPreview) {
        val existingItems = itemRepository.getAllItems().first()

        val newItems = mutableListOf<Item>()
        val itemsToUpdate = mutableListOf<Item>()
        var mergedCount = 0

        for (parsed in data.items) {
            val imported = parsed.item
            val match = existingItems.find { isDuplicateItem(it, imported) }

            if (match != null) {
                itemsToUpdate.add(match.copy(quantity = match.quantity + imported.quantity))
                mergedCount++
            } else {
                newItems.add(imported)
            }
        }

        if (newItems.isNotEmpty()) itemRepository.addItems(newItems)
        for (item in itemsToUpdate) itemRepository.updateItem(item)
        autoSavePresets(newItems)

        _importState.value = ImportState.Done(
            itemCount = newItems.size + mergedCount,
            transactionCount = 0,
            imageCount = 0,
            mergedCount = mergedCount,
            errorLines = data.errors
        )
    }

    // ===== transactions.csv 导入 =====

    private suspend fun parseTransactionsCsvFile(uri: Uri): StandalonePreviewData.TransactionsPreview {
        val existingItems = itemRepository.getAllItems().first()
        val existingIds = existingItems.map { it.id }.toSet()
        val idToName = existingItems.associate { it.id to it.nameCn }

        val tempFile = File(context.cacheDir, "import_txns_${System.currentTimeMillis()}.csv")
        context.contentResolver.openInputStream(uri).use { input ->
            input?.copyTo(tempFile.outputStream()) ?: throw Exception("无法打开文件")
        }

        val reader = BufferedReader(InputStreamReader(tempFile.inputStream(), "UTF-8"))
        val lines = reader.readLines()
        reader.close()
        tempFile.delete()

        if (lines.isEmpty()) throw Exception("文件为空")

        val firstLine = lines[0].removePrefix("\uFEFF")
        val headers = firstLine.split(",").map { it.trim().lowercase() }
        val colIndex = buildTransactionColMap(headers)
        if (colIndex.isEmpty()) throw Exception("表头无法识别")

        val validTransactions = mutableListOf<Transaction>()
        val errors = mutableListOf<String>()

        for ((index, line) in lines.withIndex()) {
            if (index == 0 || line.isBlank()) continue
            try {
                val cols = splitCsvLine(line)
                val itemId = getCol(cols, colIndex, "item_ref_id")

                if (itemId !in existingIds) {
                    errors.add("第 ${index + 1} 行：id=$itemId 在数据库中不存在")
                    continue
                }

                val typeStr = getCol(cols, colIndex, "type")
                val type = if (typeStr.contains("卖出")) TransactionType.SELL else TransactionType.BUY
                val nameCn = getCol(cols, colIndex, "name_cn").ifBlank { idToName[itemId] ?: "交易记录" }
                val price = getCol(cols, colIndex, "price").toDoubleOrNull() ?: 0.0
                val channel = getCol(cols, colIndex, "channel").ifBlank { "未知" }
                val quantity = getCol(cols, colIndex, "quantity").toIntOrNull()?.coerceAtLeast(1) ?: 1
                val date = DateUtils.normalizeYearMonth(getCol(cols, colIndex, "date"))
                val note = getCol(cols, colIndex, "note").ifBlank { null }

                validTransactions.add(Transaction(
                    id = IdGenerator.generate(),
                    itemId = itemId,
                    nameCn = nameCn,
                    type = type,
                    price = price,
                    channel = channel,
                    quantity = quantity,
                    date = date,
                    note = note
                ))
            } catch (e: Exception) {
                errors.add("第 ${index + 1} 行：${e.message ?: "格式错误"}")
            }
        }

        return StandalonePreviewData.TransactionsPreview(
            transactions = validTransactions,
            errors = errors
        )
    }

    private suspend fun confirmTransactionsCsv(data: StandalonePreviewData.TransactionsPreview) {
        if (data.transactions.isNotEmpty()) {
            transactionRepository.addTransactions(data.transactions)
        }
        _importState.value = ImportState.Done(
            itemCount = 0,
            transactionCount = data.transactions.size,
            imageCount = 0,
            mergedCount = 0,
            errorLines = data.errors
        )
    }

    // ===== image.zip 导入 =====

    private suspend fun parseImageZipFile(uri: Uri): StandalonePreviewData.ImagesPreview {
        val existingItems = itemRepository.getAllItems().first()
        val existingIds = existingItems.map { it.id }.toSet()

        val dir = File(context.cacheDir, "img_import_${System.currentTimeMillis()}")
        dir.mkdirs()
        tempDir = dir

        // 先将 ZIP 复制到临时文件，再解压（支持 GBK 编码回退）
        val tempZip = File(context.cacheDir, "img_src_${System.currentTimeMillis()}.zip")
        try {
            context.contentResolver.openInputStream(uri).use { input ->
                if (input == null) throw Exception("无法打开文件")
                tempZip.outputStream().use { out -> input.copyTo(out) }
            }
            ZipExtractor.unzipToDir(tempZip, dir)
        } finally {
            tempZip.delete()
        }

        // 查找 image/ 文件夹（递归搜索）
        val imagesDir = findDirRecursive(dir, "image")
            ?: throw Exception("压缩包中未找到 image 文件夹")

        val validGroups = mutableListOf<ImageGroup>()
        val errors = mutableListOf<String>()

        imagesDir.listFiles()?.forEach { subDir ->
            if (subDir.isDirectory) {
                val itemId = subDir.name
                val imageFiles = subDir.listFiles()?.filter { it.isFile }?.sortedBy { it.name }
                    ?: emptyList()

                if (itemId !in existingIds) {
                    errors.add("目录 $itemId：在数据库中不存在，跳过")
                } else if (imageFiles.isEmpty()) {
                    errors.add("目录 $itemId：无图片文件")
                } else {
                    validGroups.add(ImageGroup(itemId, imageFiles))
                }
            }
        }

        if (validGroups.isEmpty() && errors.isEmpty()) {
            errors.add("image 文件夹中未找到任何子目录")
        }

        return StandalonePreviewData.ImagesPreview(
            groups = validGroups,
            errors = errors
        )
    }

    private suspend fun confirmImageZip(data: StandalonePreviewData.ImagesPreview) {
        var totalImages = 0
        val itemsToUpdate = mutableListOf<Item>()
        val existingItems = itemRepository.getAllItems().first()
        val itemMap = existingItems.associateBy { it.id }

        for (group in data.groups) {
            val existing = itemMap[group.itemId] ?: continue
            val newPaths = group.imageFiles.mapNotNull { file ->
                imageFileManager.copyImageFile(group.itemId, file)
            }
            if (newPaths.isNotEmpty()) {
                itemsToUpdate.add(existing.copy(
                    imagePaths = existing.imagePaths + newPaths
                ))
                totalImages += newPaths.size
            }
        }

        for (item in itemsToUpdate) {
            itemRepository.updateItem(item)
        }

        _importState.value = ImportState.Done(
            itemCount = 0,
            transactionCount = 0,
            imageCount = totalImages,
            mergedCount = 0,
            errorLines = data.errors
        )
    }

    // ===== 辅助方法 =====

    /** 递归搜索指定目录名 */
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

data class ParsedItemData(
    val tempId: String,
    val item: Item
)

data class ImageGroup(
    val itemId: String,
    val imageFiles: List<File>
)

sealed class StandalonePreviewData {
    abstract val errors: List<String>

    data class ItemsPreview(
        val items: List<ParsedItemData>,
        override val errors: List<String>
    ) : StandalonePreviewData()

    data class TransactionsPreview(
        val transactions: List<Transaction>,
        override val errors: List<String>
    ) : StandalonePreviewData()

    data class ImagesPreview(
        val groups: List<ImageGroup>,
        override val errors: List<String>
    ) : StandalonePreviewData()
}
