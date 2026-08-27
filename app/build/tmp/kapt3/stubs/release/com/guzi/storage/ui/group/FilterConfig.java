package com.guzi.storage.ui.group;

/**
 * 筛选配置 - 多条件叠加筛选
 *
 * filters: Map<属性名, 选中的值集合>
 * - Set 中的 null 元素表示"空值"选项（筛选该字段为空的物品）
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010\"\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B#\u0012\u001c\b\u0002\u0010\u0002\u001a\u0016\u0012\u0004\u0012\u00020\u0004\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u00050\u0003\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\t\u001a\u00020\nJ\u001d\u0010\u000b\u001a\u0016\u0012\u0004\u0012\u00020\u0004\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u00050\u0003H\u00c6\u0003J\'\u0010\f\u001a\u00020\u00002\u001c\b\u0002\u0010\u0002\u001a\u0016\u0012\u0004\u0012\u00020\u0004\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u00050\u0003H\u00c6\u0001J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00040\u0011J\t\u0010\u0012\u001a\u00020\nH\u00d6\u0001J\u0006\u0010\u0013\u001a\u00020\u000eJ.\u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u00162\u001e\u0010\u0017\u001a\u001a\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00110\u0018J\t\u0010\u0019\u001a\u00020\u0004H\u00d6\u0001R%\u0010\u0002\u001a\u0016\u0012\u0004\u0012\u00020\u0004\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u00050\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\u001a"}, d2 = {"Lcom/guzi/storage/ui/group/FilterConfig;", "", "filters", "", "", "", "(Ljava/util/Map;)V", "getFilters", "()Ljava/util/Map;", "activeFilterCount", "", "component1", "copy", "equals", "", "other", "getActiveAttributes", "", "hashCode", "isActive", "matchesAll", "item", "Lcom/guzi/storage/domain/model/Item;", "attrValues", "Lkotlin/Function2;", "toString", "app_release"})
public final class FilterConfig {
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<java.lang.String, java.util.Set<java.lang.String>> filters = null;
    
    public FilterConfig(@org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, ? extends java.util.Set<java.lang.String>> filters) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<java.lang.String, java.util.Set<java.lang.String>> getFilters() {
        return null;
    }
    
    public final boolean isActive() {
        return false;
    }
    
    public final int activeFilterCount() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getActiveAttributes() {
        return null;
    }
    
    /**
     * 检查物品是否匹配所有筛选条件
     */
    public final boolean matchesAll(@org.jetbrains.annotations.NotNull()
    com.guzi.storage.domain.model.Item item, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super com.guzi.storage.domain.model.Item, ? super java.lang.String, ? extends java.util.List<java.lang.String>> attrValues) {
        return false;
    }
    
    public FilterConfig() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<java.lang.String, java.util.Set<java.lang.String>> component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.guzi.storage.ui.group.FilterConfig copy(@org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, ? extends java.util.Set<java.lang.String>> filters) {
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