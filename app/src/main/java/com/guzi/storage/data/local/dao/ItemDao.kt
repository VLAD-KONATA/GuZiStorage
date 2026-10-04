package com.guzi.storage.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.guzi.storage.data.local.entity.ItemEntity
import kotlinx.coroutines.flow.Flow

/**
 * 物品数据访问对象
 */
@Dao
interface ItemDao {

    // ===== 查询 =====

    @Query("SELECT * FROM items ORDER BY created_at DESC")
    fun getAll(): Flow<List<ItemEntity>>

    @Query("SELECT * FROM items WHERE id = :id")
    suspend fun getById(id: String): ItemEntity?

    @Query("SELECT * FROM items WHERE id = :id")
    fun getByIdFlow(id: String): Flow<ItemEntity?>

    /**
     * IP 分组信息
     */
    data class IpGroupInfo(
        val ipName: String,
        val count: Int
    )

    @Query("SELECT ip_name AS ipName, COUNT(*) AS count FROM items GROUP BY ip_name ORDER BY ip_name")
    fun getIpGroups(): Flow<List<IpGroupInfo>>

    @Query("""
        SELECT * FROM items
        WHERE ip_name = :ipName
           OR ip_name LIKE :ipName || '|%'
           OR ip_name LIKE '%|' || :ipName
           OR ip_name LIKE '%|' || :ipName || '|%'
        ORDER BY created_at DESC
    """)
    fun getItemsByIp(ipName: String): Flow<List<ItemEntity>>

    @Query("""
        SELECT * FROM items
        WHERE category = :category
           OR category LIKE :category || '|%'
           OR category LIKE '%|' || :category
           OR category LIKE '%|' || :category || '|%'
        ORDER BY created_at DESC
    """)
    fun getItemsByCategory(category: String): Flow<List<ItemEntity>>

    @Query("""
        SELECT * FROM items
        WHERE producer = :producer
           OR producer LIKE :producer || '|%'
           OR producer LIKE '%|' || :producer
           OR producer LIKE '%|' || :producer || '|%'
        ORDER BY created_at DESC
    """)
    fun getItemsByProducer(producer: String): Flow<List<ItemEntity>>

    /**
     * 关键词搜索（中文名、IP名、日文名模糊匹配）
     */
    @Query("""
        SELECT * FROM items 
        WHERE name_cn LIKE '%' || :keyword || '%' 
           OR ip_name LIKE '%' || :keyword || '%' 
           OR name_en LIKE '%' || :keyword || '%'
        ORDER BY created_at DESC
    """)
    fun search(keyword: String): Flow<List<ItemEntity>>

    /**
     * 按角色筛选（管道符分隔的 characters 列模糊匹配）
     */
    @Query("SELECT * FROM items WHERE characters LIKE '%' || :character || '%' ORDER BY created_at DESC")
    fun getItemsByCharacter(character: String): Flow<List<ItemEntity>>

    /**
     * 按种类筛选
     */
    @Query("""
        SELECT * FROM items
        WHERE category = :category
           OR category LIKE :category || '|%'
           OR category LIKE '%|' || :category
           OR category LIKE '%|' || :category || '|%'
        ORDER BY created_at DESC
    """)
    fun getItemsByCategoryExact(category: String): Flow<List<ItemEntity>>

    // ===== 写入 =====

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ItemEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<ItemEntity>)

    @Update
    suspend fun update(item: ItemEntity)

    @Delete
    suspend fun delete(item: ItemEntity)

    @Query("DELETE FROM items WHERE id IN (:ids)")
    suspend fun deleteByIds(ids: List<String>)

    @Query("SELECT COUNT(*) FROM items")
    suspend fun count(): Int
}
