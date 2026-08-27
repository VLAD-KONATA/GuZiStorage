package com.guzi.storage.ui.add;

import android.net.Uri;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.ExperimentalMaterial3Api;
import androidx.compose.material3.ExposedDropdownMenuDefaults;
import androidx.compose.material3.SnackbarHostState;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.hapticfeedback.HapticFeedbackType;
import com.guzi.storage.util.CurrencyUtils;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u00006\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0007\u001aF\u0010\u0000\u001a\u00020\u00012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\b\b\u0002\u0010\b\u001a\u00020\tH\u0007\u001aD\u0010\n\u001a\u00020\u00012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00030\f2\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u000e2\u0018\u0010\u000f\u001a\u0014\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00010\u0010H\u0003\u001a\u0010\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u0003H\u0003\u001a$\u0010\u0014\u001a\u00020\u00012\u0006\u0010\u0015\u001a\u00020\u00032\u0012\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u000eH\u0003\u001a$\u0010\u0017\u001a\u00020\u00012\u0006\u0010\u0015\u001a\u00020\u00032\u0012\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u000eH\u0003\u00a8\u0006\u0018"}, d2 = {"AddItemScreen", "", "itemId", "", "defaultIpName", "copyFromId", "onNavigateBack", "Lkotlin/Function0;", "viewModel", "Lcom/guzi/storage/ui/add/AddItemViewModel;", "ImageThumbnailRow", "imagePaths", "", "onRemove", "Lkotlin/Function1;", "onMove", "Lkotlin/Function2;", "", "SectionTitle", "title", "StatusDropdown", "value", "onValueChange", "VersionDropdown", "app_release"})
public final class AddItemScreenKt {
    
    /**
     * 添加/编辑物品页面
     */
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void AddItemScreen(@org.jetbrains.annotations.Nullable()
    java.lang.String itemId, @org.jetbrains.annotations.Nullable()
    java.lang.String defaultIpName, @org.jetbrains.annotations.Nullable()
    java.lang.String copyFromId, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateBack, @org.jetbrains.annotations.NotNull()
    com.guzi.storage.ui.add.AddItemViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void SectionTitle(java.lang.String title) {
    }
    
    /**
     * 版本强制下拉菜单（初贩/再贩二选一，不可编辑）
     */
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    private static final void VersionDropdown(java.lang.String value, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onValueChange) {
    }
    
    /**
     * 已上传图片缩略图列表，每张可点 × 删除，长按可拖拽调整顺序
     */
    @androidx.compose.runtime.Composable()
    private static final void ImageThumbnailRow(java.util.List<java.lang.String> imagePaths, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onRemove, kotlin.jvm.functions.Function2<? super java.lang.Integer, ? super java.lang.Integer, kotlin.Unit> onMove) {
    }
    
    /**
     * 状态强制下拉菜单（无伤/有伤二选一，不可编辑）
     */
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    private static final void StatusDropdown(java.lang.String value, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onValueChange) {
    }
}