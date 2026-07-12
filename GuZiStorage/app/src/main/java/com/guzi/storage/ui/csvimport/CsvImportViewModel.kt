package com.guzi.storage.ui.csvimport

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guzi.storage.data.repository.ItemRepository
import com.guzi.storage.data.repository.PresetOptionRepository
import com.guzi.storage.domain.model.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.UUID
import javax.inject.Inject

/**
 * CSV 批量导入 ViewModel
 *
 * CSV 模板列名（顺序不强制，按列名匹配）:
 * 中文名称,日文英文名称,IP名,出品商,出品地,发售形式,售卖方式,价格,种类,尺寸,材质,相关角色,柄图种类,状态,伤情详情,版本,数量,备注
 *
 * 一对多字段用 | 分隔，例如: 高木涉|佐藤美和子
 */
@HiltViewModel
class CsvImportViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val itemRepository: ItemRepository,
    private val presetOptionRepository: PresetOptionRepository
) : ViewModel() {

    sealed class ImportState {
        data object Idle : ImportState()
        data object Loading : ImportState()
        data class Preview(val items: List<Item>, val errorLines: List<String>) : ImportState()
        data class Done(val successCount: Int, val errorLines: List<String>) : ImportState()
        data class Error(val message: String) : ImportState()
    }

    private val _importState = MutableStateFlow<ImportState>(ImportState.Idle)
    val importState: StateFlow<ImportState> = _importState.asStateFlow()

    // 解析后待导入的列表
    private var parsedItems: List<Item> = emptyList()

    /**
     * 读取并解析 CSV 文件，进入预览状态
     */
    fun parseFile(uri: Uri) {
        viewModelScope.launch {
            _importState.value = ImportState.Loading
            try {
                val (items, errorLines) = parseCsv(uri)
                parsedItems = items
                _importState.value = ImportState.Preview(items, errorLines)
            } catch (e: Exception) {
                _importState.value = ImportState.Error("文件读取失败：${e.message}")
            }
        }
    }

    /**
     * 确认导入解析结果
     */
    fun confirmImport() {
        val items = parsedItems
        if (items.isEmpty()) return

        viewModelScope.launch {
            _importState.value = ImportState.Loading
            try {
                itemRepository.addItems(items)
                // 自动保存 IP 名、出品商等预设选项
                autoSavePresets(items)

                val errorLines = (_importState.value as? ImportState.Preview)?.errorLines ?: emptyList()
                _importState.value = ImportState.Done(items.size, errorLines)
            } catch (e: Exception) {
                _importState.value = ImportState.Error("导入失败：${e.message}")
            }
        }
    }

    fun reset() {
        parsedItems = emptyList()
        _importState.value = ImportState.Idle
    }

    // ===== 私有方法 =====

    private fun parseCsv(uri: Uri): Pair<List<Item>, List<String>> {
        val items = mutableListOf<Item>()
        val errorLines = mutableListOf<String>()

        val inputStream = context.contentResolver.openInputStream(uri)
            ?: throw Exception("无法打开文件")

        val reader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))
        val lines = reader.readLines()
        reader.close()

        if (lines.isEmpty()) return Pair(emptyList(), emptyList())

        // 解析表头（剥离 UTF-8 BOM，兼容 Windows 记事本/Excel 保存的文件）
        val firstLine = lines[0].removePrefix("\uFEFF")
        val headers = firstLine.split(",").map { it.trim().lowercase() }
        val colIndex = buildColMap(headers)

        // 表头未匹配到任何已知列时给出明确提示
        if (colIndex.isEmpty()) {
            errorLines.add("表头行无法识别，请确认第一行为列名（如：中文名称,IP名,发售形式,...）")
            return Pair(emptyList(), errorLines)
        }

        // 解析数据行
        for ((index, line) in lines.withIndex()) {
            if (index == 0) continue  // 跳过表头
            if (line.isBlank()) continue

            try {
                val cols = splitCsvLine(line)
                val item = buildItem(cols, colIndex)
                if (item != null) {
                    items.add(item)
                } else {
                    errorLines.add("第 ${index + 1} 行：必填字段（中文名称/IP名/发售形式/售卖方式/相关角色/柄图种类）缺失")
                }
            } catch (e: Exception) {
                errorLines.add("第 ${index + 1} 行：${e.message ?: "格式错误"}")
            }
        }

        return Pair(items, errorLines)
    }

    /**
     * 构建列名 → 索引映射（忽略大小写，支持别名）
     */
    private fun buildColMap(headers: List<String>): Map<String, Int> {
        val map = mutableMapOf<String, Int>()
        headers.forEachIndexed { index, header ->
            when (header) {
                "中文名称", "中文名" -> map["name_cn"] = index
                "日文英文名称", "日文名称", "英文名称", "日文(英文)名称", "日文（英文）名称" -> map["name_en"] = index
                "ip名", "ip" -> map["ip_name"] = index
                "出品商" -> map["producer"] = index
                "出品地" -> map["origin_places"] = index
                "发售形式" -> map["sale_format"] = index
                "售卖方式" -> map["sell_method"] = index
                "价格" -> map["price"] = index
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

    private fun buildItem(cols: List<String>, colIndex: Map<String, Int>): Item? {
        fun get(key: String): String = colIndex[key]?.let { if (it < cols.size) cols[it].trim() else "" } ?: ""
        fun getList(key: String): List<String> = get(key).split("|").map { it.trim() }.filter { it.isNotBlank() }

        val nameCn = get("name_cn")
        val ipName = get("ip_name")
        val saleFormat = getList("sale_format")
        val sellMethod = get("sell_method")
        val characters = getList("characters")
        val artTypes = getList("art_types")

        // 必填字段校验
        if (nameCn.isBlank() || ipName.isBlank() || saleFormat.isEmpty() ||
            sellMethod.isBlank() || characters.isEmpty() || artTypes.isEmpty()) {
            return null
        }

        val status = get("status").ifBlank { "无伤" }

        return Item(
            id = UUID.randomUUID().toString(),
            nameCn = nameCn,
            nameEn = get("name_en").ifBlank { null },
            ipName = ipName,
            producer = get("producer").ifBlank { null },
            originPlaces = getList("origin_places"),
            saleFormat = saleFormat,
            sellMethod = sellMethod,
            price = get("price").toDoubleOrNull(),
            category = get("category").ifBlank { null },
            sizes = getList("sizes"),
            materials = getList("materials"),
            characters = characters,
            artTypes = artTypes,
            status = status,
            damageDetails = getList("damage_details"),
            version = get("version").ifBlank { null },
            quantity = get("quantity").toIntOrNull()?.coerceAtLeast(1) ?: 1,
            note = get("note").ifBlank { null }
        )
    }

    /**
     * 简单 CSV 行解析（处理引号内包含逗号的情况）
     */
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

    /**
     * 将导入数据中的新选项自动加入预设库
     */
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
}
