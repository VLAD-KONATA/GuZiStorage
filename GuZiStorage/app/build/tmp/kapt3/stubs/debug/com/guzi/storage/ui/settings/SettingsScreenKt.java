package com.guzi.storage.ui.settings;

import android.content.Intent;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.ExperimentalMaterial3Api;
import androidx.compose.material3.SnackbarHostState;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.core.content.FileProvider;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u00002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001al\u0010\u0006\u001a\u00020\u000728\b\u0002\u0010\b\u001a2\u0012\u0013\u0012\u00110\u0003\u00a2\u0006\f\b\n\u0012\b\b\u000b\u0012\u0004\b\b(\f\u0012\u0013\u0012\u00110\u0003\u00a2\u0006\f\b\n\u0012\b\b\u000b\u0012\u0004\b\b(\r\u0012\u0004\u0012\u00020\u00070\t2\u000e\b\u0002\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00070\u000f2\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00070\u000f2\b\b\u0002\u0010\u0011\u001a\u00020\u0012H\u0007\"#\u0010\u0000\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u00020\u0001\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0005\u00a8\u0006\u0013"}, d2 = {"PRESET_ATTRIBUTES", "", "Lkotlin/Pair;", "", "getPRESET_ATTRIBUTES", "()Ljava/util/List;", "SettingsScreen", "", "onAttributeClick", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "attributeName", "attributeLabel", "onGroupingSettingsClick", "Lkotlin/Function0;", "onNavigateBack", "viewModel", "Lcom/guzi/storage/ui/settings/SettingsViewModel;", "app_debug"})
public final class SettingsScreenKt {
    
    /**
     * 所有可管理的预设属性定义
     */
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<kotlin.Pair<java.lang.String, java.lang.String>> PRESET_ATTRIBUTES = null;
    
    /**
     * 所有可管理的预设属性定义
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.util.List<kotlin.Pair<java.lang.String, java.lang.String>> getPRESET_ATTRIBUTES() {
        return null;
    }
    
    /**
     * 设置主页 - 数据管理、预设选项管理入口列表
     */
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void SettingsScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.lang.String, kotlin.Unit> onAttributeClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onGroupingSettingsClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateBack, @org.jetbrains.annotations.NotNull()
    com.guzi.storage.ui.settings.SettingsViewModel viewModel) {
    }
}