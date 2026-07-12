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
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\u0010\u001a\u00020\nJ\u0006\u0010\u0011\u001a\u00020\nR\u001d\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001d\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2 = {"Lcom/guzi/storage/ui/home/HomeViewModel;", "Landroidx/lifecycle/ViewModel;", "itemRepository", "Lcom/guzi/storage/data/repository/ItemRepository;", "userPreferencesRepository", "Lcom/guzi/storage/data/repository/UserPreferencesRepository;", "(Lcom/guzi/storage/data/repository/ItemRepository;Lcom/guzi/storage/data/repository/UserPreferencesRepository;)V", "groupingConfig", "Lkotlinx/coroutines/flow/StateFlow;", "", "", "getGroupingConfig", "()Lkotlinx/coroutines/flow/StateFlow;", "ipGroups", "Lcom/guzi/storage/ui/home/IpGroupDisplay;", "getIpGroups", "getFirstLevelAttr", "getFirstLevelLabel", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class HomeViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.guzi.storage.data.repository.ItemRepository itemRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.guzi.storage.data.repository.UserPreferencesRepository userPreferencesRepository = null;
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
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<java.lang.String>> getGroupingConfig() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.guzi.storage.ui.home.IpGroupDisplay>> getIpGroups() {
        return null;
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