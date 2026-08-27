package com.guzi.storage.ui.home;

import androidx.lifecycle.ViewModel;
import com.guzi.storage.data.repository.ItemRepository;
import com.guzi.storage.data.repository.UserPreferencesRepository;
import com.guzi.storage.util.ItemAttributeUtils;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

/**
 * 主页 ViewModel - 根据用户配置的分组层级，按第一级属性分组展示
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\u0015\u001a\u00020\rJ\u0006\u0010\u0016\u001a\u00020\rJ\u000e\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\tR\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001d\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\f0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\t0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000fR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001a"}, d2 = {"Lcom/guzi/storage/ui/home/HomeViewModel;", "Landroidx/lifecycle/ViewModel;", "itemRepository", "Lcom/guzi/storage/data/repository/ItemRepository;", "userPreferencesRepository", "Lcom/guzi/storage/data/repository/UserPreferencesRepository;", "(Lcom/guzi/storage/data/repository/ItemRepository;Lcom/guzi/storage/data/repository/UserPreferencesRepository;)V", "_sortOption", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/guzi/storage/ui/home/HomeSortOption;", "groupingConfig", "Lkotlinx/coroutines/flow/StateFlow;", "", "", "getGroupingConfig", "()Lkotlinx/coroutines/flow/StateFlow;", "ipGroups", "Lcom/guzi/storage/ui/home/IpGroupDisplay;", "getIpGroups", "sortOption", "getSortOption", "getFirstLevelAttr", "getFirstLevelLabel", "setSortOption", "", "option", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class HomeViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.guzi.storage.data.repository.ItemRepository itemRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.guzi.storage.data.repository.UserPreferencesRepository userPreferencesRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.guzi.storage.ui.home.HomeSortOption> _sortOption = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.guzi.storage.ui.home.HomeSortOption> sortOption = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<java.lang.String>> groupingConfig = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.guzi.storage.ui.home.IpGroupDisplay>> ipGroups = null;
    
    @javax.inject.Inject()
    public HomeViewModel(@org.jetbrains.annotations.NotNull()
    com.guzi.storage.data.repository.ItemRepository itemRepository, @org.jetbrains.annotations.NotNull()
    com.guzi.storage.data.repository.UserPreferencesRepository userPreferencesRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.guzi.storage.ui.home.HomeSortOption> getSortOption() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<java.lang.String>> getGroupingConfig() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.guzi.storage.ui.home.IpGroupDisplay>> getIpGroups() {
        return null;
    }
    
    public final void setSortOption(@org.jetbrains.annotations.NotNull()
    com.guzi.storage.ui.home.HomeSortOption option) {
    }
    
    /**
     * 获取当前第一级分组属性的中文名（用于 TopAppBar 标题）
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFirstLevelLabel() {
        return null;
    }
    
    /**
     * 获取当前第一级分组属性名
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFirstLevelAttr() {
        return null;
    }
}