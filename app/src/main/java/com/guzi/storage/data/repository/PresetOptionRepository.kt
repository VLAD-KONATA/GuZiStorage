package com.guzi.storage.data.repository

import com.guzi.storage.data.local.dao.PresetOptionDao
import com.guzi.storage.data.local.entity.PresetOptionEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 预设选项仓库 - 管理各属性的可选值
 *
 * 支持的属性标识（attributeName）:
 * - ip_name: IP名
 * - producer: 出品商
 * - origin_places: 出品地
 * - sale_format: 发售形式
 * - sell_method: 售卖方式
 * - category: 种类
 * - sizes: 尺寸
 * - materials: 材质
 * - characters: 相关角色
 * - art_types: 柄图种类
 * - channel: 渠道（交易记录用）
 * - type: 购入/卖出（交易记录用）
 */
@Singleton
class PresetOptionRepository @Inject constructor(
    private val presetOptionDao: PresetOptionDao
) {

    /**
     * 获取指定属性的选项列表（Flow 响应式）
     */
    fun getOptions(attributeName: String): Flow<List<String>> =
        presetOptionDao.getByAttribute(attributeName).map { entities ->
            entities.map { it.optionValue }
        }

    /**
     * 获取指定属性的选项列表（一次性）
     */
    suspend fun getOptionValues(attributeName: String): List<String> =
        presetOptionDao.getOptionValues(attributeName)

    /**
     * 添加新选项（如果不存在则添加）
     */
    suspend fun addOption(attributeName: String, optionValue: String) {
        if (optionValue.isBlank()) return
        presetOptionDao.insert(
            PresetOptionEntity(
                attributeName = attributeName,
                optionValue = optionValue
            )
        )
    }

    /**
     * 批量添加选项
     */
    suspend fun addOptions(attributeName: String, values: List<String>) {
        val entities = values.filter { it.isNotBlank() }.map {
            PresetOptionEntity(
                attributeName = attributeName,
                optionValue = it
            )
        }
        presetOptionDao.insertAll(entities)
    }

    /**
     * 删除选项
     */
    suspend fun removeOption(attributeName: String, optionValue: String) {
        presetOptionDao.deleteByAttributeAndValue(attributeName, optionValue)
    }

    /**
     * 获取所有已定义的属性名
     */
    suspend fun getAllAttributeNames(): List<String> =
        presetOptionDao.getAllAttributeNames()
}
