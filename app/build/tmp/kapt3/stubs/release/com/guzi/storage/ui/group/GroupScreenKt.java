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
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextOverflow;
import androidx.compose.ui.window.DialogProperties;
import com.guzi.storage.domain.model.Item;
import com.guzi.storage.ui.ViewMode;
import com.guzi.storage.ui.home.HomeSortOption;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000L\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\t\u001aV\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\u0006\u0010\n\u001a\u00020\u000bH\u0003\u001a\u00a2\u0001\u0010\f\u001a\u00020\u00012\u0018\u0010\r\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u000f0\u000e2\u0006\u0010\u0010\u001a\u00020\u00112\u0014\b\u0002\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u00052&\b\u0002\u0010\u0012\u001a \u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u000f0\u000e\u0012\u0004\u0012\u00020\u00010\u00052\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\b\b\u0002\u0010\n\u001a\u00020\u000bH\u0007\u001a<\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00172\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0003\u001a<\u0010\u001b\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00172\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0003\u001a*\u0010\u001c\u001a\u00020\u00012\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00030\u000e2\b\b\u0002\u0010\u001e\u001a\u00020\u001f2\b\b\u0002\u0010 \u001a\u00020\u0017H\u0007\u001a\u001a\u0010!\u001a\u00020\u00012\u0006\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u001e\u001a\u00020\u001fH\u0003\u001a\u0096\u0001\u0010\"\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0018\u0010\r\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u000f0\u000e2\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u00052$\u0010\u0012\u001a \u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u000f0\u000e\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\u0006\u0010\n\u001a\u00020\u000bH\u0003\u001aR\u0010#\u001a\u00020\u00012\u0006\u0010$\u001a\u00020\u00032\u0006\u0010%\u001a\u00020\u00112\f\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00030\u000e2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00172\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0003\u001aD\u0010\'\u001a\u00020\u00012\u0006\u0010$\u001a\u00020\u00032\u0006\u0010%\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00172\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0003\u00a8\u0006("}, d2 = {"FinalLevelContent", "", "breadcrumb", "", "onItemClick", "Lkotlin/Function1;", "onAddClick", "Lkotlin/Function0;", "onNavigateBack", "onSettingsClick", "viewModel", "Lcom/guzi/storage/ui/group/GroupViewModel;", "GroupScreen", "path", "", "Lkotlin/Pair;", "depth", "", "onSubGroupClick", "ItemGridCard", "item", "Lcom/guzi/storage/domain/model/Item;", "isSelected", "", "isSelectionMode", "onClick", "onLongClick", "ItemListCard", "ItemThumbnail", "imagePaths", "modifier", "Landroidx/compose/ui/Modifier;", "firstOnly", "SelectionIndicator", "SubGroupContent", "SubGroupGridCard", "groupName", "count", "thumbnails", "SubGroupListCard", "app_release"})
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
    kotlin.jvm.functions.Function0<kotlin.Unit> onSettingsClick, @org.jetbrains.annotations.NotNull()
    com.guzi.storage.ui.group.GroupViewModel viewModel) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class, androidx.compose.foundation.ExperimentalFoundationApi.class})
    @androidx.compose.runtime.Composable()
    private static final void SubGroupContent(java.lang.String breadcrumb, java.util.List<kotlin.Pair<java.lang.String, java.lang.String>> path, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onItemClick, kotlin.jvm.functions.Function1<? super java.util.List<kotlin.Pair<java.lang.String, java.lang.String>>, kotlin.Unit> onSubGroupClick, kotlin.jvm.functions.Function0<kotlin.Unit> onAddClick, kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateBack, kotlin.jvm.functions.Function0<kotlin.Unit> onSettingsClick, com.guzi.storage.ui.group.GroupViewModel viewModel) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.foundation.ExperimentalFoundationApi.class})
    @androidx.compose.runtime.Composable()
    private static final void SubGroupGridCard(java.lang.String groupName, int count, java.util.List<java.lang.String> thumbnails, boolean isSelected, boolean isSelectionMode, kotlin.jvm.functions.Function0<kotlin.Unit> onClick, kotlin.jvm.functions.Function0<kotlin.Unit> onLongClick) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.foundation.ExperimentalFoundationApi.class})
    @androidx.compose.runtime.Composable()
    private static final void SubGroupListCard(java.lang.String groupName, int count, boolean isSelected, boolean isSelectionMode, kotlin.jvm.functions.Function0<kotlin.Unit> onClick, kotlin.jvm.functions.Function0<kotlin.Unit> onLongClick) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class, androidx.compose.foundation.ExperimentalFoundationApi.class})
    @androidx.compose.runtime.Composable()
    private static final void FinalLevelContent(java.lang.String breadcrumb, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onItemClick, kotlin.jvm.functions.Function0<kotlin.Unit> onAddClick, kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateBack, kotlin.jvm.functions.Function0<kotlin.Unit> onSettingsClick, com.guzi.storage.ui.group.GroupViewModel viewModel) {
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
     * 物品缩略图 - 支持单图与四宫格（图片以适应方式展示，背景按边缘像素自动配色）
     *
     * @param firstOnly true=仅展示首图，多图时右上角显示 "+m" 角标（m=未展示图片数）
     */
    @androidx.compose.runtime.Composable()
    public static final void ItemThumbnail(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> imagePaths, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier, boolean firstOnly) {
    }
}