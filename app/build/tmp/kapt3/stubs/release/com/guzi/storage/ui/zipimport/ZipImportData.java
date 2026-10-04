package com.guzi.storage.ui.zipimport;

import android.content.Context;
import android.net.Uri;
import androidx.lifecycle.ViewModel;
import com.guzi.storage.data.repository.ItemRepository;
import com.guzi.storage.data.repository.PresetOptionRepository;
import com.guzi.storage.data.repository.TransactionRepository;
import com.guzi.storage.data.local.PipeListConverter;
import com.guzi.storage.domain.model.Item;
import com.guzi.storage.domain.model.Transaction;
import com.guzi.storage.domain.model.TransactionType;
import com.guzi.storage.util.CurrencyUtils;
import com.guzi.storage.util.DateUtils;
import com.guzi.storage.util.IdGenerator;
import com.guzi.storage.util.ImageFileManager;
import com.guzi.storage.util.ItemValueParser;
import com.guzi.storage.util.ZipExtractor;
import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.flow.StateFlow;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001B/\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0003\u00a2\u0006\u0002\u0010\tJ\u000f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\u000f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003H\u00c6\u0003J\u000f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\b0\u0003H\u00c6\u0003J9\u0010\u0015\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u00032\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0003H\u00c6\u0001J\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0019\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u001a\u001a\u00020\bH\u00d6\u0001R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0011\u0010\r\u001a\u00020\u000e8F\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000b\u00a8\u0006\u001b"}, d2 = {"Lcom/guzi/storage/ui/zipimport/ZipImportData;", "", "items", "", "Lcom/guzi/storage/ui/zipimport/ParsedItem;", "transactions", "Lcom/guzi/storage/ui/zipimport/ParsedTransactionData;", "errors", "", "(Ljava/util/List;Ljava/util/List;Ljava/util/List;)V", "getErrors", "()Ljava/util/List;", "getItems", "totalImageCount", "", "getTotalImageCount", "()I", "getTransactions", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "toString", "app_release"})
public final class ZipImportData {
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.guzi.storage.ui.zipimport.ParsedItem> items = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.guzi.storage.ui.zipimport.ParsedTransactionData> transactions = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> errors = null;
    
    public ZipImportData(@org.jetbrains.annotations.NotNull()
    java.util.List<com.guzi.storage.ui.zipimport.ParsedItem> items, @org.jetbrains.annotations.NotNull()
    java.util.List<com.guzi.storage.ui.zipimport.ParsedTransactionData> transactions, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> errors) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.guzi.storage.ui.zipimport.ParsedItem> getItems() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.guzi.storage.ui.zipimport.ParsedTransactionData> getTransactions() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getErrors() {
        return null;
    }
    
    public final int getTotalImageCount() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.guzi.storage.ui.zipimport.ParsedItem> component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.guzi.storage.ui.zipimport.ParsedTransactionData> component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.guzi.storage.ui.zipimport.ZipImportData copy(@org.jetbrains.annotations.NotNull()
    java.util.List<com.guzi.storage.ui.zipimport.ParsedItem> items, @org.jetbrains.annotations.NotNull()
    java.util.List<com.guzi.storage.ui.zipimport.ParsedTransactionData> transactions, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> errors) {
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