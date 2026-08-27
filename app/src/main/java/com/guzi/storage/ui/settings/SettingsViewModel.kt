package com.guzi.storage.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guzi.storage.data.repository.UserPreferencesRepository
import com.guzi.storage.util.BackupManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

/**
 * 设置页 ViewModel
 */
@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val backupManager: BackupManager
) : ViewModel() {

    val autoSavePresetsEnabled: StateFlow<Boolean> =
        userPreferencesRepository.autoSavePresetsEnabled
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = userPreferencesRepository.isAutoSavePresetsEnabled()
            )

    fun setAutoSavePresetsEnabled(enabled: Boolean) {
        userPreferencesRepository.setAutoSavePresetsEnabled(enabled)
    }

    // ===== 数据导出 =====

    private val _exportState = MutableStateFlow<ExportState>(ExportState.Idle)
    val exportState: StateFlow<ExportState> = _exportState.asStateFlow()

    fun exportBackup() {
        if (_exportState.value is ExportState.InProgress) return
        viewModelScope.launch {
            _exportState.value = ExportState.InProgress
            try {
                val file = backupManager.createBackupFile()
                _exportState.value = ExportState.Ready(file)
            } catch (e: Exception) {
                _exportState.value = ExportState.Error(e.message ?: "导出失败")
            }
        }
    }

    fun clearExportState() {
        _exportState.value = ExportState.Idle
    }
}

sealed class ExportState {
    data object Idle : ExportState()
    data object InProgress : ExportState()
    data class Ready(val file: File) : ExportState()
    data class Error(val message: String) : ExportState()
}


