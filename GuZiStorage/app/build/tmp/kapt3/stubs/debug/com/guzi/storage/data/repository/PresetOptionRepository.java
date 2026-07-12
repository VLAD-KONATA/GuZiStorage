package com.guzi.storage.data.repository;

import com.guzi.storage.data.local.dao.PresetOptionDao;
import com.guzi.storage.data.local.entity.PresetOptionEntity;
import kotlinx.coroutines.flow.Flow;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * 预设选项仓库 - 管理各属性的可选值
 *
 * 支持的属性标识（attributeName）:
 * - ip_name: IP名
 * - producer: 出品商
 * - origin_places: 出品地
 * - sale_format: 发售形式
 * - sell_method: 售卖方式
 * - category: 种类
 * - sizes: 尺寸
 * - materials: 材质
 * - characters: 相关角色
 * - art_types: 柄图种类
 * - status: 状态
 * - version: 版本
 * - channel: 渠道（交易记录用）
 * - type: 购入/卖出（交易记录用）
 */
@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\nJ$\u0010\u000b\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\b0\rH\u0086@\u00a2\u0006\u0002\u0010\u000eJ\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\b0\rH\u0086@\u00a2\u0006\u0002\u0010\u0010J\u001c\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\b0\r2\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\u0012J\u001a\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\r0\u00142\u0006\u0010\u0007\u001a\u00020\bJ\u001e\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"Lcom/guzi/storage/data/repository/PresetOptionRepository;", "", "presetOptionDao", "Lcom/guzi/storage/data/local/dao/PresetOptionDao;", "(Lcom/guzi/storage/data/local/dao/PresetOptionDao;)V", "addOption", "", "attributeName", "", "optionValue", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "addOptions", "values", "", "(Ljava/lang/String;Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllAttributeNames", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getOptionValues", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getOptions", "Lkotlinx/coroutines/flow/Flow;", "removeOption", "app_debug"})
public final class PresetOptionRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.guzi.storage.data.local.dao.PresetOptionDao presetOptionDao = null;
    
    @javax.inject.Inject()
    public PresetOptionRepository(@org.jetbrains.annotations.NotNull()
    com.guzi.storage.data.local.dao.PresetOptionDao presetOptionDao) {
        super();
    }
    
    /**
     * 获取指定属性的选项列表（Flow 响应式）
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<java.lang.String>> getOptions(@org.jetbrains.annotations.NotNull()
    java.lang.String attributeName) {
        return null;
    }
    
    /**
     * 获取指定属性的选项列表（一次性）
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getOptionValues(@org.jetbrains.annotations.NotNull()
    java.lang.String attributeName, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<java.lang.String>> $completion) {
        return null;
    }
    
    /**
     * 添加新选项（如果不存在则添加）
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object addOption(@org.jetbrains.annotations.NotNull()
    java.lang.String attributeName, @org.jetbrains.annotations.NotNull()
    java.lang.String optionValue, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * 批量添加选项
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object addOptions(@org.jetbrains.annotations.NotNull()
    java.lang.String attributeName, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> values, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * 删除选项
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object removeOption(@org.jetbrains.annotations.NotNull()
    java.lang.String attributeName, @org.jetbrains.annotations.NotNull()
    java.lang.String optionValue, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * 获取所有已定义的属性名
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getAllAttributeNames(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<java.lang.String>> $completion) {
        return null;
    }
}