package com.guzi.storage.util

/**
 * 货币工具类
 *
 * 存储格式: {金额}{货币英文简写}，如 "550y" = 550日元
 *
 * 货币代码:
 * - y  → 日元
 * - r  → 人民币
 * - w  → 韩元
 * - b  → 泰铢
 * - nt → 台币
 * - hk → 港元
 * - d  → 越南盾
 */
object CurrencyUtils {

    /** 货币代码 → 中文名称（2字符代码排前面，避免后缀匹配歧义） */
    private val CURRENCY_LIST = listOf(
        "nt" to "台币",
        "hk" to "港元",
        "y"  to "日元",
        "r"  to "人民币",
        "w"  to "韩元",
        "b"  to "泰铢",
        "d"  to "越南盾"
    )

    private val CODE_TO_NAME: Map<String, String> = CURRENCY_LIST.toMap()

    /** 默认货币代码 */
    const val DEFAULT_CURRENCY = "y"

    /** 下拉框选项列表: (code, "中文名（code）") */
    val dropdownOptions: List<Pair<String, String>> =
        CURRENCY_LIST.map { (code, name) -> code to "$name（$code）" }

    /**
     * 将存储的 price 字符串格式化为展示文本
     * "550y" → "550日元"
     * "0"    → "无"
     * "550"  → "550"（无货币后缀，原样返回）
     * null/blank → ""
     */
    fun formatPrice(priceStr: String?): String {
        if (priceStr.isNullOrBlank()) return ""
        if (priceStr == "0") return "无"
        for ((code, name) in CURRENCY_LIST) {
            if (priceStr.endsWith(code)) {
                val amount = priceStr.dropLast(code.length)
                if (amount.isNotEmpty()) return "$amount$name"
            }
        }
        return priceStr
    }

    /**
     * 解析存储的 price 字符串，拆分金额和货币代码
     * "550y" → Price("550", "y")
     * "550"  → Price("550", null)
     * null   → Price("", null)
     */
    data class ParsedPrice(val amount: String, val currencyCode: String?)

    fun parsePrice(priceStr: String?): ParsedPrice {
        if (priceStr.isNullOrBlank()) return ParsedPrice("", null)
        for ((code, name) in CURRENCY_LIST) {
            if (priceStr.endsWith(code)) {
                val amount = priceStr.dropLast(code.length)
                if (amount.isNotEmpty()) return ParsedPrice(amount, code)
            }
        }
        return ParsedPrice(priceStr, null)
    }

    /**
     * 从金额和货币代码构建存储字符串
     * ("550", "y") → "550y"
     * ("", "y") → null
     */
    fun buildPriceString(amount: String, currencyCode: String): String? {
        if (amount.isBlank()) return null
        return "$amount$currencyCode"
    }

    /**
     * CSV/ZIP 导入用价格解析。
     *
     * 空值表示价格未知，存 null；正数 + 合法货币后缀按原规则存储；
     * 不带后缀的数字、<=0、非法格式统一存 "0"，表示无价格/赠品/特殊景品。
     */
    fun parseImportedItemPrice(raw: String): String? {
        val value = raw.trim()
        if (value.isBlank()) return null

        for ((code, _) in CURRENCY_LIST) {
            if (value.endsWith(code)) {
                val amount = value.dropLast(code.length)
                val parsedAmount = amount.toDoubleOrNull() ?: return "0"
                return if (parsedAmount > 0) value else "0"
            }
        }

        return "0"
    }

    /** 获取货币中文名称 */
    fun getCurrencyName(code: String): String = CODE_TO_NAME[code] ?: code
}
