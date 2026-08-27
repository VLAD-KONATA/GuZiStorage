package com.guzi.storage.ui.detail;

import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.CardDefaults;
import androidx.compose.material3.ExperimentalMaterial3Api;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import com.guzi.storage.domain.model.Item;
import com.guzi.storage.util.CurrencyUtils;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000L\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0004\u001aF\u0010\u0000\u001a\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\t2\u0014\b\u0002\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00010\u000bH\u0003\u001a\u00a6\u0001\u0010\f\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u000e2\u000e\b\u0002\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\t2\u0014\b\u0002\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u00010\u000b2\u0014\b\u0002\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u00010\u000b2\u0014\b\u0002\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u00010\u000b28\b\u0002\u0010\n\u001a2\u0012\u0013\u0012\u00110\u000e\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\r\u0012\u0013\u0012\u00110\u0007\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0004\u0012\u00020\u00010\u00122\b\b\u0002\u0010\u0016\u001a\u00020\u0017H\u0007\u001a6\u0010\u0018\u001a\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u00032\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u000e0\u001a2\u0014\b\u0002\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00010\u000bH\u0003\u001a\u0018\u0010\u001b\u001a\u00020\u00012\u0006\u0010\u001c\u001a\u00020\u000e2\u0006\u0010\u001d\u001a\u00020\u000eH\u0003\u00a8\u0006\u001e"}, d2 = {"DetailContent", "", "modifier", "Landroidx/compose/ui/Modifier;", "item", "Lcom/guzi/storage/domain/model/Item;", "transactionCount", "", "onTransactionClick", "Lkotlin/Function0;", "onImageClick", "Lkotlin/Function1;", "DetailScreen", "itemId", "", "onNavigateBack", "onEditClick", "onCopyEditClick", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "startIndex", "viewModel", "Lcom/guzi/storage/ui/detail/DetailViewModel;", "ImagePager", "imagePaths", "", "InfoRow", "label", "value", "app_debug"})
public final class DetailScreenKt {
    
    /**
     * 物品详情页
     *
     * 布局：顶部左侧图片 + 右侧基础信息，下方可展开详细信息，最下方交易记录入口
     */
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void DetailScreen(@org.jetbrains.annotations.NotNull()
    java.lang.String itemId, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onEditClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onCopyEditClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onTransactionClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.lang.Integer, kotlin.Unit> onImageClick, @org.jetbrains.annotations.NotNull()
    com.guzi.storage.ui.detail.DetailViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void DetailContent(androidx.compose.ui.Modifier modifier, com.guzi.storage.domain.model.Item item, int transactionCount, kotlin.jvm.functions.Function0<kotlin.Unit> onTransactionClick, kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onImageClick) {
    }
    
    /**
     * 图片横向翻页器
     */
    @androidx.compose.runtime.Composable()
    private static final void ImagePager(androidx.compose.ui.Modifier modifier, java.util.List<java.lang.String> imagePaths, kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onImageClick) {
    }
    
    /**
     * 信息行：标签 + 值（值可长按复制）
     */
    @androidx.compose.runtime.Composable()
    private static final void InfoRow(java.lang.String label, java.lang.String value) {
    }
}