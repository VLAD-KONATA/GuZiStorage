package com.guzi.storage.ui.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guzi.storage.data.repository.ItemRepository
import com.guzi.storage.data.repository.TransactionRepository
import com.guzi.storage.domain.model.Item
import com.guzi.storage.domain.model.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 交易记录页 ViewModel
 */
@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val itemRepository: ItemRepository
) : ViewModel() {

    private val _itemId = MutableStateFlow<String?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val item: StateFlow<Item?> = _itemId
        .filterNotNull()
        .flatMapLatest { id -> itemRepository.getItemByIdFlow(id) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    val transactions: StateFlow<List<Transaction>> = _itemId
        .filterNotNull()
        .flatMapLatest { id -> transactionRepository.getTransactionsByItemId(id) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // 待删除的交易 ID
    private val _pendingDeleteId = MutableStateFlow<String?>(null)
    val pendingDeleteId: StateFlow<String?> = _pendingDeleteId.asStateFlow()

    fun setItemId(id: String) {
        _itemId.value = id
    }

    fun requestDeleteTransaction(transactionId: String) {
        _pendingDeleteId.value = transactionId
    }

    fun dismissDeleteTransaction() {
        _pendingDeleteId.value = null
    }

    fun confirmDeleteTransaction() {
        viewModelScope.launch {
            val id = _pendingDeleteId.value ?: return@launch
            transactionRepository.deleteTransaction(id)
            _pendingDeleteId.value = null
        }
    }
}
