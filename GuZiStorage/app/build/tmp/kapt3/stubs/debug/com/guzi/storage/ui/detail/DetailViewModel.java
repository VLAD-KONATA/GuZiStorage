package com.guzi.storage.ui.detail;

import androidx.lifecycle.ViewModel;
import com.guzi.storage.data.repository.ItemRepository;
import com.guzi.storage.data.repository.TransactionRepository;
import com.guzi.storage.domain.model.Item;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.ExperimentalCoroutinesApi;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

/**
 * 物品详情页 ViewModel
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\u001c\u001a\u00020\u001dJ\u0006\u0010\u001e\u001a\u00020\u001dJ\u0006\u0010\u001f\u001a\u00020\u001dJ\u0006\u0010 \u001a\u00020\u001dJ\u000e\u0010!\u001a\u00020\u001d2\u0006\u0010\"\u001a\u00020\u000bR\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\t0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001f\u0010\u0011\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u000e\u00a2\u0006\u000e\n\u0000\u0012\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0010R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\t0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0010R\u001d\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u000e\u00a2\u0006\u000e\n\u0000\u0012\u0004\b\u001a\u0010\u0014\u001a\u0004\b\u001b\u0010\u0010R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006#"}, d2 = {"Lcom/guzi/storage/ui/detail/DetailViewModel;", "Landroidx/lifecycle/ViewModel;", "itemRepository", "Lcom/guzi/storage/data/repository/ItemRepository;", "transactionRepository", "Lcom/guzi/storage/data/repository/TransactionRepository;", "(Lcom/guzi/storage/data/repository/ItemRepository;Lcom/guzi/storage/data/repository/TransactionRepository;)V", "_deleteComplete", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_itemId", "", "_showDeleteDialog", "deleteComplete", "Lkotlinx/coroutines/flow/StateFlow;", "getDeleteComplete", "()Lkotlinx/coroutines/flow/StateFlow;", "item", "Lcom/guzi/storage/domain/model/Item;", "getItem$annotations", "()V", "getItem", "showDeleteDialog", "getShowDeleteDialog", "transactionCount", "", "getTransactionCount$annotations", "getTransactionCount", "clearDeleteComplete", "", "confirmDelete", "dismissDelete", "requestDelete", "setItemId", "id", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class DetailViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.guzi.storage.data.repository.ItemRepository itemRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.guzi.storage.data.repository.TransactionRepository transactionRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _itemId = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.guzi.storage.domain.model.Item> item = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> transactionCount = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _showDeleteDialog = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> showDeleteDialog = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _deleteComplete = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> deleteComplete = null;
    
    @javax.inject.Inject()
    public DetailViewModel(@org.jetbrains.annotations.NotNull()
    com.guzi.storage.data.repository.ItemRepository itemRepository, @org.jetbrains.annotations.NotNull()
    com.guzi.storage.data.repository.TransactionRepository transactionRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.guzi.storage.domain.model.Item> getItem() {
        return null;
    }
    
    @kotlin.OptIn(markerClass = {kotlinx.coroutines.ExperimentalCoroutinesApi.class})
    @java.lang.Deprecated()
    public static void getItem$annotations() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> getTransactionCount() {
        return null;
    }
    
    @kotlin.OptIn(markerClass = {kotlinx.coroutines.ExperimentalCoroutinesApi.class})
    @java.lang.Deprecated()
    public static void getTransactionCount$annotations() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> getShowDeleteDialog() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> getDeleteComplete() {
        return null;
    }
    
    public final void setItemId(@org.jetbrains.annotations.NotNull()
    java.lang.String id) {
    }
    
    public final void requestDelete() {
    }
    
    public final void dismissDelete() {
    }
    
    public final void confirmDelete() {
    }
    
    public final void clearDeleteComplete() {
    }
}