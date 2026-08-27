package com.guzi.storage.util

/**
 * 日期工具 - 统一 YYYY-MM 格式标准化
 *
 * 项目所有日期字段（发售日期、交易日期等）均存储为 "YYYY-MM" 字符串，
 * 字典序与时间序一致，可直接用 Room ORDER BY 排序。
 */
object DateUtils {

    /**
     * 将各种日期输入标准化为 "YYYY-MM" 格式
     *
     * 支持的输入格式：
     * - "2025-07" / "2025-7" → "2025-07"
     * - "2025/07" / "2025/7" → "2025-07"
     * - "2025.07" / "2025.7" → "2025-07"
     * - "2025-07-15" → "2025-07"（截取年月部分）
     * - "2025y7m" / "2025y07m" → "2025-07"（兼容旧 CSV 格式）
     *
     * @return 标准化后的 "YYYY-MM"，或 null（输入无法识别时）
     */
    fun normalizeYearMonth(input: String): String? {
        val trimmed = input.trim()
        if (trimmed.isBlank()) return null

        // 1. 匹配旧格式: 4位年 + "y" + 1~2位月 + "m"（如 2022y5m）
        val ymRegex = Regex("""(\d{4})y(\d{1,2})m""")
        ymRegex.find(trimmed)?.let { match ->
            val year = match.groupValues[1]
            val month = match.groupValues[2].padStart(2, '0')
            val monthInt = month.toIntOrNull() ?: return null
            if (monthInt < 1 || monthInt > 12) return null
            return "$year-$month"
        }

        // 2. 匹配标准格式: 4位年 + 分隔符 + 1~2位月
        val regex = Regex("""(\d{4})[/.\-](\d{1,2})""")
        val match = regex.find(trimmed) ?: return null
        val year = match.groupValues[1]
        val month = match.groupValues[2].padStart(2, '0')
        val monthInt = month.toIntOrNull() ?: return null
        if (monthInt < 1 || monthInt > 12) return null
        return "$year-$month"
    }

    /**
     * 将存储的 "YYYY-MM" 格式转换为 CSV 导出格式 "YYYYyMm"
     * 如 "2022-05" → "2022y5m"
     *
     * @return CSV 格式日期字符串，空值返回 ""
     */
    fun formatYearMonthForCsv(dateStr: String?): String {
        if (dateStr.isNullOrBlank()) return ""
        val normalized = normalizeYearMonth(dateStr) ?: return dateStr
        val parts = normalized.split("-")
        val year = parts[0]
        val month = parts[1].toIntOrNull() ?: return normalized
        return "${year}y${month}m"
    }
}
