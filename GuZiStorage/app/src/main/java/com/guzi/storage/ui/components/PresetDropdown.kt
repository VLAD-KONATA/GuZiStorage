package com.guzi.storage.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

/**
 * 预设选项下拉组件
 *
 * 支持从预设选项列表选择，也支持手动输入新值
 * 新增的值会通过 onNewOption 回调通知，由外部决定是否加入预设库
 *
 * @param label 字段标签（如 "IP名"、"出品商"）
 * @param value 当前选中值
 * @param options 预设选项列表
 * @param onValueChange 值变化回调
 * @param required 是否必填
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PresetDropdown(
    label: String,
    value: String,
    options: List<String>,
    onValueChange: (String) -> Unit,
    onNewOption: ((String) -> Unit)? = null,
    required: Boolean = false,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorMessage: String? = null
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth()) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = { newValue ->
                    onValueChange(newValue)
                    // 检测是否为新输入的值
                    if (newValue.isNotBlank() && newValue !in options) {
                        onNewOption?.invoke(newValue)
                    }
                },
                label = {
                    Text(if (required) "$label *" else label)
                },
                trailingIcon = {
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = null
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                singleLine = true,
                isError = isError,
                supportingText = if (isError && errorMessage != null) {
                    { Text(errorMessage) }
                } else null
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            onValueChange(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}
