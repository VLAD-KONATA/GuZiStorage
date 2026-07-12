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
    "sell_method" to "售卖方式",
    "characters" to "相关角色",
    "art_types" to "柄图种类",
    "sizes" to "尺寸",
    "materials" to "材质",
    "origin_places" to "出品地",
    "sale_format" to "发售形式"
)

/**
 * 根据属性名获取中文标签
 */
fun getAttributeLabel(attrName: String): String =
    GROUPABLE_ATTRIBUTES.firstOrNull { it.first == attrName }?.second ?: attrName
