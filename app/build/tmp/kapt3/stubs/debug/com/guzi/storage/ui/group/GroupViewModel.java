package com.guzi.storage.ui.group;

import androidx.lifecycle.ViewModel;
import com.guzi.storage.data.repository.ItemRepository;
import com.guzi.storage.data.repository.PresetOptionRepository;
import com.guzi.storage.data.repository.UserPreferencesRepository;
import com.guzi.storage.domain.model.Item;
import com.guzi.storage.util.ItemAttributeUtils;
import com.guzi.storage.ui.home.HomeSortOption;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.ExperimentalCoroutinesApi;
import kotlinx.coroutines.FlowPreview;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

/**
 * 分组页 ViewModel - 支持多级分组导航、搜索、筛选和批量选择
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0084\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u001d\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0006\u0010J\u001a\u00020KJ\u0006\u0010L\u001a\u00020KJ\u0006\u0010M\u001a\u00020KJ\u0006\u0010N\u001a\u00020KJ\u0006\u0010O\u001a\u00020KJ\u0006\u0010P\u001a\u00020KJ\u0006\u0010Q\u001a\u00020KJ\u0006\u0010R\u001a\u00020KJ\u001a\u0010S\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\r0\u001d2\u0006\u0010T\u001a\u00020\u000fJ\u0006\u0010U\u001a\u00020KJ\u0006\u0010V\u001a\u00020KJ\u0006\u0010W\u001a\u00020KJ\u0006\u0010X\u001a\u00020KJ\u000e\u0010Y\u001a\u00020K2\u0006\u0010Z\u001a\u00020\u000bJ \u0010[\u001a\u00020K2\u0018\u0010\\\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u000f0\u000e0\rJ\u000e\u0010]\u001a\u00020K2\u0006\u0010Z\u001a\u00020\u0019J\u000e\u0010^\u001a\u00020K2\u0006\u0010_\u001a\u00020\u001bJ\u000e\u0010`\u001a\u00020K2\u0006\u0010a\u001a\u00020\u000fJ\u000e\u0010b\u001a\u00020K2\u0006\u0010c\u001a\u00020\u000fJ\u000e\u0010d\u001a\u00020K2\u0006\u0010e\u001a\u00020\u000fJ\u000e\u0010f\u001a\u00020K2\u0006\u0010g\u001a\u00020\u000fR\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R&\u0010\f\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u000f0\u000e0\r0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000f0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000f0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u00130\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u00130\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00160\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u000f0\u001d\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0019\u0010 \u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\u001d\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001fR\u001a\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u000f0#X\u0082\u0004\u00a2\u0006\b\n\u0000\u0012\u0004\b$\u0010%R\u0017\u0010&\u001a\b\u0012\u0004\u0012\u00020\u000b0\u001d\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010\u001fR&\u0010(\u001a\u001a\u0012\u0004\u0012\u00020\u000f\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\r0\u001d0)X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010*\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\r0\u001d\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010\u001fR\u0017\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00160\u001d\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010\u001fR\u0017\u0010-\u001a\b\u0012\u0004\u0012\u00020\u00160\u001d\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010\u001fR\u0017\u0010.\u001a\b\u0012\u0004\u0012\u00020\u00160\u001d\u00a2\u0006\b\n\u0000\u001a\u0004\b.\u0010\u001fR\u0017\u0010/\u001a\b\u0012\u0004\u0012\u00020\u00160\u001d\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u0010\u001fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R#\u00100\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002010\r0\u001d\u00a2\u0006\u000e\n\u0000\u0012\u0004\b2\u0010%\u001a\u0004\b3\u0010\u001fR \u00104\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002010\r0#X\u0082\u0004\u00a2\u0006\b\n\u0000\u0012\u0004\b5\u0010%R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u00106\u001a\b\u0012\u0004\u0012\u00020\u000f0\u001d\u00a2\u0006\b\n\u0000\u001a\u0004\b7\u0010\u001fR\u0017\u00108\u001a\b\u0012\u0004\u0012\u00020\u000f0\u001d\u00a2\u0006\b\n\u0000\u001a\u0004\b9\u0010\u001fR\u001d\u0010:\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u00130\u001d\u00a2\u0006\b\n\u0000\u001a\u0004\b;\u0010\u001fR\u001d\u0010<\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u00130\u001d\u00a2\u0006\b\n\u0000\u001a\u0004\b=\u0010\u001fR\u0017\u0010>\u001a\b\u0012\u0004\u0012\u00020\u00160\u001d\u00a2\u0006\b\n\u0000\u001a\u0004\b?\u0010\u001fR\u0017\u0010@\u001a\b\u0012\u0004\u0012\u00020\u00160\u001d\u00a2\u0006\b\n\u0000\u001a\u0004\bA\u0010\u001fR\u0017\u0010B\u001a\b\u0012\u0004\u0012\u00020\u00190\u001d\u00a2\u0006\b\n\u0000\u001a\u0004\bC\u0010\u001fR\u0017\u0010D\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001d\u00a2\u0006\b\n\u0000\u001a\u0004\bE\u0010\u001fR#\u0010F\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020G0\r0\u001d\u00a2\u0006\u000e\n\u0000\u0012\u0004\bH\u0010%\u001a\u0004\bI\u0010\u001fR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006h"}, d2 = {"Lcom/guzi/storage/ui/group/GroupViewModel;", "Landroidx/lifecycle/ViewModel;", "itemRepository", "Lcom/guzi/storage/data/repository/ItemRepository;", "presetOptionRepository", "Lcom/guzi/storage/data/repository/PresetOptionRepository;", "userPreferencesRepository", "Lcom/guzi/storage/data/repository/UserPreferencesRepository;", "(Lcom/guzi/storage/data/repository/ItemRepository;Lcom/guzi/storage/data/repository/PresetOptionRepository;Lcom/guzi/storage/data/repository/UserPreferencesRepository;)V", "_filterConfig", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/guzi/storage/ui/group/FilterConfig;", "_path", "", "Lkotlin/Pair;", "", "_searchField", "_searchQuery", "_selectedGroups", "", "_selectedIds", "_showBatchDeleteDialog", "", "_showGroupBatchDeleteDialog", "_sortConfig", "Lcom/guzi/storage/ui/group/SortConfig;", "_subGroupSortOption", "Lcom/guzi/storage/ui/home/HomeSortOption;", "breadcrumb", "Lkotlinx/coroutines/flow/StateFlow;", "getBreadcrumb", "()Lkotlinx/coroutines/flow/StateFlow;", "currentLevelAttr", "getCurrentLevelAttr", "debouncedSearchQuery", "Lkotlinx/coroutines/flow/Flow;", "getDebouncedSearchQuery$annotations", "()V", "filterConfig", "getFilterConfig", "filterOptionsCache", "", "groupingConfig", "getGroupingConfig", "isFinalLevel", "isGroupSelectionMode", "isSelectionMode", "isShowingItems", "items", "Lcom/guzi/storage/domain/model/Item;", "getItems$annotations", "getItems", "pathFilteredItems", "getPathFilteredItems$annotations", "searchField", "getSearchField", "searchQuery", "getSearchQuery", "selectedGroups", "getSelectedGroups", "selectedIds", "getSelectedIds", "showBatchDeleteDialog", "getShowBatchDeleteDialog", "showGroupBatchDeleteDialog", "getShowGroupBatchDeleteDialog", "sortConfig", "getSortConfig", "subGroupSortOption", "getSubGroupSortOption", "subGroups", "Lcom/guzi/storage/ui/group/SubGroupDisplay;", "getSubGroups$annotations", "getSubGroups", "clearFilter", "", "clearGroupSelection", "clearSelection", "clearSort", "confirmBatchDelete", "confirmGroupBatchDelete", "dismissBatchDelete", "dismissGroupBatchDelete", "getFilterOptions", "attr", "requestBatchDelete", "requestGroupBatchDelete", "selectAll", "selectAllGroups", "setFilterConfig", "config", "setPath", "path", "setSortConfig", "setSubGroupSortOption", "option", "toggleGroupSelection", "attrValue", "toggleSelection", "itemId", "updateSearchField", "field", "updateSearchQuery", "query", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class GroupViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.guzi.storage.data.repository.ItemRepository itemRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.guzi.storage.data.repository.PresetOptionRepository presetOptionRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.guzi.storage.data.repository.UserPreferencesRepository userPreferencesRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<kotlin.Pair<java.lang.String, java.lang.String>>> _path = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _searchQuery = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> searchQuery = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.String> debouncedSearchQuery = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _searchField = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> searchField = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.guzi.storage.ui.group.FilterConfig> _filterConfig = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.guzi.storage.ui.group.FilterConfig> filterConfig = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.guzi.storage.ui.group.SortConfig> _sortConfig = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.guzi.storage.ui.group.SortConfig> sortConfig = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.guzi.storage.ui.home.HomeSortOption> _subGroupSortOption = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.guzi.storage.ui.home.HomeSortOption> subGroupSortOption = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<java.lang.String>> groupingConfig = null;
    
    /**
     * 是否为最终级（显示物品列表而非子分组）
     */
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isFinalLevel = null;
    
    /**
     * 当前层级属性名（仅在非最终级时有效）
     */
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> currentLevelAttr = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.guzi.storage.domain.model.Item>> pathFilteredItems = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.guzi.storage.ui.group.SubGroupDisplay>> subGroups = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.guzi.storage.domain.model.Item>> items = null;
    
    /**
     * 当中间层级且搜索字段不为"分组名"且搜索文本非空时，切换为物品列表视图
     */
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isShowingItems = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> breadcrumb = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.Set<java.lang.String>> _selectedIds = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.Set<java.lang.String>> selectedIds = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isSelectionMode = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _showBatchDeleteDialog = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> showBatchDeleteDialog = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.Set<java.lang.String>> _selectedGroups = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.Set<java.lang.String>> selectedGroups = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isGroupSelectionMode = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _showGroupBatchDeleteDialog = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> showGroupBatchDeleteDialog = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<java.lang.String, kotlinx.coroutines.flow.StateFlow<java.util.List<java.lang.String>>> filterOptionsCache = null;
    
    @javax.inject.Inject()
    public GroupViewModel(@org.jetbrains.annotations.NotNull()
    com.guzi.storage.data.repository.ItemRepository itemRepository, @org.jetbrains.annotations.NotNull()
    com.guzi.storage.data.repository.PresetOptionRepository presetOptionRepository, @org.jetbrains.annotations.NotNull()
    com.guzi.storage.data.repository.UserPreferencesRepository userPreferencesRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getSearchQuery() {
        return null;
    }
    
    @kotlin.OptIn(markerClass = {kotlinx.coroutines.FlowPreview.class})
    @java.lang.Deprecated()
    private static void getDebouncedSearchQuery$annotations() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getSearchField() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.guzi.storage.ui.group.FilterConfig> getFilterConfig() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.guzi.storage.ui.group.SortConfig> getSortConfig() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.guzi.storage.ui.home.HomeSortOption> getSubGroupSortOption() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<java.lang.String>> getGroupingConfig() {
        return null;
    }
    
    /**
     * 是否为最终级（显示物品列表而非子分组）
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isFinalLevel() {
        return null;
    }
    
    /**
     * 当前层级属性名（仅在非最终级时有效）
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getCurrentLevelAttr() {
        return null;
    }
    
    @kotlin.OptIn(markerClass = {kotlinx.coroutines.ExperimentalCoroutinesApi.class})
    @java.lang.Deprecated()
    private static void getPathFilteredItems$annotations() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.guzi.storage.ui.group.SubGroupDisplay>> getSubGroups() {
        return null;
    }
    
    @kotlin.OptIn(markerClass = {kotlinx.coroutines.ExperimentalCoroutinesApi.class, kotlinx.coroutines.FlowPreview.class})
    @java.lang.Deprecated()
    public static void getSubGroups$annotations() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.guzi.storage.domain.model.Item>> getItems() {
        return null;
    }
    
    @kotlin.OptIn(markerClass = {kotlinx.coroutines.ExperimentalCoroutinesApi.class, kotlinx.coroutines.FlowPreview.class})
    @java.lang.Deprecated()
    public static void getItems$annotations() {
    }
    
    /**
     * 当中间层级且搜索字段不为"分组名"且搜索文本非空时，切换为物品列表视图
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isShowingItems() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getBreadcrumb() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.Set<java.lang.String>> getSelectedIds() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isSelectionMode() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> getShowBatchDeleteDialog() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.Set<java.lang.String>> getSelectedGroups() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isGroupSelectionMode() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> getShowGroupBatchDeleteDialog() {
        return null;
    }
    
    public final void setPath(@org.jetbrains.annotations.NotNull()
    java.util.List<kotlin.Pair<java.lang.String, java.lang.String>> path) {
    }
    
    public final void updateSearchQuery(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
    }
    
    public final void updateSearchField(@org.jetbrains.annotations.NotNull()
    java.lang.String field) {
    }
    
    public final void setFilterConfig(@org.jetbrains.annotations.NotNull()
    com.guzi.storage.ui.group.FilterConfig config) {
    }
    
    public final void clearFilter() {
    }
    
    public final void setSortConfig(@org.jetbrains.annotations.NotNull()
    com.guzi.storage.ui.group.SortConfig config) {
    }
    
    public final void setSubGroupSortOption(@org.jetbrains.annotations.NotNull()
    com.guzi.storage.ui.home.HomeSortOption option) {
    }
    
    public final void clearSort() {
    }
    
    /**
     * 获取指定属性的筛选选项（带缓存）
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<java.lang.String>> getFilterOptions(@org.jetbrains.annotations.NotNull()
    java.lang.String attr) {
        return null;
    }
    
    public final void toggleSelection(@org.jetbrains.annotations.NotNull()
    java.lang.String itemId) {
    }
    
    public final void selectAll() {
    }
    
    public final void clearSelection() {
    }
    
    public final void requestBatchDelete() {
    }
    
    public final void dismissBatchDelete() {
    }
    
    public final void confirmBatchDelete() {
    }
    
    public final void toggleGroupSelection(@org.jetbrains.annotations.NotNull()
    java.lang.String attrValue) {
    }
    
    public final void selectAllGroups() {
    }
    
    public final void clearGroupSelection() {
    }
    
    public final void requestGroupBatchDelete() {
    }
    
    public final void dismissGroupBatchDelete() {
    }
    
    /**
     * 删除选中的分组下的所有物品
     *
     * 选中一个组（attrValue）即删除「当前 path + 该组属性值」路径下的所有物品，
     * 与点击进入该组看到的物品列表一致。多值属性物品只要属于任一选中组即被删除。
     */
    public final void confirmGroupBatchDelete() {
    }
}