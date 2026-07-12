package com.guzi.storage.ui.home;

import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.lazy.grid.GridCells;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.CardDefaults;
import androidx.compose.material3.ExperimentalMaterial3Api;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextOverflow;
import com.guzi.storage.ui.ViewMode;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u00002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\u001a\u0080\u0001\u0010\u0000\u001a\u00020\u00012&\b\u0002\u0010\u0002\u001a \u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u00050\u0004\u0012\u0004\u0012\u00020\u00010\u00032\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\u0014\b\u0002\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\u00032\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\b\b\u0002\u0010\f\u001a\u00020\rH\u0007\u001a4\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00060\u00042\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00010\bH\u0003\u001a&\u0010\u0014\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u00112\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00010\bH\u0003\u00a8\u0006\u0015"}, d2 = {"HomeScreen", "", "onGroupClick", "Lkotlin/Function1;", "", "Lkotlin/Pair;", "", "onAddClick", "Lkotlin/Function0;", "onImportClick", "onStandaloneImportClick", "onSettingsClick", "viewModel", "Lcom/guzi/storage/ui/home/HomeViewModel;", "IpGroupGridCard", "groupName", "count", "", "thumbnails", "onClick", "IpGroupListCard", "app_debug"})
public final class HomeScreenKt {
    
    /**
     * 主页 - 分组列表，支持列表/收藏夹两种视图切换
     */
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void HomeScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.util.List<kotlin.Pair<java.lang.String, java.lang.String>>, kotlin.Unit> onGroupClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onAddClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onImportClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onStandaloneImportClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSettingsClick, @org.jetbrains.annotations.NotNull()
    com.guzi.storage.ui.home.HomeViewModel viewModel) {
    }
    
    /**
     * 分组卡片 - 收藏夹风格（网格视图）
     * 上方为缩略图四宫格，下方为分组名 + 件数
     */
    @androidx.compose.runtime.Composable()
    private static final void IpGroupGridCard(java.lang.String groupName, int count, java.util.List<java.lang.String> thumbnails, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    /**
     * 分组卡片 - 列表风格
     * 左侧 分组名 + 件数，右侧箭头
     */
    @androidx.compose.runtime.Composable()
    private static final void IpGroupListCard(java.lang.String groupName, int count, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
}