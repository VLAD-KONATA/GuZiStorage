package com.guzi.storage.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * 物品表实体 - 存储谷子/周边物品的基本信息
 *
 * 存储策略:
 * - 所有已知属性作为独立列
 * - 多选字段以管道符 "|" 分隔存储 (如 "中|日")
 * - extra_json 仅用于未来新增属性
 */
@Entity(
    tableName = "items",
    indices = [
        Index(value = ["ip_name"]),
        Index(value = ["status"]),
        Index(value = ["category"])
    ]
)
data class ItemEntity(
    // ===== 主键 =====
    @PrimaryKey
    val id: String,

    // ===== 基础信息（单值字段）=====
    @ColumnInfo(name = "name_cn")
    val nameCn: String,

    @ColumnInfo(name = "name_en")
    val nameEn: String? = null,

    @ColumnInfo(name = "ip_name")
    val ipName: String,

    val producer: String? = null,

    @ColumnInfo(name = "sell_method")
    val sellMethod: String,

    val price: String? = null,

    val category: String? = null,

    @ColumnInfo(name = "release_date")
    val releaseDate: String? = null,

    val version: String? = null,

    val status: String = "无伤",

    val quantity: Int = 1,

    val note: String? = null,

    // ===== 多选字段（管道符分隔存储）=====
    @ColumnInfo(name = "origin_places")
    val originPlaces: String = "",

    @ColumnInfo(name = "sale_format")
    val saleFormat: String = "",

    val sizes: String = "",

    val materials: String = "",

    val characters: String = "",

    @ColumnInfo(name = "art_types")
    val artTypes: String = "",

    @ColumnInfo(name = "damage_details")
    val damageDetails: String = "",

    @ColumnInfo(name = "image_paths")
    val imagePaths: String = "",

    // ===== 未来扩展 =====
    @ColumnInfo(name = "extra_json")
    val extraJson: String = "{}",

    // ===== 时间戳 =====
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "updated_at")
    val updatedAt: Long = System.currentTimeMillis()
)
