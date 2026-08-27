package com.guzi.storage.ui.components;

import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.CardDefaults;
import androidx.compose.material3.ExperimentalMaterial3Api;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextOverflow;
import com.guzi.storage.ui.group.FilterConfig;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000:\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\"\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u00ae\u0001\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u000e\u0010\u0004\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u00052\u0006\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u00032\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\t2\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\t2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\t2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00010\r2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\t2\u001e\u0010\u000f\u001a\u001a\u0012\u0004\u0012\u00020\u0003\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00110\u00100\tH\u0003\u001aR\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00142\u001e\u0010\u000f\u001a\u001a\u0012\u0004\u0012\u00020\u0003\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00110\u00100\t2\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00010\t2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00010\rH\u0007\u00a8\u0006\u0017"}, d2 = {"ConditionValuePicker", "", "attrName", "", "selectedValues", "", "searchQuery", "customInput", "onSearchQueryChange", "Lkotlin/Function1;", "onCustomInputChange", "onToggleValue", "onToggleEmpty", "Lkotlin/Function0;", "onAddCustom", "onGetOptions", "Lkotlinx/coroutines/flow/StateFlow;", "", "FilterDialog", "currentConfig", "Lcom/guzi/storage/ui/group/FilterConfig;", "onConfirm", "onDismiss", "app_release"})
public final class FilterDialogKt {
    
    /**
     * 筛选弹窗 - 添加式多条件叠加筛选
     *
     * 用户按需添加筛选条件（属性→值），每个条件可多选值、可搜索、可选择空值
     */
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void FilterDialog(@org.jetbrains.annotations.NotNull()
    com.guzi.storage.ui.group.FilterConfig currentConfig, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, ? extends kotlinx.coroutines.flow.StateFlow<? extends java.util.List<java.lang.String>>> onGetOptions, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.guzi.storage.ui.group.FilterConfig, kotlin.Unit> onConfirm, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss) {
    }
    
    /**
     * 条件值选择器（搜索框 + 选项列表 + 自定义输入 + 空值选项）
     */
    @androidx.compose.runtime.Composable()
    private static final void ConditionValuePicker(java.lang.String attrName, java.util.Set<java.lang.String> selectedValues, java.lang.String searchQuery, java.lang.String customInput, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onSearchQueryChange, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onCustomInputChange, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onToggleValue, kotlin.jvm.functions.Function0<kotlin.Unit> onToggleEmpty, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onAddCustom, kotlin.jvm.functions.Function1<? super java.lang.String, ? extends kotlinx.coroutines.flow.StateFlow<? extends java.util.List<java.lang.String>>> onGetOptions) {
    }
}