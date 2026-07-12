package com.guzi.storage.data.repository

import com.guzi.storage.data.local.dao.ItemDao
import com.guzi.storage.data.local.dao.ItemDao.IpGroupInfo
import com.guzi.storage.domain.model.Item
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 物品仓库 - 封装物品的 CRUD 操作
 *
 * 负责 Entity ↔ Domain Model 转换，对外暴露 Flow 响应式数据流
 */
@Singleton
class ItemRepository @Inject constructor(
    private val itemDao: ItemDao
) {

    // ===== 查询 =====

    fun getAllItems(): Flow<List<Item>> =
        itemDao.getAll().map { entities -> entities.map { Item.fromEntity(it) } }

    suspend fun getItemById(id: String): Item? =
        itemDao.getById(id)?.let { Item.fromEntity(it) }

    fun getItemByIdFlow(id: String): Flow<Item?> =
        itemDao.getByIdFlow(id).map { it?.let { entity -> Item.fromEntity(entity) } }

    fun getIpGroups(): Flow<List<IpGroupInfo>> =
        itemDao.getIpGroups()

    fun getItemsByIp(ipName: String): Flow<List<Item>> =
        itemDao.getItemsByIp(ipName).map { entities -> entities.map { Item.fromEntity(it) } }

    fun getItemsByCategory(category: String): Flow<List<Item>> =
        itemDao.getItemsByCategory(category).map { entities -> entities.map { Item.fromEntity(it) } }

    fun getItemsByProducer(producer: String): Flow<List<Item>> =
        itemDao.getItemsByProducer(producer).map { entities -> entities.map { Item.fromEntity(it) } }

    fun search(keyword: String): Flow<List<Item>> =
        itemDao.search(keyword).map { entities -> entities.map { Item.fromEntity(it) } }

    fun getItemsByCharacter(character: String): Flow<List<Item>> =
        itemDao.getItemsByCharacter(character).map { entities -> entities.map { Item.fromEntity(it) } }

    // ===== 写入 =====

    suspend fun addItem(item: Item) {
        val now = System.currentTimeMillis()
        itemDao.insert(
            item.copy(createdAt = now, updatedAt = now).toEntity()
        )
    }

    suspend fun addItems(items: List<Item>) {
        val now = System.currentTimeMillis()
        itemDao.insertAll(
            items.map { it.copy(createdAt = now, updatedAt = now).toEntity() }
        )
    }

    suspend fun updateItem(item: Item) {
        itemDao.update(
            item.copy(updatedAt = System.currentTimeMillis()).toEntity()
        )
    }

    suspend fun deleteItem(id: String) {
        val entity = itemDao.getById(id) ?: return
        itemDao.delete(entity)
    }

    suspend fun deleteItems(ids: List<String>) {
        itemDao.deleteByIds(ids)
    }

    suspend fun getItemCount(): Int = itemDao.count()
}
