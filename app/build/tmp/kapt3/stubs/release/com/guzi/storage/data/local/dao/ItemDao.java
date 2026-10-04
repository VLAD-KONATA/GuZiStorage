package com.guzi.storage.data.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.guzi.storage.data.local.entity.ItemEntity;
import kotlinx.coroutines.flow.Flow;

/**
 * 物品数据访问对象
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0011\bg\u0018\u00002\u00020\u0001:\u0001&J\u000e\u0010\u0002\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\tJ\u001c\u0010\n\u001a\u00020\u00062\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u00a7@\u00a2\u0006\u0002\u0010\u000eJ\u0014\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\f0\u0010H\'J\u0018\u0010\u0011\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0012\u001a\u00020\rH\u00a7@\u00a2\u0006\u0002\u0010\u0013J\u0018\u0010\u0014\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\b0\u00102\u0006\u0010\u0012\u001a\u00020\rH\'J\u0014\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\f0\u0010H\'J\u001c\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\f0\u00102\u0006\u0010\u0018\u001a\u00020\rH\'J\u001c\u0010\u0019\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\f0\u00102\u0006\u0010\u0018\u001a\u00020\rH\'J\u001c\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\f0\u00102\u0006\u0010\u001b\u001a\u00020\rH\'J\u001c\u0010\u001c\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\f0\u00102\u0006\u0010\u001d\u001a\u00020\rH\'J\u001c\u0010\u001e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\f0\u00102\u0006\u0010\u001f\u001a\u00020\rH\'J\u0016\u0010 \u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\tJ\u001c\u0010!\u001a\u00020\u00062\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\b0\fH\u00a7@\u00a2\u0006\u0002\u0010\u000eJ\u001c\u0010#\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\f0\u00102\u0006\u0010$\u001a\u00020\rH\'J\u0016\u0010%\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\t\u00a8\u0006\'"}, d2 = {"Lcom/guzi/storage/data/local/dao/ItemDao;", "", "count", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "delete", "", "item", "Lcom/guzi/storage/data/local/entity/ItemEntity;", "(Lcom/guzi/storage/data/local/entity/ItemEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteByIds", "ids", "", "", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAll", "Lkotlinx/coroutines/flow/Flow;", "getById", "id", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getByIdFlow", "getIpGroups", "Lcom/guzi/storage/data/local/dao/ItemDao$IpGroupInfo;", "getItemsByCategory", "category", "getItemsByCategoryExact", "getItemsByCharacter", "character", "getItemsByIp", "ipName", "getItemsByProducer", "producer", "insert", "insertAll", "items", "search", "keyword", "update", "IpGroupInfo", "app_release"})
@androidx.room.Dao()
public abstract interface ItemDao {
    
    @androidx.room.Query(value = "SELECT * FROM items ORDER BY created_at DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.guzi.storage.data.local.entity.ItemEntity>> getAll();
    
    @androidx.room.Query(value = "SELECT * FROM items WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getById(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.guzi.storage.data.local.entity.ItemEntity> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM items WHERE id = :id")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.guzi.storage.data.local.entity.ItemEntity> getByIdFlow(@org.jetbrains.annotations.NotNull()
    java.lang.String id);
    
    @androidx.room.Query(value = "SELECT ip_name AS ipName, COUNT(*) AS count FROM items GROUP BY ip_name ORDER BY ip_name")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.guzi.storage.data.local.dao.ItemDao.IpGroupInfo>> getIpGroups();
    
    @androidx.room.Query(value = "\n        SELECT * FROM items\n        WHERE ip_name = :ipName\n           OR ip_name LIKE :ipName || \'|%\'\n           OR ip_name LIKE \'%|\' || :ipName\n           OR ip_name LIKE \'%|\' || :ipName || \'|%\'\n        ORDER BY created_at DESC\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.guzi.storage.data.local.entity.ItemEntity>> getItemsByIp(@org.jetbrains.annotations.NotNull()
    java.lang.String ipName);
    
    @androidx.room.Query(value = "\n        SELECT * FROM items\n        WHERE category = :category\n           OR category LIKE :category || \'|%\'\n           OR category LIKE \'%|\' || :category\n           OR category LIKE \'%|\' || :category || \'|%\'\n        ORDER BY created_at DESC\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.guzi.storage.data.local.entity.ItemEntity>> getItemsByCategory(@org.jetbrains.annotations.NotNull()
    java.lang.String category);
    
    @androidx.room.Query(value = "\n        SELECT * FROM items\n        WHERE producer = :producer\n           OR producer LIKE :producer || \'|%\'\n           OR producer LIKE \'%|\' || :producer\n           OR producer LIKE \'%|\' || :producer || \'|%\'\n        ORDER BY created_at DESC\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.guzi.storage.data.local.entity.ItemEntity>> getItemsByProducer(@org.jetbrains.annotations.NotNull()
    java.lang.String producer);
    
    /**
     * 关键词搜索（中文名、IP名、日文名模糊匹配）
     */
    @androidx.room.Query(value = "\n        SELECT * FROM items \n        WHERE name_cn LIKE \'%\' || :keyword || \'%\' \n           OR ip_name LIKE \'%\' || :keyword || \'%\' \n           OR name_en LIKE \'%\' || :keyword || \'%\'\n        ORDER BY created_at DESC\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.guzi.storage.data.local.entity.ItemEntity>> search(@org.jetbrains.annotations.NotNull()
    java.lang.String keyword);
    
    /**
     * 按角色筛选（管道符分隔的 characters 列模糊匹配）
     */
    @androidx.room.Query(value = "SELECT * FROM items WHERE characters LIKE \'%\' || :character || \'%\' ORDER BY created_at DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.guzi.storage.data.local.entity.ItemEntity>> getItemsByCharacter(@org.jetbrains.annotations.NotNull()
    java.lang.String character);
    
    /**
     * 按种类筛选
     */
    @androidx.room.Query(value = "\n        SELECT * FROM items\n        WHERE category = :category\n           OR category LIKE :category || \'|%\'\n           OR category LIKE \'%|\' || :category\n           OR category LIKE \'%|\' || :category || \'|%\'\n        ORDER BY created_at DESC\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.guzi.storage.data.local.entity.ItemEntity>> getItemsByCategoryExact(@org.jetbrains.annotations.NotNull()
    java.lang.String category);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.guzi.storage.data.local.entity.ItemEntity item, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertAll(@org.jetbrains.annotations.NotNull()
    java.util.List<com.guzi.storage.data.local.entity.ItemEntity> items, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object update(@org.jetbrains.annotations.NotNull()
    com.guzi.storage.data.local.entity.ItemEntity item, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object delete(@org.jetbrains.annotations.NotNull()
    com.guzi.storage.data.local.entity.ItemEntity item, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM items WHERE id IN (:ids)")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteByIds(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> ids, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM items")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object count(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    /**
     * IP 分组信息
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\f\u001a\u00020\u0005H\u00c6\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0011\u001a\u00020\u0005H\u00d6\u0001J\t\u0010\u0012\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0013"}, d2 = {"Lcom/guzi/storage/data/local/dao/ItemDao$IpGroupInfo;", "", "ipName", "", "count", "", "(Ljava/lang/String;I)V", "getCount", "()I", "getIpName", "()Ljava/lang/String;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "toString", "app_release"})
    public static final class IpGroupInfo {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String ipName = null;
        private final int count = 0;
        
        public IpGroupInfo(@org.jetbrains.annotations.NotNull()
        java.lang.String ipName, int count) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getIpName() {
            return null;
        }
        
        public final int getCount() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component1() {
            return null;
        }
        
        public final int component2() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.guzi.storage.data.local.dao.ItemDao.IpGroupInfo copy(@org.jetbrains.annotations.NotNull()
        java.lang.String ipName, int count) {
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