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
import com.guzi.storage.util.ImageFileManager;
import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.flow.StateFlow;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.UUID;
import java.util.zip.ZipInputStream;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\f\u001a\u00020\rH\u00d6\u0001J\t\u0010\u000e\u001a\u00020\u000fH\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0010"}, d2 = {"Lcom/guzi/storage/ui/zipimport/ParsedTransactionData;", "", "transaction", "Lcom/guzi/storage/domain/model/Transaction;", "(Lcom/guzi/storage/domain/model/Transaction;)V", "getTransaction", "()Lcom/guzi/storage/domain/model/Transaction;", "component1", "copy", "equals", "", "other", "hashCode", "", "toString", "", "app_debug"})
public final class ParsedTransactionData {
    @org.jetbrains.annotations.NotNull()
    private final com.guzi.storage.domain.model.Transaction transaction = null;
    
    public ParsedTransactionData(@org.jetbrains.annotations.NotNull()
    com.guzi.storage.domain.model.Transaction transaction) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.guzi.storage.domain.model.Transaction getTransaction() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.guzi.storage.domain.model.Transaction component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.guzi.storage.ui.zipimport.ParsedTransactionData copy(@org.jetbrains.annotations.NotNull()
    com.guzi.storage.domain.model.Transaction transaction) {
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