package com.guzi.storage.util;

import com.guzi.storage.domain.model.Item;
import com.guzi.storage.ui.group.SortOrder;
import kotlin.reflect.KProperty1;

/**
 * Item 排序字段注册中心 - 反射式自动扫描
 *
 * 使用方式：
 * - 需要新增可排序字段时，只需在 [FIELD_LABELS] 中加一行 "字段名(驼峰)" to "中文标签"
 * - Comparator 通过反射统一生成，无需在任何地方写 when/switch
 *
 * 空值处理：所有条件统一 nullsLast（无论升降序，空值排最后）
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J&\u0010\u0018\u001a\u0012\u0012\u0004\u0012\u00020\r0\u0019j\b\u0012\u0004\u0012\u00020\r`\u001a2\u0006\u0010\u001b\u001a\u00020\u00052\u0006\u0010\u001c\u001a\u00020\u001dJ\u000e\u0010\u001e\u001a\u00020\u00052\u0006\u0010\u001b\u001a\u00020\u0005R*\u0010\u0003\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005`\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u0007\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00010\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R1\u0010\n\u001a\u0018\u0012\u0004\u0012\u00020\u0005\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\r\u0012\u0002\b\u00030\f0\u000b8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u000e\u0010\u000fR-\u0010\u0012\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u00140\u00138FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0017\u0010\u0011\u001a\u0004\b\u0015\u0010\u0016\u00a8\u0006\u001f"}, d2 = {"Lcom/guzi/storage/util/ItemSortFieldRegistry;", "", "()V", "FIELD_LABELS", "Ljava/util/LinkedHashMap;", "", "Lkotlin/collections/LinkedHashMap;", "SORTABLE_TYPES", "", "Lkotlin/reflect/KClass;", "propertyMap", "", "Lkotlin/reflect/KProperty1;", "Lcom/guzi/storage/domain/model/Item;", "getPropertyMap", "()Ljava/util/Map;", "propertyMap$delegate", "Lkotlin/Lazy;", "sortableFields", "", "Lkotlin/Pair;", "getSortableFields", "()Ljava/util/List;", "sortableFields$delegate", "buildComparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "fieldName", "order", "Lcom/guzi/storage/ui/group/SortOrder;", "getLabel", "app_release"})
public final class ItemSortFieldRegistry {
    
    /**
     * Item 属性名（驼峰命名） → 中文显示标签
     *
     * 顺序即为 UI 中"添加排序条件"弹窗的展示顺序
     * 未列入此表的字段不会作为排序候选
     */
    @org.jetbrains.annotations.NotNull()
    private static final java.util.LinkedHashMap<java.lang.String, java.lang.String> FIELD_LABELS = null;
    
    /**
     * 允许排序的 Kotlin 类型
     */
    @org.jetbrains.annotations.NotNull()
    private static final java.util.Set<kotlin.reflect.KClass<? extends java.lang.Object>> SORTABLE_TYPES = null;
    
    /**
     * 反射属性缓存
     */
    @org.jetbrains.annotations.NotNull()
    private static final kotlin.Lazy propertyMap$delegate = null;
    
    /**
     * 所有可排序字段（Kotlin属性名 → 中文标签）
     *
     * 仅返回：
     * 1. 存在于 [FIELD_LABELS] 中
     * 2. Item 上确有该属性
     * 3. 属性类型在 [SORTABLE_TYPES] 中（含可空版本）
     */
    @org.jetbrains.annotations.NotNull()
    private static final kotlin.Lazy sortableFields$delegate = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.guzi.storage.util.ItemSortFieldRegistry INSTANCE = null;
    
    private ItemSortFieldRegistry() {
        super();
    }
    
    /**
     * 反射属性缓存
     */
    private final java.util.Map<java.lang.String, kotlin.reflect.KProperty1<com.guzi.storage.domain.model.Item, ?>> getPropertyMap() {
        return null;
    }
    
    /**
     * 所有可排序字段（Kotlin属性名 → 中文标签）
     *
     * 仅返回：
     * 1. 存在于 [FIELD_LABELS] 中
     * 2. Item 上确有该属性
     * 3. 属性类型在 [SORTABLE_TYPES] 中（含可空版本）
     */
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<kotlin.Pair<java.lang.String, java.lang.String>> getSortableFields() {
        return null;
    }
    
    /**
     * 获取字段中文标签
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getLabel(@org.jetbrains.annotations.NotNull()
    java.lang.String fieldName) {
        return null;
    }
    
    /**
     * 构建单字段 Comparator
     * - 空值统一置尾（无论升降序方向）
     * - 未知字段返回中性 Comparator
     */
    @org.jetbrains.annotations.NotNull()
    public final java.util.Comparator<com.guzi.storage.domain.model.Item> buildComparator(@org.jetbrains.annotations.NotNull()
    java.lang.String fieldName, @org.jetbrains.annotations.NotNull()
    com.guzi.storage.ui.group.SortOrder order) {
        return null;
    }
}