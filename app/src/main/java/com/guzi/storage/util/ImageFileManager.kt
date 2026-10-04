package com.guzi.storage.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 图片文件管理工具
 *
 * 负责图片文件的复制、压缩、存储和删除
 * 所有图片存储在应用私有目录 images/{itemId}/ 下
 */
@Singleton
class ImageFileManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private const val COMPRESSED_MAX_DIMENSION = 1024
        private const val COMPRESSED_QUALITY = 85
        private const val SMART_COMPRESS_THRESHOLD_BYTES = 1024 * 1024
        private const val SMART_COMPRESS_TARGET_BYTES = 800 * 1024
        private const val SMART_COMPRESS_INITIAL_DIMENSION = 1600
        private const val SMART_COMPRESS_MIN_DIMENSION = 320
        private const val SMART_COMPRESS_MIN_QUALITY = 40
    }

    private val imagesDir: File
        get() = File(context.filesDir, "images").also { it.mkdirs() }

    /**
     * 获取指定物品的图片目录
     */
    fun getItemImageDir(itemId: String): File {
        return File(imagesDir, itemId).also { it.mkdirs() }
    }

    /**
     * 从 URI 复制图片到物品目录（原图）
     *
     * @param itemId 物品 ID
     * @param uri 图片来源 URI（从相册选择）
     * @return 保存后的相对路径（相对于 filesDir），如 "images/xxx/abc123.jpg"
     */
    fun copyImageToItemDir(itemId: String, uri: Uri): String? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null
            val itemDir = getItemImageDir(itemId)
            val fileName = "${IdGenerator.generate()}.jpg"
            val outputFile = File(itemDir, fileName)

            FileOutputStream(outputFile).use { output ->
                inputStream.copyTo(output)
            }
            inputStream.close()

            // 返回相对路径
            "images/$itemId/$fileName"
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 从 URI 复制图片到物品目录，可选择是否压缩
     *
     * @param compress true=压缩存储（最大1024px，JPEG质量85），false=存储原图
     */
    fun copyImageCompressed(itemId: String, uri: Uri, compress: Boolean): String? {
        if (!compress) return copyImageToItemDir(itemId, uri)

        return try {
            // 第一遍：获取图片尺寸（inJustDecodeBounds 模式下 decodeStream 返回 null 是正常的，不应用其返回值判断成败）
            val boundsOpts = BitmapFactory.Options().apply { inJustDecodeBounds = true }
            context.contentResolver.openInputStream(uri)?.use { input ->
                BitmapFactory.decodeStream(input, null, boundsOpts)
            } ?: return copyImageToItemDir(itemId, uri)
            if (boundsOpts.outWidth <= 0 || boundsOpts.outHeight <= 0) {
                return copyImageToItemDir(itemId, uri)
            }

            // 计算采样率
            var sampleSize = 1
            while (boundsOpts.outWidth / sampleSize > COMPRESSED_MAX_DIMENSION ||
                   boundsOpts.outHeight / sampleSize > COMPRESSED_MAX_DIMENSION) {
                sampleSize *= 2
            }

            // 第二遍：按采样率解码（强制 ARGB_8888，避免硬件位图导致 compress 失败）
            val decodeOpts = BitmapFactory.Options().apply {
                inSampleSize = sampleSize
                inPreferredConfig = Bitmap.Config.ARGB_8888
            }
            val bitmap = context.contentResolver.openInputStream(uri)?.use {
                BitmapFactory.decodeStream(it, null, decodeOpts)
            }
            if (bitmap == null) {
                // 解码失败（如不支持的格式），回退到原图存储
                return copyImageToItemDir(itemId, uri)
            }

            // 保存为 JPEG
            val itemDir = getItemImageDir(itemId)
            val fileName = "${IdGenerator.generate()}.jpg"
            val outputFile = File(itemDir, fileName)

            FileOutputStream(outputFile).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, COMPRESSED_QUALITY, out)
            }
            bitmap.recycle()

            "images/$itemId/$fileName"
        } catch (e: Exception) {
            e.printStackTrace()
            // 压缩过程中出现异常，回退到原图存储，保证上传成功
            copyImageToItemDir(itemId, uri)
        }
    }

    /**
     * 从 File 复制图片到物品目录（用于 ZIP 导入，保留原始扩展名）
     */
    fun copyImageFile(itemId: String, sourceFile: File, smartCompress: Boolean = false): String? {
        if (smartCompress && sourceFile.length() > SMART_COMPRESS_THRESHOLD_BYTES) {
            compressLargeImageFile(itemId, sourceFile)?.let { return it }
        }

        return try {
            val itemDir = getItemImageDir(itemId)
            val ext = sourceFile.extension.ifBlank { "jpg" }
            val fileName = "${IdGenerator.generate()}.$ext"
            val outputFile = File(itemDir, fileName)
            sourceFile.copyTo(outputFile, overwrite = true)
            "images/$itemId/$fileName"
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun compressLargeImageFile(itemId: String, sourceFile: File): String? {
        return try {
            if (sourceFile.extension.equals("gif", ignoreCase = true)) return null

            val original = BitmapFactory.decodeFile(sourceFile.absolutePath) ?: return null
            if (original.hasAlpha()) {
                original.recycle()
                return null
            }

            var maxDimension = SMART_COMPRESS_INITIAL_DIMENSION
            var bestBytes: ByteArray? = null
            while (maxDimension >= SMART_COMPRESS_MIN_DIMENSION) {
                val scaled = scaleBitmapToMaxDimension(original, maxDimension)
                var quality = 90
                while (quality >= SMART_COMPRESS_MIN_QUALITY) {
                    val bytes = ByteArrayOutputStream().use { out ->
                        scaled.compress(Bitmap.CompressFormat.JPEG, quality, out)
                        out.toByteArray()
                    }
                    if (bestBytes == null || bytes.size < bestBytes!!.size) {
                        bestBytes = bytes
                    }
                    if (bytes.size <= SMART_COMPRESS_TARGET_BYTES) {
                        val relativePath = writeCompressedBytes(itemId, bytes)
                        if (scaled !== original) scaled.recycle()
                        original.recycle()
                        return relativePath
                    }
                    quality -= 5
                }
                if (scaled !== original) scaled.recycle()
                maxDimension = (maxDimension * 0.85f).toInt()
            }

            original.recycle()
            bestBytes?.let { writeCompressedBytes(itemId, it) }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun scaleBitmapToMaxDimension(bitmap: Bitmap, maxDimension: Int): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val longest = maxOf(width, height)
        if (longest <= maxDimension) return bitmap

        val scale = maxDimension.toFloat() / longest
        val targetWidth = (width * scale).toInt().coerceAtLeast(1)
        val targetHeight = (height * scale).toInt().coerceAtLeast(1)
        return Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, true)
    }

    private fun writeCompressedBytes(itemId: String, bytes: ByteArray): String {
        val itemDir = getItemImageDir(itemId)
        val fileName = "${IdGenerator.generate()}.jpg"
        val outputFile = File(itemDir, fileName)
        outputFile.writeBytes(bytes)
        return "images/$itemId/$fileName"
    }

    /**
     * 批量从 URI 添加图片
     *
     * @param compress true=压缩存储，false=存储原图
     * @return 成功保存的相对路径列表
     */
    fun copyImages(itemId: String, uris: List<Uri>, compress: Boolean): List<String> {
        return uris.mapNotNull { uri -> copyImageCompressed(itemId, uri, compress) }
    }

    /**
     * 获取图片的绝对文件路径
     */
    fun getAbsolutePath(relativePath: String): String {
        return File(context.filesDir, relativePath).absolutePath
    }

    /**
     * 获取图片 File 对象
     */
    fun getFile(relativePath: String): File {
        return File(context.filesDir, relativePath)
    }

    /**
     * 删除物品的所有图片
     */
    fun deleteItemImages(itemId: String) {
        val itemDir = File(imagesDir, itemId)
        if (itemDir.exists()) {
            itemDir.deleteRecursively()
        }
    }

    /**
     * 删除指定图片
     */
    fun deleteImage(relativePath: String) {
        val file = File(context.filesDir, relativePath)
        if (file.exists()) {
            file.delete()
        }
    }

    /**
     * 获取物品所有图片的相对路径列表
     */
    fun getItemImagePaths(itemId: String): List<String> {
        val itemDir = File(imagesDir, itemId)
        if (!itemDir.exists()) return emptyList()
        return itemDir.listFiles()?.map { "images/$itemId/${it.name}" } ?: emptyList()
    }
}
