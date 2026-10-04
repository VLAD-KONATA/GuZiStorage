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

    // ===== 图片保存目录 =====

    private val _imageSaveDirectory = MutableStateFlow(
        prefs.getString(KEY_IMAGE_SAVE_DIRECTORY, DEFAULT_IMAGE_SAVE_DIRECTORY)
            ?: DEFAULT_IMAGE_SAVE_DIRECTORY
    )
    val imageSaveDirectory: Flow<String> = _imageSaveDirectory.asStateFlow()

    fun getImageSaveDirectory(): String = _imageSaveDirectory.value

    fun setImageSaveDirectory(path: String) {
        val normalized = path.trim().ifBlank { DEFAULT_IMAGE_SAVE_DIRECTORY }
        prefs.edit().putString(KEY_IMAGE_SAVE_DIRECTORY, normalized).apply()
        _imageSaveDirectory.value = normalized
    }

    // ===== 分组配置 =====

    private val _groupingConfig: MutableStateFlow<List<String>> = MutableStateFlow(
        loadGroupingConfig()
    )
    val groupingConfig: Flow<List<String>> = _groupingConfig.asStateFlow()

    fun getGroupingConfig(): List<String> = _groupingConfig.value

    fun setGroupingConfig(levels: List<String>) {
        val normalized = normalizeGroupingConfig(levels)
        val json = JSONArray(normalized).toString()
        prefs.edit().putString(KEY_GROUPING_CONFIG, json).apply()
        _groupingConfig.value = normalized
    }

    private fun loadGroupingConfig(): List<String> {
        val json = prefs.getString(KEY_GROUPING_CONFIG, null) ?: return DEFAULT_GROUPING
        return try {
            val arr = JSONArray(json)
            normalizeGroupingConfig((0 until arr.length()).map { arr.getString(it) })
        } catch (e: Exception) {
            DEFAULT_GROUPING
        }
    }

    private fun normalizeGroupingConfig(levels: List<String>): List<String> {
        val normalized = levels.filter { it != "version" }
        return normalized.ifEmpty { DEFAULT_GROUPING }
    }

    companion object {
        private const val KEY_AUTO_SAVE_PRESETS = "auto_save_presets"
        private const val KEY_IMAGE_SAVE_DIRECTORY = "image_save_directory"
        private const val KEY_GROUPING_CONFIG = "grouping_config"
        private val DEFAULT_GROUPING = listOf("ip_name")
        const val DEFAULT_IMAGE_SAVE_DIRECTORY = "/storage/emulated/0/Pictures/GoodsPic"
    }
}



