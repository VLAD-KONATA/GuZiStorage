package com.guzi.storage.ui.add

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guzi.storage.data.repository.ItemRepository
import com.guzi.storage.data.repository.PresetOptionRepository
import com.guzi.storage.data.repository.UserPreferencesRepository
import com.guzi.storage.domain.model.Item
import com.guzi.storage.util.CurrencyUtils
import com.guzi.storage.util.DateUtils
import com.guzi.storage.util.ImageFileManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import com.guzi.storage.util.IdGenerator
import javax.inject.Inject

/**
 * 添加/编辑物品表单的 ViewModel
 *
 * 管理表单所有字段的状态、校验逻辑和提交操作
 */
@HiltViewModel
class AddItemViewModel @Inject constructor(
    private val itemRepository: ItemRepository,
    private val presetOptionRepository: PresetOptionRepository,
    private val imageFileManager: ImageFileManager,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    // ===== 表单状态 =====
    private val _formState = MutableStateFlow(ItemFormState())
    val formState: StateFlow<ItemFormState> = _formState.asStateFlow()

    // ===== 预设选项 =====
    private val _ipOptions = MutableStateFlow<List<String>>(emptyList())
    val ipOptions: StateFlow<List<String>> = _ipOptions.asStateFlow()

    private val _producerOptions = MutableStateFlow<List<String>>(emptyList())
    val producerOptions: StateFlow<List<String>> = _producerOptions.asStateFlow()

    private val _originPlaceOptions = MutableStateFlow<List<String>>(emptyList())
    val originPlaceOptions: StateFlow<List<String>> = _originPlaceOptions.asStateFlow()

    private val _saleFormatOptions = MutableStateFlow<List<String>>(emptyList())
    val saleFormatOptions: StateFlow<List<String>> = _saleFormatOptions.asStateFlow()

    private val _sellMethodOptions = MutableStateFlow<List<String>>(emptyList())
    val sellMethodOptions: StateFlow<List<String>> = _sellMethodOptions.asStateFlow()

    private val _categoryOptions = MutableStateFlow<List<String>>(emptyList())
    val categoryOptions: StateFlow<List<String>> = _categoryOptions.asStateFlow()

    private val _sizeOptions = MutableStateFlow<List<String>>(emptyList())
    val sizeOptions: StateFlow<List<String>> = _sizeOptions.asStateFlow()

    private val _materialOptions = MutableStateFlow<List<String>>(emptyList())
    val materialOptions: StateFlow<List<String>> = _materialOptions.asStateFlow()

    private val _characterOptions = MutableStateFlow<List<String>>(emptyList())
    val characterOptions: StateFlow<List<String>> = _characterOptions.asStateFlow()

    private val _artTypeOptions = MutableStateFlow<List<String>>(emptyList())
    val artTypeOptions: StateFlow<List<String>> = _artTypeOptions.asStateFlow()

    // ===== 保存状态 =====
    private val _saveResult = MutableStateFlow<SaveResult?>(null)
    val saveResult: StateFlow<SaveResult?> = _saveResult.asStateFlow()

    // 编辑模式下加载时数据库中已引用的图片路径（用于区分旧图与本次新增图）
    private var initialImagePaths: Set<String> = emptySet()

    // ===== 初始化 =====

    init {
        loadPresetOptions()
    }

    private fun loadPresetOptions() {
        viewModelScope.launch {
            _ipOptions.value = presetOptionRepository.getOptionValues("ip_name")
            _producerOptions.value = presetOptionRepository.getOptionValues("producer")
            _originPlaceOptions.value = presetOptionRepository.getOptionValues("origin_places")
            _saleFormatOptions.value = presetOptionRepository.getOptionValues("sale_format")
            _sellMethodOptions.value = presetOptionRepository.getOptionValues("sell_method")
            _categoryOptions.value = presetOptionRepository.getOptionValues("category")
            _sizeOptions.value = presetOptionRepository.getOptionValues("sizes")
            _materialOptions.value = presetOptionRepository.getOptionValues("materials")
            _characterOptions.value = presetOptionRepository.getOptionValues("characters")
            _artTypeOptions.value = presetOptionRepository.getOptionValues("art_types")
        }
    }

    /**
     * 添加模式下预填 IP 名（从分组页进入时）
     */
    fun setDefaultIpName(ipName: String) {
        if (ipName.isBlank()) return
        val current = _formState.value
        if (!current.isEditMode && current.ipName.isBlank()) {
            _formState.value = current.copy(ipName = ipName)
        }
    }

    /**
     * 加载已有物品数据（编辑模式）
     */
    fun loadItem(itemId: String) {
        viewModelScope.launch {
            val item = itemRepository.getItemById(itemId) ?: return@launch
            val parsedPrice = CurrencyUtils.parsePrice(item.price)
            initialImagePaths = item.imagePaths.toSet()
            _formState.value = ItemFormState(
                itemId = item.id,
                nameCn = item.nameCn,
                nameEn = item.nameEn ?: "",
                ipName = item.ipName,
                producer = item.producer ?: "",
                originPlaces = item.originPlaces,
                saleFormat = item.saleFormat,
                sellMethod = item.sellMethod,
                price = parsedPrice.amount,
                currency = parsedPrice.currencyCode ?: CurrencyUtils.DEFAULT_CURRENCY,
                releaseDate = item.releaseDate ?: "",
                category = item.category ?: "",
                sizes = item.sizes,
                materials = item.materials,
                characters = item.characters,
                artTypes = item.artTypes,
                status = item.status,
                damageDetails = item.damageDetails,
                version = item.version ?: "",
                quantity = item.quantity.toString(),
                note = item.note ?: "",
                imagePaths = item.imagePaths,
                isEditMode = true
            )
        }
    }

    /**
     * 加载已有物品数据作为副本（复制后编辑）
     * 复制所有字段和图片，但使用新 ID，以新建模式保存
     */
    fun loadItemAsCopy(sourceItemId: String) {
        viewModelScope.launch {
            val item = itemRepository.getItemById(sourceItemId) ?: return@launch
            val newId = IdGenerator.generate()

            // 复制图片到新 ID 的目录
            val newImagePaths = item.imagePaths.mapNotNull { path ->
                val sourceFile = imageFileManager.getFile(path)
                if (sourceFile.exists()) {
                    imageFileManager.copyImageFile(newId, sourceFile)
                } else null
            }

            val parsedPrice = CurrencyUtils.parsePrice(item.price)
            initialImagePaths = emptySet()

            _formState.value = ItemFormState(
                itemId = newId,
                nameCn = item.nameCn,
                nameEn = item.nameEn ?: "",
                ipName = item.ipName,
                producer = item.producer ?: "",
                originPlaces = item.originPlaces,
                saleFormat = item.saleFormat,
                sellMethod = item.sellMethod,
                price = parsedPrice.amount,
                currency = parsedPrice.currencyCode ?: CurrencyUtils.DEFAULT_CURRENCY,
                releaseDate = item.releaseDate ?: "",
                category = item.category ?: "",
                sizes = item.sizes,
                materials = item.materials,
                characters = item.characters,
                artTypes = item.artTypes,
                status = item.status,
                damageDetails = item.damageDetails,
                version = item.version ?: "",
                quantity = item.quantity.toString(),
                note = item.note ?: "",
                imagePaths = newImagePaths,
                isEditMode = false
            )
        }
    }

    // ===== 表单更新方法 =====

    fun updateNameCn(value: String) {
        _formState.value = _formState.value.copy(nameCn = value)
    }

    fun updateNameEn(value: String) {
        _formState.value = _formState.value.copy(nameEn = value)
    }

    fun updateIpName(value: String) {
        _formState.value = _formState.value.copy(ipName = value)
    }

    fun updateProducer(value: String) {
        _formState.value = _formState.value.copy(producer = value)
    }

    fun updateOriginPlaces(value: List<String>) {
        _formState.value = _formState.value.copy(originPlaces = value)
    }

    fun updateSaleFormat(value: List<String>) {
        _formState.value = _formState.value.copy(saleFormat = value)
    }

    fun updateSellMethod(value: String) {
        _formState.value = _formState.value.copy(sellMethod = value)
    }

    fun updatePrice(value: String) {
        _formState.value = _formState.value.copy(price = value)
    }

    fun updateCurrency(value: String) {
        _formState.value = _formState.value.copy(currency = value)
    }

    fun updateReleaseDate(value: String) {
        _formState.value = _formState.value.copy(releaseDate = value)
    }

    fun updateCategory(value: String) {
        _formState.value = _formState.value.copy(category = value)
    }

    fun updateSizes(value: List<String>) {
        _formState.value = _formState.value.copy(sizes = value)
    }

    fun updateMaterials(value: List<String>) {
        _formState.value = _formState.value.copy(materials = value)
    }

    fun updateCharacters(value: List<String>) {
        _formState.value = _formState.value.copy(characters = value)
    }

    fun updateArtTypes(value: List<String>) {
        _formState.value = _formState.value.copy(artTypes = value)
    }

    fun updateStatus(value: String) {
        _formState.value = if (value == "无伤") {
            _formState.value.copy(status = value, damageDetails = emptyList())
        } else {
            _formState.value.copy(status = value)
        }
    }

    fun updateDamageDetails(value: List<String>) {
        _formState.value = _formState.value.copy(damageDetails = value)
    }

    fun updateVersion(value: String) {
        _formState.value = _formState.value.copy(version = value)
    }

    fun updateQuantity(value: String) {
        _formState.value = _formState.value.copy(quantity = value)
    }

    fun updateNote(value: String) {
        _formState.value = _formState.value.copy(note = value)
    }

    /**
     * 批量添加图片
     *
     * @param uris 图片 URI 列表
     * @param compress true=压缩存储，false=存储原图
     */
    fun addImages(uris: List<Uri>, compress: Boolean) {
        val itemId = _formState.value.itemId
        val paths = imageFileManager.copyImages(itemId, uris, compress)
        if (paths.isNotEmpty()) {
            _formState.value = _formState.value.copy(
                imagePaths = _formState.value.imagePaths + paths
            )
        }
    }

    /**
     * 移除图片
     *
     * 数据库已引用的旧图仅标记待删除，保存成功后才删除物理文件（不保存则不生效）；
     * 本次会话新增的图片数据库尚未引用，直接删除物理文件
     */
    fun removeImage(path: String) {
        val state = _formState.value
        if (path in initialImagePaths) {
            _formState.value = state.copy(
                imagePaths = state.imagePaths - path,
                pendingDeleteImagePaths = state.pendingDeleteImagePaths + path
            )
        } else {
            imageFileManager.deleteImage(path)
            _formState.value = state.copy(imagePaths = state.imagePaths - path)
        }
    }

    /**
     * 调整图片顺序（编辑时长按拖拽排序）
     */
    fun moveImage(fromIndex: Int, toIndex: Int) {
        val paths = _formState.value.imagePaths.toMutableList()
        if (fromIndex !in paths.indices || toIndex !in paths.indices || fromIndex == toIndex) return
        paths.add(toIndex, paths.removeAt(fromIndex))
        _formState.value = _formState.value.copy(imagePaths = paths)
    }

    /**
     * 添加新预设选项（已不再使用，保留空实现防止引用报错）
     */
    @Deprecated("新选项已在 save() 成功后统一保存")
    fun addNewPresetOption(attributeName: String, value: String) = Unit

    // ===== 校验 =====

    fun validate(): Boolean {
        val state = _formState.value
        val errors = mutableMapOf<String, String>()

        if (state.nameCn.isBlank()) errors["nameCn"] = "中文名称不能为空"
        if (state.ipName.isBlank()) errors["ipName"] = "IP名不能为空"
        if (state.saleFormat.isEmpty()) errors["saleFormat"] = "发售形式不能为空"
        if (state.characters.isEmpty()) errors["characters"] = "相关角色不能为空"
        if (state.artTypes.isEmpty()) errors["artTypes"] = "柄图种类不能为空"

        // 价格校验
        if (state.price.isNotBlank()) {
            state.price.toDoubleOrNull() ?: run {
                errors["price"] = "价格必须为数字"
            }
        }

        // 数量校验
        state.quantity.toIntOrNull()?.let { qty ->
            if (qty < 1) errors["quantity"] = "数量至少为1"
        } ?: run {
            errors["quantity"] = "数量必须为整数"
        }

        _formState.value = state.copy(validationErrors = errors)
        return errors.isEmpty()
    }

    // ===== 保存 =====

    fun save() {
        if (!validate()) return

        viewModelScope.launch {
            try {
                val state = _formState.value
                val item = Item(
                    id = state.itemId,
                    nameCn = state.nameCn,
                    nameEn = state.nameEn.ifBlank { null },
                    ipName = state.ipName,
                    producer = state.producer.ifBlank { null },
                    sellMethod = state.sellMethod,
                    price = CurrencyUtils.buildPriceString(state.price, state.currency),
                    releaseDate = DateUtils.normalizeYearMonth(state.releaseDate),
                    category = state.category.ifBlank { null },
                    version = state.version.ifBlank { "初贩" },
                    status = state.status,
                    quantity = state.quantity.toIntOrNull() ?: 1,
                    note = state.note.ifBlank { null },
                    originPlaces = state.originPlaces,
                    saleFormat = state.saleFormat,
                    sizes = state.sizes,
                    materials = state.materials,
                    characters = state.characters,
                    artTypes = state.artTypes,
                    damageDetails = state.damageDetails,
                    imagePaths = state.imagePaths
                )

                if (state.isEditMode) {
                    itemRepository.updateItem(item)
                } else {
                    itemRepository.addItem(item)
                }

                // 保存成功后，删除标记为待删除的旧图物理文件
                state.pendingDeleteImagePaths.forEach { imageFileManager.deleteImage(it) }
                _formState.value = _formState.value.copy(pendingDeleteImagePaths = emptyList())

                // 保存成功后，如果开启了自动添加预设，则将尚未在预设库中的新值写入
                if (userPreferencesRepository.isAutoSavePresetsEnabled()) {
                    saveNewPresetsFromState(state)
                }

                _saveResult.value = SaveResult.Success
            } catch (e: Exception) {
                _saveResult.value = SaveResult.Error(e.message ?: "保存失败")
            }
        }
    }

    fun clearSaveResult() {
        _saveResult.value = null
    }

    /**
     * 保存成功后，把表单中尚未在预设库里的新值写入
     */
    private suspend fun saveNewPresetsFromState(state: ItemFormState) {
        fun List<String>.newItems(existing: List<String>) = filter { it.isNotBlank() && it !in existing }
        fun String.newItem(existing: List<String>) = if (isNotBlank() && this !in existing) this else null

        state.ipName.newItem(_ipOptions.value)?.let { presetOptionRepository.addOption("ip_name", it) }
        state.producer.newItem(_producerOptions.value)?.let { presetOptionRepository.addOption("producer", it) }
        state.sellMethod.newItem(_sellMethodOptions.value)?.let { presetOptionRepository.addOption("sell_method", it) }
        state.category.newItem(_categoryOptions.value)?.let { presetOptionRepository.addOption("category", it) }
        state.originPlaces.newItems(_originPlaceOptions.value).forEach { presetOptionRepository.addOption("origin_places", it) }
        state.saleFormat.newItems(_saleFormatOptions.value).forEach { presetOptionRepository.addOption("sale_format", it) }
        state.sizes.newItems(_sizeOptions.value).forEach { presetOptionRepository.addOption("sizes", it) }
        state.materials.newItems(_materialOptions.value).forEach { presetOptionRepository.addOption("materials", it) }
        state.characters.newItems(_characterOptions.value).forEach { presetOptionRepository.addOption("characters", it) }
        state.artTypes.newItems(_artTypeOptions.value).forEach { presetOptionRepository.addOption("art_types", it) }

        // 写入后刷新预设列表
        loadPresetOptions()
    }
}

/**
 * 表单状态
 */
data class ItemFormState(
    val itemId: String = IdGenerator.generate(),
    val nameCn: String = "",
    val nameEn: String = "",
    val ipName: String = "",
    val producer: String = "",
    val originPlaces: List<String> = emptyList(),
    val saleFormat: List<String> = emptyList(),
    val sellMethod: String = "",
    val price: String = "",
    val currency: String = CurrencyUtils.DEFAULT_CURRENCY,
    val releaseDate: String = "",
    val category: String = "",
    val sizes: List<String> = emptyList(),
    val materials: List<String> = emptyList(),
    val characters: List<String> = emptyList(),
    val artTypes: List<String> = emptyList(),
    val status: String = "无伤",
    val damageDetails: List<String> = emptyList(),
    val version: String = "初贩",
    val quantity: String = "1",
    val note: String = "",
    val imagePaths: List<String> = emptyList(),
    val pendingDeleteImagePaths: List<String> = emptyList(),
    val isEditMode: Boolean = false,
    val validationErrors: Map<String, String> = emptyMap()
)

/**
 * 保存结果
 */
sealed class SaveResult {
    data object Success : SaveResult()
    data class Error(val message: String) : SaveResult()
}
