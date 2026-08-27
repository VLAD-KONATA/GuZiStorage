package com.guzi.storage.data.local

/**
 * 管道符分隔列表转换工具
 *
 * 用于在 Entity（String）和领域模型（List<String>）之间转换多选字段
 * 存储格式示例: "中|日" → ["中", "日"]
 */
object PipeListConverter {

    private const val DELIMITER = "|"

    /**
     * List<String> → 管道符分隔字符串
     * 空列表返回空字符串
     */
    fun fromList(list: List<String>): String = list.joinToString(DELIMITER)

    /**
     * 管道符分隔字符串 → List<String>
     * 空字符串返回空列表
     */
    fun toList(value: String): List<String> =
        if (value.isBlank()) emptyList() else value.split(DELIMITER)
}
