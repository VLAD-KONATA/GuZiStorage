package com.guzi.storage.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.guzi.storage.util.ItemValueParser

/**
 * 多选字段组件
 *
 * 展示已选标签 + 输入框，支持从预设选项选择或手动输入
 * 已选项以 Chip 形式展示，可点击 × 移除
 *
 * @param label 字段标签
 * @param selectedValues 已选值列表
 * @param options 预设选项列表
 * @param onValuesChange 值变化回调
 * @param required 是否必填
 */
@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MultiSelectField(
    label: String,
    selectedValues: List<String>,
    options: List<String>,
    onValuesChange: (List<String>) -> Unit,
    onNewOption: ((String) -> Unit)? = null,
    required: Boolean = false,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorMessage: String? = null
) {
    var inputText by remember { mutableStateOf("") }
    // 可选项列表折叠状态：选项超过阈值时默认折叠，选中后恢复折叠
    var listExpanded by remember { mutableStateOf(false) }

    // 过滤掉已选的选项
    val availableOptions = options.filter { it !in selectedValues }

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = if (required) "$label *" else label,
            style = androidx.compose.material3.MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        // 已选项 Chip 展示
        if (selectedValues.isNotEmpty()) {
            FlowRow(
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                selectedValues.forEach { value ->
                    AssistChip(
                        onClick = { onValuesChange(selectedValues - value) },
                        label = { Text(value) },
                        trailingIcon = {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "移除",
                                modifier = Modifier.size(16.dp)
                            )
                        },
                        modifier = Modifier.padding(end = 4.dp, bottom = 4.dp)
                    )
                }
            }
        }

        // 输入框 + 添加按钮
        OutlinedTextField(
            value = inputText,
            onValueChange = { inputText = it },
            placeholder = { Text("输入或从下方选择") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = {
                    val value = inputText.trim()
                    if (value.isNotBlank() && !ItemValueParser.hasPipe(value) && value !in selectedValues) {
                        onValuesChange(selectedValues + value)
                        // 注：不在这里调用 onNewOption，新选项由 ViewModel 保存成功后统一处理
                        inputText = ""
                    }
                }) {
                    Icon(Icons.Default.Add, contentDescription = "添加")
                }
            },
            isError = isError,
            supportingText = if (isError && errorMessage != null) {
                { Text(errorMessage) }
            } else null
        )

        // 可选项列表（超过阈值时折叠，可展开/收起）
        if (availableOptions.isNotEmpty()) {
            val collapseThreshold = 12
            val shouldCollapse = availableOptions.size > collapseThreshold
            val displayedOptions =
                if (shouldCollapse && !listExpanded) availableOptions.take(collapseThreshold)
                else availableOptions

            FlowRow(
                modifier = Modifier.padding(top = 8.dp)
            ) {
                displayedOptions.forEach { option ->
                    AssistChip(
                        onClick = {
                            onValuesChange(selectedValues + option)
                            listExpanded = false
                        },
                        label = { Text(option) },
                        modifier = Modifier.padding(end = 4.dp, bottom = 4.dp)
                    )
                }
                if (shouldCollapse) {
                    AssistChip(
                        onClick = { listExpanded = !listExpanded },
                        label = {
                            Text(
                                if (listExpanded) "收起"
                                else "展开更多（共 ${availableOptions.size} 项）"
                            )
                        },
                        leadingIcon = {
                            Icon(
                                if (listExpanded) Icons.Default.ExpandLess
                                else Icons.Default.ExpandMore,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                        },
                        modifier = Modifier.padding(end = 4.dp, bottom = 4.dp)
                    )
                }
            }
        }
    }
}
