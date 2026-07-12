package com.guzi.storage.ui.group;

import androidx.lifecycle.ViewModel;
import com.guzi.storage.data.repository.ItemRepository;
import com.guzi.storage.data.repository.PresetOptionRepository;
import com.guzi.storage.data.repository.UserPreferencesRepository;
import com.guzi.storage.domain.model.Item;
import com.guzi.storage.util.ItemAttributeUtils;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.ExperimentalCoroutinesApi;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

/**
 * 分组页 ViewModel - 支持多级分组导航、搜索、筛选和批量选择
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\"\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010%\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0010\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0006\u00105\u001a\u000206J\u0006\u00107\u001a\u000206J\u0006\u00108\u001a\u000206J\u0006\u00109\u001a\u000206J\u001a\u0010:\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\r0\u00162\u0006\u0010;\u001a\u00020\u000fJ\u0006\u0010<\u001a\u000206J\u0006\u0010=\u001a\u000206J\u000e\u0010>\u001a\u0002062\u0006\u0010?\u001a\u00020\u000bJ \u0010@\u001a\u0002062\u0018\u0010A\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u000f0\u000e0\rJ\u000e\u0010B\u001a\u0002062\u0006\u0010C\u001a\u00020\u000fJ\u000e\u0010D\u001a\u0002062\u0006\u0010E\u001a\u00020\u000fR\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R&\u0010\f\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u000f0\u000e0\r0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000f0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u00120\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0019\u0010\u0019\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0018R\u0017\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0018R&\u0010\u001d\u001a\u001a\u0012\u0004\u0012\u00020\u000f\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\r0\u00160\u001eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u001f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\r0\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0018R\u0017\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00140\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0018R\u0017\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00140\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0018R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R#\u0010#\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020$0\r0\u0016\u00a2\u0006\u000e\n\u0000\u0012\u0004\b%\u0010&\u001a\u0004\b\'\u0010\u0018R \u0010(\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020$0\r0)X\u0082\u0004\u00a2\u0006\b\n\u0000\u0012\u0004\b*\u0010&R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010+\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010\u0018R\u001d\u0010-\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u00120\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b.\u0010\u0018R\u0017\u0010/\u001a\b\u0012\u0004\u0012\u00020\u00140\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b0\u0010\u0018R#\u00101\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002020\r0\u0016\u00a2\u0006\u000e\n\u0000\u0012\u0004\b3\u0010&\u001a\u0004\b4\u0010\u0018R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006F"}, d2 = {"Lcom/guzi/storage/ui/group/GroupViewModel;", "Landroidx/lifecycle/ViewModel;", "itemRepository", "Lcom/guzi/storage/data/repository/ItemRepository;", "presetOptionRepository", "Lcom/guzi/storage/data/repository/PresetOptionRepository;", "userPreferencesRepository", "Lcom/guzi/storage/data/repository/UserPreferencesRepository;", "(Lcom/guzi/storage/data/repository/ItemRepository;Lcom/guzi/storage/data/repository/PresetOptionRepository;Lcom/guzi/storage/data/repository/UserPreferencesRepository;)V", "_filterConfig", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/guzi/storage/ui/group/FilterConfig;", "_path", "", "Lkotlin/Pair;", "", "_searchQuery", "_selectedIds", "", "_showBatchDeleteDialog", "", "breadcrumb", "Lkotlinx/coroutines/flow/StateFlow;", "getBreadcrumb", "()Lkotlinx/coroutines/flow/StateFlow;", "currentLevelAttr", "getCurrentLevelAttr", "filterConfig", "getFilterConfig", "filterOptionsCache", "", "groupingConfig", "getGroupingConfig", "isFinalLevel", "isSelectionMode", "items", "Lcom/guzi/storage/domain/model/Item;", "getItems$annotations", "()V", "getItems", "pathFilteredItems", "Lkotlinx/coroutines/flow/Flow;", "getPathFilteredItems$annotations", "searchQuery", "getSearchQuery", "selectedIds", "getSelectedIds", "showBatchDeleteDialog", "getShowBatchDeleteDialog", "subGroups", "Lcom/guzi/storage/ui/group/SubGroupDisplay;", "getSubGroups$annotations", "getSubGroups", "clearFilter", "", "clearSelection", "confirmBatchDelete", "dismissBatchDelete", "getFilterOptions", "attr", "requestBatchDelete", "selectAll", "setFilterConfig", "config", "setPath", "path", "toggleSelection", "itemId", "updateSearchQuery", "query", "app_debug"})
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
    private final kotlinx.coroutines.flow.MutableStateFlow<com.guzi.storage.ui.group.FilterConfig> _filterConfig = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.guzi.storage.ui.group.FilterConfig> filterConfig = null;
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
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.guzi.storage.ui.group.FilterConfig> getFilterConfig() {
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
    
    @kotlin.OptIn(markerClass = {kotlinx.coroutines.ExperimentalCoroutinesApi.class})
    @java.lang.Deprecated()
    public static void getSubGroups$annotations() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.guzi.storage.domain.model.Item>> getItems() {
        return null;
    }
    
    @kotlin.OptIn(markerClass = {kotlinx.coroutines.ExperimentalCoroutinesApi.class})
    @java.lang.Deprecated()
    public static void getItems$annotations() {
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
    
    public final void setPath(@org.jetbrains.annotations.NotNull()
    java.util.List<kotlin.Pair<java.lang.String, java.lang.String>> path) {
    }
    
    public final void updateSearchQuery(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
    }
    
    public final void setFilterConfig(@org.jetbrains.annotations.NotNull()
    com.guzi.storage.ui.group.FilterConfig config) {
    }
    
    public final void clearFilter() {
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
}