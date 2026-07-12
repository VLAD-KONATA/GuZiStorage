package com.guzi.storage.util

import com.guzi.storage.domain.model.Item

/**
 * 物品属性取值工具 - 统一访问单值和多值字段
 */
object ItemAttributeUtils {

    /**
     * 获取物品在指定属性下的单值（如果属性是多值字段，返回第一个值或 null）
     * 用于分组：多值字段的物品会被分到多个分组
     */
    fun getValues(item: Item, attr: String): List<String> {
        return when (attr) {
            "ip_name" -> listOf(item.ipName)
            "producer" -> listOfNotNull(item.producer)
            "category" -> listOfNotNull(item.category)
            "status" -> listOf(item.status)
            "version" -> listOfNotNull(item.version)
            "sell_method" -> listOf(item.sellMethod)
            "characters" -> item.characters
            "art_types" -> item.artTypes
            "sizes" -> item.sizes
            "materials" -> item.materials
            "origin_places" -> item.originPlaces
            "sale_format" -> item.saleFormat
            else -> emptyList()
        }
    }

    /**
     * 判断物品在指定属性下是否有值
     */
    fun hasValue(item: Item, attr: String): Boolean {
        return getValues(item, attr).isNotEmpty()
    }

    /**
     * 判断物品是否匹配筛选条件
     * @param selectedValues 选中的值集合，null 元素表示"空值"选项
     */
    fun matchesFilter(item: Item, attr: String, selectedValues: Set<String?>): Boolean {
        if (selectedValues.isEmpty()) return true

        val itemValues = getValues(item, attr)
        val hasEmpty = null in selectedValues
        val nonNullSelected = selectedValues.filterNotNull()

        // 如果选了空值，且物品该字段为空
        if (hasEmpty && itemValues.isEmpty()) return true

        // 如果选了具体值，且物品包含其中任一值
        if (nonNullSelected.isNotEmpty() && itemValues.any { it in nonNullSelected }) return true

        return false
    }
}
