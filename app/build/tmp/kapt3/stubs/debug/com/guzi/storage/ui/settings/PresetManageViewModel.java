package com.guzi.storage.ui.settings;

import androidx.lifecycle.ViewModel;
import com.guzi.storage.data.repository.PresetOptionRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.ExperimentalCoroutinesApi;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

/**
 * 预设选项管理 ViewModel
 *
 * 管理某个属性（如 ip_name、category 等）下的选项列表
 * 支持新增、单条删除和长按批量删除
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\"\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\b\r\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u0007J\u0006\u0010\u001e\u001a\u00020\u001cJ\u0006\u0010\u001f\u001a\u00020\u001cJ\u0006\u0010 \u001a\u00020\u001cJ\u0006\u0010!\u001a\u00020\u001cJ\u0006\u0010\"\u001a\u00020\u001cJ\u0006\u0010#\u001a\u00020\u001cJ\u000e\u0010$\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u0007J\u0006\u0010%\u001a\u00020\u001cJ\u000e\u0010&\u001a\u00020\u001c2\u0006\u0010\'\u001a\u00020\u0007J\u000e\u0010(\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u0007R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\n0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000fR#\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00110\u000e\u00a2\u0006\u000e\n\u0000\u0012\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u000fR\u0019\u0010\u0015\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u000fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\n0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u000fR\u0017\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\f0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u000f\u00a8\u0006)"}, d2 = {"Lcom/guzi/storage/ui/settings/PresetManageViewModel;", "Landroidx/lifecycle/ViewModel;", "presetOptionRepository", "Lcom/guzi/storage/data/repository/PresetOptionRepository;", "(Lcom/guzi/storage/data/repository/PresetOptionRepository;)V", "_attributeName", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_pendingDeleteValue", "_selectedValues", "", "_showBatchDeleteDialog", "", "isSelectionMode", "Lkotlinx/coroutines/flow/StateFlow;", "()Lkotlinx/coroutines/flow/StateFlow;", "options", "", "getOptions$annotations", "()V", "getOptions", "pendingDeleteValue", "getPendingDeleteValue", "selectedValues", "getSelectedValues", "showBatchDeleteDialog", "getShowBatchDeleteDialog", "addOption", "", "value", "clearSelection", "confirmBatchDelete", "confirmDelete", "dismissBatchDelete", "dismissDelete", "requestBatchDelete", "requestDelete", "selectAll", "setAttributeName", "name", "toggleSelection", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class PresetManageViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.guzi.storage.data.repository.PresetOptionRepository presetOptionRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _attributeName = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<java.lang.String>> options = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _pendingDeleteValue = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> pendingDeleteValue = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.Set<java.lang.String>> _selectedValues = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.Set<java.lang.String>> selectedValues = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isSelectionMode = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _showBatchDeleteDialog = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> showBatchDeleteDialog = null;
    
    @javax.inject.Inject()
    public PresetManageViewModel(@org.jetbrains.annotations.NotNull()
    com.guzi.storage.data.repository.PresetOptionRepository presetOptionRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<java.lang.String>> getOptions() {
        return null;
    }
    
    @kotlin.OptIn(markerClass = {kotlinx.coroutines.ExperimentalCoroutinesApi.class})
    @java.lang.Deprecated()
    public static void getOptions$annotations() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getPendingDeleteValue() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.Set<java.lang.String>> getSelectedValues() {
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
    
    public final void setAttributeName(@org.jetbrains.annotations.NotNull()
    java.lang.String name) {
    }
    
    public final void addOption(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    public final void requestDelete(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    public final void dismissDelete() {
    }
    
    public final void confirmDelete() {
    }
    
    public final void toggleSelection(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
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