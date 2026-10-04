package com.guzi.storage.ui.components;

import androidx.compose.foundation.layout.ExperimentalLayoutApi;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.ExperimentalMaterial3Api;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Modifier;
import com.guzi.storage.util.ItemValueParser;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000,\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0088\u0001\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u00052\u0018\u0010\u0007\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u0005\u0012\u0004\u0012\u00020\u00010\b2\u0016\b\u0002\u0010\t\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0001\u0018\u00010\b2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\u000b2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0003H\u0007\u00a8\u0006\u0010"}, d2 = {"MultiSelectField", "", "label", "", "selectedValues", "", "options", "onValuesChange", "Lkotlin/Function1;", "onNewOption", "required", "", "modifier", "Landroidx/compose/ui/Modifier;", "isError", "errorMessage", "app_release"})
public final class MultiSelectFieldKt {
    
    /**
     * 多选字段组件
     *
     * 展示已选标签 + 输入框，支持从预设选项选择或手动输入
     * 已选项以 Chip 形式展示，可点击 × 移除
     *
     * @param label 字段标签
     * @param selectedValues 已选值列表
     * @param options 预设选项列表
     * @param onValuesChange 值变化回调
     * @param required 是否必填
     */
    @kotlin.OptIn(markerClass = {androidx.compose.foundation.layout.ExperimentalLayoutApi.class, androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void MultiSelectField(@org.jetbrains.annotations.NotNull()
    java.lang.String label, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> selectedValues, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> options, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.util.List<java.lang.String>, kotlin.Unit> onValuesChange, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNewOption, boolean required, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier, boolean isError, @org.jetbrains.annotations.Nullable()
    java.lang.String errorMessage) {
    }
}