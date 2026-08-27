package com.guzi.storage.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.guzi.storage.data.local.dao.ItemDao;
import com.guzi.storage.data.local.dao.PresetOptionDao;
import com.guzi.storage.data.local.dao.TransactionDao;
import com.guzi.storage.data.local.entity.ItemEntity;
import com.guzi.storage.data.local.entity.PresetOptionEntity;
import com.guzi.storage.data.local.entity.TransactionEntity;

/**
 * Room 数据库
 *
 * 版本 1: 初始版本
 * - items 表 (物品)
 * - transactions 表 (交易记录)
 * - preset_options 表 (预设选项)
 *
 * 版本 2: 新增 release_date 列 (发售日期, YYYY-MM)
 * - items 表新增 release_date 字段
 * - 使用 fallbackToDestructiveMigration，自动重建
 *
 * 版本 3: price 列类型变更 Double → String (格式: 金额+货币代码, 如 "550y")
 * - items 表 price 字段改为 TEXT 存储
 * - 使用 fallbackToDestructiveMigration，自动重建
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\bH&\u00a8\u0006\t"}, d2 = {"Lcom/guzi/storage/data/local/AppDatabase;", "Landroidx/room/RoomDatabase;", "()V", "itemDao", "Lcom/guzi/storage/data/local/dao/ItemDao;", "presetOptionDao", "Lcom/guzi/storage/data/local/dao/PresetOptionDao;", "transactionDao", "Lcom/guzi/storage/data/local/dao/TransactionDao;", "app_debug"})
@androidx.room.Database(entities = {com.guzi.storage.data.local.entity.ItemEntity.class, com.guzi.storage.data.local.entity.TransactionEntity.class, com.guzi.storage.data.local.entity.PresetOptionEntity.class}, version = 3, exportSchema = true)
public abstract class AppDatabase extends androidx.room.RoomDatabase {
    
    public AppDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.guzi.storage.data.local.dao.ItemDao itemDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.guzi.storage.data.local.dao.TransactionDao transactionDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.guzi.storage.data.local.dao.PresetOptionDao presetOptionDao();
}