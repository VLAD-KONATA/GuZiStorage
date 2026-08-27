package com.guzi.storage.data.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.guzi.storage.data.local.entity.PresetOptionEntity;
import kotlinx.coroutines.flow.Flow;

/**
 * 预设选项数据访问对象
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u001e\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\u000bJ\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\t0\rH\u00a7@\u00a2\u0006\u0002\u0010\u000eJ\u001c\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\u00102\u0006\u0010\b\u001a\u00020\tH\'J\u001c\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\t0\r2\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\u0012J\u0016\u0010\u0013\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u001c\u0010\u0014\u001a\u00020\u00032\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00050\rH\u00a7@\u00a2\u0006\u0002\u0010\u0016\u00a8\u0006\u0017"}, d2 = {"Lcom/guzi/storage/data/local/dao/PresetOptionDao;", "", "delete", "", "option", "Lcom/guzi/storage/data/local/entity/PresetOptionEntity;", "(Lcom/guzi/storage/data/local/entity/PresetOptionEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteByAttributeAndValue", "attributeName", "", "optionValue", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllAttributeNames", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getByAttribute", "Lkotlinx/coroutines/flow/Flow;", "getOptionValues", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insert", "insertAll", "options", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_release"})
@androidx.room.Dao()
public abstract interface PresetOptionDao {
    
    @androidx.room.Query(value = "SELECT * FROM preset_options WHERE attribute_name = :attributeName ORDER BY sort_order, option_value")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.guzi.storage.data.local.entity.PresetOptionEntity>> getByAttribute(@org.jetbrains.annotations.NotNull()
    java.lang.String attributeName);
    
    @androidx.room.Query(value = "SELECT option_value FROM preset_options WHERE attribute_name = :attributeName ORDER BY sort_order, option_value")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getOptionValues(@org.jetbrains.annotations.NotNull()
    java.lang.String attributeName, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<java.lang.String>> $completion);
    
    @androidx.room.Query(value = "SELECT DISTINCT attribute_name FROM preset_options")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllAttributeNames(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<java.lang.String>> $completion);
    
    @androidx.room.Insert(onConflict = 5)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.guzi.storage.data.local.entity.PresetOptionEntity option, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Insert(onConflict = 5)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertAll(@org.jetbrains.annotations.NotNull()
    java.util.List<com.guzi.storage.data.local.entity.PresetOptionEntity> options, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object delete(@org.jetbrains.annotations.NotNull()
    com.guzi.storage.data.local.entity.PresetOptionEntity option, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM preset_options WHERE attribute_name = :attributeName AND option_value = :optionValue")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteByAttributeAndValue(@org.jetbrains.annotations.NotNull()
    java.lang.String attributeName, @org.jetbrains.annotations.NotNull()
    java.lang.String optionValue, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}