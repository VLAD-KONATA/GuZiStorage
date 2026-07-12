package com.guzi.storage.ui.add

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.guzi.storage.ui.components.MultiSelectField
import com.guzi.storage.ui.components.PresetDropdown

/**
 * 添加/编辑物品页面
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemScreen(
    itemId: String? = null,
    defaultIpName: String? = null,
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
    val statusOptions by viewModel.statusOptions.collectAsState()
    val versionOptions by viewModel.versionOptions.collectAsState()

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
    LaunchedEffect(itemId) {
        if (itemId != null) {
            viewModel.loadItem(itemId)
        } else if (defaultIpName != null) {
            viewModel.setDefaultIpName(defaultIpName)
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

            OutlinedTextField(
                value = formState.nameEn,
                onValueChange = viewModel::updateNameEn,
                label = { Text("日文/英文名称") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            PresetDropdown(
                label = "IP名",
                value = formState.ipName,
                options = ipOptions,
                onValueChange = viewModel::updateIpName,
                required = true,
                isError = "ipName" in formState.validationErrors,
                errorMessage = formState.validationErrors["ipName"]
            )

            PresetDropdown(
                label = "出品商",
                value = formState.producer,
                options = producerOptions,
                onValueChange = viewModel::updateProducer
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
                required = true,
                isError = "saleFormat" in formState.validationErrors,
                errorMessage = formState.validationErrors["saleFormat"]
            )

            PresetDropdown(
                label = "售卖方式",
                value = formState.sellMethod,
                options = sellMethodOptions,
                onValueChange = viewModel::updateSellMethod,
                required = true,
                isError = "sellMethod" in formState.validationErrors,
                errorMessage = formState.validationErrors["sellMethod"]
            )

            // ===== 详细信息 =====
            SectionTitle("详细信息")

            OutlinedTextField(
                value = formState.price,
                onValueChange = viewModel::updatePrice,
                label = { Text("价格") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = "price" in formState.validationErrors,
                supportingText = formState.validationErrors["price"]?.let { { Text(it) } }
            )

            PresetDropdown(
                label = "种类",
                value = formState.category,
                options = categoryOptions,
                onValueChange = viewModel::updateCategory
            )

            MultiSelectField(
                label = "尺寸",
                selectedValues = formState.sizes,
                options = sizeOptions,
                onValuesChange = viewModel::updateSizes
            )

            MultiSelectField(
                label = "材质",
                selectedValues = formState.materials,
                options = materialOptions,
                onValuesChange = viewModel::updateMaterials
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

            // ===== 状态与数量 =====
            SectionTitle("状态与数量")

            PresetDropdown(
                label = "状态",
                value = formState.status,
                options = statusOptions,
                onValueChange = viewModel::updateStatus
            )

            if (formState.status != "无伤") {
                MultiSelectField(
                    label = "伤情详情",
                    selectedValues = formState.damageDetails,
                    options = listOf("底锈", "内锈", "划痕", "胶痕", "折痕", "剪坏"),
                    onValuesChange = viewModel::updateDamageDetails
                )
            }

            PresetDropdown(
                label = "版本",
                value = formState.version,
                options = versionOptions,
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
                    text = "已添加 ${formState.imagePaths.size} 张图片",
                    style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
                    color = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // ===== 备注 =====
            SectionTitle("备注")

            OutlinedTextField(
                value = formState.note,
                onValueChange = viewModel::updateNote,
                label = { Text("备注") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 5
            )

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
