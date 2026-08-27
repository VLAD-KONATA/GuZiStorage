package com.guzi.storage.ui.components;

import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.CardDefaults;
import androidx.compose.material3.ExperimentalMaterial3Api;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import com.guzi.storage.ui.group.SortConfig;
import com.guzi.storage.ui.group.SortCriterion;
import com.guzi.storage.ui.group.SortOrder;
import com.guzi.storage.util.ItemSortFieldRegistry;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000.\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a:\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\u00072\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\nH\u0003\u001a2\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\r2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00010\u00072\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\nH\u0007\u00a8\u0006\u0010"}, d2 = {"SortCriterionCard", "", "index", "", "criterion", "Lcom/guzi/storage/ui/group/SortCriterion;", "onOrderChange", "Lkotlin/Function1;", "Lcom/guzi/storage/ui/group/SortOrder;", "onRemove", "Lkotlin/Function0;", "SortDialog", "currentConfig", "Lcom/guzi/storage/ui/group/SortConfig;", "onConfirm", "onDismiss", "app_debug"})
public final class SortDialogKt {
    
    /**
     * 排序弹窗 - 添加式多条件叠加排序
     *
     * 字段列表由 ItemSortFieldRegistry 反射自动生成
     * 每个条件包含：字段 + 升序/降序方向
     * 列表顺序 = 排序优先级（顶部优先）
     */
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void SortDialog(@org.jetbrains.annotations.NotNull()
    com.guzi.storage.ui.group.SortConfig currentConfig, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.guzi.storage.ui.group.SortConfig, kotlin.Unit> onConfirm, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    private static final void SortCriterionCard(int index, com.guzi.storage.ui.group.SortCriterion criterion, kotlin.jvm.functions.Function1<? super com.guzi.storage.ui.group.SortOrder, kotlin.Unit> onOrderChange, kotlin.jvm.functions.Function0<kotlin.Unit> onRemove) {
    }
}