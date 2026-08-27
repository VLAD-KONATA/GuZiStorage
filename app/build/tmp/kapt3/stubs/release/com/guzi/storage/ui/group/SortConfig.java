package com.guzi.storage.ui.group;

import com.guzi.storage.domain.model.Item;
import com.guzi.storage.util.ItemSortFieldRegistry;

/**
 * 排序配置 - 多条件叠加排序
 *
 * criteria 列表中的顺序即为排序优先级：越靠前优先级越高
 * 具体 Comparator 由 [ItemSortFieldRegistry] 反射生成
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001B\u0015\u0012\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\u0002\u0010\u0005J\u0006\u0010\b\u001a\u00020\tJ\u001a\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00032\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0003J\u000f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\u0019\u0010\u000e\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0012\u001a\u00020\tH\u00d6\u0001J\u0006\u0010\u0013\u001a\u00020\u0010J\t\u0010\u0014\u001a\u00020\u0015H\u00d6\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\u0016"}, d2 = {"Lcom/guzi/storage/ui/group/SortConfig;", "", "criteria", "", "Lcom/guzi/storage/ui/group/SortCriterion;", "(Ljava/util/List;)V", "getCriteria", "()Ljava/util/List;", "activeSortCount", "", "apply", "Lcom/guzi/storage/domain/model/Item;", "items", "component1", "copy", "equals", "", "other", "hashCode", "isActive", "toString", "", "app_release"})
public final class SortConfig {
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.guzi.storage.ui.group.SortCriterion> criteria = null;
    
    public SortConfig(@org.jetbrains.annotations.NotNull()
    java.util.List<com.guzi.storage.ui.group.SortCriterion> criteria) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.guzi.storage.ui.group.SortCriterion> getCriteria() {
        return null;
    }
    
    public final boolean isActive() {
        return false;
    }
    
    public final int activeSortCount() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.guzi.storage.domain.model.Item> apply(@org.jetbrains.annotations.NotNull()
    java.util.List<com.guzi.storage.domain.model.Item> items) {
        return null;
    }
    
    public SortConfig() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.guzi.storage.ui.group.SortCriterion> component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.guzi.storage.ui.group.SortConfig copy(@org.jetbrains.annotations.NotNull()
    java.util.List<com.guzi.storage.ui.group.SortCriterion> criteria) {
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