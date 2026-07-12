package com.guzi.storage.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guzi.storage.data.repository.ItemRepository
import com.guzi.storage.data.repository.UserPreferencesRepository
import com.guzi.storage.ui.getAttributeLabel
import com.guzi.storage.util.ItemAttributeUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * 主页分组展示数据（含缩略图）
 */
data class IpGroupDisplay(
    val attrValue: String,   // 分组属性值（如 IP名"原神" 或 角色名"佐藤美和子"）
    val attrName: String,    // 分组属性名（如 "ip_name" 或 "characters"）
    val count: Int,
    val thumbnails: List<String>  // 最多4张缩略图路径
)

/**
 * 主页 ViewModel - 根据用户配置的分组层级，按第一级属性分组展示
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val itemRepository: ItemRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    val groupingConfig: StateFlow<List<String>> =
        userPreferencesRepository.groupingConfig
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = userPreferencesRepository.getGroupingConfig()
            )

    val ipGroups: StateFlow<List<IpGroupDisplay>> =
        combine(
            itemRepository.getAllItems(),
            userPreferencesRepository.groupingConfig
        ) { items, config ->
            val firstAttr = config.firstOrNull() ?: "ip_name"
            items.flatMap { item ->
                ItemAttributeUtils.getValues(item, firstAttr).map { value ->
                    value to item
                }
            }
                .groupBy { it.first }
                .map { (attrValue, pairs) ->
                    val groupItems = pairs.map { it.second }
                    IpGroupDisplay(
                        attrValue = attrValue,
                        attrName = firstAttr,
                        count = groupItems.size,
                        thumbnails = groupItems
                            .flatMap { it.imagePaths }
                            .take(4)
                    )
                }
                .sortedBy { it.attrValue }
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    /**
     * 获取当前第一级分组属性的中文名（用于 TopAppBar 标题）
     */
    fun getFirstLevelLabel(): String {
        val firstAttr = groupingConfig.value.firstOrNull() ?: "ip_name"
        return getAttributeLabel(firstAttr)
    }

    /**
     * 获取当前第一级分组属性名
     */
    fun getFirstLevelAttr(): String {
        return groupingConfig.value.firstOrNull() ?: "ip_name"
    }
}
