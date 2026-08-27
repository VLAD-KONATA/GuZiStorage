package com.guzi.storage.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.guzi.storage.ui.group.SortConfig
import com.guzi.storage.ui.group.SortCriterion
import com.guzi.storage.ui.group.SortOrder
import com.guzi.storage.util.ItemSortFieldRegistry

/**
 * 排序弹窗 - 添加式多条件叠加排序
 *
 * 字段列表由 ItemSortFieldRegistry 反射自动生成
 * 每个条件包含：字段 + 升序/降序方向
 * 列表顺序 = 排序优先级（顶部优先）
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortDialog(
    currentConfig: SortConfig,
    onConfirm: (SortConfig) -> Unit,
    onDismiss: () -> Unit
) {
    var localCriteria by remember {
        mutableStateOf(currentConfig.criteria.toList())
    }

    var showAddPicker by remember { mutableStateOf(false) }

    val sortableFields = ItemSortFieldRegistry.sortableFields

    fun addCriterion(field: String) {
        if (localCriteria.none { it.field == field }) {
            localCriteria = localCriteria + SortCriterion(field, SortOrder.ASC)
        }
        showAddPicker = false
    }

    fun removeCriterion(index: Int) {
        localCriteria = localCriteria.toMutableList().apply { removeAt(index) }
    }

    fun changeOrder(index: Int, order: SortOrder) {
        localCriteria = localCriteria.toMutableList().also {
            it[index] = it[index].copy(order = order)
        }
    }

    // 添加字段选择弹窗
    if (showAddPicker) {
        val usedFields = localCriteria.map { it.field }.toSet()
        val available = sortableFields.filter { it.first !in usedFields }
        AlertDialog(
            onDismissRequest = { showAddPicker = false },
            title = { Text("添加排序条件") },
            text = {
                if (available.isEmpty()) {
                    Text("所有字段均已添加")
                } else {
                    Column {
                        available.forEach { (fieldName, label) ->
                            TextButton(
                                onClick = { addCriterion(fieldName) },
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("排序") },
                navigationIcon = {
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "关闭")
                    }
                },
                actions = {
                    TextButton(onClick = { localCriteria = emptyList() }) {
                        Text("重置")
                    }
                    TextButton(onClick = {
                        onConfirm(SortConfig(criteria = localCriteria))
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
            item {
                Text(
                    text = "按添加顺序作为排序优先级：越靠前优先级越高",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
            }

            itemsIndexed(
                items = localCriteria,
                key = { _, c -> c.field }
            ) { index, criterion ->
                SortCriterionCard(
                    index = index,
                    criterion = criterion,
                    onOrderChange = { order -> changeOrder(index, order) },
                    onRemove = { removeCriterion(index) }
                )
            }

            // 添加按钮
            item {
                val canAdd = localCriteria.size < sortableFields.size
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
                            text = "添加排序条件",
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (canAdd) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            }

            if (localCriteria.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 48.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "暂无排序条件，点击上方按钮添加",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SortCriterionCard(
    index: Int,
    criterion: SortCriterion,
    onOrderChange: (SortOrder) -> Unit,
    onRemove: () -> Unit
) {
    val label = ItemSortFieldRegistry.getLabel(criterion.field)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // 优先级序号
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .background(MaterialTheme.colorScheme.primary, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${index + 1}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = label,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )

            FilterChip(
                selected = criterion.order == SortOrder.ASC,
                onClick = { onOrderChange(SortOrder.ASC) },
                label = { Text("升序") },
                leadingIcon = {
                    Icon(
                        Icons.Default.ArrowUpward,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                }
            )
            FilterChip(
                selected = criterion.order == SortOrder.DESC,
                onClick = { onOrderChange(SortOrder.DESC) },
                label = { Text("降序") },
                leadingIcon = {
                    Icon(
                        Icons.Default.ArrowDownward,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                }
            )

            IconButton(onClick = onRemove) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "删除条件",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
