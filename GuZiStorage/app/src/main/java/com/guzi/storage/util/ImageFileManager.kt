package com.guzi.storage.util

import android.content.Context
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 图片文件管理工具
 *
 * 负责图片文件的复制、存储和删除
 * 所有图片存储在应用私有目录 images/{itemId}/ 下
 */
@Singleton
class ImageFileManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val imagesDir: File
        get() = File(context.filesDir, "images").also { it.mkdirs() }

    /**
     * 获取指定物品的图片目录
     */
    fun getItemImageDir(itemId: String): File {
        return File(imagesDir, itemId).also { it.mkdirs() }
    }

    /**
     * 从 URI 复制图片到物品目录
     *
     * @param itemId 物品 ID
     * @param uri 图片来源 URI（从相册选择）
     * @return 保存后的相对路径（相对于 filesDir），如 "images/xxx/abc123.jpg"
     */
    fun copyImageToItemDir(itemId: String, uri: Uri): String? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null
            val itemDir = getItemImageDir(itemId)
            val fileName = "${UUID.randomUUID()}.jpg"
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
