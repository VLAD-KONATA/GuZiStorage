package com.guzi.storage.ui.fileimport;

import android.content.Context;
import android.net.Uri;
import androidx.lifecycle.SavedStateHandle;
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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2 = {"Lcom/guzi/storage/ui/fileimport/StandaloneImportType;", "", "(Ljava/lang/String;I)V", "ITEMS_CSV", "TRANSACTIONS_CSV", "IMAGE_ZIP", "app_debug"})
public enum StandaloneImportType {
    /*public static final*/ ITEMS_CSV /* = new ITEMS_CSV() */,
    /*public static final*/ TRANSACTIONS_CSV /* = new TRANSACTIONS_CSV() */,
    /*public static final*/ IMAGE_ZIP /* = new IMAGE_ZIP() */;
    
    StandaloneImportType() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.guzi.storage.ui.fileimport.StandaloneImportType> getEntries() {
        return null;
    }
}