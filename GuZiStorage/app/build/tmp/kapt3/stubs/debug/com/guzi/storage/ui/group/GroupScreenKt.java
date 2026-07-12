package com.guzi.storage.ui.group;

import androidx.compose.foundation.ExperimentalFoundationApi;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.lazy.grid.GridCells;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.CardDefaults;
import androidx.compose.material3.ExperimentalMaterial3Api;
import androidx.compose.material3.TopAppBarDefaults;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.ContentScale;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextOverflow;
import androidx.compose.ui.window.DialogProperties;
import com.guzi.storage.domain.model.Item;
import com.guzi.storage.ui.ViewMode;
import com.guzi.storage.ui.components.ConfirmDialog;
import java.io.File;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000L\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\b\u001aH\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\u0006\u0010\t\u001a\u00020\nH\u0003\u001a\u0092\u0001\u0010\u000b\u001a\u00020\u00012\u0018\u0010\f\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u000e0\r2\u0006\u0010\u000f\u001a\u00020\u00102\u0014\b\u0002\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u00052&\b\u0002\u0010\u0011\u001a \u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u000e0\r\u0012\u0004\u0012\u00020\u00010\u00052\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\b\b\u0002\u0010\t\u001a\u00020\nH\u0007\u001a<\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00162\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0003\u001a<\u0010\u001a\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00162\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0003\u001a \u0010\u001b\u001a\u00020\u00012\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00030\r2\b\b\u0002\u0010\u001d\u001a\u00020\u001eH\u0007\u001a\u001a\u0010\u001f\u001a\u00020\u00012\u0006\u0010\u0015\u001a\u00020\u00162\b\b\u0002\u0010\u001d\u001a\u00020\u001eH\u0003\u001at\u0010 \u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0018\u0010\f\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u000e0\r2$\u0010\u0011\u001a \u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u000e0\r\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\u0006\u0010\t\u001a\u00020\nH\u0003\u001a4\u0010!\u001a\u00020\u00012\u0006\u0010\"\u001a\u00020\u00032\u0006\u0010#\u001a\u00020\u00102\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00030\r2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0003\u001a&\u0010%\u001a\u00020\u00012\u0006\u0010\"\u001a\u00020\u00032\u0006\u0010#\u001a\u00020\u00102\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0003\u00a8\u0006&"}, d2 = {"FinalLevelContent", "", "breadcrumb", "", "onItemClick", "Lkotlin/Function1;", "onAddClick", "Lkotlin/Function0;", "onNavigateBack", "viewModel", "Lcom/guzi/storage/ui/group/GroupViewModel;", "GroupScreen", "path", "", "Lkotlin/Pair;", "depth", "", "onSubGroupClick", "ItemGridCard", "item", "Lcom/guzi/storage/domain/model/Item;", "isSelected", "", "isSelectionMode", "onClick", "onLongClick", "ItemListCard", "ItemThumbnail", "imagePaths", "modifier", "Landroidx/compose/ui/Modifier;", "SelectionIndicator", "SubGroupContent", "SubGroupGridCard", "groupName", "count", "thumbnails", "SubGroupListCard", "app_debug"})
public final class GroupScreenKt {
    
    /**
     * 分组子页面 - 支持多级分组导航
     *
     * 非最终级：显示子分组卡片（网格/列表）
     * 最终级：显示物品列表（搜索、筛选弹窗、批量选择删除）
     */
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void GroupScreen(@org.jetbrains.annotations.NotNull()
    java.util.List<kotlin.Pair<java.lang.String, java.lang.String>> path, int depth, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onItemClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.util.List<kotlin.Pair<java.lang.String, java.lang.String>>, kotlin.Unit> onSubGroupClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onAddClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateBack, @org.jetbrains.annotations.NotNull()
    com.guzi.storage.ui.group.GroupViewModel viewModel) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    private static final void SubGroupContent(java.lang.String breadcrumb, java.util.List<kotlin.Pair<java.lang.String, java.lang.String>> path, kotlin.jvm.functions.Function1<? super java.util.List<kotlin.Pair<java.lang.String, java.lang.String>>, kotlin.Unit> onSubGroupClick, kotlin.jvm.functions.Function0<kotlin.Unit> onAddClick, kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateBack, com.guzi.storage.ui.group.GroupViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void SubGroupGridCard(java.lang.String groupName, int count, java.util.List<java.lang.String> thumbnails, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void SubGroupListCard(java.lang.String groupName, int count, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    private static final void FinalLevelContent(java.lang.String breadcrumb, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onItemClick, kotlin.jvm.functions.Function0<kotlin.Unit> onAddClick, kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateBack, com.guzi.storage.ui.group.GroupViewModel viewModel) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.foundation.ExperimentalFoundationApi.class})
    @androidx.compose.runtime.Composable()
    private static final void ItemGridCard(com.guzi.storage.domain.model.Item item, boolean isSelected, boolean isSelectionMode, kotlin.jvm.functions.Function0<kotlin.Unit> onClick, kotlin.jvm.functions.Function0<kotlin.Unit> onLongClick) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.foundation.ExperimentalFoundationApi.class})
    @androidx.compose.runtime.Composable()
    private static final void ItemListCard(com.guzi.storage.domain.model.Item item, boolean isSelected, boolean isSelectionMode, kotlin.jvm.functions.Function0<kotlin.Unit> onClick, kotlin.jvm.functions.Function0<kotlin.Unit> onLongClick) {
    }
    
    /**
     * 选中状态指示器（圆形勾选标记）
     */
    @androidx.compose.runtime.Composable()
    private static final void SelectionIndicator(boolean isSelected, androidx.compose.ui.Modifier modifier) {
    }
    
    /**
     * 物品缩略图 - 支持单图与四宫格
     */
    @androidx.compose.runtime.Composable()
    public static final void ItemThumbnail(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> imagePaths, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
}