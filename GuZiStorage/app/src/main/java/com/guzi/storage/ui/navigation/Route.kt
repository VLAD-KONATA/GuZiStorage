package com.guzi.storage.ui.navigation

import android.net.Uri

/**
 * 路由定义 - 所有页面的导航路径
 */
sealed class Route(val path: String) {
    // 主页（IP 分组列表）
    data object Home : Route("home")

    // 分组子页面（支持多级分组路径）
    // path 格式：attr1=val1;attr2=val2（URL编码）
    data object Group : Route("group?path={path}") {
        fun createRoute(path: List<Pair<String, String>>): String {
            if (path.isEmpty()) return "group"
            val pathStr = path.joinToString(";") { "${it.first}=${Uri.encode(it.second)}" }
            return "group?path=${Uri.encode(pathStr)}"
        }

        fun parsePath(pathStr: String?): List<Pair<String, String>> {
            if (pathStr.isNullOrBlank()) return emptyList()
            return pathStr.split(";").mapNotNull { segment ->
                val parts = segment.split("=", limit = 2)
                if (parts.size == 2) parts[0] to Uri.decode(parts[1]) else null
            }
        }
    }

    // 添加/编辑物品
    data object AddItem : Route("add_item?itemId={itemId}&defaultIpName={defaultIpName}") {
        fun createRoute(itemId: String? = null, defaultIpName: String? = null): String {
            val params = mutableListOf<String>()
            if (itemId != null) params.add("itemId=${Uri.encode(itemId)}")
            if (defaultIpName != null) params.add("defaultIpName=${Uri.encode(defaultIpName)}")
            return if (params.isEmpty()) "add_item" else "add_item?${params.joinToString("&")}"
        }
    }

    // 物品详情页
    data object Detail : Route("detail/{itemId}") {
        fun createRoute(itemId: String) = "detail/${Uri.encode(itemId)}"
    }

    // 交易记录列表页
    data object Transaction : Route("transaction/{itemId}") {
        fun createRoute(itemId: String) = "transaction/${Uri.encode(itemId)}"
    }

    // 添加/编辑交易记录
    // itemId 为必传路径参数，itemNameCn/transactionId 为可选查询参数
    data object AddTransaction : Route("add_transaction/{itemId}?itemNameCn={itemNameCn}&transactionId={transactionId}") {
        fun createRoute(itemId: String, itemNameCn: String = "", transactionId: String? = null): String {
            var route = "add_transaction/${Uri.encode(itemId)}?itemNameCn=${Uri.encode(itemNameCn)}"
            if (transactionId != null) route += "&transactionId=${Uri.encode(transactionId)}"
            return route
        }
    }

    // ZIP 批量导入页
    data object ZipImport : Route("zip_import")

    // 单独文件导入页（items.csv / transactions.csv / image.zip）
    data object StandaloneImport : Route("standalone_import?type={type}") {
        fun createRoute(type: String) = "standalone_import?type=$type"
    }

    // 设置页
    data object Settings : Route("settings")

    // 分组设置页
    data object GroupingSettings : Route("grouping_settings")

    // 预设选项管理页（attributeName + attributeLabel 为查询参数）
    data object PresetManage : Route("preset_manage/{attributeName}?attributeLabel={attributeLabel}") {
        fun createRoute(attributeName: String, attributeLabel: String) =
            "preset_manage/${Uri.encode(attributeName)}?attributeLabel=${Uri.encode(attributeLabel)}"
    }

    // 大图预览（itemId 必传，startIndex 可选）
    data object ImageViewer : Route("image_viewer/{itemId}?startIndex={startIndex}") {
        fun createRoute(itemId: String, startIndex: Int = 0) =
            "image_viewer/${Uri.encode(itemId)}?startIndex=$startIndex"
    }
}
