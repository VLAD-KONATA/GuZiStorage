package com.guzi.storage.ui.zipimport

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * ZIP 批量导入页面
 *
 * 流程: 选择 ZIP 文件 → 预览解析结果 → 确认导入 → 完成报告
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ZipImportScreen(
    onNavigateBack: () -> Unit = {},
    viewModel: ZipImportViewModel = hiltViewModel()
) {
    val importState by viewModel.importState.collectAsState()
    var smartCompressImages by rememberSaveable { mutableStateOf(false) }

    val filePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        if (uri != null) viewModel.parseFile(uri)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("批量导入") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (val state = importState) {
                is ZipImportViewModel.ImportState.Idle -> {
                    IdleContent(onSelectFile = {
                        filePicker.launch(arrayOf("application/zip", "application/x-zip-compressed"))
                    })
                }

                is ZipImportViewModel.ImportState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is ZipImportViewModel.ImportState.Preview -> {
                    PreviewContent(
                        data = state.data,
                        smartCompressImages = smartCompressImages,
                        onSmartCompressChange = { smartCompressImages = it },
                        onConfirm = { viewModel.confirmImport(smartCompressImages) },
                        onCancel = viewModel::reset
                    )
                }

                is ZipImportViewModel.ImportState.Done -> {
                    DoneContent(
                        itemCount = state.itemCount,
                        transactionCount = state.transactionCount,
                        imageCount = state.imageCount,
                        mergedCount = state.mergedCount,
                        errorLines = state.errorLines,
                        onBack = {
                            viewModel.reset()
                            onNavigateBack()
                        },
                        onImportMore = viewModel::reset
                    )
                }

                is ZipImportViewModel.ImportState.Error -> {
                    ErrorContent(
                        message = state.message,
                        onRetry = viewModel::reset
                    )
                }
            }
        }
    }
}

@Composable
private fun IdleContent(onSelectFile: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Default.FileUpload,
            contentDescription = null,
            modifier = Modifier.size(72.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.height(24.dp))
        Text(
            text = "选择 ZIP 压缩包导入",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "压缩包结构：\n" +
                    "├── items.csv（谷子信息，含 id 列）\n" +
                    "├── transactions.csv（交易记录，id 为外键）\n" +
                    "└── image/（图片文件夹）\n" +
                    "    └── {id}/（与 items.csv 的 id 对应）\n\n" +
                    "必填字段：中文名称、IP名、相关角色、柄图种类\n" +
                    "一对多字段用 | 分隔",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(32.dp))
        Button(onClick = onSelectFile) {
            Icon(Icons.Default.FileUpload, contentDescription = null)
            Text("  选择压缩包")
        }
    }
}

@Composable
private fun PreviewContent(
    data: ZipImportData,
    smartCompressImages: Boolean,
    onSmartCompressChange: (Boolean) -> Unit,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        // 统计卡片
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("解析结果预览", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(18.dp))
                        Text("物品 ${data.items.size} 条")
                    }
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Icon(Icons.Default.SwapHoriz, contentDescription = null, modifier = Modifier.size(18.dp))
                        Text("交易 ${data.transactions.size} 条")
                    }
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Icon(Icons.Default.Image, contentDescription = null, modifier = Modifier.size(18.dp))
                        Text("图片 ${data.totalImageCount} 张")
                    }
                }
                if (data.errors.isNotEmpty()) {
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "警告 ${data.errors.size} 条",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // 物品预览
            if (data.items.isNotEmpty()) {
                item {
                    Text(
                        "物品记录（前 20 条预览）：",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                items(data.items.take(20), key = { "${it.tempId}:${it.item.id}" }) { parsedItem ->
                    val item = parsedItem.item
                    val imageInfo = if (parsedItem.imageFiles.isNotEmpty()) " [${parsedItem.imageFiles.size}张图]" else ""
                    Text(
                        text = "• ${item.nameCn}（${item.ipNames.joinToString("、")}）$imageInfo",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                }
                if (data.items.size > 20) {
                    item {
                        Text(
                            "...及另外 ${data.items.size - 20} 条",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // 交易记录预览
            if (data.transactions.isNotEmpty()) {
                item {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "交易记录（前 10 条预览）：",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                items(data.transactions.take(10), key = { it.transaction.id }) { parsedTxn ->
                    val t = parsedTxn.transaction
                    Text(
                        text = "• ${t.type.value} ${t.nameCn} ¥${t.price} ×${t.quantity}",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                }
            }

            // 错误信息
            if (data.errors.isNotEmpty()) {
                item {
                    Spacer(Modifier.height(8.dp))
                    HorizontalDivider()
                    Spacer(Modifier.height(8.dp))
                    Text("警告信息：", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.error)
                }
                itemsIndexed(data.errors, key = { index, error -> "$index:$error" }) { _, error ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.padding(vertical = 2.dp)
                    ) {
                        Icon(Icons.Default.Warning, contentDescription = null, modifier = Modifier.size(14.dp), tint = MaterialTheme.colorScheme.error)
                        Text(error, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }

        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("智能压缩大图", style = MaterialTheme.typography.bodyMedium)
                Switch(
                    checked = smartCompressImages,
                    onCheckedChange = onSmartCompressChange
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(onClick = onCancel, modifier = Modifier.weight(1f)) { Text("取消") }
                Button(
                    onClick = onConfirm,
                    modifier = Modifier.weight(1f),
                    enabled = data.items.isNotEmpty()
                ) {
                    Text("确认导入 ${data.items.size} 条")
                }
            }
        }
    }
}

@Composable
private fun DoneContent(
    itemCount: Int,
    transactionCount: Int,
    imageCount: Int,
    mergedCount: Int,
    errorLines: List<String>,
    onBack: () -> Unit,
    onImportMore: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(72.dp), tint = MaterialTheme.colorScheme.primary)
        Spacer(Modifier.height(16.dp))
        Text("导入完成", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Text("成功处理 $itemCount 条物品")
        if (mergedCount > 0) {
            Text(
                "其中 $mergedCount 条合并到已有记录",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        if (transactionCount > 0) Text("$transactionCount 条交易记录")
        if (imageCount > 0) Text("$imageCount 张图片")
        if (errorLines.isNotEmpty()) {
            Spacer(Modifier.height(4.dp))
            Text("跳过 ${errorLines.size} 条异常", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }
        Spacer(Modifier.height(32.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedButton(onClick = onImportMore) { Text("继续导入") }
            Button(onClick = onBack) { Text("返回主页") }
        }
    }
}

@Composable
private fun ErrorContent(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(Icons.Default.Warning, contentDescription = null, modifier = Modifier.size(56.dp), tint = MaterialTheme.colorScheme.error)
        Spacer(Modifier.height(16.dp))
        Text(message, color = MaterialTheme.colorScheme.error)
        Spacer(Modifier.height(16.dp))
        Button(onClick = onRetry) { Text("重新选择文件") }
    }
}
