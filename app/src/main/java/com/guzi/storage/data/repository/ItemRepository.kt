package com.guzi.storage.data.repository

import com.guzi.storage.data.local.dao.ItemDao
import com.guzi.storage.data.local.dao.ItemDao.IpGroupInfo
import com.guzi.storage.domain.model.Item
import com.guzi.storage.util.ImageFileManager
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
    private val itemDao: ItemDao,
    private val imageFileManager: ImageFileManager
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
            items.mapIndexed { index, item ->
                val createdAt = now + index
                item.copy(createdAt = createdAt, updatedAt = createdAt).toEntity()
            }
        )
    }

    suspend fun updateItem(item: Item) {
        val now = System.currentTimeMillis()
        val existing = itemDao.getById(item.id)
        val createdAt = item.createdAt.takeIf { it > 0 }
            ?: existing?.createdAt
            ?: now
        itemDao.update(
            item.copy(
                createdAt = createdAt,
                updatedAt = now
            ).toEntity()
        )
    }

    suspend fun deleteItem(id: String) {
        val entity = itemDao.getById(id) ?: return
        itemDao.delete(entity)
        imageFileManager.deleteItemImages(id)
    }

    suspend fun deleteItems(ids: List<String>) {
        itemDao.deleteByIds(ids)
        ids.forEach { imageFileManager.deleteItemImages(it) }
    }

    suspend fun getItemCount(): Int = itemDao.count()
}
