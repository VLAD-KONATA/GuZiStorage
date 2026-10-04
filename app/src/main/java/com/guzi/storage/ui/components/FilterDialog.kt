package com.guzi.storage.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.guzi.storage.ui.SEARCH_FILTER_ATTRIBUTES
import com.guzi.storage.ui.getAttributeLabel
import com.guzi.storage.ui.group.FilterConfig

/**
 * 筛选弹窗 - 添加式多条件叠加筛选
 *
 * 用户按需添加筛选条件（属性→值），每个条件可多选值、可搜索、可选择空值
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterDialog(
    currentConfig: FilterConfig,
    onGetOptions: (String) -> kotlinx.coroutines.flow.StateFlow<List<String>>,
    onConfirm: (FilterConfig) -> Unit,
    onDismiss: () -> Unit
) {
    // 本地编辑状态：使用 mutableStateOf + 整体替换确保 recompose
    var localFilters by remember {
        mutableStateOf<Map<String, Set<String?>>>(
            currentConfig.filters.mapValues { it.value.toSet() }
        )
    }

    // 记录哪些条件展开
    var expandedAttr by remember { mutableStateOf<String?>(null) }

    // 是否显示"添加条件"属性选择弹窗
    var showAddPicker by remember { mutableStateOf(false) }

    // 搜索文本
    var searchQuery by remember { mutableStateOf("") }

    // 自定义输入
    var customInput by remember { mutableStateOf("") }

    // ===== 操作函数 =====

    fun toggleValue(attr: String, value: String?) {
        val current = localFilters[attr] ?: emptySet()
        val newSet = if (value in current) current - value else current + value
        localFilters = localFilters + (attr to newSet)
    }

    fun removeCondition(attr: String) {
        localFilters = localFilters - attr
        if (expandedAttr == attr) expandedAttr = null
    }

    fun addCondition(attr: String) {
        if (attr !in localFilters) {
            localFilters = localFilters + (attr to emptySet())
            expandedAttr = attr
        }
        showAddPicker = false
    }

    fun addCustomValue(attr: String, text: String) {
        if (text.isNotBlank()) {
            val current = localFilters[attr] ?: emptySet()
            if (text !in current) {
                localFilters = localFilters + (attr to (current + text))
            }
        }
    }

    // ===== 添加条件属性选择弹窗 =====
    if (showAddPicker) {
        val available = SEARCH_FILTER_ATTRIBUTES.filter { it.first !in localFilters }
        AlertDialog(
            onDismissRequest = { showAddPicker = false },
            title = { Text("添加筛选条件") },
            text = {
                if (available.isEmpty()) {
                    Text("所有属性均已添加")
                } else {
                    Column {
                        available.forEach { (attrName, label) ->
                            TextButton(
                                onClick = { addCondition(attrName) },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(label, modifier = Modifier.fillMaxWidth())
                            }
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { showAddPicker = false }) { Text("取消") }
            }
        )
    }

    // ===== 主界面 =====
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("筛选") },
                navigationIcon = {
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "关闭")
                    }
                },
                actions = {
                    TextButton(onClick = { localFilters = emptyMap() }) {
                        Text("重置")
                    }
                    TextButton(onClick = {
                        onConfirm(FilterConfig(
                            filters = localFilters.filterValues { it.isNotEmpty() }
                        ))
                    }) {
                        Text("确定")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            // 已添加的筛选条件
            items(localFilters.keys.toList(), key = { it }) { attrName ->
                val label = getAttributeLabel(attrName)
                val selected = localFilters[attrName] ?: emptySet()
                val isExpanded = expandedAttr == attrName

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    // 条件头部：属性名 + 已选值摘要 + 展开/删除
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expandedAttr = if (isExpanded) null else attrName }
                            .padding(horizontal = 12.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = label,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Medium
                            )
                            if (selected.isNotEmpty()) {
                                val displayValues = selected.map { it ?: "空值" }.joinToString("、")
                                Text(
                                    text = displayValues,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.primary,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            } else {
                                Text(
                                    text = "点击选择筛选值",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        IconButton(onClick = { expandedAttr = if (isExpanded) null else attrName }) {
                            Icon(
                                imageVector = if (isExpanded) Icons.Default.ExpandLess
                                              else Icons.Default.ExpandMore,
                                contentDescription = if (isExpanded) "收起" else "展开"
                            )
                        }
                        IconButton(onClick = { removeCondition(attrName) }) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "删除条件",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }

                    // 展开内容：搜索 + 选项列表 + 空值
                    AnimatedVisibility(visible = isExpanded) {
                        ConditionValuePicker(
                            attrName = attrName,
                            selectedValues = selected,
                            searchQuery = searchQuery,
                            customInput = customInput,
                            onSearchQueryChange = { searchQuery = it },
                            onCustomInputChange = { customInput = it },
                            onToggleValue = { value -> toggleValue(attrName, value) },
                            onToggleEmpty = { toggleValue(attrName, null) },
                            onAddCustom = { text ->
                                addCustomValue(attrName, text)
                                customInput = ""
                            },
                            onGetOptions = onGetOptions
                        )
                    }
                }
            }

            // 添加筛选条件按钮
            item {
                val canAdd = localFilters.size < SEARCH_FILTER_ATTRIBUTES.size
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(enabled = canAdd) { showAddPicker = true }
                            .padding(vertical = 12.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = null,
                            tint = if (canAdd) MaterialTheme.colorScheme.primary
                                   else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
                        )
                        Text(
                            text = "添加筛选条件",
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (canAdd) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            }

            // 空状态提示
            if (localFilters.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 48.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "暂无筛选条件，点击下方按钮添加",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

/**
 * 条件值选择器（搜索框 + 选项列表 + 自定义输入 + 空值选项）
 */
@Composable
private fun ConditionValuePicker(
    attrName: String,
    selectedValues: Set<String?>,
    searchQuery: String,
    customInput: String,
    onSearchQueryChange: (String) -> Unit,
    onCustomInputChange: (String) -> Unit,
    onToggleValue: (String) -> Unit,
    onToggleEmpty: () -> Unit,
    onAddCustom: (String) -> Unit,
    onGetOptions: (String) -> kotlinx.coroutines.flow.StateFlow<List<String>>
) {
    val options by onGetOptions(attrName).collectAsState()
    val filteredOptions = if (searchQuery.isBlank()) options
                          else options.filter { it.contains(searchQuery, ignoreCase = true) }

    // 已选的自定义值（不在预设库中的）
    val customSelected = selectedValues.filterNotNull().filter { it !in options }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        // 搜索框
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("搜索选项...") },
            singleLine = true,
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { onSearchQueryChange("") }) {
                        Icon(Icons.Default.Close, contentDescription = "清除")
                    }
                }
            }
        )

        // 选项列表
        if (filteredOptions.isNotEmpty()) {
            Column(modifier = Modifier.padding(vertical = 4.dp)) {
                filteredOptions.forEach { value ->
                    val isChecked = value in selectedValues
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onToggleValue(value) }
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = isChecked,
                            onCheckedChange = { onToggleValue(value) }
                        )
                        Text(
                            text = value,
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (isChecked) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }

        // 已添加的自定义值
        if (customSelected.isNotEmpty()) {
            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
            customSelected.forEach { value ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onToggleValue(value) }
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = true, onCheckedChange = { onToggleValue(value) })
                    Text(
                        text = value,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        // 自定义输入
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = customInput,
                onValueChange = onCustomInputChange,
                modifier = Modifier.weight(1f),
                placeholder = { Text("输入自定义值...") },
                singleLine = true
            )
            TextButton(
                onClick = { onAddCustom(customInput) },
                enabled = customInput.isNotBlank()
            ) {
                Text("添加")
            }
        }

        // 空值选项
        HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onToggleEmpty() }
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = null in selectedValues,
                onCheckedChange = { onToggleEmpty() }
            )
            Text(
                text = "包含空值（该字段为空的物品）",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
