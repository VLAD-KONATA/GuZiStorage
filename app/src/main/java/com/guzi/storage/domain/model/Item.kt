package com.guzi.storage.domain.model

import com.guzi.storage.data.local.PipeListConverter
import com.guzi.storage.data.local.entity.ItemEntity

/**
 * 物品领域模型 - 业务层使用的物品数据结构
 *
 * 所有多选字段以 List<String> 表示，通过 PipeListConverter 与 Entity 互转
 */
data class Item(
    val id: String,
    val nameCn: String,
    val nameEn: String? = null,
    val ipName: String,
    val producer: String? = null,
    val sellMethod: String? = null,
    val price: String? = null,
    val category: String? = null,
    val releaseDate: String? = null,
    val version: String? = null,
    val status: String = "无伤",
    val quantity: Int = 1,
    val originalBagQuantity: Int? = null,
    val note: String? = null,
    val originPlaces: List<String> = emptyList(),
    val saleFormat: List<String> = emptyList(),
    val sizes: List<String> = emptyList(),
    val materials: List<String> = emptyList(),
    val characters: List<String> = emptyList(),
    val artTypes: List<String> = emptyList(),
    val damageDetails: List<String> = emptyList(),
    val imagePaths: List<String> = emptyList(),
    val referenceLinks: List<String> = emptyList(),
    val extraAttributes: Map<String, Any> = emptyMap(),
    val createdAt: Long = 0,
    val updatedAt: Long = 0
) {
    val ipNames: List<String>
        get() = PipeListConverter.toList(ipName)

    val producers: List<String>
        get() = PipeListConverter.toList(producer ?: "")

    val categories: List<String>
        get() = PipeListConverter.toList(category ?: "")

    /**
     * 显示名称：
     * - 再贩版本在前加（再贩）前缀，初贩不标注
     * - 有伤时附加状态详情后缀
     * 例: "（再贩）抱兔q（底锈）"
     */
    val displayName: String
        get() {
            val versionPrefix = if (version != null && version != "初贩") "（${version}）" else ""
            val damageSuffix = if (status != "无伤" && damageDetails.isNotEmpty())
                "（${damageDetails.joinToString("，")}）" else ""
            return "$versionPrefix$nameCn$damageSuffix"
        }

    /**
     * 转换为 Entity 存储
     */
    fun toEntity(): ItemEntity = ItemEntity(
        id = id,
        nameCn = nameCn,
        nameEn = nameEn,
        ipName = ipName,
        producer = producer,
        sellMethod = sellMethod,
        price = price,
        category = category,
        releaseDate = releaseDate,
        version = version,
        status = status,
        quantity = quantity,
        originalBagQuantity = originalBagQuantity,
        note = note,
        originPlaces = PipeListConverter.fromList(originPlaces),
        saleFormat = PipeListConverter.fromList(saleFormat).ifBlank { null },
        sizes = PipeListConverter.fromList(sizes),
        materials = PipeListConverter.fromList(materials),
        characters = PipeListConverter.fromList(characters),
        artTypes = PipeListConverter.fromList(artTypes),
        damageDetails = PipeListConverter.fromList(damageDetails),
        imagePaths = PipeListConverter.fromList(imagePaths),
        referenceLinks = PipeListConverter.fromList(referenceLinks),
        extraJson = "{}",
        createdAt = createdAt,
        updatedAt = updatedAt
    )

    companion object {
        /**
         * 从 Entity 构建领域模型
         */
        fun fromEntity(entity: ItemEntity): Item = Item(
            id = entity.id,
            nameCn = entity.nameCn,
            nameEn = entity.nameEn,
            ipName = entity.ipName,
            producer = entity.producer,
            sellMethod = entity.sellMethod,
            price = entity.price,
            category = entity.category,
            releaseDate = entity.releaseDate,
            version = entity.version,
            status = entity.status,
            quantity = entity.quantity,
            originalBagQuantity = entity.originalBagQuantity,
            note = entity.note,
            originPlaces = PipeListConverter.toList(entity.originPlaces),
            saleFormat = PipeListConverter.toList(entity.saleFormat ?: ""),
            sizes = PipeListConverter.toList(entity.sizes),
            materials = PipeListConverter.toList(entity.materials),
            characters = PipeListConverter.toList(entity.characters),
            artTypes = PipeListConverter.toList(entity.artTypes),
            damageDetails = PipeListConverter.toList(entity.damageDetails),
            imagePaths = PipeListConverter.toList(entity.imagePaths),
            referenceLinks = PipeListConverter.toList(entity.referenceLinks),
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
    }
}
