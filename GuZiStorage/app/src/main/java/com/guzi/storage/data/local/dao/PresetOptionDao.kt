package com.guzi.storage.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.guzi.storage.data.local.entity.PresetOptionEntity
import kotlinx.coroutines.flow.Flow

/**
 * 预设选项数据访问对象
 */
@Dao
interface PresetOptionDao {

    // ===== 查询 =====

    @Query("SELECT * FROM preset_options WHERE attribute_name = :attributeName ORDER BY sort_order, option_value")
    fun getByAttribute(attributeName: String): Flow<List<PresetOptionEntity>>

    @Query("SELECT option_value FROM preset_options WHERE attribute_name = :attributeName ORDER BY sort_order, option_value")
    suspend fun getOptionValues(attributeName: String): List<String>

    @Query("SELECT DISTINCT attribute_name FROM preset_options")
    suspend fun getAllAttributeNames(): List<String>

    // ===== 写入 =====

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(option: PresetOptionEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(options: List<PresetOptionEntity>)

    @Delete
    suspend fun delete(option: PresetOptionEntity)

    @Query("DELETE FROM preset_options WHERE attribute_name = :attributeName AND option_value = :optionValue")
    suspend fun deleteByAttributeAndValue(attributeName: String, optionValue: String)
}
