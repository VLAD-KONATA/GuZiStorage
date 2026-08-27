package com.guzi.storage.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guzi.storage.data.repository.ItemRepository
import com.guzi.storage.data.repository.TransactionRepository
import com.guzi.storage.domain.model.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 物品详情页 ViewModel
 */
@HiltViewModel
class DetailViewModel @Inject constructor(
    private val itemRepository: ItemRepository,
    private val transactionRepository: TransactionRepository
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
    val transactionCount: StateFlow<Int> = _itemId
        .filterNotNull()
        .flatMapLatest { id ->
            transactionRepository.getTransactionsByItemId(id).map { it.size }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    // 控制删除确认弹窗
    private val _showDeleteDialog = MutableStateFlow(false)
    val showDeleteDialog: StateFlow<Boolean> = _showDeleteDialog.asStateFlow()

    // 删除完成后触发导航返回
    private val _deleteComplete = MutableStateFlow(false)
    val deleteComplete: StateFlow<Boolean> = _deleteComplete.asStateFlow()

    fun setItemId(id: String) {
        _itemId.value = id
    }

    fun requestDelete() {
        _showDeleteDialog.value = true
    }

    fun dismissDelete() {
        _showDeleteDialog.value = false
    }

    fun confirmDelete() {
        viewModelScope.launch {
            val id = _itemId.value ?: return@launch
            itemRepository.deleteItem(id)
            _showDeleteDialog.value = false
            _deleteComplete.value = true
        }
    }

    fun clearDeleteComplete() {
        _deleteComplete.value = false
    }

    /**
     * 将指定图片设置为首图（移动到图片列表第一位）
     */
    fun setFirstImage(imagePath: String) {
        viewModelScope.launch {
            val id = _itemId.value ?: return@launch
            val item = itemRepository.getItemById(id) ?: return@launch
            val paths = item.imagePaths
            if (paths.size < 2 || paths.firstOrNull() == imagePath) return@launch
            itemRepository.updateItem(
                item.copy(imagePaths = listOf(imagePath) + (paths - imagePath))
            )
        }
    }
}
