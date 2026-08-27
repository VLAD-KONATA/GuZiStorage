package com.guzi.storage.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guzi.storage.data.repository.PresetOptionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 预设选项管理 ViewModel
 *
 * 管理某个属性（如 ip_name、category 等）下的选项列表
 * 支持新增、单条删除和长按批量删除
 */
@HiltViewModel
class PresetManageViewModel @Inject constructor(
    private val presetOptionRepository: PresetOptionRepository
) : ViewModel() {

    private val _attributeName = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    val options: StateFlow<List<String>> = _attributeName
        .filter { it.isNotEmpty() }
        .flatMapLatest { name -> presetOptionRepository.getOptions(name) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // ===== 单条删除 =====

    private val _pendingDeleteValue = MutableStateFlow<String?>(null)
    val pendingDeleteValue: StateFlow<String?> = _pendingDeleteValue.asStateFlow()

    // ===== 批量选择状态 =====

    private val _selectedValues = MutableStateFlow<Set<String>>(emptySet())
    val selectedValues: StateFlow<Set<String>> = _selectedValues.asStateFlow()

    val isSelectionMode: StateFlow<Boolean> = _selectedValues
        .map { it.isNotEmpty() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    private val _showBatchDeleteDialog = MutableStateFlow(false)
    val showBatchDeleteDialog: StateFlow<Boolean> = _showBatchDeleteDialog.asStateFlow()

    // ===== 公开方法 =====

    fun setAttributeName(name: String) {
        _attributeName.value = name
    }

    fun addOption(value: String) {
        if (value.isBlank()) return
        viewModelScope.launch {
            presetOptionRepository.addOption(_attributeName.value, value.trim())
        }
    }

    // 单条删除
    fun requestDelete(value: String) {
        _pendingDeleteValue.value = value
    }

    fun dismissDelete() {
        _pendingDeleteValue.value = null
    }

    fun confirmDelete() {
        viewModelScope.launch {
            val value = _pendingDeleteValue.value ?: return@launch
            presetOptionRepository.removeOption(_attributeName.value, value)
            _pendingDeleteValue.value = null
        }
    }

    // 批量选择
    fun toggleSelection(value: String) {
        val current = _selectedValues.value.toMutableSet()
        if (current.contains(value)) current.remove(value) else current.add(value)
        _selectedValues.value = current
    }

    fun selectAll() {
        _selectedValues.value = options.value.toSet()
    }

    fun clearSelection() {
        _selectedValues.value = emptySet()
    }

    fun requestBatchDelete() {
        if (_selectedValues.value.isNotEmpty()) {
            _showBatchDeleteDialog.value = true
        }
    }

    fun dismissBatchDelete() {
        _showBatchDeleteDialog.value = false
    }

    fun confirmBatchDelete() {
        viewModelScope.launch {
            _selectedValues.value.forEach { value ->
                presetOptionRepository.removeOption(_attributeName.value, value)
            }
            _selectedValues.value = emptySet()
            _showBatchDeleteDialog.value = false
        }
    }
}

