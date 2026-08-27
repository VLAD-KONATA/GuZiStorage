package com.guzi.storage.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.input.pointer.changedToUp
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

/**
 * 右侧快速滚动条（FastScroller）
 *
 * 在可滚动列表右侧绘制一个可拖拽的滑块，方便在内容较多时快速定位。
 * 滑块位置基于可见 item 的 index 近似计算，适用于等高或近等高的列表项。
 * 仅在列表滚动或拖拽滑块时显示，停止后延迟淡出隐藏；触摸区域为右侧
 * [touchWidth] 宽度，在该区域内按下即跳转到对应位置并跟随手指拖拽，
 * 区域外不干扰列表正常滚动。
 *
 * 用法：
 * ```
 * val state = rememberLazyListState()
 * LazyColumn(
 *     state = state,
 *     modifier = Modifier.fillMaxSize().verticalFastScroller(state)
 * ) { ... }
 * ```
 */
fun Modifier.verticalFastScroller(
    state: LazyListState,
    width: Dp = 30.dp,
    touchWidth: Dp = 36.dp,
    minThumbHeight: Dp = 48.dp,
    padding: Dp = 4.dp
): Modifier = composed {
    val li = state.layoutInfo
    fastScrollerImpl(
        totalItems = li.totalItemsCount,
        firstVisibleIndex = li.visibleItemsInfo.firstOrNull()?.index ?: 0,
        lastVisibleIndex = li.visibleItemsInfo.lastOrNull()?.index ?: 0,
        viewportWidth = li.viewportSize.width,
        viewportHeight = li.viewportSize.height.toFloat(),
        isScrollInProgress = state.isScrollInProgress,
        scrollToIndex = { index -> state.scrollToItem(index) },
        width = width,
        touchWidth = touchWidth,
        minThumbHeight = minThumbHeight,
        padding = padding
    )
}

/** [LazyVerticalGrid] 专用的快速滚动条重载 */
fun Modifier.verticalFastScroller(
    state: LazyGridState,
    width: Dp = 30.dp,
    touchWidth: Dp = 36.dp,
    minThumbHeight: Dp = 48.dp,
    padding: Dp = 4.dp
): Modifier = composed {
    val li = state.layoutInfo
    fastScrollerImpl(
        totalItems = li.totalItemsCount,
        firstVisibleIndex = li.visibleItemsInfo.firstOrNull()?.index ?: 0,
        lastVisibleIndex = li.visibleItemsInfo.lastOrNull()?.index ?: 0,
        viewportWidth = li.viewportSize.width,
        viewportHeight = li.viewportSize.height.toFloat(),
        isScrollInProgress = state.isScrollInProgress,
        scrollToIndex = { index -> state.scrollToItem(index) },
        width = width,
        touchWidth = touchWidth,
        minThumbHeight = minThumbHeight,
        padding = padding
    )
}

@Composable
private fun Modifier.fastScrollerImpl(
    totalItems: Int,
    firstVisibleIndex: Int,
    lastVisibleIndex: Int,
    viewportWidth: Int,
    viewportHeight: Float,
    isScrollInProgress: Boolean,
    scrollToIndex: suspend (Int) -> Unit,
    width: Dp,
    touchWidth: Dp,
    minThumbHeight: Dp,
    padding: Dp
): Modifier {
    val density = LocalDensity.current
    val scope = rememberCoroutineScope()
    var isDragging by remember { mutableStateOf(false) }
    var visible by remember { mutableStateOf(false) }
    val scrollJobRef = remember { object { var job: Job? = null } }
    val thumbColor = MaterialTheme.colorScheme.onSurfaceVariant

    // 滚动或拖拽时显示滑块，停止后延迟 1 秒淡出隐藏
    LaunchedEffect(isScrollInProgress, isDragging) {
        if (isScrollInProgress || isDragging) {
            visible = true
        } else {
            delay(1000)
            visible = false
        }
    }

    val thumbAlpha by animateFloatAsState(
        targetValue = if (visible) 0.6f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "thumbAlpha"
    )

    val widthPx = with(density) { width.toPx() }
    val touchWidthPx = with(density) { touchWidth.toPx() }
    val minThumbHeightPx = with(density) { minThumbHeight.toPx() }
    val paddingPx = with(density) { padding.toPx() }

    val visibleCount = (lastVisibleIndex - firstVisibleIndex + 1).coerceAtLeast(0)
    val scrollRange = (totalItems - visibleCount).coerceAtLeast(0)
    val canScroll = scrollRange > 0 && viewportHeight > 0f && totalItems > 0

    val thumbHeight = if (canScroll) {
        (viewportHeight * visibleCount.toFloat() / totalItems).coerceAtLeast(minThumbHeightPx)
    } else 0f
    val trackHeight = (viewportHeight - thumbHeight).coerceAtLeast(0f)
    val scrollProgress = if (canScroll) firstVisibleIndex.toFloat() / scrollRange else 0f
    val thumbY = scrollProgress * trackHeight
    val thumbX = viewportWidth - paddingPx - widthPx
    val touchStart = viewportWidth - touchWidthPx - paddingPx

    fun requestScroll(y: Float) {
        if (trackHeight <= 0f || scrollRange <= 0) return
        val progress = (y / trackHeight).coerceIn(0f, 1f)
        val target = (progress * scrollRange).roundToInt()
        scrollJobRef.job?.cancel()
        scrollJobRef.job = scope.launch { scrollToIndex(target) }
    }

    return this
        .pointerInput(totalItems, viewportWidth, viewportHeight) {
            if (!canScroll) return@pointerInput
            awaitEachGesture {
                val down = awaitFirstDown(requireUnconsumed = false)
                if (down.position.x < touchStart) return@awaitEachGesture
                isDragging = true
                down.consume()
                requestScroll(down.position.y)
                while (true) {
                    val event = awaitPointerEvent()
                    val change = event.changes.firstOrNull() ?: break
                    if (change.changedToUp()) break
                    change.consume()
                    requestScroll(change.position.y)
                }
                isDragging = false
            }
        }
        .drawWithContent {
            drawContent()
            if (canScroll && thumbAlpha > 0f) {
                drawRoundRect(
                    color = thumbColor.copy(alpha = thumbAlpha),
                    topLeft = Offset(thumbX, thumbY),
                    size = Size(widthPx, thumbHeight),
                    cornerRadius = CornerRadius(widthPx / 2f, widthPx / 2f)
                )
            }
        }
}
