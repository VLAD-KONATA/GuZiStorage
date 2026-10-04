package com.guzi.storage.ui.add

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.guzi.storage.ui.components.AdaptiveImage
import com.guzi.storage.ui.components.MultiSelectField
import com.guzi.storage.util.CurrencyUtils
import com.guzi.storage.ui.components.PresetDropdown
import kotlinx.coroutines.launch

/**
 * 添加/编辑物品页面
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemScreen(
    itemId: String? = null,
    defaultIpName: String? = null,
    copyFromId: String? = null,
    onNavigateBack: () -> Unit = {},
    viewModel: AddItemViewModel = hiltViewModel()
) {
    val formState by viewModel.formState.collectAsState()
    val saveResult by viewModel.saveResult.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    // 预设选项
    val ipOptions by viewModel.ipOptions.collectAsState()
    val producerOptions by viewModel.producerOptions.collectAsState()
    val originPlaceOptions by viewModel.originPlaceOptions.collectAsState()
    val saleFormatOptions by viewModel.saleFormatOptions.collectAsState()
    val sellMethodOptions by viewModel.sellMethodOptions.collectAsState()
    val categoryOptions by viewModel.categoryOptions.collectAsState()
    val sizeOptions by viewModel.sizeOptions.collectAsState()
    val materialOptions by viewModel.materialOptions.collectAsState()
    val characterOptions by viewModel.characterOptions.collectAsState()
    val artTypeOptions by viewModel.artTypeOptions.collectAsState()

    // 图片压缩选项
    var compressImages by rememberSaveable { mutableStateOf(true) }

    // 图片选择器
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri> ->
        if (uris.isNotEmpty()) {
            viewModel.addImages(uris, compressImages)
        }
    }

    // 编辑模式加载数据
    LaunchedEffect(itemId, copyFromId) {
        when {
            itemId != null -> viewModel.loadItem(itemId)
            copyFromId != null -> viewModel.loadItemAsCopy(copyFromId)
            defaultIpName != null -> viewModel.setDefaultIpName(defaultIpName)
        }
    }

    // 保存结果处理
    LaunchedEffect(saveResult) {
        when (saveResult) {
            is SaveResult.Success -> {
                snackbarHostState.showSnackbar("保存成功")
                viewModel.clearSaveResult()
                onNavigateBack()
            }
            is SaveResult.Error -> {
                snackbarHostState.showSnackbar((saveResult as SaveResult.Error).message)
                viewModel.clearSaveResult()
            }
            null -> {}
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(if (formState.isEditMode) "编辑谷子" else "添加谷子")
                },
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
            // ===== 基本信息 =====
            SectionTitle("基本信息")

            OutlinedTextField(
                value = formState.nameCn,
                onValueChange = viewModel::updateNameCn,
                label = { Text("中文名称 *") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = "nameCn" in formState.validationErrors,
                supportingText = formState.validationErrors["nameCn"]?.let { { Text(it) } }
            )

            MultiSelectField(
                label = "IP名",
                selectedValues = formState.ipNames,
                options = ipOptions,
                onValuesChange = viewModel::updateIpNames,
                required = true,
                isError = "ipName" in formState.validationErrors,
                errorMessage = formState.validationErrors["ipName"]
            )

            MultiSelectField(
                label = "相关角色",
                selectedValues = formState.characters,
                options = characterOptions,
                onValuesChange = viewModel::updateCharacters,
                required = true,
                isError = "characters" in formState.validationErrors,
                errorMessage = formState.validationErrors["characters"]
            )

            MultiSelectField(
                label = "柄图种类",
                selectedValues = formState.artTypes,
                options = artTypeOptions,
                onValuesChange = viewModel::updateArtTypes,
                required = true,
                isError = "artTypes" in formState.validationErrors,
                errorMessage = formState.validationErrors["artTypes"]
            )

            MultiSelectField(
                label = "种类",
                selectedValues = formState.categories,
                options = categoryOptions,
                onValuesChange = viewModel::updateCategories
            )

            MultiSelectField(
                label = "尺寸",
                selectedValues = formState.sizes,
                options = sizeOptions,
                onValuesChange = viewModel::updateSizes
            )

            // ===== 详细信息 =====
            SectionTitle("详细信息")

            OutlinedTextField(
                value = formState.nameEn,
                onValueChange = viewModel::updateNameEn,
                label = { Text("日文/英文名称") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            MultiSelectField(
                label = "出品商",
                selectedValues = formState.producers,
                options = producerOptions,
                onValuesChange = viewModel::updateProducers
            )

            MultiSelectField(
                label = "出品地",
                selectedValues = formState.originPlaces,
                options = originPlaceOptions,
                onValuesChange = viewModel::updateOriginPlaces
            )

            MultiSelectField(
                label = "发售形式",
                selectedValues = formState.saleFormat,
                options = saleFormatOptions,
                onValuesChange = viewModel::updateSaleFormat,
                isError = "saleFormat" in formState.validationErrors,
                errorMessage = formState.validationErrors["saleFormat"]
            )

            PresetDropdown(
                label = "售卖方式",
                value = formState.sellMethod,
                options = sellMethodOptions,
                onValueChange = viewModel::updateSellMethod,
                isError = "sellMethod" in formState.validationErrors,
                errorMessage = formState.validationErrors["sellMethod"]
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = formState.isNoPrice,
                    onCheckedChange = viewModel::updateIsNoPrice
                )
                Text("该商品无价格")
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.Top,
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = formState.price,
                    onValueChange = viewModel::updatePrice,
                    label = { Text("价格") },
                    enabled = !formState.isNoPrice,
                    modifier = Modifier.width(120.dp),
                    singleLine = true,
                    isError = "price" in formState.validationErrors,
                    supportingText = formState.validationErrors["price"]?.let { { Text(it) } }
                )

                var currencyExpanded by remember { mutableStateOf(false) }
                val currencyLabel = CurrencyUtils.dropdownOptions
                    .find { it.first == formState.currency }?.second ?: ""
                ExposedDropdownMenuBox(
                    expanded = currencyExpanded,
                    onExpandedChange = { if (!formState.isNoPrice) currencyExpanded = it }
                ) {
                    OutlinedTextField(
                        value = currencyLabel,
                        onValueChange = {},
                        readOnly = true,
                        enabled = !formState.isNoPrice,
                        label = { Text("货币") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = currencyExpanded)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = currencyExpanded,
                        onDismissRequest = { currencyExpanded = false }
                    ) {
                        CurrencyUtils.dropdownOptions.forEach { (code, label) ->
                            DropdownMenuItem(
                                text = { Text(label) },
                                onClick = {
                                    viewModel.updateCurrency(code)
                                    currencyExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            OutlinedTextField(
                value = formState.releaseDate,
                onValueChange = viewModel::updateReleaseDate,
                label = { Text("发售日期") },
                placeholder = { Text("YYYY-MM（如 2025-07）") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            MultiSelectField(
                label = "材质",
                selectedValues = formState.materials,
                options = materialOptions,
                onValuesChange = viewModel::updateMaterials
            )

            StatusDropdown(
                value = formState.status,
                onValueChange = viewModel::updateStatus
            )

            if (formState.status != "无伤") {
                MultiSelectField(
                    label = "状态详情",
                    selectedValues = formState.damageDetails,
                    options = listOf("底锈", "内锈", "划痕", "胶痕", "折痕", "剪坏"),
                    onValuesChange = viewModel::updateDamageDetails
                )
            }

            // 版本（强制下拉二选一：初贩/再贩）
            VersionDropdown(
                value = formState.version,
                onValueChange = viewModel::updateVersion
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = formState.isOriginalBagUnknown,
                    onCheckedChange = viewModel::updateIsOriginalBagUnknown
                )
                Text("该商品无明确原袋")
            }

            OutlinedTextField(
                value = formState.originalBagQuantity,
                onValueChange = viewModel::updateOriginalBagQuantity,
                label = { Text("原袋") },
                enabled = !formState.isOriginalBagUnknown,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = formState.note,
                onValueChange = viewModel::updateNote,
                label = { Text("备注") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 5
            )

            MultiSelectField(
                label = "参考链接",
                selectedValues = formState.referenceLinks,
                options = emptyList(),
                onValuesChange = viewModel::updateReferenceLinks,
                isError = "referenceLinks" in formState.validationErrors,
                errorMessage = formState.validationErrors["referenceLinks"]
            )

            // ===== 图片 =====
            SectionTitle("图片")

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "压缩存储（节省空间）",
                    style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
                )
                Switch(
                    checked = compressImages,
                    onCheckedChange = { compressImages = it }
                )
            }

            OutlinedButton(
                onClick = { imagePickerLauncher.launch("image/*") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Text("  批量添加图片")
            }

            if (formState.imagePaths.isNotEmpty()) {
                Text(
                    text = "已添加 ${formState.imagePaths.size} 张图片（长按图片可拖动调整顺序）",
                    style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
                    color = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant
                )
                ImageThumbnailRow(
                    imagePaths = formState.imagePaths,
                    onRemove = viewModel::removeImage,
                    onMove = viewModel::moveImage
                )
            }

            // 底部留白
            androidx.compose.foundation.layout.Spacer(modifier = Modifier.padding(bottom = 80.dp))
        }
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
        color = androidx.compose.material3.MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(top = 8.dp)
    )
}

/**
 * 版本强制下拉菜单（初贩/再贩二选一，不可编辑）
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun VersionDropdown(
    value: String,
    onValueChange: (String) -> Unit
) {
    val options = listOf("初贩", "再贩")
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        OutlinedTextField(
            value = value.ifBlank { "初贩" },
            onValueChange = {},
            readOnly = true,
            label = { Text("版本") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
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

/**
 * 已上传图片缩略图列表，每张可点 × 删除，长按可拖拽调整顺序
 */
@Composable
private fun ImageThumbnailRow(
    imagePaths: List<String>,
    onRemove: (String) -> Unit,
    onMove: (Int, Int) -> Unit
) {
    val listState = rememberLazyListState()
    val hapticFeedback = LocalHapticFeedback.current
    val scope = rememberCoroutineScope()
    // 单个缩略图占位宽度（88dp 图 + 8dp 间距）
    val itemStepPx = with(LocalDensity.current) { 96.dp.toPx() }

    // 拖拽状态：正在拖拽的图片下标与累计偏移
    var draggingIndex by remember { mutableStateOf(-1) }
    var dragOffsetX by remember { mutableFloatStateOf(0f) }
    var dragOffsetY by remember { mutableFloatStateOf(0f) }
    var lastAutoScrollTime by remember { mutableStateOf(0L) }

    LazyRow(
        state = listState,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        itemsIndexed(imagePaths, key = { _, path -> path }) { index, path ->
            val currentIndex by rememberUpdatedState(index)
            val isDragging = index == draggingIndex

            Box(
                modifier = Modifier
                    .size(88.dp)
                    .zIndex(if (isDragging) 1f else 0f)
                    .graphicsLayer {
                        if (isDragging) {
                            translationX = dragOffsetX
                            translationY = dragOffsetY
                            scaleX = 1.05f
                            scaleY = 1.05f
                            shadowElevation = 8f
                        }
                    }
                    .pointerInput(path) {
                        detectDragGesturesAfterLongPress(
                            onDragStart = {
                                hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                                draggingIndex = currentIndex
                                dragOffsetX = 0f
                                dragOffsetY = 0f
                            },
                            onDrag = { change, dragAmount ->
                                change.consume()
                                dragOffsetX += dragAmount.x
                                dragOffsetY += dragAmount.y

                                val current = draggingIndex
                                if (current >= 0) {
                                    // 累计位移超过一格时与相邻项交换
                                    when {
                                        dragOffsetX >= itemStepPx && current < imagePaths.lastIndex -> {
                                            onMove(current, current + 1)
                                            draggingIndex = current + 1
                                            dragOffsetX -= itemStepPx
                                        }
                                        dragOffsetX <= -itemStepPx && current > 0 -> {
                                            onMove(current, current - 1)
                                            draggingIndex = current - 1
                                            dragOffsetX += itemStepPx
                                        }
                                    }
                                    // 拖至列表边缘时自动滚动
                                    val now = System.currentTimeMillis()
                                    if (now - lastAutoScrollTime > 250) {
                                        val layoutInfo = listState.layoutInfo
                                        val itemInfo = layoutInfo.visibleItemsInfo.firstOrNull { it.index == current }
                                        if (itemInfo != null) {
                                            when {
                                                itemInfo.offset + itemInfo.size > layoutInfo.viewportEndOffset - 48
                                                        && current < imagePaths.lastIndex -> {
                                                    lastAutoScrollTime = now
                                                    scope.launch { listState.animateScrollToItem(current + 1) }
                                                }
                                                itemInfo.offset < layoutInfo.viewportStartOffset + 48
                                                        && current > 0 -> {
                                                    lastAutoScrollTime = now
                                                    scope.launch { listState.animateScrollToItem(current - 1) }
                                                }
                                            }
                                        }
                                    }
                                }
                            },
                            onDragEnd = {
                                draggingIndex = -1
                                dragOffsetX = 0f
                                dragOffsetY = 0f
                            },
                            onDragCancel = {
                                draggingIndex = -1
                                dragOffsetX = 0f
                                dragOffsetY = 0f
                            }
                        )
                    }
            ) {
                AdaptiveImage(
                    imagePath = path,
                    modifier = Modifier
                        .size(80.dp)
                        .align(Alignment.BottomStart)
                        .clip(RoundedCornerShape(8.dp))
                )
                IconButton(
                    onClick = { onRemove(path) },
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.TopEnd)
                        .clip(CircleShape)
                        .background(androidx.compose.material3.MaterialTheme.colorScheme.error)
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "删除图片",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

/**
 * 状态强制下拉菜单（无伤/有伤二选一，不可编辑）
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StatusDropdown(
    value: String,
    onValueChange: (String) -> Unit
) {
    val options = listOf("无伤", "有伤")
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        OutlinedTextField(
            value = value.ifBlank { "无伤" },
            onValueChange = {},
            readOnly = true,
            label = { Text("状态") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
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
