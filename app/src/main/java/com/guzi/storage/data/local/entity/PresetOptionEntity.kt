package com.guzi.storage.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 预设选项表实体 - 存储各属性的可选值
 *
 * attribute_name: 属性标识（如 "ip_name", "channel", "category" 等）
 * option_value: 选项值（如 "名侦探柯南", "咸鱼" 等）
 *
 * 同一属性下的选项值不可重复（联合唯一约束）
 */
@Entity(
    tableName = "preset_options",
    indices = [
        androidx.room.Index(
            value = ["attribute_name", "option_value"],
            unique = true
        )
    ]
)
data class PresetOptionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "attribute_name")
    val attributeName: String,

    @ColumnInfo(name = "option_value")
    val optionValue: String,

    @ColumnInfo(name = "sort_order")
    val sortOrder: Int = 0
)
