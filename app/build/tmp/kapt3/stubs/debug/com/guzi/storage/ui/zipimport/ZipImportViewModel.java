package com.guzi.storage.ui.zipimport;

import android.content.Context;
import android.net.Uri;
import androidx.lifecycle.ViewModel;
import com.guzi.storage.data.repository.ItemRepository;
import com.guzi.storage.data.repository.PresetOptionRepository;
import com.guzi.storage.data.repository.TransactionRepository;
import com.guzi.storage.domain.model.Item;
import com.guzi.storage.domain.model.Transaction;
import com.guzi.storage.domain.model.TransactionType;
import com.guzi.storage.util.DateUtils;
import com.guzi.storage.util.IdGenerator;
import com.guzi.storage.util.ImageFileManager;
import com.guzi.storage.util.ZipExtractor;
import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.flow.StateFlow;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import javax.inject.Inject;

/**
 * ZIP 批量导入 ViewModel
 *
 * ZIP 结构：
 * - items.csv        谷子基本信息（含临时 id 列）
 * - transactions.csv 交易记录（id 列为外键，对应 items.csv 的 id）
 * - image/           图片文件夹，子目录名 = items.csv 中的 id
 *
 * 临时 id 仅用于导入时的关联，不会写入数据库
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0090\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0007\u0018\u00002\u00020\u0001:\u0001CB1\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\fJ\u001c\u0010\u0018\u001a\u00020\u00192\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001bH\u0082@\u00a2\u0006\u0002\u0010\u001dJ>\u0010\u001e\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u00010\u001c\u0012\n\u0012\b\u0012\u0004\u0012\u00020 0\u001b0\u001f2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020 0\u001b2\u0012\u0010\"\u001a\u000e\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020$0#H\u0002J\"\u0010%\u001a\u000e\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020$0#2\f\u0010&\u001a\b\u0012\u0004\u0012\u00020 0\u001bH\u0002J\"\u0010\'\u001a\u000e\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020$0#2\f\u0010&\u001a\b\u0012\u0004\u0012\u00020 0\u001bH\u0002J\b\u0010(\u001a\u00020\u0019H\u0002J\u0006\u0010)\u001a\u00020\u0019J\u001a\u0010*\u001a\u0004\u0018\u00010\u00172\u0006\u0010+\u001a\u00020\u00172\u0006\u0010,\u001a\u00020 H\u0002J\u001a\u0010-\u001a\u0004\u0018\u00010\u00172\u0006\u0010+\u001a\u00020\u00172\u0006\u0010,\u001a\u00020 H\u0002J2\u0010.\u001a\u00020 2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020 0\u001b2\u0012\u0010\"\u001a\u000e\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020$0#2\u0006\u0010/\u001a\u00020 H\u0002J\u0018\u00100\u001a\u0002012\u0006\u00102\u001a\u00020\u001c2\u0006\u00103\u001a\u00020\u001cH\u0002J\b\u00104\u001a\u00020\u0019H\u0014J\u000e\u00105\u001a\u00020\u00192\u0006\u00106\u001a\u000207J2\u00108\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u0002090\u001b\u0012\n\u0012\b\u0012\u0004\u0012\u00020 0\u001b0\u001f2\u0006\u0010:\u001a\u00020\u00172\b\u0010;\u001a\u0004\u0018\u00010\u0017H\u0002J<\u0010<\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u00020=0\u001b\u0012\n\u0012\b\u0012\u0004\u0012\u00020 0\u001b0\u001f2\u0006\u0010:\u001a\u00020\u00172\u0012\u0010>\u001a\u000e\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020 0#H\u0002J\u0010\u0010?\u001a\u00020\u00152\u0006\u00106\u001a\u000207H\u0002J\u0006\u0010@\u001a\u00020\u0019J\u0016\u0010A\u001a\b\u0012\u0004\u0012\u00020 0\u001b2\u0006\u0010B\u001a\u00020 H\u0002R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006D"}, d2 = {"Lcom/guzi/storage/ui/zipimport/ZipImportViewModel;", "Landroidx/lifecycle/ViewModel;", "context", "Landroid/content/Context;", "itemRepository", "Lcom/guzi/storage/data/repository/ItemRepository;", "transactionRepository", "Lcom/guzi/storage/data/repository/TransactionRepository;", "presetOptionRepository", "Lcom/guzi/storage/data/repository/PresetOptionRepository;", "imageFileManager", "Lcom/guzi/storage/util/ImageFileManager;", "(Landroid/content/Context;Lcom/guzi/storage/data/repository/ItemRepository;Lcom/guzi/storage/data/repository/TransactionRepository;Lcom/guzi/storage/data/repository/PresetOptionRepository;Lcom/guzi/storage/util/ImageFileManager;)V", "_importState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/guzi/storage/ui/zipimport/ZipImportViewModel$ImportState;", "importState", "Lkotlinx/coroutines/flow/StateFlow;", "getImportState", "()Lkotlinx/coroutines/flow/StateFlow;", "parsedData", "Lcom/guzi/storage/ui/zipimport/ZipImportData;", "tempDir", "Ljava/io/File;", "autoSavePresets", "", "items", "", "Lcom/guzi/storage/domain/model/Item;", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "buildItem", "Lkotlin/Pair;", "", "cols", "colIndex", "", "", "buildItemColMap", "headers", "buildTransactionColMap", "cleanupTempDir", "confirmImport", "findDirRecursive", "dir", "name", "findFileRecursive", "getCol", "key", "isDuplicateItem", "", "existing", "imported", "onCleared", "parseFile", "uri", "Landroid/net/Uri;", "parseItemsCsv", "Lcom/guzi/storage/ui/zipimport/ParsedItem;", "file", "imagesDir", "parseTransactionsCsv", "Lcom/guzi/storage/ui/zipimport/ParsedTransactionData;", "idMap", "parseZip", "reset", "splitCsvLine", "line", "ImportState", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class ZipImportViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.guzi.storage.data.repository.ItemRepository itemRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.guzi.storage.data.repository.TransactionRepository transactionRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.guzi.storage.data.repository.PresetOptionRepository presetOptionRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.guzi.storage.util.ImageFileManager imageFileManager = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.guzi.storage.ui.zipimport.ZipImportViewModel.ImportState> _importState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.guzi.storage.ui.zipimport.ZipImportViewModel.ImportState> importState = null;
    @org.jetbrains.annotations.Nullable()
    private com.guzi.storage.ui.zipimport.ZipImportData parsedData;
    @org.jetbrains.annotations.Nullable()
    private java.io.File tempDir;
    
    @javax.inject.Inject()
    public ZipImportViewModel(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.guzi.storage.data.repository.ItemRepository itemRepository, @org.jetbrains.annotations.NotNull()
    com.guzi.storage.data.repository.TransactionRepository transactionRepository, @org.jetbrains.annotations.NotNull()
    com.guzi.storage.data.repository.PresetOptionRepository presetOptionRepository, @org.jetbrains.annotations.NotNull()
    com.guzi.storage.util.ImageFileManager imageFileManager) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.guzi.storage.ui.zipimport.ZipImportViewModel.ImportState> getImportState() {
        return null;
    }
    
    public final void parseFile(@org.jetbrains.annotations.NotNull()
    android.net.Uri uri) {
    }
    
    public final void confirmImport() {
    }
    
    public final void reset() {
    }
    
    private final com.guzi.storage.ui.zipimport.ZipImportData parseZip(android.net.Uri uri) {
        return null;
    }
    
    private final kotlin.Pair<java.util.List<com.guzi.storage.ui.zipimport.ParsedItem>, java.util.List<java.lang.String>> parseItemsCsv(java.io.File file, java.io.File imagesDir) {
        return null;
    }
    
    private final kotlin.Pair<java.util.List<com.guzi.storage.ui.zipimport.ParsedTransactionData>, java.util.List<java.lang.String>> parseTransactionsCsv(java.io.File file, java.util.Map<java.lang.String, java.lang.String> idMap) {
        return null;
    }
    
    private final java.util.Map<java.lang.String, java.lang.Integer> buildItemColMap(java.util.List<java.lang.String> headers) {
        return null;
    }
    
    private final java.util.Map<java.lang.String, java.lang.Integer> buildTransactionColMap(java.util.List<java.lang.String> headers) {
        return null;
    }
    
    /**
     * 递归搜索指定文件名（不区分大小写）
     */
    private final java.io.File findFileRecursive(java.io.File dir, java.lang.String name) {
        return null;
    }
    
    /**
     * 递归搜索指定目录名（不区分大小写）
     */
    private final java.io.File findDirRecursive(java.io.File dir, java.lang.String name) {
        return null;
    }
    
    /**
     * 判断两个物品是否在指定字段上完全相同（用于导入去重）
     */
    private final boolean isDuplicateItem(com.guzi.storage.domain.model.Item existing, com.guzi.storage.domain.model.Item imported) {
        return false;
    }
    
    private final java.lang.String getCol(java.util.List<java.lang.String> cols, java.util.Map<java.lang.String, java.lang.Integer> colIndex, java.lang.String key) {
        return null;
    }
    
    private final kotlin.Pair<com.guzi.storage.domain.model.Item, java.util.List<java.lang.String>> buildItem(java.util.List<java.lang.String> cols, java.util.Map<java.lang.String, java.lang.Integer> colIndex) {
        return null;
    }
    
    private final java.util.List<java.lang.String> splitCsvLine(java.lang.String line) {
        return null;
    }
    
    private final java.lang.Object autoSavePresets(java.util.List<com.guzi.storage.domain.model.Item> items, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final void cleanupTempDir() {
    }
    
    @java.lang.Override()
    protected void onCleared() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b7\u0018\u00002\u00020\u0001:\u0005\u0003\u0004\u0005\u0006\u0007B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0005\b\t\n\u000b\f\u00a8\u0006\r"}, d2 = {"Lcom/guzi/storage/ui/zipimport/ZipImportViewModel$ImportState;", "", "()V", "Done", "Error", "Idle", "Loading", "Preview", "Lcom/guzi/storage/ui/zipimport/ZipImportViewModel$ImportState$Done;", "Lcom/guzi/storage/ui/zipimport/ZipImportViewModel$ImportState$Error;", "Lcom/guzi/storage/ui/zipimport/ZipImportViewModel$ImportState$Idle;", "Lcom/guzi/storage/ui/zipimport/ZipImportViewModel$ImportState$Loading;", "Lcom/guzi/storage/ui/zipimport/ZipImportViewModel$ImportState$Preview;", "app_debug"})
    public static abstract class ImportState {
        
        private ImportState() {
            super();
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0087\b\u0018\u00002\u00020\u0001B3\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u00a2\u0006\u0002\u0010\nJ\t\u0010\u0012\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0013\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u00c6\u0003JA\u0010\u0017\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u00c6\u0001J\u0013\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u00d6\u0003J\t\u0010\u001c\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\u001d\u001a\u00020\tH\u00d6\u0001R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000e\u00a8\u0006\u001e"}, d2 = {"Lcom/guzi/storage/ui/zipimport/ZipImportViewModel$ImportState$Done;", "Lcom/guzi/storage/ui/zipimport/ZipImportViewModel$ImportState;", "itemCount", "", "transactionCount", "imageCount", "mergedCount", "errorLines", "", "", "(IIIILjava/util/List;)V", "getErrorLines", "()Ljava/util/List;", "getImageCount", "()I", "getItemCount", "getMergedCount", "getTransactionCount", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "", "hashCode", "toString", "app_debug"})
        public static final class Done extends com.guzi.storage.ui.zipimport.ZipImportViewModel.ImportState {
            private final int itemCount = 0;
            private final int transactionCount = 0;
            private final int imageCount = 0;
            private final int mergedCount = 0;
            @org.jetbrains.annotations.NotNull()
            private final java.util.List<java.lang.String> errorLines = null;
            
            public Done(int itemCount, int transactionCount, int imageCount, int mergedCount, @org.jetbrains.annotations.NotNull()
            java.util.List<java.lang.String> errorLines) {
            }
            
            public final int getItemCount() {
                return 0;
            }
            
            public final int getTransactionCount() {
                return 0;
            }
            
            public final int getImageCount() {
                return 0;
            }
            
            public final int getMergedCount() {
                return 0;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final java.util.List<java.lang.String> getErrorLines() {
                return null;
            }
            
            public final int component1() {
                return 0;
            }
            
            public final int component2() {
                return 0;
            }
            
            public final int component3() {
                return 0;
            }
            
            public final int component4() {
                return 0;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final java.util.List<java.lang.String> component5() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final com.guzi.storage.ui.zipimport.ZipImportViewModel.ImportState.Done copy(int itemCount, int transactionCount, int imageCount, int mergedCount, @org.jetbrains.annotations.NotNull()
            java.util.List<java.lang.String> errorLines) {
                return null;
            }
            
            @java.lang.Override()
            public boolean equals(@org.jetbrains.annotations.Nullable()
            java.lang.Object other) {
                return false;
            }
            
            @java.lang.Override()
            public int hashCode() {
                return 0;
            }
            
            @java.lang.Override()
            @org.jetbrains.annotations.NotNull()
            public java.lang.String toString() {
                return null;
            }
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0010"}, d2 = {"Lcom/guzi/storage/ui/zipimport/ZipImportViewModel$ImportState$Error;", "Lcom/guzi/storage/ui/zipimport/ZipImportViewModel$ImportState;", "message", "", "(Ljava/lang/String;)V", "getMessage", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_debug"})
        public static final class Error extends com.guzi.storage.ui.zipimport.ZipImportViewModel.ImportState {
            @org.jetbrains.annotations.NotNull()
            private final java.lang.String message = null;
            
            public Error(@org.jetbrains.annotations.NotNull()
            java.lang.String message) {
            }
            
            @org.jetbrains.annotations.NotNull()
            public final java.lang.String getMessage() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final java.lang.String component1() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final com.guzi.storage.ui.zipimport.ZipImportViewModel.ImportState.Error copy(@org.jetbrains.annotations.NotNull()
            java.lang.String message) {
                return null;
            }
            
            @java.lang.Override()
            public boolean equals(@org.jetbrains.annotations.Nullable()
            java.lang.Object other) {
                return false;
            }
            
            @java.lang.Override()
            public int hashCode() {
                return 0;
            }
            
            @java.lang.Override()
            @org.jetbrains.annotations.NotNull()
            public java.lang.String toString() {
                return null;
            }
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u00c7\n\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0013\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u00d6\u0003J\t\u0010\u0007\u001a\u00020\bH\u00d6\u0001J\t\u0010\t\u001a\u00020\nH\u00d6\u0001\u00a8\u0006\u000b"}, d2 = {"Lcom/guzi/storage/ui/zipimport/ZipImportViewModel$ImportState$Idle;", "Lcom/guzi/storage/ui/zipimport/ZipImportViewModel$ImportState;", "()V", "equals", "", "other", "", "hashCode", "", "toString", "", "app_debug"})
        public static final class Idle extends com.guzi.storage.ui.zipimport.ZipImportViewModel.ImportState {
            @org.jetbrains.annotations.NotNull()
            public static final com.guzi.storage.ui.zipimport.ZipImportViewModel.ImportState.Idle INSTANCE = null;
            
            private Idle() {
            }
            
            @java.lang.Override()
            public boolean equals(@org.jetbrains.annotations.Nullable()
            java.lang.Object other) {
                return false;
            }
            
            @java.lang.Override()
            public int hashCode() {
                return 0;
            }
            
            @java.lang.Override()
            @org.jetbrains.annotations.NotNull()
            public java.lang.String toString() {
                return null;
            }
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u00c7\n\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0013\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u00d6\u0003J\t\u0010\u0007\u001a\u00020\bH\u00d6\u0001J\t\u0010\t\u001a\u00020\nH\u00d6\u0001\u00a8\u0006\u000b"}, d2 = {"Lcom/guzi/storage/ui/zipimport/ZipImportViewModel$ImportState$Loading;", "Lcom/guzi/storage/ui/zipimport/ZipImportViewModel$ImportState;", "()V", "equals", "", "other", "", "hashCode", "", "toString", "", "app_debug"})
        public static final class Loading extends com.guzi.storage.ui.zipimport.ZipImportViewModel.ImportState {
            @org.jetbrains.annotations.NotNull()
            public static final com.guzi.storage.ui.zipimport.ZipImportViewModel.ImportState.Loading INSTANCE = null;
            
            private Loading() {
            }
            
            @java.lang.Override()
            public boolean equals(@org.jetbrains.annotations.Nullable()
            java.lang.Object other) {
                return false;
            }
            
            @java.lang.Override()
            public int hashCode() {
                return 0;
            }
            
            @java.lang.Override()
            @org.jetbrains.annotations.NotNull()
            public java.lang.String toString() {
                return null;
            }
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0011"}, d2 = {"Lcom/guzi/storage/ui/zipimport/ZipImportViewModel$ImportState$Preview;", "Lcom/guzi/storage/ui/zipimport/ZipImportViewModel$ImportState;", "data", "Lcom/guzi/storage/ui/zipimport/ZipImportData;", "(Lcom/guzi/storage/ui/zipimport/ZipImportData;)V", "getData", "()Lcom/guzi/storage/ui/zipimport/ZipImportData;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "app_debug"})
        public static final class Preview extends com.guzi.storage.ui.zipimport.ZipImportViewModel.ImportState {
            @org.jetbrains.annotations.NotNull()
            private final com.guzi.storage.ui.zipimport.ZipImportData data = null;
            
            public Preview(@org.jetbrains.annotations.NotNull()
            com.guzi.storage.ui.zipimport.ZipImportData data) {
            }
            
            @org.jetbrains.annotations.NotNull()
            public final com.guzi.storage.ui.zipimport.ZipImportData getData() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final com.guzi.storage.ui.zipimport.ZipImportData component1() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final com.guzi.storage.ui.zipimport.ZipImportViewModel.ImportState.Preview copy(@org.jetbrains.annotations.NotNull()
            com.guzi.storage.ui.zipimport.ZipImportData data) {
                return null;
            }
            
            @java.lang.Override()
            public boolean equals(@org.jetbrains.annotations.Nullable()
            java.lang.Object other) {
                return false;
            }
            
            @java.lang.Override()
            public int hashCode() {
                return 0;
            }
            
            @java.lang.Override()
            @org.jetbrains.annotations.NotNull()
            public java.lang.String toString() {
                return null;
            }
        }
    }
}