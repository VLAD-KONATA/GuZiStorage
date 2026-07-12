package com.guzi.storage.ui.group

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guzi.storage.data.repository.ItemRepository
import com.guzi.storage.data.repository.PresetOptionRepository
import com.guzi.storage.data.repository.UserPreferencesRepository
import com.guzi.storage.domain.model.Item
import com.guzi.storage.util.ItemAttributeUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 子分组展示数据（中间层级）
 */
data class SubGroupDisplay(
    val attrValue: String,
    val attrName: String,
    val count: Int,
    val thumbnails: List<String>
)

/**
 * 分组页 ViewModel - 支持多级分组导航、搜索、筛选和批量选择
 */
@HiltViewModel
class GroupViewModel @Inject constructor(
    private val itemRepository: ItemRepository,
    private val presetOptionRepository: PresetOptionRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _path = MutableStateFlow<List<Pair<String, String>>>(emptyList())
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _filterConfig = MutableStateFlow(FilterConfig())
    val filterConfig: StateFlow<FilterConfig> = _filterConfig.asStateFlow()

    val groupingConfig: StateFlow<List<String>> =
        userPreferencesRepository.groupingConfig
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = userPreferencesRepository.getGroupingConfig()
            )

    // ===== 当前层级信息 =====

    /**
     * 是否为最终级（显示物品列表而非子分组）
     */
    val isFinalLevel: StateFlow<Boolean> =
        combine(_path, groupingConfig) { path, config ->
            path.size >= config.size
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = true
        )

    /**
     * 当前层级属性名（仅在非最终级时有效）
     */
    val currentLevelAttr: StateFlow<String?> =
        combine(_path, groupingConfig) { path, config ->
            config.getOrNull(path.size)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    // ===== 按路径过滤物品 =====

    @OptIn(ExperimentalCoroutinesApi::class)
    private val pathFilteredItems: Flow<List<Item>> =
        combine(_path, itemRepository.getAllItems()) { path, items ->
            if (path.isEmpty()) {
                items
            } else {
                items.filter { item ->
                    path.all { (attr, value) ->
                        ItemAttributeUtils.getValues(item, attr).contains(value)
                    }
                }
            }
        }

    // ===== 子分组列表（中间层级） =====

    @OptIn(ExperimentalCoroutinesApi::class)
    val subGroups: StateFlow<List<SubGroupDisplay>> =
        combine(pathFilteredItems, _path, groupingConfig) { items, path, config ->
            val depth = path.size
            val attr = config.getOrNull(depth) ?: return@combine emptyList()
            if (depth >= config.size) return@combine emptyList()

            items.flatMap { item ->
                ItemAttributeUtils.getValues(item, attr).map { value -> value to item }
            }
                .groupBy { it.first }
                .map { (attrValue, pairs) ->
                    val groupItems = pairs.map { it.second }
                    SubGroupDisplay(
                        attrValue = attrValue,
                        attrName = attr,
                        count = groupItems.size,
                        thumbnails = groupItems.flatMap { it.imagePaths }.take(4)
                    )
                }
                .sortedBy { it.attrValue }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // ===== 最终级物品列表（带搜索+筛选） =====

    @OptIn(ExperimentalCoroutinesApi::class)
    val items: StateFlow<List<Item>> =
        combine(pathFilteredItems, _searchQuery, _filterConfig) { items, query, filter ->
            Triple(items, query, filter)
        }.map { (items, query, filter) ->
            items.filter { item ->
                val matchesSearch = query.isBlank() ||
                        item.nameCn.contains(query, ignoreCase = true) ||
                        item.displayName.contains(query, ignoreCase = true) ||
                        item.characters.any { it.contains(query, ignoreCase = true) } ||
                        item.category?.contains(query, ignoreCase = true) == true ||
                        item.ipName.contains(query, ignoreCase = true)
                val matchesFilter = filter.matchesAll(item) { i, attr ->
                    ItemAttributeUtils.getValues(i, attr)
                }
                matchesSearch && matchesFilter
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // ===== 面包屑标题 =====

    val breadcrumb: StateFlow<String> =
        _path.map { path ->
            if (path.isEmpty()) ""
            else path.joinToString(" / ") { it.second }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ""
        )

    // ===== 批量选择状态 =====

    private val _selectedIds = MutableStateFlow<Set<String>>(emptySet())
    val selectedIds: StateFlow<Set<String>> = _selectedIds.asStateFlow()

    val isSelectionMode: StateFlow<Boolean> = _selectedIds
        .map { ids -> ids.isNotEmpty() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    private val _showBatchDeleteDialog = MutableStateFlow(false)
    val showBatchDeleteDialog: StateFlow<Boolean> = _showBatchDeleteDialog.asStateFlow()

    // ===== 公开方法 =====

    fun setPath(path: List<Pair<String, String>>) {
        _path.value = path
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setFilterConfig(config: FilterConfig) {
        _filterConfig.value = config
    }

    fun clearFilter() {
        _filterConfig.value = FilterConfig()
    }

    // ===== 筛选选项缓存 =====

    private val filterOptionsCache = mutableMapOf<String, StateFlow<List<String>>>()

    /**
     * 获取指定属性的筛选选项（带缓存）
     */
    fun getFilterOptions(attr: String): StateFlow<List<String>> {
        return filterOptionsCache.getOrPut(attr) {
            presetOptionRepository.getOptions(attr)
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5000),
                    initialValue = emptyList()
                )
        }
    }

    fun toggleSelection(itemId: String) {
        val current = _selectedIds.value.toMutableSet()
        if (current.contains(itemId)) current.remove(itemId) else current.add(itemId)
        _selectedIds.value = current
    }

    fun selectAll() {
        _selectedIds.value = items.value.map { it.id }.toSet()
    }

    fun clearSelection() {
        _selectedIds.value = emptySet()
    }

    fun requestBatchDelete() {
        if (_selectedIds.value.isNotEmpty()) {
            _showBatchDeleteDialog.value = true
        }
    }

    fun dismissBatchDelete() {
        _showBatchDeleteDialog.value = false
    }

    fun confirmBatchDelete() {
        viewModelScope.launch {
            itemRepository.deleteItems(_selectedIds.value.toList())
            _selectedIds.value = emptySet()
            _showBatchDeleteDialog.value = false
        }
    }
}
