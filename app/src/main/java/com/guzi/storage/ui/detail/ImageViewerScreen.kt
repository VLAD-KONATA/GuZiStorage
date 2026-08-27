package com.guzi.storage.ui.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import java.io.File

/**
 * 全屏大图预览页
 *
 * 点击屏幕切换控件栏显示/隐藏
 * 左右滑动翻看多张图片
 * 长按屏幕弹出"设置为首图"选项
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageViewerScreen(
    itemId: String,
    startIndex: Int = 0,
    onClose: () -> Unit = {},
    viewModel: DetailViewModel = hiltViewModel()
) {
    LaunchedEffect(itemId) {
        viewModel.setItemId(itemId)
    }

    val item by viewModel.item.collectAsState()
    val imagePaths = item?.imagePaths ?: emptyList()
    val context = LocalContext.current

    var showControls by remember { mutableStateOf(true) }

    // 长按弹出的"设置为首图"底部选项
    var showSetFirstImageSheet by remember { mutableStateOf(false) }
    // 设置首图成功后记录目标路径，待图片顺序刷新后滚动跟随
    var pendingScrollPath by remember { mutableStateOf<String?>(null) }

    if (imagePaths.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Text("无图片", color = Color.White)
        }
        return
    }

    val pagerState = rememberPagerState(
        initialPage = startIndex.coerceIn(0, imagePaths.lastIndex),
        pageCount = { imagePaths.size }
    )

    // 设置首图后图片顺序变化，滚动到该图片的新位置（成为第一张）
    LaunchedEffect(imagePaths) {
        pendingScrollPath?.let { path ->
            val newIndex = imagePaths.indexOf(path)
            if (newIndex >= 0 && newIndex != pagerState.currentPage) {
                pagerState.scrollToPage(newIndex)
            }
            pendingScrollPath = null
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .combinedClickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { showControls = !showControls },
                onLongClick = { showSetFirstImageSheet = true }
            )
    ) {
        // 图片翻页
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            AsyncImage(
                model = File(context.filesDir, imagePaths[page]),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        }

        // 顶部控件栏（关闭按钮 + 页码）
        AnimatedVisibility(
            visible = showControls,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 0.dp)
            ) {
                // 关闭按钮
                IconButton(
                    onClick = onClose,
                    modifier = Modifier
                        .padding(12.dp)
                        .align(Alignment.TopStart)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.4f))
                ) {
                    Icon(Icons.Default.Close, contentDescription = "关闭", tint = Color.White)
                }

                // 页码
                if (imagePaths.size > 1) {
                    Text(
                        text = "${pagerState.currentPage + 1} / ${imagePaths.size}",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 18.dp)
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = 0.4f))
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    )
                }

                // 底部页码点
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 32.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    repeat(imagePaths.size) { index ->
                        Box(
                            modifier = Modifier
                                .size(if (pagerState.currentPage == index) 10.dp else 6.dp)
                                .clip(CircleShape)
                                .background(
                                    if (pagerState.currentPage == index) Color.White
                                    else Color.White.copy(alpha = 0.5f)
                                )
                        )
                    }
                }
            }
        }

        // 长按弹出的底部选项：设置为首图
        AnimatedVisibility(
            visible = showSetFirstImageSheet,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { showSetFirstImageSheet = false },
                contentAlignment = Alignment.BottomCenter
            ) {
                val currentPath = imagePaths.getOrNull(pagerState.currentPage)
                val isAlreadyFirst = currentPath != null && imagePaths.firstOrNull() == currentPath
                Surface(
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                    color = Color(0xFF2C2C2E),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(vertical = 8.dp)) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null,
                                    enabled = !isAlreadyFirst
                                ) {
                                    val path = imagePaths.getOrNull(pagerState.currentPage)
                                    if (path != null) {
                                        viewModel.setFirstImage(path)
                                        pendingScrollPath = path
                                    }
                                    showSetFirstImageSheet = false
                                }
                                .padding(horizontal = 20.dp, vertical = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Star,
                                contentDescription = null,
                                tint = Color.White.copy(alpha = if (isAlreadyFirst) 0.4f else 1f),
                                modifier = Modifier.size(22.dp)
                            )
                            Text(
                                text = if (isAlreadyFirst) "当前已是首图" else "设置为首图",
                                color = Color.White.copy(alpha = if (isAlreadyFirst) 0.4f else 1f),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        HorizontalDivider(color = Color.White.copy(alpha = 0.1f))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) { showSetFirstImageSheet = false }
                                .padding(horizontal = 20.dp, vertical = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = null,
                                tint = Color.White.copy(alpha = 0.7f),
                                modifier = Modifier.size(22.dp)
                            )
                            Text(
                                text = "取消",
                                color = Color.White.copy(alpha = 0.7f),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        }
    }
}
