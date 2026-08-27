package com.guzi.storage.ui.navigation;

import androidx.compose.runtime.Composable;
import androidx.navigation.NavHostController;
import androidx.navigation.NavType;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u001a\u001a\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u0007\u00a8\u0006\u0006"}, d2 = {"AppNavGraph", "", "navController", "Landroidx/navigation/NavHostController;", "startDestination", "", "app_release"})
public final class NavGraphKt {
    
    /**
     * 应用导航图
     *
     * 页面流转:
     * Home → Group → Detail → Transaction → AddTransaction
     * Home / Group → AddItem（添加/编辑）
     * Detail → ImageViewer（大图预览）
     * Home → Settings → PresetManage / GroupingSettings
     * Home → ZipImport
     */
    @androidx.compose.runtime.Composable()
    public static final void AppNavGraph(@org.jetbrains.annotations.NotNull()
    androidx.navigation.NavHostController navController, @org.jetbrains.annotations.NotNull()
    java.lang.String startDestination) {
    }
}