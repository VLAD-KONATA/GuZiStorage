package com.guzi.storage.ui.fileimport

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StandaloneImportScreen(
    onNavigateBack: () -> Unit = {},
    viewModel: StandaloneImportViewModel = hiltViewModel()
) {
    val importState by viewModel.importState.collectAsState()
    var smartCompressImages by rememberSaveable { mutableStateOf(false) }
    val title = when (viewModel.importType) {
        StandaloneImportType.ITEMS_CSV -> "导入物品 CSV"
        StandaloneImportType.TRANSACTIONS_CSV -> "导入交易记录 CSV"
        StandaloneImportType.IMAGE_ZIP -> "导入图片 ZIP"
    }

    val filePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        if (uri != null) viewModel.parseFile(uri)
    }

    val mimeTypes = when (viewModel.importType) {
        StandaloneImportType.IMAGE_ZIP -> arrayOf("application/zip", "application/x-zip-compressed")
        else -> arrayOf("text/csv", "text/comma-separated-values", "application/csv")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
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
                is StandaloneImportViewModel.ImportState.Idle -> {
                    IdleContent(
                        importType = viewModel.importType,
                        onSelectFile = { filePicker.launch(mimeTypes) }
                    )
                }

                is StandaloneImportViewModel.ImportState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is StandaloneImportViewModel.ImportState.Preview -> {
                    PreviewContent(
                        importType = viewModel.importType,
                        data = state.data,
                        smartCompressImages = smartCompressImages,
                        onSmartCompressChange = { smartCompressImages = it },
                        onConfirm = { viewModel.confirmImport(smartCompressImages) },
                        onCancel = viewModel::reset
                    )
                }

                is StandaloneImportViewModel.ImportState.Done -> {
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

                is StandaloneImportViewModel.ImportState.Error -> {
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
private fun IdleContent(
    importType: StandaloneImportType,
    onSelectFile: () -> Unit
) {
    val (title, desc) = when (importType) {
        StandaloneImportType.ITEMS_CSV -> Pair(
            "选择 items.csv 文件导入",
            "CSV 列：id,中文名称,IP名,发售形式,售卖方式,\n相关角色,柄图种类,发售日期,原袋,参考链接...\n（一对多用 | 分隔）\n\n必填：中文名称、IP名、相关角色、柄图种类\n\n导入时自动按物品属性去重，\n重复物品会合并数量"
        )
        StandaloneImportType.TRANSACTIONS_CSV -> Pair(
            "选择 transactions.csv 文件导入",
            "CSV 列：id,类型,名称,价格,渠道,\n数量,日期,备注\n\n注意：id 必须对应 app 数据库中\n已有的谷子 id（UUID），\n否则该行将被跳过"
        )
        StandaloneImportType.IMAGE_ZIP -> Pair(
            "选择 image.zip 文件导入",
            "ZIP 结构：\n└── image/\n    └── {谷子id}/\n        └── 图片文件\n\n注意：子目录名必须对应 app 数据库中\n已有的谷子 id（UUID），\n不存在的将被跳过\n\n图片将添加到对应谷子的相册中"
        )
    }

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
        Text(text = title, style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))
        Text(
            text = desc,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(32.dp))
        Button(onClick = onSelectFile) {
            Icon(Icons.Default.FileUpload, contentDescription = null)
            Text("  选择文件")
        }
    }
}

@Composable
private fun PreviewContent(
    importType: StandaloneImportType,
    data: StandalonePreviewData,
    smartCompressImages: Boolean,
    onSmartCompressChange: (Boolean) -> Unit,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        // 统计卡片
        Card(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("解析结果预览", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    when (data) {
                        is StandalonePreviewData.ItemsPreview -> {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(18.dp))
                                Text("物品 ${data.items.size} 条")
                            }
                        }
                        is StandalonePreviewData.TransactionsPreview -> {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                Icon(Icons.Default.SwapHoriz, contentDescription = null, modifier = Modifier.size(18.dp))
                                Text("交易记录 ${data.transactions.size} 条")
                            }
                        }
                        is StandalonePreviewData.ImagesPreview -> {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                Icon(Icons.Default.Image, contentDescription = null, modifier = Modifier.size(18.dp))
                                val totalImages = data.groups.sumOf { it.imageFiles.size }
                                Text("图片 $totalImages 张（${data.groups.size} 个物品）")
                            }
                        }
                    }
                    if (data.errors.isNotEmpty()) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Icon(Icons.Default.Warning, contentDescription = null, modifier = Modifier.size(18.dp), tint = MaterialTheme.colorScheme.error)
                            Text("警告 ${data.errors.size} 条", color = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // 预览列表
            when (data) {
                is StandalonePreviewData.ItemsPreview -> {
                    if (data.items.isNotEmpty()) {
                        item { Text("物品记录（前 20 条预览）：", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant) }
                        items(data.items.take(20), key = { "${it.tempId}:${it.item.id}" }) { parsed ->
                            val item = parsed.item
                            Text("• ${item.nameCn}（${item.ipNames.joinToString("、")}）×${item.quantity}", style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(vertical = 2.dp))
                        }
                        if (data.items.size > 20) {
                            item { Text("...及另外 ${data.items.size - 20} 条", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant) }
                        }
                    }
                }
                is StandalonePreviewData.TransactionsPreview -> {
                    if (data.transactions.isNotEmpty()) {
                        item { Text("交易记录（前 20 条预览）：", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant) }
                        items(data.transactions.take(20), key = { it.id }) { t ->
                            Text("• ${t.type.value} ${t.nameCn} ¥${t.price} ×${t.quantity}", style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(vertical = 2.dp))
                        }
                        if (data.transactions.size > 20) {
                            item { Text("...及另外 ${data.transactions.size - 20} 条", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant) }
                        }
                    }
                }
                is StandalonePreviewData.ImagesPreview -> {
                    if (data.groups.isNotEmpty()) {
                        item { Text("图片分组（前 20 条预览）：", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant) }
                        items(data.groups.take(20), key = { it.itemId }) { group ->
                            Text("• 物品 ${group.itemId.take(8)}...：${group.imageFiles.size} 张图片", style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(vertical = 2.dp))
                        }
                    }
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
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.padding(vertical = 2.dp)) {
                        Icon(Icons.Default.Warning, contentDescription = null, modifier = Modifier.size(14.dp), tint = MaterialTheme.colorScheme.error)
                        Text(error, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }

        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            if (importType == StandaloneImportType.IMAGE_ZIP) {
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
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(onClick = onCancel, modifier = Modifier.weight(1f)) { Text("取消") }
                Button(onClick = onConfirm, modifier = Modifier.weight(1f)) {
                    val confirmText = when (data) {
                        is StandalonePreviewData.ItemsPreview -> "确认导入 ${data.items.size} 条"
                        is StandalonePreviewData.TransactionsPreview -> "确认导入 ${data.transactions.size} 条"
                        is StandalonePreviewData.ImagesPreview -> "确认导入 ${data.groups.sumOf { it.imageFiles.size }} 张"
                    }
                    Text(confirmText)
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
        if (itemCount > 0) {
            Text("成功处理 $itemCount 条物品")
            if (mergedCount > 0) {
                Text("其中 $mergedCount 条合并到已有记录", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
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
