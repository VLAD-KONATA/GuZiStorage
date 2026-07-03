package com.guzi.storage.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * 交易记录表实体 - 存储物品的购入/卖出记录
 *
 * 通过 item_id 外键关联 ItemEntity，级联删除
 */
@Entity(
    tableName = "transactions",
    foreignKeys = [
        ForeignKey(
            entity = ItemEntity::class,
            parentColumns = ["id"],
            childColumns = ["item_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["item_id"])
    ]
)
data class TransactionEntity(
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "item_id")
    val itemId: String,

    @ColumnInfo(name = "name_cn")
    val nameCn: String,

    // "购入" 或 "卖出"
    val type: String,

    val price: Double,

    val channel: String,

    val quantity: Int = 1,

    // 格式: "YYYY-MM"
    val date: String? = null,

    @ColumnInfo(name = "image_paths")
    val imagePaths: String = "",

    val note: String? = null,

    // 未来扩展
    @ColumnInfo(name = "extra_json")
    val extraJson: String = "{}",

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "updated_at")
    val updatedAt: Long = System.currentTimeMillis()
)
