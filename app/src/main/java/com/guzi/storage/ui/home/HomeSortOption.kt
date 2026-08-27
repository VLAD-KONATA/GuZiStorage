package com.guzi.storage.ui.home

/**
 * 分组卡片排序选项（单选下拉）
 *
 * 分组卡片只有两个维度可排序：分组名 与 件数
 * 泛型 apply 可同时用于 HomeScreen(IpGroupDisplay) 和 GroupScreen(SubGroupDisplay)
 */
enum class HomeSortOption(val displayLabel: String) {
    NAME_ASC("按分组名 升序"),
    NAME_DESC("按分组名 降序"),
    COUNT_ASC("按件数 升序"),
    COUNT_DESC("按件数 降序");

    fun <T> apply(
        groups: List<T>,
        attrValueSelector: (T) -> String,
        countSelector: (T) -> Int
    ): List<T> = when (this) {
        NAME_ASC -> groups.sortedBy { attrValueSelector(it) }
        NAME_DESC -> groups.sortedByDescending { attrValueSelector(it) }
        COUNT_ASC -> groups.sortedBy { countSelector(it) }
        COUNT_DESC -> groups.sortedByDescending { countSelector(it) }
    }

    companion object {
        val DEFAULT = COUNT_DESC
    }
}
