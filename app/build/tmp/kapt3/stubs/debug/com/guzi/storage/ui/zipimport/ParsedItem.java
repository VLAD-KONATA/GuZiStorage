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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\u0002\u0010\tJ\t\u0010\u0010\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0011\u001a\u00020\u0005H\u00c6\u0003J\u000f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u00c6\u0003J-\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u00c6\u0001J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0017\u001a\u00020\u0018H\u00d6\u0001J\t\u0010\u0019\u001a\u00020\u0003H\u00d6\u0001R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u001a"}, d2 = {"Lcom/guzi/storage/ui/zipimport/ParsedItem;", "", "tempId", "", "item", "Lcom/guzi/storage/domain/model/Item;", "imageFiles", "", "Ljava/io/File;", "(Ljava/lang/String;Lcom/guzi/storage/domain/model/Item;Ljava/util/List;)V", "getImageFiles", "()Ljava/util/List;", "getItem", "()Lcom/guzi/storage/domain/model/Item;", "getTempId", "()Ljava/lang/String;", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
public final class ParsedItem {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String tempId = null;
    @org.jetbrains.annotations.NotNull()
    private final com.guzi.storage.domain.model.Item item = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.io.File> imageFiles = null;
    
    public ParsedItem(@org.jetbrains.annotations.NotNull()
    java.lang.String tempId, @org.jetbrains.annotations.NotNull()
    com.guzi.storage.domain.model.Item item, @org.jetbrains.annotations.NotNull()
    java.util.List<? extends java.io.File> imageFiles) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTempId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.guzi.storage.domain.model.Item getItem() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.io.File> getImageFiles() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.guzi.storage.domain.model.Item component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.io.File> component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.guzi.storage.ui.zipimport.ParsedItem copy(@org.jetbrains.annotations.NotNull()
    java.lang.String tempId, @org.jetbrains.annotations.NotNull()
    com.guzi.storage.domain.model.Item item, @org.jetbrains.annotations.NotNull()
    java.util.List<? extends java.io.File> imageFiles) {
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