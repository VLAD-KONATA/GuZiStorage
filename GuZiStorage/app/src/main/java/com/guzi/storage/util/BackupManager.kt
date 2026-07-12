package com.guzi.storage.util

import android.content.Context
import com.guzi.storage.data.repository.ItemRepository
import com.guzi.storage.data.repository.TransactionRepository
import com.guzi.storage.domain.model.Item
import com.guzi.storage.domain.model.Transaction
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 数据备份管理器 - 生成 ZIP 备份文件
 *
 * 备份内容：
 * - items.csv        谷子基本信息（id 为 app 内 UUID）
 * - transactions.csv 交易记录（id 列为外键，对应 items.csv 的 id）
 * - image/           图片文件夹，子目录名 = 物品 id
 *
 * 导出的 ZIP 可直接用于再次导入
 */
@Singleton
class BackupManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val itemRepository: ItemRepository,
    private val transactionRepository: TransactionRepository
) {

    /**
     * 生成 ZIP 备份文件，写入 app cache 目录，返回 File 引用
     */
    suspend fun createBackupFile(): File {
        val items = itemRepository.getAllItems().first()
        val transactions = transactionRepository.getAllTransactions().first()

        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val zipFile = File(context.cacheDir, "guzi_backup_$timestamp.zip")

        ZipOutputStream(FileOutputStream(zipFile)).use { zipOut ->
            // 1. items.csv（带 UTF-8 BOM，方便 Excel 正确识别编码）
            val itemsCsv = buildItemsCsv(items)
            zipOut.putNextEntry(ZipEntry("items.csv"))
            zipOut.write(BOM_UTF8)
            zipOut.write(itemsCsv.toByteArray(Charsets.UTF_8))
            zipOut.closeEntry()

            // 2. transactions.csv（有交易记录才导出）
            if (transactions.isNotEmpty()) {
                val txnsCsv = buildTransactionsCsv(transactions)
                zipOut.putNextEntry(ZipEntry("transactions.csv"))
                zipOut.write(BOM_UTF8)
                zipOut.write(txnsCsv.toByteArray(Charsets.UTF_8))
                zipOut.closeEntry()
            }

            // 3. image/ 文件夹（按物品 id 分子目录）
            for (item in items) {
                for (relativePath in item.imagePaths) {
                    val file = File(context.filesDir, relativePath)
                    if (file.exists()) {
                        // relativePath 格式: images/{itemId}/{filename}
                        // ZIP 内路径:   image/{itemId}/{filename}
                        val zipPath = relativePath.replaceFirst("images/", "image/")
                        zipOut.putNextEntry(ZipEntry(zipPath))
                        file.inputStream().use { it.copyTo(zipOut) }
                        zipOut.closeEntry()
                    }
                }
            }
        }

        return zipFile
    }

    // ===== CSV 构建 =====

    private fun buildItemsCsv(items: List<Item>): String {
        val sb = StringBuilder()
        sb.appendLine("id,中文名称,日文英文名称,IP名,出品商,出品地,发售形式,售卖方式,价格,种类,尺寸,材质,相关角色,柄图种类,状态,伤情详情,版本,数量,备注")

        for (item in items) {
            val cols = listOf(
                item.id,
                item.nameCn,
                item.nameEn ?: "",
                item.ipName,
                item.producer ?: "",
                item.originPlaces.joinToString("|"),
                item.saleFormat.joinToString("|"),
                item.sellMethod,
                item.price?.toString() ?: "",
                item.category ?: "",
                item.sizes.joinToString("|"),
                item.materials.joinToString("|"),
                item.characters.joinToString("|"),
                item.artTypes.joinToString("|"),
                item.status,
                item.damageDetails.joinToString("|"),
                item.version ?: "",
                item.quantity.toString(),
                item.note ?: ""
            )
            sb.appendLine(cols.joinToString(",") { escapeCsv(it) })
        }
        return sb.toString()
    }

    private fun buildTransactionsCsv(transactions: List<Transaction>): String {
        val sb = StringBuilder()
        sb.appendLine("id,类型,名称,价格,渠道,数量,日期,备注")

        for (t in transactions) {
            val cols = listOf(
                t.itemId,       // 外键，对应 items.csv 的 id
                t.type.value,   // 购入/卖出
                t.nameCn,
                t.price.toString(),
                t.channel,
                t.quantity.toString(),
                t.date ?: "",
                t.note ?: ""
            )
            sb.appendLine(cols.joinToString(",") { escapeCsv(it) })
        }
        return sb.toString()
    }

    companion object {
        /** UTF-8 BOM 字节头，写入 CSV 文件开头以便 Excel 正确识别编码 */
        private val BOM_UTF8 = byteArrayOf(0xEF.toByte(), 0xBB.toByte(), 0xBF.toByte())
    }

    /** CSV 转义：含逗号、引号、换行的值用双引号包裹，内部引号双写 */
    private fun escapeCsv(value: String): String {
        return if (value.contains(',') || value.contains('"') || value.contains('\n') || value.contains('\r')) {
            "\"${value.replace("\"", "\"\"")}\""
        } else {
            value
        }
    }
}
