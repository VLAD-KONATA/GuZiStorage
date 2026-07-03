package com.guzi.storage.domain.model

import com.guzi.storage.data.local.PipeListConverter
import com.guzi.storage.data.local.entity.TransactionEntity

/**
 * 交易记录领域模型
 */
data class Transaction(
    val id: String,
    val itemId: String,
    val nameCn: String,
    val type: TransactionType,
    val price: Double,
    val channel: String,
    val quantity: Int = 1,
    val date: String? = null,
    val imagePaths: List<String> = emptyList(),
    val note: String? = null,
    val createdAt: Long = 0,
    val updatedAt: Long = 0
) {
    fun toEntity(): TransactionEntity = TransactionEntity(
        id = id,
        itemId = itemId,
        nameCn = nameCn,
        type = type.value,
        price = price,
        channel = channel,
        quantity = quantity,
        date = date,
        imagePaths = PipeListConverter.fromList(imagePaths),
        note = note,
        createdAt = createdAt,
        updatedAt = updatedAt
    )

    companion object {
        fun fromEntity(entity: TransactionEntity): Transaction = Transaction(
            id = entity.id,
            itemId = entity.itemId,
            nameCn = entity.nameCn,
            type = TransactionType.fromValue(entity.type),
            price = entity.price,
            channel = entity.channel,
            quantity = entity.quantity,
            date = entity.date,
            imagePaths = PipeListConverter.toList(entity.imagePaths),
            note = entity.note,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
    }
}

/**
 * 交易类型枚举
 */
enum class TransactionType(val value: String) {
    BUY("购入"),
    SELL("卖出");

    companion object {
        fun fromValue(value: String): TransactionType =
            entries.find { it.value == value } ?: BUY
    }
}
