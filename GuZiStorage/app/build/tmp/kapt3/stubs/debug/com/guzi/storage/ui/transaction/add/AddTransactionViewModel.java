package com.guzi.storage.ui.transaction.add;

import androidx.lifecycle.ViewModel;
import com.guzi.storage.data.repository.ItemRepository;
import com.guzi.storage.data.repository.PresetOptionRepository;
import com.guzi.storage.data.repository.UserPreferencesRepository;
import com.guzi.storage.data.repository.TransactionRepository;
import com.guzi.storage.domain.model.Transaction;
import com.guzi.storage.domain.model.TransactionType;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import java.util.UUID;
import javax.inject.Inject;

/**
 * 添加/编辑交易记录 ViewModel
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\'\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u000e\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u000eJ\u0006\u0010\u001e\u001a\u00020\u001cJ\u0016\u0010\u001f\u001a\u00020\u001c2\u0006\u0010 \u001a\u00020\u000e2\u0006\u0010!\u001a\u00020\u000eJ\b\u0010\"\u001a\u00020\u001cH\u0002J\u000e\u0010#\u001a\u00020\u001c2\u0006\u0010$\u001a\u00020\u000eJ\u0006\u0010%\u001a\u00020\u001cJ\u000e\u0010&\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u000eJ\u000e\u0010\'\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u000eJ\u000e\u0010(\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u000eJ\u000e\u0010)\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u000eJ\u000e\u0010*\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u000eJ\u000e\u0010+\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u000eJ\b\u0010,\u001a\u00020-H\u0002R\u001a\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0011\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00120\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0017\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00100\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u0019\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006."}, d2 = {"Lcom/guzi/storage/ui/transaction/add/AddTransactionViewModel;", "Landroidx/lifecycle/ViewModel;", "transactionRepository", "Lcom/guzi/storage/data/repository/TransactionRepository;", "presetOptionRepository", "Lcom/guzi/storage/data/repository/PresetOptionRepository;", "itemRepository", "Lcom/guzi/storage/data/repository/ItemRepository;", "userPreferencesRepository", "Lcom/guzi/storage/data/repository/UserPreferencesRepository;", "(Lcom/guzi/storage/data/repository/TransactionRepository;Lcom/guzi/storage/data/repository/PresetOptionRepository;Lcom/guzi/storage/data/repository/ItemRepository;Lcom/guzi/storage/data/repository/UserPreferencesRepository;)V", "_channelOptions", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "", "_formState", "Lcom/guzi/storage/ui/transaction/add/TransactionFormState;", "_saveResult", "Lcom/guzi/storage/ui/transaction/add/TransactionSaveResult;", "channelOptions", "Lkotlinx/coroutines/flow/StateFlow;", "getChannelOptions", "()Lkotlinx/coroutines/flow/StateFlow;", "formState", "getFormState", "saveResult", "getSaveResult", "addNewChannelOption", "", "value", "clearSaveResult", "initForAdd", "itemId", "itemNameCn", "loadPresetOptions", "loadTransaction", "transactionId", "save", "updateChannel", "updateDate", "updateNote", "updatePrice", "updateQuantity", "updateType", "validate", "", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class AddTransactionViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.guzi.storage.data.repository.TransactionRepository transactionRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.guzi.storage.data.repository.PresetOptionRepository presetOptionRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.guzi.storage.data.repository.ItemRepository itemRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.guzi.storage.data.repository.UserPreferencesRepository userPreferencesRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.guzi.storage.ui.transaction.add.TransactionFormState> _formState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.guzi.storage.ui.transaction.add.TransactionFormState> formState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<java.lang.String>> _channelOptions = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<java.lang.String>> channelOptions = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.guzi.storage.ui.transaction.add.TransactionSaveResult> _saveResult = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.guzi.storage.ui.transaction.add.TransactionSaveResult> saveResult = null;
    
    @javax.inject.Inject()
    public AddTransactionViewModel(@org.jetbrains.annotations.NotNull()
    com.guzi.storage.data.repository.TransactionRepository transactionRepository, @org.jetbrains.annotations.NotNull()
    com.guzi.storage.data.repository.PresetOptionRepository presetOptionRepository, @org.jetbrains.annotations.NotNull()
    com.guzi.storage.data.repository.ItemRepository itemRepository, @org.jetbrains.annotations.NotNull()
    com.guzi.storage.data.repository.UserPreferencesRepository userPreferencesRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.guzi.storage.ui.transaction.add.TransactionFormState> getFormState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<java.lang.String>> getChannelOptions() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.guzi.storage.ui.transaction.add.TransactionSaveResult> getSaveResult() {
        return null;
    }
    
    private final void loadPresetOptions() {
    }
    
    /**
     * 初始化：设置关联物品 ID 和物品名称
     */
    public final void initForAdd(@org.jetbrains.annotations.NotNull()
    java.lang.String itemId, @org.jetbrains.annotations.NotNull()
    java.lang.String itemNameCn) {
    }
    
    /**
     * 加载已有记录（编辑模式）
     */
    public final void loadTransaction(@org.jetbrains.annotations.NotNull()
    java.lang.String transactionId) {
    }
    
    public final void updateType(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    public final void updatePrice(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    public final void updateChannel(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    public final void updateQuantity(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    public final void updateDate(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    public final void updateNote(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    public final void addNewChannelOption(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    private final boolean validate() {
        return false;
    }
    
    public final void save() {
    }
    
    public final void clearSaveResult() {
    }
}