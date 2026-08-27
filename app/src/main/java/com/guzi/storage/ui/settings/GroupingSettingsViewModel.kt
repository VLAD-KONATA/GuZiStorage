package com.guzi.storage.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guzi.storage.data.repository.UserPreferencesRepository
import com.guzi.storage.ui.GROUPABLE_ATTRIBUTES
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class GroupingSettingsViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    val groupingConfig: StateFlow<List<String>> =
        userPreferencesRepository.groupingConfig
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = userPreferencesRepository.getGroupingConfig()
            )

    fun addLevel(attrName: String) {
        val current = groupingConfig.value.toMutableList()
        if (attrName !in current && current.size < MAX_LEVELS) {
            current.add(attrName)
            userPreferencesRepository.setGroupingConfig(current)
        }
    }

    fun removeLevel(index: Int) {
        val current = groupingConfig.value.toMutableList()
        if (index in current.indices) {
            current.removeAt(index)
            userPreferencesRepository.setGroupingConfig(current)
        }
    }

    fun moveUp(index: Int) {
        if (index <= 0) return
        val current = groupingConfig.value.toMutableList()
        if (index !in current.indices) return
        val tmp = current[index - 1]
        current[index - 1] = current[index]
        current[index] = tmp
        userPreferencesRepository.setGroupingConfig(current)
    }

    fun moveDown(index: Int) {
        val current = groupingConfig.value.toMutableList()
        if (index !in current.indices || index >= current.size - 1) return
        val tmp = current[index + 1]
        current[index + 1] = current[index]
        current[index] = tmp
        userPreferencesRepository.setGroupingConfig(current)
    }

    fun resetToDefault() {
        userPreferencesRepository.setGroupingConfig(listOf("ip_name"))
    }

    fun getAvailableAttributes(): List<Pair<String, String>> {
        val used = groupingConfig.value.toSet()
        return GROUPABLE_ATTRIBUTES.filter { it.first !in used }
    }

    companion object {
        const val MAX_LEVELS = 5
    }
}
