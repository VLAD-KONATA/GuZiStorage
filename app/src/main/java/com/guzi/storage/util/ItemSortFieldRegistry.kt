package com.guzi.storage.util

import com.guzi.storage.domain.model.Item
import com.guzi.storage.ui.group.SortOrder
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

/**
 * Item 排序字段注册中心 - 反射式自动扫描
 *
 * 使用方式：
 * - 需要新增可排序字段时，只需在 [FIELD_LABELS] 中加一行 "字段名(驼峰)" to "中文标签"
 * - Comparator 通过反射统一生成，无需在任何地方写 when/switch
 *
 * 空值处理：所有条件统一 nullsLast（无论升降序，空值排最后）
 */
object ItemSortFieldRegistry {

    /**
     * Item 属性名（驼峰命名） → 中文显示标签
     *
     * 顺序即为 UI 中"添加排序条件"弹窗的展示顺序
     * 未列入此表的字段不会作为排序候选
     */
    private val FIELD_LABELS: LinkedHashMap<String, String> = linkedMapOf(
        "createdAt" to "创建时间",
        "updatedAt" to "更新时间",
        "nameCn" to "中文名称",
        "nameEn" to "日文/英文名称",
        "ipName" to "IP名",
        "producer" to "出品商",
        "sellMethod" to "售卖方式",
        "category" to "种类",
        "version" to "版本",
        "status" to "状态",
        "price" to "价格",
        "releaseDate" to "发售日期",
        "quantity" to "数量"
        // 未来若添加新排序字段：新增一行 "字段名" to "中文标签" 即可
    )

    /** 允许排序的 Kotlin 类型 */
    private val SORTABLE_TYPES = setOf(
        String::class,
        Int::class,
        Long::class,
        Double::class,
        Float::class
    )

    /** 反射属性缓存 */
    private val propertyMap: Map<String, KProperty1<Item, *>> by lazy {
        Item::class.memberProperties.associateBy { it.name }
    }

    /**
     * 所有可排序字段（Kotlin属性名 → 中文标签）
     *
     * 仅返回：
     * 1. 存在于 [FIELD_LABELS] 中
     * 2. Item 上确有该属性
     * 3. 属性类型在 [SORTABLE_TYPES] 中（含可空版本）
     */
    val sortableFields: List<Pair<String, String>> by lazy {
        FIELD_LABELS.mapNotNull { (name, label) ->
            val prop = propertyMap[name] ?: return@mapNotNull null
            val classifier = prop.returnType.classifier
            if (classifier in SORTABLE_TYPES) (name to label) else null
        }
    }

    /**
     * 获取字段中文标签
     */
    fun getLabel(fieldName: String): String =
        FIELD_LABELS[fieldName] ?: fieldName

    /**
     * 构建单字段 Comparator
     * - 空值统一置尾（无论升降序方向）
     * - 未知字段返回中性 Comparator
     */
    fun buildComparator(fieldName: String, order: SortOrder): Comparator<Item> {
        val prop = propertyMap[fieldName] ?: return Comparator { _, _ -> 0 }
        val ascComparator = Comparator<Item> { a, b ->
            val va = prop.get(a)
            val vb = prop.get(b)
            when {
                va == null && vb == null -> 0
                va == null -> 1
                vb == null -> -1
                else -> {
                    @Suppress("UNCHECKED_CAST")
                    (va as Comparable<Any>).compareTo(vb)
                }
            }
        }
        return if (order == SortOrder.DESC) ascComparator.reversed() else ascComparator
    }
}
