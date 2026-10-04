package com.guzi.storage.util

/**
 * 谷子字段解析规则。
 *
 * CSV/ZIP 导入和手动保存共用这里，避免同一字段在不同入口表现不一致。
 */
object ItemValueParser {

    fun parseMultiValue(value: String): List<String> =
        value.split("|")
            .map { it.trim() }
            .filter { it.isNotBlank() }

    fun parseQuantity(value: String): Int {
        val quantity = value.trim().toIntOrNull()
        return if (quantity != null && quantity > 0) quantity else 0
    }

    fun parseOriginalBagQuantity(value: String): Int? {
        val quantity = value.trim().toIntOrNull()
        return when {
            quantity == null -> null
            quantity >= 0 -> quantity
            else -> null
        }
    }

    fun hasPipe(value: String): Boolean = value.contains("|")
}
