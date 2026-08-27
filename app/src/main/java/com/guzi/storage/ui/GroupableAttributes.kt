package com.guzi.storage.ui

/**
 * 可用于分组和筛选的属性列表（attributeName → 中文标签）
 */
val GROUPABLE_ATTRIBUTES = listOf(
    "ip_name" to "IP名",
    "producer" to "出品商",
    "category" to "种类",
    "status" to "状态",
    "version" to "版本",
    "release_date" to "发售日期",
    "sell_method" to "售卖方式",
    "characters" to "相关角色",
    "art_types" to "柄图种类",
    "sizes" to "尺寸",
    "materials" to "材质",
    "origin_places" to "出品地",
    "sale_format" to "发售形式"
)

/**
 * 搜索/筛选属性列表（attributeName → 中文标签）
 * 包含物品级别字段（id, name_cn, name_en, note）+ 分组属性，按指定顺序排列
 */
val SEARCH_FILTER_ATTRIBUTES = listOf(
    "id" to "ID",
    "name_cn" to "中文名称",
    "name_en" to "日文/英文名称",
    "category" to "种类",
    "characters" to "相关角色",
    "ip_name" to "IP名",
    "producer" to "出品商",
    "origin_places" to "出品地",
    "sale_format" to "发售形式",
    "sell_method" to "售卖方式",
    "release_date" to "发售日期",
    "sizes" to "尺寸",
    "materials" to "材质",
    "art_types" to "柄图种类",
    "status" to "状态",
    "version" to "版本",
    "note" to "备注"
)

/**
 * 根据属性名获取中文标签（搜索/筛选属性优先，回退到分组属性）
 */
fun getAttributeLabel(attrName: String): String =
    SEARCH_FILTER_ATTRIBUTES.firstOrNull { it.first == attrName }?.second
        ?: GROUPABLE_ATTRIBUTES.firstOrNull { it.first == attrName }?.second ?: attrName

/**
 * 搜索字段特殊值
 */
const val SEARCH_FIELD_ALL = "all"
const val SEARCH_FIELD_GROUP_NAME = "分组名"

/**
 * 根据搜索字段获取显示标签
 */
fun getSearchFieldLabel(field: String): String = when (field) {
    SEARCH_FIELD_ALL -> "全部"
    SEARCH_FIELD_GROUP_NAME -> "分组名"
    else -> getAttributeLabel(field)
}
