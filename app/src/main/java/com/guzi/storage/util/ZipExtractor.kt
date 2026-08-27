package com.guzi.storage.util

import java.io.File
import java.nio.charset.Charset
import java.util.zip.ZipInputStream

/**
 * ZIP 解压工具
 *
 * 自动处理文件名编码：通过读取 general purpose bit flag 的 bit 11
 * 判断 UTF-8 / GBK，兼容 Windows 压缩工具生成的中文文件名，避免 MALFORMED 错误。
 */
object ZipExtractor {

    /**
     * 将 ZIP 文件解压到目标目录（带路径穿越防护和编码回退）
     *
     * 注意：调用方需先将 ContentResolver 的 InputStream 复制为临时文件，
     * 因为编码检测和回退都需要读取文件。
     */
    fun unzipToDir(zipFile: File, destDir: File) {
        val charset = detectZipCharset(zipFile)
        try {
            unzipWithCharset(zipFile, destDir, charset)
        } catch (e: Exception) {
            // 检测的编码失败，尝试另一个
            val fallback = if (charset == Charsets.UTF_8) Charset.forName("GBK") else Charsets.UTF_8
            destDir.deleteRecursively()
            destDir.mkdirs()
            unzipWithCharset(zipFile, destDir, fallback)
        }
    }

    /**
     * 检测 ZIP 文件名编码：
     * - general purpose bit flag bit 11 = 1 → UTF-8（标准 zip 规范）
     * - bit 11 = 0 → GBK（Windows 压缩工具默认行为）
     */
    private fun detectZipCharset(zipFile: File): Charset {
        return try {
            zipFile.inputStream().use { stream ->
                val header = ByteArray(30)
                if (stream.read(header) < 30) return Charsets.UTF_8
                // 检查 PK 签名 (0x50 0x4B)
                if (header[0] != 0x50.toByte() || header[1] != 0x4B.toByte()) return Charsets.UTF_8
                val bitFlag = (header[6].toInt() and 0xFF) or ((header[7].toInt() and 0xFF) shl 8)
                if ((bitFlag and 0x0800) != 0) Charsets.UTF_8 else Charset.forName("GBK")
            }
        } catch (e: Exception) {
            Charsets.UTF_8
        }
    }

    private fun unzipWithCharset(zipFile: File, destDir: File, charset: Charset) {
        ZipInputStream(zipFile.inputStream(), charset).use { zipStream ->
            var entry = zipStream.nextEntry
            while (entry != null) {
                val outputFile = File(destDir, entry.name)
                if (!outputFile.canonicalPath.startsWith(destDir.canonicalPath)) {
                    zipStream.closeEntry()
                    entry = zipStream.nextEntry
                    continue
                }
                if (entry.isDirectory) {
                    outputFile.mkdirs()
                } else {
                    outputFile.parentFile?.mkdirs()
                    outputFile.outputStream().use { output -> zipStream.copyTo(output) }
                }
                zipStream.closeEntry()
                entry = zipStream.nextEntry
            }
        }
    }
}
