package com.guzi.storage.ui.group

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guzi.storage.data.repository.ItemRepository
import com.guzi.storage.data.repository.PresetOptionRepository
import com.guzi.storage.data.repository.UserPreferencesRepository
import com.guzi.storage.domain.model.Item
import com.guzi.storage.util.ItemAttributeUtils
import com.guzi.storage.ui.SEARCH_FILTER_ATTRIBUTES
import com.guzi.storage.ui.SEARCH_FIELD_ALL
import com.guzi.storage.ui.home.HomeSortOption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.first
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
    @OptIn(FlowPreview::class)
    private val debouncedSearchQuery: Flow<String> = _searchQuery.debounce(300)

    private val _searchField = MutableStateFlow(SEARCH_FIELD_ALL)
    val searchField: StateFlow<String> = _searchField.asStateFlow()

    private val _filterConfig = MutableStateFlow(FilterConfig())
    val filterConfig: StateFlow<FilterConfig> = _filterConfig.asStateFlow()

    private val _sortConfig = MutableStateFlow(SortConfig())
    val sortConfig: StateFlow<SortConfig> = _sortConfig.asStateFlow()

    // 子分组排序选项（中间层级卡片排序，与 HomeScreen 一致）
    private val _subGroupSortOption = MutableStateFlow(HomeSortOption.DEFAULT)
    val subGroupSortOption: StateFlow<HomeSortOption> = _subGroupSortOption.asStateFlow()

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

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val subGroups: StateFlow<List<SubGroupDisplay>> =
        combine(pathFilteredItems, _path, groupingConfig, _subGroupSortOption,
            combine(debouncedSearchQuery, _searchField, _filterConfig) { query, field, filter -> Triple(query, field, filter) }
        ) { items, path, config, sortOpt, searchTriple ->
            val (query, field, filter) = searchTriple
            val depth = path.size
            val attr = config.getOrNull(depth) ?: return@combine emptyList()
            if (depth >= config.size) return@combine emptyList()

            // 按筛选条件过滤物品后再分组
            val filteredItems = items.filter { item ->
                filter.matchesAll(item) { i, a -> ItemAttributeUtils.getValues(i, a) }
            }

            filteredItems.flatMap { item ->
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
                // 仅当搜索字段为"分组名"时按分组名过滤
                .filter { query.isBlank() || (field == "分组名" && it.attrValue.contains(query, ignoreCase = true)) }
                .let { sortOpt.apply(it, { it.attrValue }, { it.count }) }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // ===== 最终级物品列表（带搜索+筛选+排序） =====

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val items: StateFlow<List<Item>> =
        combine(pathFilteredItems, debouncedSearchQuery, _searchField, _filterConfig, _sortConfig) { items, query, field, filter, sort ->
            val filtered = items.filter { item ->
                val matchesSearch = when {
                    query.isBlank() -> true
                    field == "all" -> SEARCH_FILTER_ATTRIBUTES.any { (a, _) ->
                        ItemAttributeUtils.getValues(item, a).any { it.contains(query, ignoreCase = true) }
                    }
                    field == "分组名" -> true // 分组名不适用于物品搜索
                    else -> ItemAttributeUtils.getValues(item, field).any { it.contains(query, ignoreCase = true) }
                }
                val matchesFilter = filter.matchesAll(item) { i, attr ->
                    ItemAttributeUtils.getValues(i, attr)
                }
                matchesSearch && matchesFilter
            }
            sort.apply(filtered)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // ===== 中间层级是否切换为物品视图 =====

    /**
     * 当中间层级且搜索字段不为"分组名"且搜索文本非空时，切换为物品列表视图
     */
    val isShowingItems: StateFlow<Boolean> =
        combine(isFinalLevel, _searchField, _searchQuery) { final, field, query ->
            !final && field != "分组名" && query.isNotBlank()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
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

    // ===== 分组级批量选择状态（中间层级，按子分组 attrValue 标识） =====

    private val _selectedGroups = MutableStateFlow<Set<String>>(emptySet())
    val selectedGroups: StateFlow<Set<String>> = _selectedGroups.asStateFlow()

    val isGroupSelectionMode: StateFlow<Boolean> = _selectedGroups
        .map { it.isNotEmpty() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    private val _showGroupBatchDeleteDialog = MutableStateFlow(false)
    val showGroupBatchDeleteDialog: StateFlow<Boolean> = _showGroupBatchDeleteDialog.asStateFlow()

    // ===== 公开方法 =====

    fun setPath(path: List<Pair<String, String>>) {
        _path.value = path
        _searchQuery.value = ""
        _searchField.value = SEARCH_FIELD_ALL
        _selectedGroups.value = emptySet()
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun updateSearchField(field: String) {
        _searchField.value = field
    }

    fun setFilterConfig(config: FilterConfig) {
        _filterConfig.value = config
    }

    fun clearFilter() {
        _filterConfig.value = FilterConfig()
    }

    fun setSortConfig(config: SortConfig) {
        _sortConfig.value = config
    }

    fun setSubGroupSortOption(option: HomeSortOption) {
        _subGroupSortOption.value = option
    }

    fun clearSort() {
        _sortConfig.value = SortConfig()
    }

    // ===== 筛选选项缓存 =====

    private val filterOptionsCache = mutableMapOf<String, StateFlow<List<String>>>()

    /**
     * 获取指定属性的筛选选项（带缓存）
     */
    fun getFilterOptions(attr: String): StateFlow<List<String>> {
        return filterOptionsCache.getOrPut(attr) {
            presetOptionRepository.getOptions(attr)
                .map { options ->
                    if (attr == "sizes") {
                        options.mapNotNull(ItemAttributeUtils::normalizeSizeForMatching).distinct()
                    } else {
                        options
                    }
                }
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

    // ===== 分组级选择/删除方法 =====

    fun toggleGroupSelection(attrValue: String) {
        val current = _selectedGroups.value.toMutableSet()
        if (current.contains(attrValue)) current.remove(attrValue) else current.add(attrValue)
        _selectedGroups.value = current
    }

    fun selectAllGroups() {
        _selectedGroups.value = subGroups.value.map { it.attrValue }.toSet()
    }

    fun clearGroupSelection() {
        _selectedGroups.value = emptySet()
    }

    fun requestGroupBatchDelete() {
        if (_selectedGroups.value.isNotEmpty()) {
            _showGroupBatchDeleteDialog.value = true
        }
    }

    fun dismissGroupBatchDelete() {
        _showGroupBatchDeleteDialog.value = false
    }

    /**
     * 删除选中的分组下的所有物品
     *
     * 选中一个组（attrValue）即删除「当前 path + 该组属性值」路径下的所有物品，
     * 与点击进入该组看到的物品列表一致。多值属性物品只要属于任一选中组即被删除。
     */
    fun confirmGroupBatchDelete() {
        viewModelScope.launch {
            val attr = currentLevelAttr.value ?: return@launch
            val selected = _selectedGroups.value
            if (selected.isEmpty()) return@launch
            val idsToDelete = pathFilteredItems.first()
                .filter { item ->
                    ItemAttributeUtils.getValues(item, attr).any { it in selected }
                }
                .map { it.id }
            itemRepository.deleteItems(idsToDelete)
            _selectedGroups.value = emptySet()
            _showGroupBatchDeleteDialog.value = false
        }
    }
}
