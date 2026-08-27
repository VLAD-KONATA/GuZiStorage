package com.guzi.storage.data.repository;

import com.guzi.storage.data.local.dao.ItemDao;
import com.guzi.storage.data.local.dao.ItemDao.IpGroupInfo;
import com.guzi.storage.domain.model.Item;
import kotlinx.coroutines.flow.Flow;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * 物品仓库 - 封装物品的 CRUD 操作
 *
 * 负责 Entity ↔ Domain Model 转换，对外暴露 Flow 响应式数据流
 */
@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\r\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\tJ\u001c\u0010\n\u001a\u00020\u00062\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\b0\fH\u0086@\u00a2\u0006\u0002\u0010\rJ\u0016\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u0010H\u0086@\u00a2\u0006\u0002\u0010\u0011J\u001c\u0010\u0012\u001a\u00020\u00062\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00100\fH\u0086@\u00a2\u0006\u0002\u0010\rJ\u0012\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\f0\u0015J\u0012\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170\f0\u0015J\u0018\u0010\u0018\u001a\u0004\u0018\u00010\b2\u0006\u0010\u000f\u001a\u00020\u0010H\u0086@\u00a2\u0006\u0002\u0010\u0011J\u0016\u0010\u0019\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\b0\u00152\u0006\u0010\u000f\u001a\u00020\u0010J\u000e\u0010\u001a\u001a\u00020\u001bH\u0086@\u00a2\u0006\u0002\u0010\u001cJ\u001a\u0010\u001d\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\f0\u00152\u0006\u0010\u001e\u001a\u00020\u0010J\u001a\u0010\u001f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\f0\u00152\u0006\u0010 \u001a\u00020\u0010J\u001a\u0010!\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\f0\u00152\u0006\u0010\"\u001a\u00020\u0010J\u001a\u0010#\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\f0\u00152\u0006\u0010$\u001a\u00020\u0010J\u001a\u0010%\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\f0\u00152\u0006\u0010&\u001a\u00020\u0010J\u0016\u0010\'\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\tR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006("}, d2 = {"Lcom/guzi/storage/data/repository/ItemRepository;", "", "itemDao", "Lcom/guzi/storage/data/local/dao/ItemDao;", "(Lcom/guzi/storage/data/local/dao/ItemDao;)V", "addItem", "", "item", "Lcom/guzi/storage/domain/model/Item;", "(Lcom/guzi/storage/domain/model/Item;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "addItems", "items", "", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteItem", "id", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteItems", "ids", "getAllItems", "Lkotlinx/coroutines/flow/Flow;", "getIpGroups", "Lcom/guzi/storage/data/local/dao/ItemDao$IpGroupInfo;", "getItemById", "getItemByIdFlow", "getItemCount", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getItemsByCategory", "category", "getItemsByCharacter", "character", "getItemsByIp", "ipName", "getItemsByProducer", "producer", "search", "keyword", "updateItem", "app_debug"})
public final class ItemRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.guzi.storage.data.local.dao.ItemDao itemDao = null;
    
    @javax.inject.Inject()
    public ItemRepository(@org.jetbrains.annotations.NotNull()
    com.guzi.storage.data.local.dao.ItemDao itemDao) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.guzi.storage.domain.model.Item>> getAllItems() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getItemById(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.guzi.storage.domain.model.Item> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.guzi.storage.domain.model.Item> getItemByIdFlow(@org.jetbrains.annotations.NotNull()
    java.lang.String id) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.guzi.storage.data.local.dao.ItemDao.IpGroupInfo>> getIpGroups() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.guzi.storage.domain.model.Item>> getItemsByIp(@org.jetbrains.annotations.NotNull()
    java.lang.String ipName) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.guzi.storage.domain.model.Item>> getItemsByCategory(@org.jetbrains.annotations.NotNull()
    java.lang.String category) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.guzi.storage.domain.model.Item>> getItemsByProducer(@org.jetbrains.annotations.NotNull()
    java.lang.String producer) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.guzi.storage.domain.model.Item>> search(@org.jetbrains.annotations.NotNull()
    java.lang.String keyword) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.guzi.storage.domain.model.Item>> getItemsByCharacter(@org.jetbrains.annotations.NotNull()
    java.lang.String character) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object addItem(@org.jetbrains.annotations.NotNull()
    com.guzi.storage.domain.model.Item item, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object addItems(@org.jetbrains.annotations.NotNull()
    java.util.List<com.guzi.storage.domain.model.Item> items, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateItem(@org.jetbrains.annotations.NotNull()
    com.guzi.storage.domain.model.Item item, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteItem(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteItems(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> ids, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getItemCount(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
}