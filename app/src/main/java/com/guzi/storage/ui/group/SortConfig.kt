package com.guzi.storage.ui.group

import com.guzi.storage.domain.model.Item
import com.guzi.storage.util.ItemSortFieldRegistry

/**
 * 排序方向
 */
enum class SortOrder { ASC, DESC }

/**
 * 单个排序条件
 * @param field Kotlin 属性名（驼峰），如 "nameCn"、"createdAt"
 * @param order 升序 / 降序
 */
data class SortCriterion(
    val field: String,
    val order: SortOrder = SortOrder.ASC
)

/**
 * 排序配置 - 多条件叠加排序
 *
 * criteria 列表中的顺序即为排序优先级：越靠前优先级越高
 * 具体 Comparator 由 [ItemSortFieldRegistry] 反射生成
 */
data class SortConfig(
    val criteria: List<SortCriterion> = emptyList()
) {
    fun isActive(): Boolean = criteria.isNotEmpty()

    fun activeSortCount(): Int = criteria.size

    fun apply(items: List<Item>): List<Item> {
        if (criteria.isEmpty()) return items
        val combined = criteria
            .map { ItemSortFieldRegistry.buildComparator(it.field, it.order) }
            .reduce { acc, cmp -> acc.then(cmp) }
        return items.sortedWith(combined)
    }
}
