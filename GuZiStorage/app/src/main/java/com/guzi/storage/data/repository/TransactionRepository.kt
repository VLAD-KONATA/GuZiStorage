package com.guzi.storage.data.repository

import com.guzi.storage.data.local.dao.TransactionDao
import com.guzi.storage.domain.model.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 交易记录仓库 - 封装交易记录的 CRUD 操作
 */
@Singleton
class TransactionRepository @Inject constructor(
    private val transactionDao: TransactionDao
) {

    // ===== 查询 =====

    fun getAllTransactions(): Flow<List<Transaction>> =
        transactionDao.getAll().map { entities -> entities.map { Transaction.fromEntity(it) } }

    fun getTransactionsByItemId(itemId: String): Flow<List<Transaction>> =
        transactionDao.getByItemId(itemId).map { entities -> entities.map { Transaction.fromEntity(it) } }

    suspend fun getTransactionById(id: String): Transaction? =
        transactionDao.getById(id)?.let { Transaction.fromEntity(it) }

    suspend fun getTransactionCountByItemId(itemId: String): Int =
        transactionDao.countByItemId(itemId)

    // ===== 写入 =====

    suspend fun addTransaction(transaction: Transaction) {
        val now = System.currentTimeMillis()
        transactionDao.insert(
            transaction.copy(createdAt = now, updatedAt = now).toEntity()
        )
    }

    suspend fun addTransactions(transactions: List<Transaction>) {
        val now = System.currentTimeMillis()
        transactionDao.insertAll(
            transactions.map { it.copy(createdAt = now, updatedAt = now).toEntity() }
        )
    }

    suspend fun updateTransaction(transaction: Transaction) {
        transactionDao.update(
            transaction.copy(updatedAt = System.currentTimeMillis()).toEntity()
        )
    }

    suspend fun deleteTransaction(id: String) {
        val entity = transactionDao.getById(id) ?: return
        transactionDao.delete(entity)
    }

    suspend fun deleteTransactions(ids: List<String>) {
        transactionDao.deleteByIds(ids)
    }
}
