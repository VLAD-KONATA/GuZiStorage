package com.guzi.storage.ui.navigation

/**
 * 路由定义 - 所有页面的导航路径
 */
sealed class Route(val path: String) {
    // 主页（IP 分组列表）
    data object Home : Route("home")

    // 分组子页面（某 IP 下的网格列表）
    data object Group : Route("group/{ipName}") {
        fun createRoute(ipName: String) = "group/$ipName"
    }

    // 添加/编辑物品
    data object AddItem : Route("add_item?itemId={itemId}") {
        fun createRoute(itemId: String? = null): String {
            return if (itemId != null) "add_item?itemId=$itemId" else "add_item"
        }
    }

    // 物品详情页
    data object Detail : Route("detail/{itemId}") {
        fun createRoute(itemId: String) = "detail/$itemId"
    }

    // 交易记录页
    data object Transaction : Route("transaction/{itemId}") {
        fun createRoute(itemId: String) = "transaction/$itemId"
    }
}
