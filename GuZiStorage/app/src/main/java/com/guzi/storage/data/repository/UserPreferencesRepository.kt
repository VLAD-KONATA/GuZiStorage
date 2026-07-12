package com.guzi.storage.data.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.json.JSONArray
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 用户偏好设置仓库 - 基于 SharedPreferences
 */
@Singleton
class UserPreferencesRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    // ===== 自动保存预设 =====

    private val _autoSavePresetsEnabled = MutableStateFlow(
        prefs.getBoolean(KEY_AUTO_SAVE_PRESETS, true)
    )
    val autoSavePresetsEnabled: Flow<Boolean> = _autoSavePresetsEnabled.asStateFlow()

    fun isAutoSavePresetsEnabled(): Boolean = _autoSavePresetsEnabled.value

    fun setAutoSavePresetsEnabled(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_AUTO_SAVE_PRESETS, enabled).apply()
        _autoSavePresetsEnabled.value = enabled
    }

    // ===== 分组配置 =====

    private val _groupingConfig: MutableStateFlow<List<String>> = MutableStateFlow(
        loadGroupingConfig()
    )
    val groupingConfig: Flow<List<String>> = _groupingConfig.asStateFlow()

    fun getGroupingConfig(): List<String> = _groupingConfig.value

    fun setGroupingConfig(levels: List<String>) {
        val json = JSONArray(levels).toString()
        prefs.edit().putString(KEY_GROUPING_CONFIG, json).apply()
        _groupingConfig.value = levels
    }

    private fun loadGroupingConfig(): List<String> {
        val json = prefs.getString(KEY_GROUPING_CONFIG, null) ?: return DEFAULT_GROUPING
        return try {
            val arr = JSONArray(json)
            (0 until arr.length()).map { arr.getString(it) }
        } catch (e: Exception) {
            DEFAULT_GROUPING
        }
    }

    companion object {
        private const val KEY_AUTO_SAVE_PRESETS = "auto_save_presets"
        private const val KEY_GROUPING_CONFIG = "grouping_config"
        private val DEFAULT_GROUPING = listOf("ip_name")
    }
}



