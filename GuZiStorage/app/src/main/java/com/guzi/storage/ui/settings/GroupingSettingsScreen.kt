package com.guzi.storage.ui.settings

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
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.guzi.storage.ui.getAttributeLabel

/**
 * 分组设置页 - 配置多级分组层级
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupingSettingsScreen(
    onNavigateBack: () -> Unit = {},
    viewModel: GroupingSettingsViewModel = hiltViewModel()
) {
    val groupingConfig by viewModel.groupingConfig.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }

    if (showAddDialog) {
        val available = viewModel.getAvailableAttributes()
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("添加分组层级") },
            text = {
                if (available.isEmpty()) {
                    Text("所有属性已使用，或已达到最大层级数（${GroupingSettingsViewModel.MAX_LEVELS}）")
                } else {
                    Column {
                        available.forEach { (attrName, label) ->
                            TextButton(
                                onClick = {
                                    viewModel.addLevel(attrName)
                                    showAddDialog = false
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = label,
                                    modifier = Modifier.fillMaxWidth(),
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text("取消")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("分组设置") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.resetToDefault() }) {
                        Icon(Icons.Default.RestartAlt, contentDescription = "恢复默认")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text(
                text = "设置浏览谷子时的分组层级，将按此顺序逐级展示",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            groupingConfig.forEachIndexed { index, attrName ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "第 ${index + 1} 级",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = getAttributeLabel(attrName),
                                style = MaterialTheme.typography.titleMedium
                            )
                        }

                        Row {
                            IconButton(
                                onClick = { viewModel.moveUp(index) },
                                enabled = index > 0
                            ) {
                                Icon(Icons.Default.ArrowUpward, contentDescription = "上移")
                            }
                            IconButton(
                                onClick = { viewModel.moveDown(index) },
                                enabled = index < groupingConfig.size - 1
                            ) {
                                Icon(Icons.Default.ArrowDownward, contentDescription = "下移")
                            }
                            IconButton(onClick = { viewModel.removeLevel(index) }) {
                                Icon(
                                    Icons.Default.Close,
                                    contentDescription = "删除",
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

            OutlinedButton(
                onClick = { showAddDialog = true },
                modifier = Modifier.fillMaxWidth(),
                enabled = groupingConfig.size < GroupingSettingsViewModel.MAX_LEVELS
            ) {
                Text("添加分组层级")
            }

            Text(
                text = "当前共 ${groupingConfig.size} 级，最大 ${GroupingSettingsViewModel.MAX_LEVELS} 级",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
