package com.guzi.storage.ui.group

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
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
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.guzi.storage.domain.model.Item
import com.guzi.storage.ui.SEARCH_FILTER_ATTRIBUTES
import com.guzi.storage.ui.ViewMode
import com.guzi.storage.ui.components.AdaptiveImage
import com.guzi.storage.ui.components.ConfirmDialog
import com.guzi.storage.ui.components.FilterDialog
import com.guzi.storage.ui.components.SortDialog
import com.guzi.storage.ui.components.verticalFastScroller
import com.guzi.storage.ui.getSearchFieldLabel
import com.guzi.storage.ui.home.HomeSortOption

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
    onSettingsClick: () -> Unit = {},
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
            onSettingsClick = onSettingsClick,
            viewModel = viewModel
        )
    } else {
        SubGroupContent(
            breadcrumb = breadcrumb,
            path = path,
            onItemClick = onItemClick,
            onSubGroupClick = onSubGroupClick,
            onAddClick = onAddClick,
            onNavigateBack = onNavigateBack,
            onSettingsClick = onSettingsClick,
            viewModel = viewModel
        )
    }
}

// ===== 子分组模式（中间层级） =====

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun SubGroupContent(
    breadcrumb: String,
    path: List<Pair<String, String>>,
    onItemClick: (String) -> Unit,
    onSubGroupClick: (List<Pair<String, String>>) -> Unit,
    onAddClick: () -> Unit,
    onNavigateBack: () -> Unit,
    onSettingsClick: () -> Unit,
    viewModel: GroupViewModel
) {
    val subGroups by viewModel.subGroups.collectAsState()
    val currentLevelAttr by viewModel.currentLevelAttr.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val searchField by viewModel.searchField.collectAsState()
    val subGroupSortOption by viewModel.subGroupSortOption.collectAsState()
    val selectedGroups by viewModel.selectedGroups.collectAsState()
    val isGroupSelectionMode by viewModel.isGroupSelectionMode.collectAsState()
    val showGroupBatchDeleteDialog by viewModel.showGroupBatchDeleteDialog.collectAsState()
    // 物品视图相关状态
    val isShowingItems by viewModel.isShowingItems.collectAsState()
    val items by viewModel.items.collectAsState()
    val filterConfig by viewModel.filterConfig.collectAsState()
    val sortConfig by viewModel.sortConfig.collectAsState()
    var showSearch by remember { mutableStateOf(false) }
    var showSortMenu by remember { mutableStateOf(false) }
    var showFieldMenu by remember { mutableStateOf(false) }
    var showFilterDialog by remember { mutableStateOf(false) }
    var showSortDialog by remember { mutableStateOf(false) }
    var viewMode by rememberSaveable { mutableStateOf(ViewMode.GRID) }
    val itemGridState = rememberLazyGridState()
    val itemListState = rememberLazyListState()
    val groupGridState = rememberLazyGridState()
    val groupListState = rememberLazyListState()

    val activeFilterCount = filterConfig.activeFilterCount()
    val activeSortCount = sortConfig.activeSortCount()

    // 分组级批量删除确认弹窗
    if (showGroupBatchDeleteDialog) {
        ConfirmDialog(
            title = "批量删除分组",
            message = "确定要删除选中的 ${selectedGroups.size} 个分组下的所有谷子吗？\n这些分组下的所有物品及其交易记录都将被删除。",
            confirmText = "确认删除",
            onConfirm = viewModel::confirmGroupBatchDelete,
            onDismiss = viewModel::dismissGroupBatchDelete
        )
    }

    // 物品视图筛选弹窗
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

    // 物品视图排序弹窗
    if (showSortDialog) {
        Dialog(
            onDismissRequest = { showSortDialog = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            SortDialog(
                currentConfig = sortConfig,
                onConfirm = { config ->
                    viewModel.setSortConfig(config)
                    showSortDialog = false
                },
                onDismiss = { showSortDialog = false }
            )
        }
    }

    Scaffold(
        topBar = {
            if (isGroupSelectionMode) {
                // 分组选择模式 TopAppBar
                TopAppBar(
                    title = { Text("已选 ${selectedGroups.size} 组") },
                    navigationIcon = {
                        IconButton(onClick = viewModel::clearGroupSelection) {
                            Icon(Icons.Default.Clear, contentDescription = "取消选择")
                        }
                    },
                    actions = {
                        IconButton(onClick = viewModel::selectAllGroups) {
                            Icon(Icons.Default.SelectAll, contentDescription = "全选")
                        }
                        IconButton(onClick = viewModel::requestGroupBatchDelete) {
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
                TopAppBar(
                    title = { Text(breadcrumb, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
                        }
                    },
                    actions = {
                        // 筛选按钮（始终显示，分组视图和物品视图均可筛选）
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
                        if (isShowingItems) {
                            // 物品视图：排序弹窗
                            IconButton(onClick = { showSortDialog = true }) {
                                BadgedBox(
                                    badge = {
                                        if (activeSortCount > 0) {
                                            Badge { Text(activeSortCount.toString()) }
                                        }
                                    }
                                ) {
                                    Icon(
                                        Icons.Default.Sort,
                                        contentDescription = "排序",
                                        tint = if (activeSortCount > 0) MaterialTheme.colorScheme.primary
                                               else MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        } else {
                            // 分组视图：排序下拉菜单（与 HomeScreen 一致）
                            Box {
                                IconButton(onClick = { showSortMenu = true }) {
                                    Icon(Icons.Default.Sort, contentDescription = "排序")
                                }
                                DropdownMenu(
                                    expanded = showSortMenu,
                                    onDismissRequest = { showSortMenu = false }
                                ) {
                                    HomeSortOption.entries.forEach { option ->
                                        DropdownMenuItem(
                                            text = { Text(option.displayLabel) },
                                            onClick = {
                                                viewModel.setSubGroupSortOption(option)
                                                showSortMenu = false
                                            },
                                            trailingIcon = {
                                                if (option == subGroupSortOption) {
                                                    Icon(
                                                        Icons.Default.Check,
                                                        contentDescription = "已选择",
                                                        tint = MaterialTheme.colorScheme.primary
                                                    )
                                                }
                                            }
                                        )
                                    }
                                }
                            }
                        }
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
                        IconButton(onClick = onSettingsClick) {
                            Icon(Icons.Default.Settings, contentDescription = "设置")
                        }
                    }
                )
            }
        },
        floatingActionButton = {
            if (!isGroupSelectionMode) {
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
            if (showSearch && !isGroupSelectionMode) {
                // 搜索栏：字段选择下拉 + 文本输入
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // 字段选择下拉
                    Box {
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .padding(horizontal = 10.dp, vertical = 8.dp)
                                .combinedClickable(onClick = { showFieldMenu = true }),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = getSearchFieldLabel(searchField),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Icon(
                                Icons.Default.ArrowDropDown,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        DropdownMenu(
                            expanded = showFieldMenu,
                            onDismissRequest = { showFieldMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("全部") },
                                onClick = {
                                    viewModel.updateSearchField("all")
                                    showFieldMenu = false
                                }
                            )
                            // 物品视图下不显示“分组名”选项
                            if (!isShowingItems) {
                                DropdownMenuItem(
                                    text = { Text("分组名") },
                                    onClick = {
                                        viewModel.updateSearchField("分组名")
                                        showFieldMenu = false
                                    }
                                )
                            }
                            SEARCH_FILTER_ATTRIBUTES.forEach { (attr, label) ->
                                DropdownMenuItem(
                                    text = { Text(label) },
                                    onClick = {
                                        viewModel.updateSearchField(attr)
                                        showFieldMenu = false
                                    }
                                )
                            }
                        }
                    }
                    // 搜索文本输入框
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = viewModel::updateSearchQuery,
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("输入搜索内容...") },
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
            }

            if (isShowingItems) {
                // 物品视图（搜索非分组名字段时）
                if (items.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = when {
                                activeFilterCount > 0 -> "当前条件下暂无谷子"
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
                                state = itemGridState,
                                contentPadding = PaddingValues(12.dp),
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier.fillMaxSize().verticalFastScroller(itemGridState)
                            ) {
                                items(items, key = { it.id }) { item ->
                                    ItemGridCard(
                                        item = item,
                                        isSelected = false,
                                        isSelectionMode = false,
                                        onClick = { onItemClick(item.id) },
                                        onLongClick = { }
                                    )
                                }
                            }
                        }
                        ViewMode.LIST -> {
                            LazyColumn(
                                state = itemListState,
                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.fillMaxSize().verticalFastScroller(itemListState)
                            ) {
                                items(items, key = { it.id }) { item ->
                                    ItemListCard(
                                        item = item,
                                        isSelected = false,
                                        isSelectionMode = false,
                                        onClick = { onItemClick(item.id) },
                                        onLongClick = { }
                                    )
                                }
                            }
                        }
                    }
                }
            } else {
                // 分组视图（默认或分组名搜索时）
                if (subGroups.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = when {
                                activeFilterCount > 0 -> "当前筛选条件下暂无子分组"
                                searchQuery.isNotEmpty() -> "无搜索结果"
                                else -> "该分组暂无子分组"
                            },
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
                                state = groupGridState,
                                contentPadding = PaddingValues(12.dp),
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier.fillMaxSize().verticalFastScroller(groupGridState)
                            ) {
                                items(subGroups, key = { "${it.attrName}:${it.attrValue}" }) { group ->
                                    SubGroupGridCard(
                                        groupName = group.attrValue,
                                        count = group.count,
                                        thumbnails = group.thumbnails,
                                        isSelected = group.attrValue in selectedGroups,
                                        isSelectionMode = isGroupSelectionMode,
                                        onClick = {
                                            if (isGroupSelectionMode) {
                                                viewModel.toggleGroupSelection(group.attrValue)
                                            } else {
                                                onSubGroupClick(path + (attr to group.attrValue))
                                            }
                                        },
                                        onLongClick = {
                                            viewModel.toggleGroupSelection(group.attrValue)
                                        }
                                    )
                                }
                            }
                        }
                        ViewMode.LIST -> {
                            LazyColumn(
                                state = groupListState,
                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.fillMaxSize().verticalFastScroller(groupListState)
                            ) {
                                items(subGroups, key = { "${it.attrName}:${it.attrValue}" }) { group ->
                                    SubGroupListCard(
                                        groupName = group.attrValue,
                                        count = group.count,
                                        isSelected = group.attrValue in selectedGroups,
                                        isSelectionMode = isGroupSelectionMode,
                                        onClick = {
                                            if (isGroupSelectionMode) {
                                                viewModel.toggleGroupSelection(group.attrValue)
                                            } else {
                                                onSubGroupClick(path + (attr to group.attrValue))
                                            }
                                        },
                                        onLongClick = {
                                            viewModel.toggleGroupSelection(group.attrValue)
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
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SubGroupGridCard(
    groupName: String,
    count: Int,
    thumbnails: List<String>,
    isSelected: Boolean,
    isSelectionMode: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(onClick = onClick, onLongClick = onLongClick),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 4.dp else 2.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.secondaryContainer
                             else MaterialTheme.colorScheme.surface
        )
    ) {
        Box {
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
            if (isSelectionMode) {
                SelectionIndicator(
                    isSelected = isSelected,
                    modifier = Modifier
                        .padding(6.dp)
                        .align(Alignment.TopEnd)
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SubGroupListCard(
    groupName: String,
    count: Int,
    isSelected: Boolean,
    isSelectionMode: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(onClick = onClick, onLongClick = onLongClick),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 4.dp else 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.secondaryContainer
                             else MaterialTheme.colorScheme.surface
        )
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
            if (isSelectionMode) {
                SelectionIndicator(isSelected = isSelected)
            } else {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

// ===== 最终级模式（物品列表） =====

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun FinalLevelContent(
    breadcrumb: String,
    onItemClick: (String) -> Unit,
    onAddClick: () -> Unit,
    onNavigateBack: () -> Unit,
    onSettingsClick: () -> Unit,
    viewModel: GroupViewModel
) {
    val items by viewModel.items.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val searchField by viewModel.searchField.collectAsState()
    val selectedIds by viewModel.selectedIds.collectAsState()
    val isSelectionMode by viewModel.isSelectionMode.collectAsState()
    val showBatchDeleteDialog by viewModel.showBatchDeleteDialog.collectAsState()
    val filterConfig by viewModel.filterConfig.collectAsState()
    val sortConfig by viewModel.sortConfig.collectAsState()

    var showSearch by remember { mutableStateOf(false) }
    var showFilterDialog by remember { mutableStateOf(false) }
    var showSortDialog by remember { mutableStateOf(false) }
    var showFieldMenu by remember { mutableStateOf(false) }
    var viewMode by rememberSaveable { mutableStateOf(ViewMode.GRID) }
    val itemGridState = rememberLazyGridState()
    val itemListState = rememberLazyListState()

    val activeFilterCount = filterConfig.activeFilterCount()
    val activeSortCount = sortConfig.activeSortCount()

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

    // 排序弹窗
    if (showSortDialog) {
        Dialog(
            onDismissRequest = { showSortDialog = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            SortDialog(
                currentConfig = sortConfig,
                onConfirm = { config ->
                    viewModel.setSortConfig(config)
                    showSortDialog = false
                },
                onDismiss = { showSortDialog = false }
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
                        // 排序按钮（带 Badge）
                        IconButton(onClick = { showSortDialog = true }) {
                            BadgedBox(
                                badge = {
                                    if (activeSortCount > 0) {
                                        Badge { Text(activeSortCount.toString()) }
                                    }
                                }
                            ) {
                                Icon(
                                    Icons.Default.Sort,
                                    contentDescription = "排序",
                                    tint = if (activeSortCount > 0) MaterialTheme.colorScheme.primary
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
                        IconButton(onClick = onSettingsClick) {
                            Icon(Icons.Default.Settings, contentDescription = "设置")
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
            // 搜索栏：字段选择下拉 + 文本输入
            if (showSearch && !isSelectionMode) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // 字段选择下拉
                    Box {
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .padding(horizontal = 10.dp, vertical = 8.dp)
                                .combinedClickable(onClick = { showFieldMenu = true }),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = getSearchFieldLabel(searchField),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Icon(
                                Icons.Default.ArrowDropDown,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        DropdownMenu(
                            expanded = showFieldMenu,
                            onDismissRequest = { showFieldMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("全部") },
                                onClick = {
                                    viewModel.updateSearchField("all")
                                    showFieldMenu = false
                                }
                            )
                            SEARCH_FILTER_ATTRIBUTES.forEach { (attr, label) ->
                                DropdownMenuItem(
                                    text = { Text(label) },
                                    onClick = {
                                        viewModel.updateSearchField(attr)
                                        showFieldMenu = false
                                    }
                                )
                            }
                        }
                    }
                    // 搜索文本输入框
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = viewModel::updateSearchQuery,
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("输入搜索内容...") },
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
                            state = itemGridState,
                            contentPadding = PaddingValues(12.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.fillMaxSize().verticalFastScroller(itemGridState)
                        ) {
                            items(items, key = { it.id }) { item ->
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
                            state = itemListState,
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxSize().verticalFastScroller(itemListState)
                        ) {
                            items(items, key = { it.id }) { item ->
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
                Box {
                    ItemThumbnail(
                        imagePaths = item.imagePaths,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f),
                        firstOnly = true,
                        quantity = item.quantity
                    )
                    if (item.quantity == 0) {
                        NotOwnedOverlay(modifier = Modifier.matchParentSize())
                    }
                }

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
                    if (item.categories.isNotEmpty()) {
                        Text(
                            text = item.categories.joinToString("、"),
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
                    AdaptiveImage(
                        imagePath = firstImage,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Image,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
                    )
                }
                if (item.quantity == 0) {
                    NotOwnedOverlay(modifier = Modifier.matchParentSize())
                }
                QuantityBadge(
                    quantity = item.quantity,
                    modifier = Modifier.align(Alignment.BottomEnd)
                )
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
                    if (item.categories.isNotEmpty()) add(item.categories.joinToString("、"))
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

@Composable
private fun NotOwnedOverlay(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.background(Color.Black.copy(alpha = 0.45f)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "暂未拥有",
            style = MaterialTheme.typography.labelMedium,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
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
 * 物品缩略图 - 支持单图与四宫格（图片以适应方式展示，背景按边缘像素自动配色）
 *
 * @param firstOnly true=仅展示首图，多图时右上角显示 "+m" 角标（m=未展示图片数）
 */
@Composable
fun ItemThumbnail(
    imagePaths: List<String>,
    modifier: Modifier = Modifier,
    firstOnly: Boolean = false,
    quantity: Int? = null
) {
    if (firstOnly) {
        Box(
            modifier = modifier.clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
        ) {
            val firstImage = imagePaths.firstOrNull()
            if (firstImage != null) {
                AdaptiveImage(
                    imagePath = firstImage,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
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
            if (imagePaths.size > 1) {
                Text(
                    text = "+${imagePaths.size - 1}",
                    color = Color.White,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(6.dp)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.55f))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                )
            }
            QuantityBadge(
                quantity = quantity,
                modifier = Modifier.align(Alignment.BottomEnd)
            )
        }
        return
    }

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
                AdaptiveImage(
                    imagePath = displayPaths[0],
                    modifier = Modifier.fillMaxSize()
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
                                AdaptiveImage(
                                    imagePath = path,
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight()
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

@Composable
private fun QuantityBadge(
    quantity: Int?,
    modifier: Modifier = Modifier
) {
    if (quantity == null || quantity < 1) return

    Text(
        text = "×$quantity",
        color = Color.White,
        style = MaterialTheme.typography.labelMedium,
        fontWeight = FontWeight.Bold,
        modifier = modifier
            .padding(6.dp)
            .clip(CircleShape)
            .background(Color.Black.copy(alpha = 0.55f))
            .padding(horizontal = 8.dp, vertical = 2.dp)
    )
}
