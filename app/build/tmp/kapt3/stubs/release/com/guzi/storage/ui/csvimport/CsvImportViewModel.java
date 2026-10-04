package com.guzi.storage.ui.csvimport;

import android.content.Context;
import android.net.Uri;
import androidx.lifecycle.ViewModel;
import com.guzi.storage.data.repository.ItemRepository;
import com.guzi.storage.data.repository.PresetOptionRepository;
import com.guzi.storage.data.local.PipeListConverter;
import com.guzi.storage.domain.model.Item;
import com.guzi.storage.util.CurrencyUtils;
import com.guzi.storage.util.DateUtils;
import com.guzi.storage.util.ItemValueParser;
import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.flow.StateFlow;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.guzi.storage.util.IdGenerator;
import javax.inject.Inject;

/**
 * CSV 批量导入 ViewModel
 *
 * CSV 模板列名（顺序不强制，按列名匹配）:
 * id,中文名称,日文/英文名称,IP名,出品商,出品地,发售形式,售卖方式,价格,发售日期,种类,尺寸,材质,相关角色,柄图种类,状态,版本,数量,原袋,参考链接,备注
 *
 * 状态列规则：空→无伤，有值（如 底锈 或 底锈|划痕）→有伤+状态详情
 * 一对多字段用 | 分隔，例如: 高木涉|佐藤美和子
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001:\u0001(B!\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u001c\u0010\u0013\u001a\u00020\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H\u0082@\u00a2\u0006\u0002\u0010\u0016J\"\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u001a0\u00182\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00190\u0011H\u0002J>\u0010\u001c\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u00010\u0012\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00190\u00110\u001d2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00190\u00112\u0012\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u001a0\u0018H\u0002J\u0006\u0010 \u001a\u00020\u0014J(\u0010!\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u0011\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00190\u00110\u001d2\u0006\u0010\"\u001a\u00020#H\u0002J\u000e\u0010$\u001a\u00020\u00142\u0006\u0010\"\u001a\u00020#J\u0006\u0010%\u001a\u00020\u0014J\u0016\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00190\u00112\u0006\u0010\'\u001a\u00020\u0019H\u0002R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006)"}, d2 = {"Lcom/guzi/storage/ui/csvimport/CsvImportViewModel;", "Landroidx/lifecycle/ViewModel;", "context", "Landroid/content/Context;", "itemRepository", "Lcom/guzi/storage/data/repository/ItemRepository;", "presetOptionRepository", "Lcom/guzi/storage/data/repository/PresetOptionRepository;", "(Landroid/content/Context;Lcom/guzi/storage/data/repository/ItemRepository;Lcom/guzi/storage/data/repository/PresetOptionRepository;)V", "_importState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/guzi/storage/ui/csvimport/CsvImportViewModel$ImportState;", "importState", "Lkotlinx/coroutines/flow/StateFlow;", "getImportState", "()Lkotlinx/coroutines/flow/StateFlow;", "parsedItems", "", "Lcom/guzi/storage/domain/model/Item;", "autoSavePresets", "", "items", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "buildColMap", "", "", "", "headers", "buildItem", "Lkotlin/Pair;", "cols", "colIndex", "confirmImport", "parseCsv", "uri", "Landroid/net/Uri;", "parseFile", "reset", "splitCsvLine", "line", "ImportState", "app_release"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class CsvImportViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.guzi.storage.data.repository.ItemRepository itemRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.guzi.storage.data.repository.PresetOptionRepository presetOptionRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.guzi.storage.ui.csvimport.CsvImportViewModel.ImportState> _importState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.guzi.storage.ui.csvimport.CsvImportViewModel.ImportState> importState = null;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.guzi.storage.domain.model.Item> parsedItems;
    
    @javax.inject.Inject()
    public CsvImportViewModel(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.guzi.storage.data.repository.ItemRepository itemRepository, @org.jetbrains.annotations.NotNull()
    com.guzi.storage.data.repository.PresetOptionRepository presetOptionRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.guzi.storage.ui.csvimport.CsvImportViewModel.ImportState> getImportState() {
        return null;
    }
    
    /**
     * 读取并解析 CSV 文件，进入预览状态
     */
    public final void parseFile(@org.jetbrains.annotations.NotNull()
    android.net.Uri uri) {
    }
    
    /**
     * 确认导入解析结果
     */
    public final void confirmImport() {
    }
    
    public final void reset() {
    }
    
    private final kotlin.Pair<java.util.List<com.guzi.storage.domain.model.Item>, java.util.List<java.lang.String>> parseCsv(android.net.Uri uri) {
        return null;
    }
    
    /**
     * 构建列名 → 索引映射（忽略大小写，支持别名）
     */
    private final java.util.Map<java.lang.String, java.lang.Integer> buildColMap(java.util.List<java.lang.String> headers) {
        return null;
    }
    
    private final kotlin.Pair<com.guzi.storage.domain.model.Item, java.util.List<java.lang.String>> buildItem(java.util.List<java.lang.String> cols, java.util.Map<java.lang.String, java.lang.Integer> colIndex) {
        return null;
    }
    
    /**
     * 简单 CSV 行解析（处理引号内包含逗号的情况）
     */
    private final java.util.List<java.lang.String> splitCsvLine(java.lang.String line) {
        return null;
    }
    
    /**
     * 将导入数据中的新选项自动加入预设库
     */
    private final java.lang.Object autoSavePresets(java.util.List<com.guzi.storage.domain.model.Item> items, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b7\u0018\u00002\u00020\u0001:\u0005\u0003\u0004\u0005\u0006\u0007B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0005\b\t\n\u000b\f\u00a8\u0006\r"}, d2 = {"Lcom/guzi/storage/ui/csvimport/CsvImportViewModel$ImportState;", "", "()V", "Done", "Error", "Idle", "Loading", "Preview", "Lcom/guzi/storage/ui/csvimport/CsvImportViewModel$ImportState$Done;", "Lcom/guzi/storage/ui/csvimport/CsvImportViewModel$ImportState$Error;", "Lcom/guzi/storage/ui/csvimport/CsvImportViewModel$ImportState$Idle;", "Lcom/guzi/storage/ui/csvimport/CsvImportViewModel$ImportState$Loading;", "Lcom/guzi/storage/ui/csvimport/CsvImportViewModel$ImportState$Preview;", "app_release"})
    public static abstract class ImportState {
        
        private ImportState() {
            super();
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0087\b\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\u0002\u0010\u0007J\t\u0010\f\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0003J#\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u00d6\u0003J\t\u0010\u0013\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\u0014\u001a\u00020\u0006H\u00d6\u0001R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0015"}, d2 = {"Lcom/guzi/storage/ui/csvimport/CsvImportViewModel$ImportState$Done;", "Lcom/guzi/storage/ui/csvimport/CsvImportViewModel$ImportState;", "successCount", "", "errorLines", "", "", "(ILjava/util/List;)V", "getErrorLines", "()Ljava/util/List;", "getSuccessCount", "()I", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "toString", "app_release"})
        public static final class Done extends com.guzi.storage.ui.csvimport.CsvImportViewModel.ImportState {
            private final int successCount = 0;
            @org.jetbrains.annotations.NotNull()
            private final java.util.List<java.lang.String> errorLines = null;
            
            public Done(int successCount, @org.jetbrains.annotations.NotNull()
            java.util.List<java.lang.String> errorLines) {
            }
            
            public final int getSuccessCount() {
                return 0;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final java.util.List<java.lang.String> getErrorLines() {
                return null;
            }
            
            public final int component1() {
                return 0;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final java.util.List<java.lang.String> component2() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final com.guzi.storage.ui.csvimport.CsvImportViewModel.ImportState.Done copy(int successCount, @org.jetbrains.annotations.NotNull()
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
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0010"}, d2 = {"Lcom/guzi/storage/ui/csvimport/CsvImportViewModel$ImportState$Error;", "Lcom/guzi/storage/ui/csvimport/CsvImportViewModel$ImportState;", "message", "", "(Ljava/lang/String;)V", "getMessage", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_release"})
        public static final class Error extends com.guzi.storage.ui.csvimport.CsvImportViewModel.ImportState {
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
            public final com.guzi.storage.ui.csvimport.CsvImportViewModel.ImportState.Error copy(@org.jetbrains.annotations.NotNull()
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
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u00c7\n\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0013\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u00d6\u0003J\t\u0010\u0007\u001a\u00020\bH\u00d6\u0001J\t\u0010\t\u001a\u00020\nH\u00d6\u0001\u00a8\u0006\u000b"}, d2 = {"Lcom/guzi/storage/ui/csvimport/CsvImportViewModel$ImportState$Idle;", "Lcom/guzi/storage/ui/csvimport/CsvImportViewModel$ImportState;", "()V", "equals", "", "other", "", "hashCode", "", "toString", "", "app_release"})
        public static final class Idle extends com.guzi.storage.ui.csvimport.CsvImportViewModel.ImportState {
            @org.jetbrains.annotations.NotNull()
            public static final com.guzi.storage.ui.csvimport.CsvImportViewModel.ImportState.Idle INSTANCE = null;
            
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
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u00c7\n\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0013\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u00d6\u0003J\t\u0010\u0007\u001a\u00020\bH\u00d6\u0001J\t\u0010\t\u001a\u00020\nH\u00d6\u0001\u00a8\u0006\u000b"}, d2 = {"Lcom/guzi/storage/ui/csvimport/CsvImportViewModel$ImportState$Loading;", "Lcom/guzi/storage/ui/csvimport/CsvImportViewModel$ImportState;", "()V", "equals", "", "other", "", "hashCode", "", "toString", "", "app_release"})
        public static final class Loading extends com.guzi.storage.ui.csvimport.CsvImportViewModel.ImportState {
            @org.jetbrains.annotations.NotNull()
            public static final com.guzi.storage.ui.csvimport.CsvImportViewModel.ImportState.Loading INSTANCE = null;
            
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
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B!\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003\u00a2\u0006\u0002\u0010\u0007J\u000f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\u000f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003H\u00c6\u0003J)\u0010\r\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003H\u00c6\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u00d6\u0003J\t\u0010\u0012\u001a\u00020\u0013H\u00d6\u0001J\t\u0010\u0014\u001a\u00020\u0006H\u00d6\u0001R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\t\u00a8\u0006\u0015"}, d2 = {"Lcom/guzi/storage/ui/csvimport/CsvImportViewModel$ImportState$Preview;", "Lcom/guzi/storage/ui/csvimport/CsvImportViewModel$ImportState;", "items", "", "Lcom/guzi/storage/domain/model/Item;", "errorLines", "", "(Ljava/util/List;Ljava/util/List;)V", "getErrorLines", "()Ljava/util/List;", "getItems", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_release"})
        public static final class Preview extends com.guzi.storage.ui.csvimport.CsvImportViewModel.ImportState {
            @org.jetbrains.annotations.NotNull()
            private final java.util.List<com.guzi.storage.domain.model.Item> items = null;
            @org.jetbrains.annotations.NotNull()
            private final java.util.List<java.lang.String> errorLines = null;
            
            public Preview(@org.jetbrains.annotations.NotNull()
            java.util.List<com.guzi.storage.domain.model.Item> items, @org.jetbrains.annotations.NotNull()
            java.util.List<java.lang.String> errorLines) {
            }
            
            @org.jetbrains.annotations.NotNull()
            public final java.util.List<com.guzi.storage.domain.model.Item> getItems() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final java.util.List<java.lang.String> getErrorLines() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final java.util.List<com.guzi.storage.domain.model.Item> component1() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final java.util.List<java.lang.String> component2() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final com.guzi.storage.ui.csvimport.CsvImportViewModel.ImportState.Preview copy(@org.jetbrains.annotations.NotNull()
            java.util.List<com.guzi.storage.domain.model.Item> items, @org.jetbrains.annotations.NotNull()
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
    }
}