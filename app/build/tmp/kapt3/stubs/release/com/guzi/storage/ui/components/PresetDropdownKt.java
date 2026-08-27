package com.guzi.storage.ui.components;

import androidx.compose.material.icons.Icons;
import androidx.compose.material3.ExperimentalMaterial3Api;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Modifier;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000,\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a|\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u00062\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\b2\u0016\b\u0002\u0010\t\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0001\u0018\u00010\b2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\u000b2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0003H\u0007\u00a8\u0006\u0010"}, d2 = {"PresetDropdown", "", "label", "", "value", "options", "", "onValueChange", "Lkotlin/Function1;", "onNewOption", "required", "", "modifier", "Landroidx/compose/ui/Modifier;", "isError", "errorMessage", "app_release"})
public final class PresetDropdownKt {
    
    /**
     * 预设选项下拉组件
     *
     * 支持从预设选项列表选择，也支持手动输入新值
     * 新增的值会通过 onNewOption 回调通知，由外部决定是否加入预设库
     *
     * @param label 字段标签（如 "IP名"、"出品商"）
     * @param value 当前选中值
     * @param options 预设选项列表
     * @param onValueChange 值变化回调
     * @param required 是否必填
     */
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void PresetDropdown(@org.jetbrains.annotations.NotNull()
    java.lang.String label, @org.jetbrains.annotations.NotNull()
    java.lang.String value, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> options, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onValueChange, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNewOption, boolean required, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier, boolean isError, @org.jetbrains.annotations.Nullable()
    java.lang.String errorMessage) {
    }
}