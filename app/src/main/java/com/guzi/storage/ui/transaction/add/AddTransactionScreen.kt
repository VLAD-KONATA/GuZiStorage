package com.guzi.storage.ui.transaction.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.guzi.storage.domain.model.TransactionType
import com.guzi.storage.ui.components.PresetDropdown

/**
 * 添加/编辑交易记录页面
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(
    itemId: String,
    itemNameCn: String,
    transactionId: String? = null,
    onNavigateBack: () -> Unit = {},
    viewModel: AddTransactionViewModel = hiltViewModel()
) {
    val formState by viewModel.formState.collectAsState()
    val saveResult by viewModel.saveResult.collectAsState()
    val channelOptions by viewModel.channelOptions.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    // 初始化表单
    LaunchedEffect(itemId, transactionId) {
        if (transactionId != null) {
            viewModel.loadTransaction(transactionId)
        } else {
            viewModel.initForAdd(itemId, itemNameCn)
        }
    }

    // 保存结果处理
    LaunchedEffect(saveResult) {
        when (saveResult) {
            is TransactionSaveResult.Success -> {
                viewModel.clearSaveResult()
                onNavigateBack()
            }
            is TransactionSaveResult.Error -> {
                snackbarHostState.showSnackbar((saveResult as TransactionSaveResult.Error).message)
                viewModel.clearSaveResult()
            }
            null -> {}
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (formState.isEditMode) "编辑记录" else "添加记录") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.save() }) {
                Icon(Icons.Default.Check, contentDescription = "保存")
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // 物品名称（只读）
            Text(
                text = "物品：${formState.nameCn}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 8.dp)
            )

            // ===== 购入 / 卖出 切换 =====
            SectionTitle("类型")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TransactionType.entries.forEach { type ->
                    FilterChip(
                        selected = formState.type == type.value,
                        onClick = { viewModel.updateType(type.value) },
                        label = { Text(type.value) }
                    )
                }
            }

            // ===== 金额 =====
            SectionTitle("交易信息")

            OutlinedTextField(
                value = formState.price,
                onValueChange = viewModel::updatePrice,
                label = { Text("价格 *") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = "price" in formState.validationErrors,
                supportingText = formState.validationErrors["price"]?.let { { Text(it) } }
            )

            PresetDropdown(
                label = "渠道",
                value = formState.channel,
                options = channelOptions,
                onValueChange = viewModel::updateChannel,
                onNewOption = viewModel::addNewChannelOption,
                required = true,
                isError = "channel" in formState.validationErrors,
                errorMessage = formState.validationErrors["channel"]
            )

            OutlinedTextField(
                value = formState.quantity,
                onValueChange = viewModel::updateQuantity,
                label = { Text("数量") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = "quantity" in formState.validationErrors,
                supportingText = formState.validationErrors["quantity"]?.let { { Text(it) } }
            )

            OutlinedTextField(
                value = formState.date,
                onValueChange = viewModel::updateDate,
                label = { Text("年月（如 2024-03）") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = { Text("YYYY-MM") }
            )

            // ===== 备注 =====
            SectionTitle("备注")
            OutlinedTextField(
                value = formState.note,
                onValueChange = viewModel::updateNote,
                label = { Text("备注") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 5
            )

            androidx.compose.foundation.layout.Spacer(modifier = Modifier.padding(bottom = 80.dp))
        }
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(top = 4.dp)
    )
}
