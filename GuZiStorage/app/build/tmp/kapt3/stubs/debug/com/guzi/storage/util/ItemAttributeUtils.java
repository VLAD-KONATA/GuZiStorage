package com.guzi.storage.util;

import com.guzi.storage.domain.model.Item;

/**
 * 物品属性取值工具 - 统一访问单值和多值字段
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\"\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0005J\u0016\u0010\t\u001a\u00020\n2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0005J&\u0010\u000b\u001a\u00020\n2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00052\u000e\u0010\f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\r\u00a8\u0006\u000e"}, d2 = {"Lcom/guzi/storage/util/ItemAttributeUtils;", "", "()V", "getValues", "", "", "item", "Lcom/guzi/storage/domain/model/Item;", "attr", "hasValue", "", "matchesFilter", "selectedValues", "", "app_debug"})
public final class ItemAttributeUtils {
    @org.jetbrains.annotations.NotNull()
    public static final com.guzi.storage.util.ItemAttributeUtils INSTANCE = null;
    
    private ItemAttributeUtils() {
        super();
    }
    
    /**
     * 获取物品在指定属性下的单值（如果属性是多值字段，返回第一个值或 null）
     * 用于分组：多值字段的物品会被分到多个分组
     */
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getValues(@org.jetbrains.annotations.NotNull()
    com.guzi.storage.domain.model.Item item, @org.jetbrains.annotations.NotNull()
    java.lang.String attr) {
        return null;
    }
    
    /**
     * 判断物品在指定属性下是否有值
     */
    public final boolean hasValue(@org.jetbrains.annotations.NotNull()
    com.guzi.storage.domain.model.Item item, @org.jetbrains.annotations.NotNull()
    java.lang.String attr) {
        return false;
    }
    
    /**
     * 判断物品是否匹配筛选条件
     * @param selectedValues 选中的值集合，null 元素表示"空值"选项
     */
    public final boolean matchesFilter(@org.jetbrains.annotations.NotNull()
    com.guzi.storage.domain.model.Item item, @org.jetbrains.annotations.NotNull()
    java.lang.String attr, @org.jetbrains.annotations.NotNull()
    java.util.Set<java.lang.String> selectedValues) {
        return false;
    }
}