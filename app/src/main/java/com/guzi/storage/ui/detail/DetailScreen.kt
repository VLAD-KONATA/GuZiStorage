package com.guzi.storage.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.guzi.storage.domain.model.Item
import com.guzi.storage.ui.components.AdaptiveImage
import com.guzi.storage.ui.components.ConfirmDialog
import com.guzi.storage.util.CurrencyUtils

/**
 * 物品详情页
 *
 * 布局：顶部左侧图片 + 右侧基础信息，下方可展开详细信息，最下方交易记录入口
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    itemId: String,
    onNavigateBack: () -> Unit = {},
    onEditClick: (String) -> Unit = {},
    onCopyEditClick: (String) -> Unit = {},
    onTransactionClick: (String) -> Unit = {},
    onImageClick: (itemId: String, startIndex: Int) -> Unit = { _, _ -> },
    viewModel: DetailViewModel = hiltViewModel()
) {
    LaunchedEffect(itemId) {
        viewModel.setItemId(itemId)
    }

    val item by viewModel.item.collectAsState()
    val transactionCount by viewModel.transactionCount.collectAsState()
    val showDeleteDialog by viewModel.showDeleteDialog.collectAsState()
    val deleteComplete by viewModel.deleteComplete.collectAsState()
    val clipboardManager = LocalClipboardManager.current

    // 删除完成后返回
    LaunchedEffect(deleteComplete) {
        if (deleteComplete) {
            viewModel.clearDeleteComplete()
            onNavigateBack()
        }
    }

    // 删除确认弹窗
    if (showDeleteDialog && item != null) {
        ConfirmDialog(
            title = "删除谷子",
            message = "确定要删除「${item!!.displayName}」吗？\n关联的 $transactionCount 条交易记录也将同时删除。",
            confirmText = "确认删除",
            onConfirm = viewModel::confirmDelete,
            onDismiss = viewModel::dismissDelete
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(item?.displayName ?: "谷子详情") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
                    }
                },
                actions = {
                    IconButton(onClick = { item?.let { onEditClick(it.id) } }) {
                        Icon(Icons.Default.Edit, contentDescription = "编辑")
                    }
                    IconButton(onClick = { item?.let { onCopyEditClick(it.id) } }) {
                        Icon(Icons.Default.ContentCopy, contentDescription = "复制后编辑")
                    }
                    IconButton(onClick = viewModel::requestDelete) {
                        Icon(Icons.Default.Delete, contentDescription = "删除")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (item == null) {
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("加载中...", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        } else {
            DetailContent(
                item = item!!,
                transactionCount = transactionCount,
                onTransactionClick = { onTransactionClick(item!!.id) },
                onImageClick = { index -> onImageClick(item!!.id, index) },
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}

@Composable
private fun DetailContent(
    modifier: Modifier = Modifier,
    item: Item,
    transactionCount: Int,
    onTransactionClick: () -> Unit,
    onImageClick: (Int) -> Unit = {}
) {
    val clipboardManager = LocalClipboardManager.current
    var detailExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // ===== 顶部：左侧图片 + 右侧基础信息 =====
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // 左：图片轮播
            ImagePager(
                imagePaths = item.imagePaths,
                onImageClick = onImageClick,
                modifier = Modifier
                    .weight(0.45f)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(10.dp))
            )

            // 右：基础信息
            Column(
                modifier = Modifier
                    .weight(0.55f)
                    .padding(top = 4.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // 名称
                Text(
                    text = item.displayName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                // 主键ID
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "ID：${item.id}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = "复制ID",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .size(16.dp)
                            .clickable { clipboardManager.setText(AnnotatedString(item.id)) }
                    )
                }
                InfoRow(label = "IP", value = item.ipName)
                if (item.characters.isNotEmpty()) {
                    InfoRow(label = "角色", value = item.characters.joinToString("、"))
                }
                if (item.category != null) {
                    InfoRow(label = "种类", value = item.category)
                }
                if (item.sizes.isNotEmpty()) {
                    InfoRow(label = "尺寸", value = item.sizes.joinToString("、"))
                }
            }
        }

        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))

        // ===== 可展开：详细信息 =====
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            // 展开/收起按钮
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "详细信息",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                IconButton(onClick = { detailExpanded = !detailExpanded }) {
                    Icon(
                        imageVector = if (detailExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = if (detailExpanded) "收起" else "展开"
                    )
                }
            }

            if (detailExpanded) {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    if (item.nameEn != null) InfoRow(label = "日文/英文名", value = item.nameEn)
                    if (item.producer != null) InfoRow(label = "出品商", value = item.producer)
                    if (item.originPlaces.isNotEmpty()) InfoRow(label = "出品地", value = item.originPlaces.joinToString("、"))
                    if (item.saleFormat.isNotEmpty()) InfoRow(label = "发售形式", value = item.saleFormat.joinToString("、"))
                    InfoRow(label = "售卖方式", value = item.sellMethod)
                    if (item.price != null) InfoRow(label = "价格", value = CurrencyUtils.formatPrice(item.price))
                    if (item.releaseDate != null) InfoRow(label = "发售日期", value = item.releaseDate)
                    if (item.materials.isNotEmpty()) InfoRow(label = "材质", value = item.materials.joinToString("、"))
                    if (item.artTypes.isNotEmpty()) InfoRow(label = "柄图种类", value = item.artTypes.joinToString("、"))
                    InfoRow(label = "状态", value = if (item.status == "无伤") "无伤" else "${item.status}（${item.damageDetails.joinToString("，")}）")
                    if (item.version != null) InfoRow(label = "版本", value = item.version)
                    InfoRow(label = "数量", value = "${item.quantity} 件")
                    if (item.note != null) InfoRow(label = "备注", value = item.note)
                }
                Spacer(Modifier.height(8.dp))
            }
        }

        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))

        // ===== 交易记录入口 =====
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "购入/卖出记录",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "共 $transactionCount 条记录",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
                TextButton(onClick = onTransactionClick) {
                    Text("查看全部")
                }
            }
        }

        Spacer(Modifier.height(24.dp))
    }
}

/**
 * 图片横向翻页器
 */
@Composable
private fun ImagePager(
    modifier: Modifier = Modifier,
    imagePaths: List<String>,
    onImageClick: (Int) -> Unit = {}
) {
    if (imagePaths.isEmpty()) {
        Box(
            modifier = modifier.background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Image,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
            )
        }
    } else {
        val pagerState = rememberPagerState(pageCount = { imagePaths.size })
        Box(modifier = modifier) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                AdaptiveImage(
                    imagePath = imagePaths[page],
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { onImageClick(page) }
                )
            }
            // 页码指示器
            if (imagePaths.size > 1) {
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    repeat(imagePaths.size) { index ->
                        Box(
                            modifier = Modifier
                                .size(if (pagerState.currentPage == index) 8.dp else 5.dp)
                                .clip(CircleShape)
                                .background(
                                    if (pagerState.currentPage == index)
                                        MaterialTheme.colorScheme.primary
                                    else
                                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                                )
                        )
                    }
                }
            }
        }
    }
}

/**
 * 信息行：标签 + 值（值可长按复制）
 */
@Composable
private fun InfoRow(label: String, value: String) {
    Row(verticalAlignment = Alignment.Top) {
        Text(
            text = "$label：",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            modifier = Modifier.width(80.dp)
        )
        SelectionContainer {
            Text(
                text = value,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
