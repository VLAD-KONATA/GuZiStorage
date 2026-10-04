package com.guzi.storage.util

import android.content.Context
import android.media.MediaScannerConnection
import com.guzi.storage.data.repository.UserPreferencesRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 将 App 私有目录中的原图复制到用户配置的外部目录。
 */
@Singleton
class ImageExportManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val imageFileManager: ImageFileManager,
    private val userPreferencesRepository: UserPreferencesRepository
) {
    fun saveImage(relativePath: String): Result<File> = runCatching {
        val source = imageFileManager.getFile(relativePath)
        require(source.exists()) { "原图文件不存在" }

        val targetDir = File(userPreferencesRepository.getImageSaveDirectory())
        if (!targetDir.exists()) {
            require(targetDir.mkdirs()) { "无法创建保存目录" }
        }
        require(targetDir.isDirectory) { "保存路径不是文件夹" }

        val target = uniqueTargetFile(targetDir, source.name)
        source.copyTo(target, overwrite = false)
        MediaScannerConnection.scanFile(
            context,
            arrayOf(target.absolutePath),
            null,
            null
        )
        target
    }

    private fun uniqueTargetFile(targetDir: File, sourceName: String): File {
        val baseName = sourceName.substringBeforeLast('.', sourceName)
        val extension = sourceName.substringAfterLast('.', missingDelimiterValue = "")
        val direct = File(targetDir, sourceName)
        if (!direct.exists()) return direct

        val suffix = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val timestampName = if (extension.isBlank()) {
            "${baseName}_$suffix"
        } else {
            "${baseName}_$suffix.$extension"
        }
        val timestampFile = File(targetDir, timestampName)
        if (!timestampFile.exists()) return timestampFile

        var index = 2
        while (true) {
            val fileName = if (extension.isBlank()) {
                "${baseName}_${suffix}_$index"
            } else {
                "${baseName}_${suffix}_$index.$extension"
            }
            val candidate = File(targetDir, fileName)
            if (!candidate.exists()) return candidate
            index++
        }
    }
}
