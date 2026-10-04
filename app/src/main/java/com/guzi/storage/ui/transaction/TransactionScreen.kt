package com.guzi.storage.ui.transaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.guzi.storage.domain.model.Transaction
import com.guzi.storage.domain.model.TransactionType
import com.guzi.storage.ui.components.ConfirmDialog

/**
 * 交易记录列表页 - 展示某物品的所有购入/卖出记录
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionScreen(
    itemId: String,
    onNavigateBack: () -> Unit = {},
    onAddTransaction: (String) -> Unit = {},
    onEditTransaction: (String) -> Unit = {},
    viewModel: TransactionViewModel = hiltViewModel()
) {
    LaunchedEffect(itemId) {
        viewModel.setItemId(itemId)
    }

    val item by viewModel.item.collectAsState()
    val transactions by viewModel.transactions.collectAsState()
    val pendingDeleteId by viewModel.pendingDeleteId.collectAsState()

    // 删除确认弹窗
    if (pendingDeleteId != null) {
        ConfirmDialog(
            title = "删除记录",
            message = "确定要删除这条交易记录吗？",
            confirmText = "确认删除",
            onConfirm = viewModel::confirmDeleteTransaction,
            onDismiss = viewModel::dismissDeleteTransaction
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(item?.let { "「${it.displayName}」的记录" } ?: "交易记录") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onAddTransaction(itemId) }) {
                Icon(Icons.Default.Add, contentDescription = "添加记录")
            }
        }
    ) { paddingValues ->
        if (transactions.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "还没有交易记录\n点击右下角 + 添加",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(transactions, key = { it.id }) { transaction ->
                    TransactionCard(
                        transaction = transaction,
                        onEditClick = { onEditTransaction(transaction.id) },
                        onDeleteClick = { viewModel.requestDeleteTransaction(transaction.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun TransactionCard(
    transaction: Transaction,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    val isBuy = transaction.type == TransactionType.BUY
    val typeColor = if (isBuy) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            // 顶部：类型标签 + 金额 + 操作按钮
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SuggestionChip(
                        onClick = {},
                        label = { Text(transaction.type.value) },
                        colors = SuggestionChipDefaults.suggestionChipColors(
                            containerColor = typeColor.copy(alpha = 0.12f),
                            labelColor = typeColor
                        )
                    )
                    Text(
                        text = "¥${transaction.price}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = typeColor
                    )
                }

                Row {
                    IconButton(onClick = onEditClick) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "编辑",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    IconButton(onClick = onDeleteClick) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "删除",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }

            // 渠道 + 数量
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "渠道：${transaction.channel}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.width(16.dp))
                Text(
                    text = "数量：${transaction.quantity}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // 日期 + 备注
            if (transaction.date != null || transaction.note != null) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (transaction.date != null) {
                        Text(
                            text = transaction.date,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    if (transaction.date != null && transaction.note != null) {
                        Spacer(Modifier.width(12.dp))
                    }
                    if (transaction.note != null) {
                        Text(
                            text = "备注：${transaction.note}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}
