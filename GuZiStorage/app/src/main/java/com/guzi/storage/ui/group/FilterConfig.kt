package com.guzi.storage.ui.group

/**
 * 筛选配置 - 多条件叠加筛选
 *
 * filters: Map<属性名, 选中的值集合>
 * - Set 中的 null 元素表示"空值"选项（筛选该字段为空的物品）
 */
data class FilterConfig(
    val filters: Map<String, Set<String?>> = emptyMap()
) {
    fun isActive(): Boolean = filters.values.any { it.isNotEmpty() }

    fun activeFilterCount(): Int = filters.values.count { it.isNotEmpty() }

    fun getActiveAttributes(): List<String> =
        filters.keys.filter { filters[it]?.isNotEmpty() == true }

    /**
     * 检查物品是否匹配所有筛选条件
     */
    fun matchesAll(
        item: com.guzi.storage.domain.model.Item,
        attrValues: (com.guzi.storage.domain.model.Item, String) -> List<String>
    ): Boolean {
        for ((attr, selectedValues) in filters) {
            if (selectedValues.isEmpty()) continue
            val itemValues = attrValues(item, attr)
            val hasEmpty = null in selectedValues
            val nonNullSelected = selectedValues.filterNotNull()

            val matches = (hasEmpty && itemValues.isEmpty()) ||
                    (nonNullSelected.isNotEmpty() && itemValues.any { it in nonNullSelected })
            if (!matches) return false
        }
        return true
    }
}
