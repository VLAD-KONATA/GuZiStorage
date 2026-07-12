package com.guzi.storage.ui.transaction;

import androidx.lifecycle.ViewModel;
import com.guzi.storage.data.repository.ItemRepository;
import com.guzi.storage.data.repository.TransactionRepository;
import com.guzi.storage.domain.model.Item;
import com.guzi.storage.domain.model.Transaction;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.ExperimentalCoroutinesApi;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

/**
 * 交易记录页 ViewModel
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\u0019\u001a\u00020\u001aJ\u0006\u0010\u001b\u001a\u00020\u001aJ\u000e\u0010\u001c\u001a\u00020\u001a2\u0006\u0010\u001d\u001a\u00020\tJ\u000e\u0010\u001e\u001a\u00020\u001a2\u0006\u0010\u001f\u001a\u00020\tR\u0016\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001f\u0010\u000b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\r0\f\u00a2\u0006\u000e\n\u0000\u0012\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u0012\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0011R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R#\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\u00150\f\u00a2\u0006\u000e\n\u0000\u0012\u0004\b\u0017\u0010\u000f\u001a\u0004\b\u0018\u0010\u0011\u00a8\u0006 "}, d2 = {"Lcom/guzi/storage/ui/transaction/TransactionViewModel;", "Landroidx/lifecycle/ViewModel;", "transactionRepository", "Lcom/guzi/storage/data/repository/TransactionRepository;", "itemRepository", "Lcom/guzi/storage/data/repository/ItemRepository;", "(Lcom/guzi/storage/data/repository/TransactionRepository;Lcom/guzi/storage/data/repository/ItemRepository;)V", "_itemId", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_pendingDeleteId", "item", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/guzi/storage/domain/model/Item;", "getItem$annotations", "()V", "getItem", "()Lkotlinx/coroutines/flow/StateFlow;", "pendingDeleteId", "getPendingDeleteId", "transactions", "", "Lcom/guzi/storage/domain/model/Transaction;", "getTransactions$annotations", "getTransactions", "confirmDeleteTransaction", "", "dismissDeleteTransaction", "requestDeleteTransaction", "transactionId", "setItemId", "id", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class TransactionViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.guzi.storage.data.repository.TransactionRepository transactionRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.guzi.storage.data.repository.ItemRepository itemRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _itemId = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.guzi.storage.domain.model.Item> item = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.guzi.storage.domain.model.Transaction>> transactions = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _pendingDeleteId = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> pendingDeleteId = null;
    
    @javax.inject.Inject()
    public TransactionViewModel(@org.jetbrains.annotations.NotNull()
    com.guzi.storage.data.repository.TransactionRepository transactionRepository, @org.jetbrains.annotations.NotNull()
    com.guzi.storage.data.repository.ItemRepository itemRepository) {
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
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.guzi.storage.domain.model.Transaction>> getTransactions() {
        return null;
    }
    
    @kotlin.OptIn(markerClass = {kotlinx.coroutines.ExperimentalCoroutinesApi.class})
    @java.lang.Deprecated()
    public static void getTransactions$annotations() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getPendingDeleteId() {
        return null;
    }
    
    public final void setItemId(@org.jetbrains.annotations.NotNull()
    java.lang.String id) {
    }
    
    public final void requestDeleteTransaction(@org.jetbrains.annotations.NotNull()
    java.lang.String transactionId) {
    }
    
    public final void dismissDeleteTransaction() {
    }
    
    public final void confirmDeleteTransaction() {
    }
}