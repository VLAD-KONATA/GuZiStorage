package com.guzi.storage.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.material.icons.filled.FolderZip
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.guzi.storage.ui.ViewMode
import com.guzi.storage.ui.components.verticalFastScroller
import com.guzi.storage.ui.group.ItemThumbnail

/**
 * 主页 - 分组列表，支持列表/收藏夹两种视图切换
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onGroupClick: (List<Pair<String, String>>) -> Unit = {},
    onAddClick: () -> Unit = {},
    onImportClick: () -> Unit = {},
    onStandaloneImportClick: (String) -> Unit = {},
    onSettingsClick: () -> Unit = {},
    viewModel: HomeViewModel = hiltViewModel()
) {
    val ipGroups by viewModel.ipGroups.collectAsState()
    val groupingConfig by viewModel.groupingConfig.collectAsState()
    val sortOption by viewModel.sortOption.collectAsState()
    var showAddMenu by remember { mutableStateOf(false) }
    var showSortMenu by remember { mutableStateOf(false) }
    var viewMode by rememberSaveable { mutableStateOf(ViewMode.GRID) }
    val gridState = rememberLazyGridState()
    val listState = rememberLazyListState()

    val firstAttr = groupingConfig.firstOrNull() ?: "ip_name"

    // 添加方式选择对话框
    if (showAddMenu) {
        AlertDialog(
            onDismissRequest = { showAddMenu = false },
            title = { Text("添加方式") },
            text = {
                Column {
                    ListItem(
                        headlineContent = { Text("手动添加") },
                        supportingContent = { Text("逐项填写单条谷子信息") },
                        leadingContent = {
                            Icon(Icons.Default.PostAdd, contentDescription = null)
                        },
                        modifier = Modifier.clickable {
                            showAddMenu = false
                            onAddClick()
                        }
                    )
                    HorizontalDivider()
                    ListItem(
                        headlineContent = { Text("ZIP 批量导入") },
                        supportingContent = { Text("同时导入物品、交易记录和图片") },
                        leadingContent = {
                            Icon(Icons.Default.FolderZip, contentDescription = null)
                        },
                        modifier = Modifier.clickable {
                            showAddMenu = false
                            onImportClick()
                        }
                    )
                    ListItem(
                        headlineContent = { Text("导入物品 CSV") },
                        supportingContent = { Text("单独导入 items.csv，自动去重合并") },
                        leadingContent = {
                            Icon(Icons.Default.Description, contentDescription = null)
                        },
                        modifier = Modifier.clickable {
                            showAddMenu = false
                            onStandaloneImportClick("items_csv")
                        }
                    )
                    ListItem(
                        headlineContent = { Text("导入交易记录 CSV") },
                        supportingContent = { Text("单独导入 transactions.csv，id 须对应已有谷子") },
                        leadingContent = {
                            Icon(Icons.Default.Description, contentDescription = null)
                        },
                        modifier = Modifier.clickable {
                            showAddMenu = false
                            onStandaloneImportClick("transactions_csv")
                        }
                    )
                    ListItem(
                        headlineContent = { Text("导入图片 ZIP") },
                        supportingContent = { Text("单独导入图片，子目录名须对应已有谷子 id") },
                        leadingContent = {
                            Icon(Icons.Default.Image, contentDescription = null)
                        },
                        modifier = Modifier.clickable {
                            showAddMenu = false
                            onStandaloneImportClick("image_zip")
                        }
                    )
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { showAddMenu = false }) {
                    Text("取消")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (firstAttr == "ip_name") "谷子收纳"
                        else "按${viewModel.getFirstLevelLabel()}分组"
                    )
                },
                actions = {
                    // 排序按钮 + 下拉菜单
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
                                        viewModel.setSortOption(option)
                                        showSortMenu = false
                                    },
                                    trailingIcon = {
                                        if (option == sortOption) {
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
                    IconButton(onClick = onSettingsClick) {
                        Icon(Icons.Default.Settings, contentDescription = "设置")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddMenu = true }) {
                Icon(Icons.Default.Add, contentDescription = "添加谷子")
            }
        }
    ) { paddingValues ->
        if (ipGroups.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "还没有任何谷子记录",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "点击右下角 + 开始添加",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            when (viewMode) {
                ViewMode.GRID -> {
                    // 收藏夹风格：两列网格卡片
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        state = gridState,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .verticalFastScroller(gridState),
                        contentPadding = PaddingValues(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(ipGroups) { group ->
                            IpGroupGridCard(
                                groupName = group.attrValue,
                                count = group.count,
                                thumbnails = group.thumbnails,
                                onClick = { onGroupClick(listOf(firstAttr to group.attrValue)) }
                            )
                        }
                    }
                }
                ViewMode.LIST -> {
                    // 列表风格：单列行卡片
                    LazyColumn(
                        state = listState,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .verticalFastScroller(listState),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(ipGroups) { group ->
                            IpGroupListCard(
                                groupName = group.attrValue,
                                count = group.count,
                                onClick = { onGroupClick(listOf(firstAttr to group.attrValue)) }
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * 分组卡片 - 收藏夹风格（网格视图）
 * 上方为缩略图四宫格，下方为分组名 + 件数
 */
@Composable
private fun IpGroupGridCard(
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

/**
 * 分组卡片 - 列表风格
 * 左侧 分组名 + 件数，右侧箭头
 */
@Composable
private fun IpGroupListCard(
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
