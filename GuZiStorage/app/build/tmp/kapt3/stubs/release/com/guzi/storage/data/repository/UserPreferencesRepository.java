package com.guzi.storage.data.repository;

import android.content.Context;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.flow.Flow;
import org.json.JSONArray;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * 用户偏好设置仓库 - 基于 SharedPreferences
 */
@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u0007\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bB\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\n0\tJ\u0006\u0010\u0014\u001a\u00020\u0007J\u000e\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u0002J\u000e\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0007J\u0014\u0010\u0019\u001a\u00020\u00172\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\n0\tR\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00070\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000eR\u0016\u0010\u0011\u001a\n \u0013*\u0004\u0018\u00010\u00120\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001c"}, d2 = {"Lcom/guzi/storage/data/repository/UserPreferencesRepository;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "_autoSavePresetsEnabled", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_groupingConfig", "", "", "autoSavePresetsEnabled", "Lkotlinx/coroutines/flow/Flow;", "getAutoSavePresetsEnabled", "()Lkotlinx/coroutines/flow/Flow;", "groupingConfig", "getGroupingConfig", "prefs", "Landroid/content/SharedPreferences;", "kotlin.jvm.PlatformType", "isAutoSavePresetsEnabled", "loadGroupingConfig", "setAutoSavePresetsEnabled", "", "enabled", "setGroupingConfig", "levels", "Companion", "app_release"})
public final class UserPreferencesRepository {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    private final android.content.SharedPreferences prefs = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _autoSavePresetsEnabled = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.Boolean> autoSavePresetsEnabled = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<java.lang.String>> _groupingConfig = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.util.List<java.lang.String>> groupingConfig = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_AUTO_SAVE_PRESETS = "auto_save_presets";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_GROUPING_CONFIG = "grouping_config";
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<java.lang.String> DEFAULT_GROUPING = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.guzi.storage.data.repository.UserPreferencesRepository.Companion Companion = null;
    
    @javax.inject.Inject()
    public UserPreferencesRepository(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Boolean> getAutoSavePresetsEnabled() {
        return null;
    }
    
    public final boolean isAutoSavePresetsEnabled() {
        return false;
    }
    
    public final void setAutoSavePresetsEnabled(boolean enabled) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<java.lang.String>> getGroupingConfig() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getGroupingConfig() {
        return null;
    }
    
    public final void setGroupingConfig(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> levels) {
    }
    
    private final java.util.List<java.lang.String> loadGroupingConfig() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/guzi/storage/data/repository/UserPreferencesRepository$Companion;", "", "()V", "DEFAULT_GROUPING", "", "", "KEY_AUTO_SAVE_PRESETS", "KEY_GROUPING_CONFIG", "app_release"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}