package com.guzi.storage.ui.fileimport;

import android.net.Uri;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.CardDefaults;
import androidx.compose.material3.ExperimentalMaterial3Api;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000D\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\u001aR\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00032\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00010\u000bH\u0003\u001a\u001e\u0010\r\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\t2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\u000bH\u0003\u001a\u001e\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00010\u000bH\u0003\u001aP\u0010\u0014\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00010\u001a2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00010\u000b2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00010\u000bH\u0003\u001a\"\u0010\u001d\u001a\u00020\u00012\u000e\b\u0002\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00010\u000b2\b\b\u0002\u0010\u001f\u001a\u00020 H\u0007\u00a8\u0006!"}, d2 = {"DoneContent", "", "itemCount", "", "transactionCount", "imageCount", "mergedCount", "errorLines", "", "", "onBack", "Lkotlin/Function0;", "onImportMore", "ErrorContent", "message", "onRetry", "IdleContent", "importType", "Lcom/guzi/storage/ui/fileimport/StandaloneImportType;", "onSelectFile", "PreviewContent", "data", "Lcom/guzi/storage/ui/fileimport/StandalonePreviewData;", "smartCompressImages", "", "onSmartCompressChange", "Lkotlin/Function1;", "onConfirm", "onCancel", "StandaloneImportScreen", "onNavigateBack", "viewModel", "Lcom/guzi/storage/ui/fileimport/StandaloneImportViewModel;", "app_release"})
public final class StandaloneImportScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void StandaloneImportScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateBack, @org.jetbrains.annotations.NotNull()
    com.guzi.storage.ui.fileimport.StandaloneImportViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void IdleContent(com.guzi.storage.ui.fileimport.StandaloneImportType importType, kotlin.jvm.functions.Function0<kotlin.Unit> onSelectFile) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void PreviewContent(com.guzi.storage.ui.fileimport.StandaloneImportType importType, com.guzi.storage.ui.fileimport.StandalonePreviewData data, boolean smartCompressImages, kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onSmartCompressChange, kotlin.jvm.functions.Function0<kotlin.Unit> onConfirm, kotlin.jvm.functions.Function0<kotlin.Unit> onCancel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void DoneContent(int itemCount, int transactionCount, int imageCount, int mergedCount, java.util.List<java.lang.String> errorLines, kotlin.jvm.functions.Function0<kotlin.Unit> onBack, kotlin.jvm.functions.Function0<kotlin.Unit> onImportMore) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ErrorContent(java.lang.String message, kotlin.jvm.functions.Function0<kotlin.Unit> onRetry) {
    }
}