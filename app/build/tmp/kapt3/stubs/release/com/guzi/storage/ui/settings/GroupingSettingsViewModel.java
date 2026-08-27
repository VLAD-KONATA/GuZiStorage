package com.guzi.storage.ui.settings;

import androidx.lifecycle.ViewModel;
import com.guzi.storage.data.repository.UserPreferencesRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\b\u0007\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\bJ\u0018\u0010\u000e\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0\u000f0\u0007J\u000e\u0010\u0010\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\u0012J\u000e\u0010\u0013\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\u0012J\u000e\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\u0012J\u0006\u0010\u0015\u001a\u00020\fR\u001d\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lcom/guzi/storage/ui/settings/GroupingSettingsViewModel;", "Landroidx/lifecycle/ViewModel;", "userPreferencesRepository", "Lcom/guzi/storage/data/repository/UserPreferencesRepository;", "(Lcom/guzi/storage/data/repository/UserPreferencesRepository;)V", "groupingConfig", "Lkotlinx/coroutines/flow/StateFlow;", "", "", "getGroupingConfig", "()Lkotlinx/coroutines/flow/StateFlow;", "addLevel", "", "attrName", "getAvailableAttributes", "Lkotlin/Pair;", "moveDown", "index", "", "moveUp", "removeLevel", "resetToDefault", "Companion", "app_release"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class GroupingSettingsViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.guzi.storage.data.repository.UserPreferencesRepository userPreferencesRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<java.lang.String>> groupingConfig = null;
    public static final int MAX_LEVELS = 5;
    @org.jetbrains.annotations.NotNull()
    public static final com.guzi.storage.ui.settings.GroupingSettingsViewModel.Companion Companion = null;
    
    @javax.inject.Inject()
    public GroupingSettingsViewModel(@org.jetbrains.annotations.NotNull()
    com.guzi.storage.data.repository.UserPreferencesRepository userPreferencesRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<java.lang.String>> getGroupingConfig() {
        return null;
    }
    
    public final void addLevel(@org.jetbrains.annotations.NotNull()
    java.lang.String attrName) {
    }
    
    public final void removeLevel(int index) {
    }
    
    public final void moveUp(int index) {
    }
    
    public final void moveDown(int index) {
    }
    
    public final void resetToDefault() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<kotlin.Pair<java.lang.String, java.lang.String>> getAvailableAttributes() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/guzi/storage/ui/settings/GroupingSettingsViewModel$Companion;", "", "()V", "MAX_LEVELS", "", "app_release"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}