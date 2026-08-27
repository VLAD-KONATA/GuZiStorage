package com.guzi.storage.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.guzi.storage.data.local.dao.ItemDao
import com.guzi.storage.data.local.dao.PresetOptionDao
import com.guzi.storage.data.local.dao.TransactionDao
import com.guzi.storage.data.local.entity.ItemEntity
import com.guzi.storage.data.local.entity.PresetOptionEntity
import com.guzi.storage.data.local.entity.TransactionEntity

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
@Database(
    entities = [
        ItemEntity::class,
        TransactionEntity::class,
        PresetOptionEntity::class
    ],
    version = 3,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
    abstract fun transactionDao(): TransactionDao
    abstract fun presetOptionDao(): PresetOptionDao
}
