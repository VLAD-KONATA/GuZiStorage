package com.guzi.storage.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.guzi.storage.data.local.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow

/**
 * 交易记录数据访问对象
 */
@Dao
interface TransactionDao {

    // ===== 查询 =====

    @Query("SELECT * FROM transactions ORDER BY date IS NULL, date ASC, created_at DESC")
    fun getAll(): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transactions WHERE item_id = :itemId ORDER BY date IS NULL, date ASC, created_at DESC")
    fun getByItemId(itemId: String): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transactions WHERE id = :id")
    suspend fun getById(id: String): TransactionEntity?

    @Query("SELECT COUNT(*) FROM transactions WHERE item_id = :itemId")
    suspend fun countByItemId(itemId: String): Int

    // ===== 写入 =====

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: TransactionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(transactions: List<TransactionEntity>)

    @Update
    suspend fun update(transaction: TransactionEntity)

    @Delete
    suspend fun delete(transaction: TransactionEntity)

    @Query("DELETE FROM transactions WHERE id IN (:ids)")
    suspend fun deleteByIds(ids: List<String>)
}
