package com.guzi.storage.ui.group

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SelectAll
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.guzi.storage.domain.model.Item
import com.guzi.storage.ui.ViewMode
import com.guzi.storage.ui.components.ConfirmDialog
import com.guzi.storage.ui.components.FilterDialog
import java.io.File

/**
 * 分组子页面 - 支持多级分组导航
 *
 * 非最终级：显示子分组卡片（网格/列表）
 * 最终级：显示物品列表（搜索、筛选弹窗、批量选择删除）
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupScreen(
    path: List<Pair<String, String>>,
    depth: Int,
    onItemClick: (String) -> Unit = {},
    onSubGroupClick: (List<Pair<String, String>>) -> Unit = {},
    onAddClick: () -> Unit = {},
    onNavigateBack: () -> Unit = {},
    viewModel: GroupViewModel = hiltViewModel()
) {
    LaunchedEffect(path) {
        viewModel.setPath(path)
    }

    val isFinalLevel by viewModel.isFinalLevel.collectAsState()
    val breadcrumb by viewModel.breadcrumb.collectAsState()

    if (isFinalLevel) {
        FinalLevelContent(
            breadcrumb = breadcrumb,
            onItemClick = onItemClick,
            onAddClick = onAddClick,
            onNavigateBack = onNavigateBack,
            viewModel = viewModel
        )
    } else {
        SubGroupContent(
            breadcrumb = breadcrumb,
            path = path,
            onSubGroupClick = onSubGroupClick,
            onAddClick = onAddClick,
            onNavigateBack = onNavigateBack,
            viewModel = viewModel
        )
    }
}

// ===== 子分组模式（中间层级） =====

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SubGroupContent(
    breadcrumb: String,
    path: List<Pair<String, String>>,
    onSubGroupClick: (List<Pair<String, String>>) -> Unit,
    onAddClick: () -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: GroupViewModel
) {
    val subGroups by viewModel.subGroups.collectAsState()
    val currentLevelAttr by viewModel.currentLevelAttr.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    var showSearch by remember { mutableStateOf(false) }
    var viewMode by rememberSaveable { mutableStateOf(ViewMode.GRID) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(breadcrumb, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        viewMode = if (viewMode == ViewMode.GRID) ViewMode.LIST else ViewMode.GRID
                    }) {
                        Icon(
                            imageVector = if (viewMode == ViewMode.GRID) Icons.Default.ViewList
                                          else Icons.Default.GridView,
                            contentDescription = if (viewMode == ViewMode.GRID) "切换为列表" else "切换为收藏夹"
                        )
                    }
                    IconButton(onClick = { showSearch = !showSearch }) {
                        Icon(Icons.Default.Search, contentDescription = "搜索")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(Icons.Default.Add, contentDescription = "添加谷子")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (showSearch) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = viewModel::updateSearchQuery,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    placeholder = { Text("搜索名称、角色、种类...") },
                    singleLine = true,
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { viewModel.updateSearchQuery("") }) {
                                Icon(Icons.Default.Clear, contentDescription = "清除")
                            }
                        }
                    }
                )
            }

            if (subGroups.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (searchQuery.isNotEmpty()) "无搜索结果"
                               else "该分组暂无子分组",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                val attr = currentLevelAttr ?: ""
                when (viewMode) {
                    ViewMode.GRID -> {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(12.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(subGroups) { group ->
                                SubGroupGridCard(
                                    groupName = group.attrValue,
                                    count = group.count,
                                    thumbnails = group.thumbnails,
                                    onClick = {
                                        onSubGroupClick(path + (attr to group.attrValue))
                                    }
                                )
                            }
                        }
                    }
                    ViewMode.LIST -> {
                        LazyColumn(
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(subGroups) { group ->
                                SubGroupListCard(
                                    groupName = group.attrValue,
                                    count = group.count,
                                    onClick = {
                                        onSubGroupClick(path + (attr to group.attrValue))
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SubGroupGridCard(
    groupName: String,
    count: Int,
    thumbnails: List<String>,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column {
            ItemThumbnail(
                imagePaths = thumbnails,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = groupName,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "$count 件",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun SubGroupListCard(
    groupName: String,
    count: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 14.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = groupName,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "$count 件",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// ===== 最终级模式（物品列表） =====

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FinalLevelContent(
    breadcrumb: String,
    onItemClick: (String) -> Unit,
    onAddClick: () -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: GroupViewModel
) {
    val items by viewModel.items.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedIds by viewModel.selectedIds.collectAsState()
    val isSelectionMode by viewModel.isSelectionMode.collectAsState()
    val showBatchDeleteDialog by viewModel.showBatchDeleteDialog.collectAsState()
    val filterConfig by viewModel.filterConfig.collectAsState()

    var showSearch by remember { mutableStateOf(false) }
    var showFilterDialog by remember { mutableStateOf(false) }
    var viewMode by rememberSaveable { mutableStateOf(ViewMode.GRID) }

    val activeFilterCount = filterConfig.activeFilterCount()

    // 批量删除确认弹窗
    if (showBatchDeleteDialog) {
        ConfirmDialog(
            title = "批量删除",
            message = "确定要删除选中的 ${selectedIds.size} 件谷子吗？\n关联的交易记录也将同时删除。",
            confirmText = "确认删除",
            onConfirm = viewModel::confirmBatchDelete,
            onDismiss = viewModel::dismissBatchDelete
        )
    }

    // 筛选弹窗
    if (showFilterDialog) {
        Dialog(
            onDismissRequest = { showFilterDialog = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            FilterDialog(
                currentConfig = filterConfig,
                onGetOptions = { attr -> viewModel.getFilterOptions(attr) },
                onConfirm = { config ->
                    viewModel.setFilterConfig(config)
                    showFilterDialog = false
                },
                onDismiss = { showFilterDialog = false }
            )
        }
    }

    Scaffold(
        topBar = {
            if (isSelectionMode) {
                // 选择模式 TopAppBar
                TopAppBar(
                    title = { Text("已选 ${selectedIds.size} 件") },
                    navigationIcon = {
                        IconButton(onClick = viewModel::clearSelection) {
                            Icon(Icons.Default.Clear, contentDescription = "取消选择")
                        }
                    },
                    actions = {
                        IconButton(onClick = viewModel::selectAll) {
                            Icon(Icons.Default.SelectAll, contentDescription = "全选")
                        }
                        IconButton(onClick = viewModel::requestBatchDelete) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "删除选中",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                )
            } else {
                // 普通 TopAppBar
                TopAppBar(
                    title = { Text(breadcrumb, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
                        }
                    },
                    actions = {
                        // 筛选按钮（带 Badge）
                        IconButton(onClick = { showFilterDialog = true }) {
                            BadgedBox(
                                badge = {
                                    if (activeFilterCount > 0) {
                                        Badge { Text(activeFilterCount.toString()) }
                                    }
                                }
                            ) {
                                Icon(
                                    Icons.Default.FilterList,
                                    contentDescription = "筛选",
                                    tint = if (activeFilterCount > 0) MaterialTheme.colorScheme.primary
                                           else MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                        // 视图切换按钮
                        IconButton(onClick = {
                            viewMode = if (viewMode == ViewMode.GRID) ViewMode.LIST else ViewMode.GRID
                        }) {
                            Icon(
                                imageVector = if (viewMode == ViewMode.GRID) Icons.Default.ViewList
                                              else Icons.Default.GridView,
                                contentDescription = if (viewMode == ViewMode.GRID) "切换为列表" else "切换为收藏夹"
                            )
                        }
                        IconButton(onClick = { showSearch = !showSearch }) {
                            Icon(Icons.Default.Search, contentDescription = "搜索")
                        }
                    }
                )
            }
        },
        floatingActionButton = {
            if (!isSelectionMode) {
                FloatingActionButton(onClick = onAddClick) {
                    Icon(Icons.Default.Add, contentDescription = "添加谷子")
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // 搜索栏
            if (showSearch && !isSelectionMode) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = viewModel::updateSearchQuery,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    placeholder = { Text("搜索名称、角色、种类...") },
                    singleLine = true,
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { viewModel.updateSearchQuery("") }) {
                                Icon(Icons.Default.Clear, contentDescription = "清除")
                            }
                        }
                    }
                )
            }

            if (items.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = when {
                            activeFilterCount > 0 -> "当前筛选条件下暂无谷子"
                            searchQuery.isNotEmpty() -> "无搜索结果"
                            else -> "该分组暂无谷子"
                        },
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                when (viewMode) {
                    ViewMode.GRID -> {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(12.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(items) { item ->
                                val isSelected = item.id in selectedIds
                                ItemGridCard(
                                    item = item,
                                    isSelected = isSelected,
                                    isSelectionMode = isSelectionMode,
                                    onClick = {
                                        if (isSelectionMode) viewModel.toggleSelection(item.id)
                                        else onItemClick(item.id)
                                    },
                                    onLongClick = { viewModel.toggleSelection(item.id) }
                                )
                            }
                        }
                    }
                    ViewMode.LIST -> {
                        LazyColumn(
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(items) { item ->
                                val isSelected = item.id in selectedIds
                                ItemListCard(
                                    item = item,
                                    isSelected = isSelected,
                                    isSelectionMode = isSelectionMode,
                                    onClick = {
                                        if (isSelectionMode) viewModel.toggleSelection(item.id)
                                        else onItemClick(item.id)
                                    },
                                    onLongClick = { viewModel.toggleSelection(item.id) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// ===== 物品卡片组件 =====

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ItemGridCard(
    item: Item,
    isSelected: Boolean,
    isSelectionMode: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(onClick = onClick, onLongClick = onLongClick),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 6.dp else 2.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.secondaryContainer
                             else MaterialTheme.colorScheme.surface
        )
    ) {
        Box {
            Column {
                ItemThumbnail(
                    imagePaths = item.imagePaths,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                )

                Column(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = item.displayName,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    if (item.characters.isNotEmpty()) {
                        Text(
                            text = item.characters.joinToString("、"),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    if (item.category != null) {
                        Text(
                            text = item.category,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            if (isSelectionMode) {
                SelectionIndicator(
                    isSelected = isSelected,
                    modifier = Modifier.align(Alignment.TopEnd)
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ItemListCard(
    item: Item,
    isSelected: Boolean,
    isSelectionMode: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(onClick = onClick, onLongClick = onLongClick),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 4.dp else 1.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.secondaryContainer
                             else MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                val firstImage = item.imagePaths.firstOrNull()
                if (firstImage != null) {
                    AsyncImage(
                        model = File(context.filesDir, firstImage),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Image,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
                    )
                }
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = item.displayName,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                val subInfo = buildList {
                    if (item.characters.isNotEmpty()) add(item.characters.joinToString("、"))
                    if (item.category != null) add(item.category)
                    if (item.status != "无伤") add(item.status)
                }.joinToString(" · ")
                if (subInfo.isNotEmpty()) {
                    Text(
                        text = subInfo,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            if (isSelectionMode) {
                SelectionIndicator(
                    isSelected = isSelected,
                    modifier = Modifier
                )
            }
        }
    }
}

/**
 * 选中状态指示器（圆形勾选标记）
 */
@Composable
private fun SelectionIndicator(
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(6.dp)
            .size(24.dp)
            .clip(CircleShape)
            .background(
                if (isSelected) MaterialTheme.colorScheme.primary
                else Color.White.copy(alpha = 0.8f)
            ),
        contentAlignment = Alignment.Center
    ) {
        if (isSelected) {
            Icon(
                Icons.Default.Check,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

/**
 * 物品缩略图 - 支持单图与四宫格
 */
@Composable
fun ItemThumbnail(
    imagePaths: List<String>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val displayPaths = imagePaths.take(4)

    Box(
        modifier = modifier.clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
    ) {
        when {
            displayPaths.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Image,
                        contentDescription = null,
                        modifier = Modifier.size(40.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
                    )
                }
            }

            displayPaths.size == 1 -> {
                AsyncImage(
                    model = File(context.filesDir, displayPaths[0]),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            else -> {
                Column(modifier = Modifier.fillMaxSize()) {
                    val rows = displayPaths.chunked(2)
                    rows.forEach { rowPaths ->
                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                        ) {
                            rowPaths.forEach { path ->
                                AsyncImage(
                                    model = File(context.filesDir, path),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                            if (rowPaths.size < 2) {
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight()
                                        .background(MaterialTheme.colorScheme.surfaceVariant)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
