package com.guzi.storage.ui.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
 */
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { showControls = !showControls }
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
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(6.dp)
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
    }
}
