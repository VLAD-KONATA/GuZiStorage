package com.guzi.storage.ui.transaction.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guzi.storage.data.repository.ItemRepository
import com.guzi.storage.data.repository.PresetOptionRepository
import com.guzi.storage.data.repository.UserPreferencesRepository
import com.guzi.storage.data.repository.TransactionRepository
import com.guzi.storage.domain.model.Transaction
import com.guzi.storage.domain.model.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.guzi.storage.util.IdGenerator
import javax.inject.Inject

/**
 * 添加/编辑交易记录 ViewModel
 */
@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val presetOptionRepository: PresetOptionRepository,
    private val itemRepository: ItemRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    // ===== 表单状态 =====
    private val _formState = MutableStateFlow(TransactionFormState())
    val formState: StateFlow<TransactionFormState> = _formState.asStateFlow()

    // ===== 渠道预设选项 =====
    private val _channelOptions = MutableStateFlow<List<String>>(emptyList())
    val channelOptions: StateFlow<List<String>> = _channelOptions.asStateFlow()

    // ===== 保存结果 =====
    private val _saveResult = MutableStateFlow<TransactionSaveResult?>(null)
    val saveResult: StateFlow<TransactionSaveResult?> = _saveResult.asStateFlow()

    init {
        loadPresetOptions()
    }

    private fun loadPresetOptions() {
        viewModelScope.launch {
            _channelOptions.value = presetOptionRepository.getOptionValues("channel")
        }
    }

    /**
     * 初始化：设置关联物品 ID 和物品名称
     */
    fun initForAdd(itemId: String, itemNameCn: String) {
        _formState.value = _formState.value.copy(
            itemId = itemId,
            nameCn = itemNameCn,
            isEditMode = false
        )
        // 名称为空时自动从数据库加载
        if (itemNameCn.isBlank()) {
            viewModelScope.launch {
                val item = itemRepository.getItemById(itemId)
                if (item != null) {
                    _formState.value = _formState.value.copy(nameCn = item.nameCn)
                }
            }
        }
    }

    /**
     * 加载已有记录（编辑模式）
     */
    fun loadTransaction(transactionId: String) {
        viewModelScope.launch {
            val t = transactionRepository.getTransactionById(transactionId) ?: return@launch
            _formState.value = TransactionFormState(
                transactionId = t.id,
                itemId = t.itemId,
                nameCn = t.nameCn,
                type = t.type.value,
                price = t.price.toString(),
                channel = t.channel,
                quantity = t.quantity.toString(),
                date = t.date ?: "",
                note = t.note ?: "",
                isEditMode = true
            )
        }
    }

    // ===== 表单更新 =====

    fun updateType(value: String) {
        _formState.value = _formState.value.copy(type = value)
    }

    fun updatePrice(value: String) {
        _formState.value = _formState.value.copy(price = value)
    }

    fun updateChannel(value: String) {
        _formState.value = _formState.value.copy(channel = value)
    }

    fun updateQuantity(value: String) {
        _formState.value = _formState.value.copy(quantity = value)
    }

    fun updateDate(value: String) {
        _formState.value = _formState.value.copy(date = value)
    }

    fun updateNote(value: String) {
        _formState.value = _formState.value.copy(note = value)
    }

    fun addNewChannelOption(value: String) = Unit // 已在 save() 后统一处理

    // ===== 校验 =====

    private fun validate(): Boolean {
        val state = _formState.value
        val errors = mutableMapOf<String, String>()

        if (state.price.isBlank()) {
            errors["price"] = "价格不能为空"
        } else if (state.price.toDoubleOrNull() == null) {
            errors["price"] = "价格必须为数字"
        }

        if (state.channel.isBlank()) {
            errors["channel"] = "渠道不能为空"
        }

        state.quantity.toIntOrNull()?.let { q ->
            if (q < 1) errors["quantity"] = "数量至少为1"
        } ?: run {
            errors["quantity"] = "数量必须为整数"
        }

        _formState.value = state.copy(validationErrors = errors)
        return errors.isEmpty()
    }

    // ===== 保存 =====

    fun save() {
        if (!validate()) return

        viewModelScope.launch {
            try {
                val state = _formState.value
                val transaction = Transaction(
                    id = state.transactionId,
                    itemId = state.itemId,
                    nameCn = state.nameCn,
                    type = TransactionType.fromValue(state.type),
                    price = state.price.toDouble(),
                    channel = state.channel,
                    quantity = state.quantity.toIntOrNull() ?: 1,
                    date = state.date.ifBlank { null },
                    note = state.note.ifBlank { null }
                )

                if (state.isEditMode) {
                    transactionRepository.updateTransaction(transaction)
                } else {
                    transactionRepository.addTransaction(transaction)
                }

                // 保存成功后，如果开启了自动添加预设，则将渠道新内容写入
                if (state.channel.isNotBlank() && userPreferencesRepository.isAutoSavePresetsEnabled()) {
                    val existingChannels = presetOptionRepository.getOptionValues("channel")
                    if (state.channel !in existingChannels) {
                        presetOptionRepository.addOption("channel", state.channel)
                        loadPresetOptions()
                    }
                }

                _saveResult.value = TransactionSaveResult.Success
            } catch (e: Exception) {
                _saveResult.value = TransactionSaveResult.Error(e.message ?: "保存失败")
            }
        }
    }

    fun clearSaveResult() {
        _saveResult.value = null
    }
}

/**
 * 交易表单状态
 */
data class TransactionFormState(
    val transactionId: String = IdGenerator.generate(),
    val itemId: String = "",
    val nameCn: String = "",
    val type: String = TransactionType.BUY.value,
    val price: String = "",
    val channel: String = "",
    val quantity: String = "1",
    val date: String = "",
    val note: String = "",
    val isEditMode: Boolean = false,
    val validationErrors: Map<String, String> = emptyMap()
)

/**
 * 保存结果
 */
sealed class TransactionSaveResult {
    data object Success : TransactionSaveResult()
    data class Error(val message: String) : TransactionSaveResult()
}
