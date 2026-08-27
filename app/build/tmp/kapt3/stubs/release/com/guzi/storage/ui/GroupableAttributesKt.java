package com.guzi.storage.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u000b\u001a\u000e\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\u0003\u001a\u000e\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u0003\"#\u0010\u0000\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u00020\u0001\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0005\"\u000e\u0010\u0006\u001a\u00020\u0003X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0007\u001a\u00020\u0003X\u0086T\u00a2\u0006\u0002\n\u0000\"#\u0010\b\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u00020\u0001\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0005\u00a8\u0006\u000e"}, d2 = {"GROUPABLE_ATTRIBUTES", "", "Lkotlin/Pair;", "", "getGROUPABLE_ATTRIBUTES", "()Ljava/util/List;", "SEARCH_FIELD_ALL", "SEARCH_FIELD_GROUP_NAME", "SEARCH_FILTER_ATTRIBUTES", "getSEARCH_FILTER_ATTRIBUTES", "getAttributeLabel", "attrName", "getSearchFieldLabel", "field", "app_release"})
public final class GroupableAttributesKt {
    
    /**
     * 可用于分组和筛选的属性列表（attributeName → 中文标签）
     */
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<kotlin.Pair<java.lang.String, java.lang.String>> GROUPABLE_ATTRIBUTES = null;
    
    /**
     * 搜索/筛选属性列表（attributeName → 中文标签）
     * 包含物品级别字段（id, name_cn, name_en, note）+ 分组属性，按指定顺序排列
     */
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<kotlin.Pair<java.lang.String, java.lang.String>> SEARCH_FILTER_ATTRIBUTES = null;
    
    /**
     * 搜索字段特殊值
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String SEARCH_FIELD_ALL = "all";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String SEARCH_FIELD_GROUP_NAME = "\u5206\u7ec4\u540d";
    
    /**
     * 可用于分组和筛选的属性列表（attributeName → 中文标签）
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.util.List<kotlin.Pair<java.lang.String, java.lang.String>> getGROUPABLE_ATTRIBUTES() {
        return null;
    }
    
    /**
     * 搜索/筛选属性列表（attributeName → 中文标签）
     * 包含物品级别字段（id, name_cn, name_en, note）+ 分组属性，按指定顺序排列
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.util.List<kotlin.Pair<java.lang.String, java.lang.String>> getSEARCH_FILTER_ATTRIBUTES() {
        return null;
    }
    
    /**
     * 根据属性名获取中文标签（搜索/筛选属性优先，回退到分组属性）
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String getAttributeLabel(@org.jetbrains.annotations.NotNull()
    java.lang.String attrName) {
        return null;
    }
    
    /**
     * 根据搜索字段获取显示标签
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String getSearchFieldLabel(@org.jetbrains.annotations.NotNull()
    java.lang.String field) {
        return null;
    }
}