package com.guzi.storage.util;

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
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001:\u0001\u0018B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\r\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0005J\u0010\u0010\u0010\u001a\u00020\u00052\b\u0010\u0011\u001a\u0004\u0018\u00010\u0005J\u000e\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u0005J\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0015\u001a\u00020\u0005J\u0010\u0010\u0016\u001a\u00020\u00172\b\u0010\u0011\u001a\u0004\u0018\u00010\u0005R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R \u0010\u0006\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\b0\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R#\u0010\n\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\b0\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u0019"}, d2 = {"Lcom/guzi/storage/util/CurrencyUtils;", "", "()V", "CODE_TO_NAME", "", "", "CURRENCY_LIST", "", "Lkotlin/Pair;", "DEFAULT_CURRENCY", "dropdownOptions", "getDropdownOptions", "()Ljava/util/List;", "buildPriceString", "amount", "currencyCode", "formatPrice", "priceStr", "getCurrencyName", "code", "parseImportedItemPrice", "raw", "parsePrice", "Lcom/guzi/storage/util/CurrencyUtils$ParsedPrice;", "ParsedPrice", "app_release"})
public final class CurrencyUtils {
    
    /**
     * 货币代码 → 中文名称（2字符代码排前面，避免后缀匹配歧义）
     */
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<kotlin.Pair<java.lang.String, java.lang.String>> CURRENCY_LIST = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.Map<java.lang.String, java.lang.String> CODE_TO_NAME = null;
    
    /**
     * 默认货币代码
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String DEFAULT_CURRENCY = "y";
    
    /**
     * 下拉框选项列表: (code, "中文名（code）")
     */
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<kotlin.Pair<java.lang.String, java.lang.String>> dropdownOptions = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.guzi.storage.util.CurrencyUtils INSTANCE = null;
    
    private CurrencyUtils() {
        super();
    }
    
    /**
     * 下拉框选项列表: (code, "中文名（code）")
     */
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<kotlin.Pair<java.lang.String, java.lang.String>> getDropdownOptions() {
        return null;
    }
    
    /**
     * 将存储的 price 字符串格式化为展示文本
     * "550y" → "550日元"
     * "0"    → "无"
     * "550"  → "550"（无货币后缀，原样返回）
     * null/blank → ""
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String formatPrice(@org.jetbrains.annotations.Nullable()
    java.lang.String priceStr) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.guzi.storage.util.CurrencyUtils.ParsedPrice parsePrice(@org.jetbrains.annotations.Nullable()
    java.lang.String priceStr) {
        return null;
    }
    
    /**
     * 从金额和货币代码构建存储字符串
     * ("550", "y") → "550y"
     * ("", "y") → null
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String buildPriceString(@org.jetbrains.annotations.NotNull()
    java.lang.String amount, @org.jetbrains.annotations.NotNull()
    java.lang.String currencyCode) {
        return null;
    }
    
    /**
     * CSV/ZIP 导入用价格解析。
     *
     * 空值表示价格未知，存 null；正数 + 合法货币后缀按原规则存储；
     * 不带后缀的数字、<=0、非法格式统一存 "0"，表示无价格/赠品/特殊景品。
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String parseImportedItemPrice(@org.jetbrains.annotations.NotNull()
    java.lang.String raw) {
        return null;
    }
    
    /**
     * 获取货币中文名称
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCurrencyName(@org.jetbrains.annotations.NotNull()
    java.lang.String code) {
        return null;
    }
    
    /**
     * 解析存储的 price 字符串，拆分金额和货币代码
     * "550y" → Price("550", "y")
     * "550"  → Price("550", null)
     * null   → Price("", null)
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\n\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u001f\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001J\t\u0010\u0011\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007\u00a8\u0006\u0012"}, d2 = {"Lcom/guzi/storage/util/CurrencyUtils$ParsedPrice;", "", "amount", "", "currencyCode", "(Ljava/lang/String;Ljava/lang/String;)V", "getAmount", "()Ljava/lang/String;", "getCurrencyCode", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "app_release"})
    public static final class ParsedPrice {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String amount = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String currencyCode = null;
        
        public ParsedPrice(@org.jetbrains.annotations.NotNull()
        java.lang.String amount, @org.jetbrains.annotations.Nullable()
        java.lang.String currencyCode) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getAmount() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getCurrencyCode() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.guzi.storage.util.CurrencyUtils.ParsedPrice copy(@org.jetbrains.annotations.NotNull()
        java.lang.String amount, @org.jetbrains.annotations.Nullable()
        java.lang.String currencyCode) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
}