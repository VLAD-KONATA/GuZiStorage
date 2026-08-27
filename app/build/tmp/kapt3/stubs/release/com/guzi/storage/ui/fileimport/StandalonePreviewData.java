package com.guzi.storage.ui.fileimport;

import android.content.Context;
import android.net.Uri;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.guzi.storage.data.repository.ItemRepository;
import com.guzi.storage.data.repository.PresetOptionRepository;
import com.guzi.storage.data.repository.TransactionRepository;
import com.guzi.storage.domain.model.Item;
import com.guzi.storage.util.DateUtils;
import com.guzi.storage.util.IdGenerator;
import com.guzi.storage.domain.model.Transaction;
import com.guzi.storage.domain.model.TransactionType;
import com.guzi.storage.util.ImageFileManager;
import com.guzi.storage.util.ZipExtractor;
import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.flow.StateFlow;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b7\u0018\u00002\u00020\u0001:\u0003\b\t\nB\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002R\u0018\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007\u0082\u0001\u0003\u000b\f\r\u00a8\u0006\u000e"}, d2 = {"Lcom/guzi/storage/ui/fileimport/StandalonePreviewData;", "", "()V", "errors", "", "", "getErrors", "()Ljava/util/List;", "ImagesPreview", "ItemsPreview", "TransactionsPreview", "Lcom/guzi/storage/ui/fileimport/StandalonePreviewData$ImagesPreview;", "Lcom/guzi/storage/ui/fileimport/StandalonePreviewData$ItemsPreview;", "Lcom/guzi/storage/ui/fileimport/StandalonePreviewData$TransactionsPreview;", "app_release"})
public abstract class StandalonePreviewData {
    
    private StandalonePreviewData() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract java.util.List<java.lang.String> getErrors();
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B!\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003\u00a2\u0006\u0002\u0010\u0007J\u000f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\u000f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003H\u00c6\u0003J)\u0010\r\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003H\u00c6\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u00d6\u0003J\t\u0010\u0012\u001a\u00020\u0013H\u00d6\u0001J\t\u0010\u0014\u001a\u00020\u0006H\u00d6\u0001R\u001a\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\t\u00a8\u0006\u0015"}, d2 = {"Lcom/guzi/storage/ui/fileimport/StandalonePreviewData$ImagesPreview;", "Lcom/guzi/storage/ui/fileimport/StandalonePreviewData;", "groups", "", "Lcom/guzi/storage/ui/fileimport/ImageGroup;", "errors", "", "(Ljava/util/List;Ljava/util/List;)V", "getErrors", "()Ljava/util/List;", "getGroups", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_release"})
    public static final class ImagesPreview extends com.guzi.storage.ui.fileimport.StandalonePreviewData {
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.guzi.storage.ui.fileimport.ImageGroup> groups = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<java.lang.String> errors = null;
        
        public ImagesPreview(@org.jetbrains.annotations.NotNull()
        java.util.List<com.guzi.storage.ui.fileimport.ImageGroup> groups, @org.jetbrains.annotations.NotNull()
        java.util.List<java.lang.String> errors) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.guzi.storage.ui.fileimport.ImageGroup> getGroups() {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.util.List<java.lang.String> getErrors() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.guzi.storage.ui.fileimport.ImageGroup> component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<java.lang.String> component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.guzi.storage.ui.fileimport.StandalonePreviewData.ImagesPreview copy(@org.jetbrains.annotations.NotNull()
        java.util.List<com.guzi.storage.ui.fileimport.ImageGroup> groups, @org.jetbrains.annotations.NotNull()
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B!\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003\u00a2\u0006\u0002\u0010\u0007J\u000f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\u000f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003H\u00c6\u0003J)\u0010\r\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003H\u00c6\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u00d6\u0003J\t\u0010\u0012\u001a\u00020\u0013H\u00d6\u0001J\t\u0010\u0014\u001a\u00020\u0006H\u00d6\u0001R\u001a\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\t\u00a8\u0006\u0015"}, d2 = {"Lcom/guzi/storage/ui/fileimport/StandalonePreviewData$ItemsPreview;", "Lcom/guzi/storage/ui/fileimport/StandalonePreviewData;", "items", "", "Lcom/guzi/storage/ui/fileimport/ParsedItemData;", "errors", "", "(Ljava/util/List;Ljava/util/List;)V", "getErrors", "()Ljava/util/List;", "getItems", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_release"})
    public static final class ItemsPreview extends com.guzi.storage.ui.fileimport.StandalonePreviewData {
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.guzi.storage.ui.fileimport.ParsedItemData> items = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<java.lang.String> errors = null;
        
        public ItemsPreview(@org.jetbrains.annotations.NotNull()
        java.util.List<com.guzi.storage.ui.fileimport.ParsedItemData> items, @org.jetbrains.annotations.NotNull()
        java.util.List<java.lang.String> errors) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.guzi.storage.ui.fileimport.ParsedItemData> getItems() {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.util.List<java.lang.String> getErrors() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.guzi.storage.ui.fileimport.ParsedItemData> component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<java.lang.String> component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.guzi.storage.ui.fileimport.StandalonePreviewData.ItemsPreview copy(@org.jetbrains.annotations.NotNull()
        java.util.List<com.guzi.storage.ui.fileimport.ParsedItemData> items, @org.jetbrains.annotations.NotNull()
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B!\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003\u00a2\u0006\u0002\u0010\u0007J\u000f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\u000f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003H\u00c6\u0003J)\u0010\r\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003H\u00c6\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u00d6\u0003J\t\u0010\u0012\u001a\u00020\u0013H\u00d6\u0001J\t\u0010\u0014\u001a\u00020\u0006H\u00d6\u0001R\u001a\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\t\u00a8\u0006\u0015"}, d2 = {"Lcom/guzi/storage/ui/fileimport/StandalonePreviewData$TransactionsPreview;", "Lcom/guzi/storage/ui/fileimport/StandalonePreviewData;", "transactions", "", "Lcom/guzi/storage/domain/model/Transaction;", "errors", "", "(Ljava/util/List;Ljava/util/List;)V", "getErrors", "()Ljava/util/List;", "getTransactions", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_release"})
    public static final class TransactionsPreview extends com.guzi.storage.ui.fileimport.StandalonePreviewData {
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.guzi.storage.domain.model.Transaction> transactions = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<java.lang.String> errors = null;
        
        public TransactionsPreview(@org.jetbrains.annotations.NotNull()
        java.util.List<com.guzi.storage.domain.model.Transaction> transactions, @org.jetbrains.annotations.NotNull()
        java.util.List<java.lang.String> errors) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.guzi.storage.domain.model.Transaction> getTransactions() {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.util.List<java.lang.String> getErrors() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.guzi.storage.domain.model.Transaction> component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<java.lang.String> component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.guzi.storage.ui.fileimport.StandalonePreviewData.TransactionsPreview copy(@org.jetbrains.annotations.NotNull()
        java.util.List<com.guzi.storage.domain.model.Transaction> transactions, @org.jetbrains.annotations.NotNull()
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
}