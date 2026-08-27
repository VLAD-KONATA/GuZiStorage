package com.guzi.storage.ui.components

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.concurrent.ConcurrentHashMap
import android.graphics.Color as AndroidColor

/**
 * 自适应图片展示组件
 *
 * 图片以「适应（Fit）」方式完整展示，不裁剪长边；
 * 留白背景色根据图片外围像素的平均亮度自动选择白色或黑色，使边缘衔接自然。
 *
 * @param imagePath 相对于 filesDir 的图片路径，如 "images/{itemId}/xxx.jpg"
 */
@Composable
fun AdaptiveImage(
    imagePath: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    placeholderBackground: Color = MaterialTheme.colorScheme.surfaceVariant
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var edgeIsLight by remember(imagePath) {
        mutableStateOf(EdgeBackgroundResolver.cached(imagePath))
    }

    val request = remember(imagePath) {
        ImageRequest.Builder(context)
            .data(File(context.filesDir, imagePath))
            // 关闭硬件位图，否则无法读取像素做边缘取色
            .allowHardware(false)
            .build()
    }

    val background = when (edgeIsLight) {
        true -> Color.White
        false -> Color.Black
        null -> placeholderBackground
    }

    Box(
        modifier = modifier.background(background),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = request,
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit,
            onSuccess = { state ->
                if (edgeIsLight == null) {
                    val drawable = state.result.drawable
                    scope.launch {
                        edgeIsLight = EdgeBackgroundResolver.resolve(imagePath, drawable)
                    }
                }
            }
        )
    }
}

/**
 * 图片边缘亮度解析器（带内存缓存，避免列表滚动时反复计算）
 */
private object EdgeBackgroundResolver {

    private val cache = ConcurrentHashMap<String, Boolean>()

    fun cached(imagePath: String): Boolean? = cache[imagePath]

    suspend fun resolve(imagePath: String, drawable: Drawable): Boolean {
        cache[imagePath]?.let { return it }
        val isLight = withContext(Dispatchers.Default) { computeEdgeIsLight(drawable) }
        cache[imagePath] = isLight
        return isLight
    }

    /**
     * 采样图片四条边缘的像素，计算平均感知亮度
     *
     * @return true=边缘偏亮（用白底），false=边缘偏暗（用黑底）
     */
    private fun computeEdgeIsLight(drawable: Drawable): Boolean {
        val bitmap = (drawable as? BitmapDrawable)?.bitmap ?: return true
        val width = bitmap.width
        val height = bitmap.height
        if (width < 3 || height < 3) return true
        if (bitmap.config == Bitmap.Config.HARDWARE) return true

        val inset = 1
        val steps = 16
        var luminanceSum = 0.0
        var sampleCount = 0

        fun sample(x: Int, y: Int) {
            val pixel = bitmap.getPixel(
                x.coerceIn(0, width - 1),
                y.coerceIn(0, height - 1)
            )
            // 忽略近乎透明的像素（PNG 透明边框）
            if (AndroidColor.alpha(pixel) < 32) return
            luminanceSum += (0.299 * AndroidColor.red(pixel) +
                    0.587 * AndroidColor.green(pixel) +
                    0.114 * AndroidColor.blue(pixel)) / 255.0
            sampleCount++
        }

        for (i in 0..steps) {
            val x = (width - 1) * i / steps
            val y = (height - 1) * i / steps
            sample(x, inset)
            sample(x, height - 1 - inset)
            sample(inset, y)
            sample(width - 1 - inset, y)
        }

        // 边缘全透明时默认白底
        if (sampleCount == 0) return true
        return luminanceSum / sampleCount >= 0.5
    }
}
