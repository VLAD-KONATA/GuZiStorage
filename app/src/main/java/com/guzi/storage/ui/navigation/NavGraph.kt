package com.guzi.storage.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.guzi.storage.ui.add.AddItemScreen
import com.guzi.storage.ui.zipimport.ZipImportScreen
import com.guzi.storage.ui.fileimport.StandaloneImportScreen
import com.guzi.storage.ui.detail.DetailScreen
import com.guzi.storage.ui.detail.ImageViewerScreen
import com.guzi.storage.ui.group.GroupScreen
import com.guzi.storage.ui.home.HomeScreen
import com.guzi.storage.ui.settings.GroupingSettingsScreen
import com.guzi.storage.ui.settings.PresetManageScreen
import com.guzi.storage.ui.settings.SettingsScreen
import com.guzi.storage.ui.transaction.TransactionScreen
import com.guzi.storage.ui.transaction.add.AddTransactionScreen

/**
 * 应用导航图
 *
 * 页面流转:
 * Home → Group → Detail → Transaction → AddTransaction
 * Home / Group → AddItem（添加/编辑）
 * Detail → ImageViewer（大图预览）
 * Home → Settings → PresetManage / GroupingSettings
 * Home → ZipImport
 */
@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: String = Route.Home.path
) {
    NavHost(navController = navController, startDestination = startDestination) {

        // ===== 主页 =====
        composable(Route.Home.path) {
            HomeScreen(
                onGroupClick = { path ->
                    navController.navigate(Route.Group.createRoute(path))
                },
                onAddClick = {
                    navController.navigate(Route.AddItem.createRoute())
                },
                onImportClick = {
                    navController.navigate(Route.ZipImport.path)
                },
                onStandaloneImportClick = { type ->
                    navController.navigate(Route.StandaloneImport.createRoute(type))
                },
                onSettingsClick = {
                    navController.navigate(Route.Settings.path)
                }
            )
        }

        // ===== 添加/编辑物品 =====
        composable(
            route = Route.AddItem.path,
            arguments = listOf(
                navArgument("itemId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                },
                navArgument("defaultIpName") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                },
                navArgument("copyFromId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId")
            val defaultIpName = backStackEntry.arguments?.getString("defaultIpName")
            val copyFromId = backStackEntry.arguments?.getString("copyFromId")
            AddItemScreen(
                itemId = itemId,
                defaultIpName = defaultIpName,
                copyFromId = copyFromId,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // ===== 分组子页面（支持多级分组） =====
        composable(
            route = Route.Group.path,
            arguments = listOf(
                navArgument("path") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            val pathStr = backStackEntry.arguments?.getString("path")
            val path = Route.Group.parsePath(pathStr)
            val depth = path.size
            GroupScreen(
                path = path,
                depth = depth,
                onItemClick = { itemId ->
                    navController.navigate(Route.Detail.createRoute(itemId))
                },
                onSubGroupClick = { newPath ->
                    navController.navigate(Route.Group.createRoute(newPath))
                },
                onAddClick = {
                    val defaultIp = path.firstOrNull { it.first == "ip_name" }?.second
                    navController.navigate(Route.AddItem.createRoute(defaultIpName = defaultIp))
                },
                onNavigateBack = { navController.popBackStack() },
                onSettingsClick = {
                    navController.navigate(Route.Settings.path)
                }
            )
        }

        // ===== 物品详情页 =====
        composable(
            route = Route.Detail.path,
            arguments = listOf(
                navArgument("itemId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId") ?: ""
            DetailScreen(
                itemId = itemId,
                onNavigateBack = { navController.popBackStack() },
                onEditClick = { id ->
                    navController.navigate(Route.AddItem.createRoute(itemId = id))
                },
                onCopyEditClick = { id ->
                    navController.navigate(Route.AddItem.createRoute(copyFromId = id))
                },
                onTransactionClick = { id ->
                    navController.navigate(Route.Transaction.createRoute(id))
                },
                onImageClick = { id, index ->
                    navController.navigate(Route.ImageViewer.createRoute(id, index))
                }
            )
        }

        // ===== 交易记录列表页 =====
        composable(
            route = Route.Transaction.path,
            arguments = listOf(
                navArgument("itemId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId") ?: ""
            TransactionScreen(
                itemId = itemId,
                onNavigateBack = { navController.popBackStack() },
                onAddTransaction = { id ->
                    navController.navigate(Route.AddTransaction.createRoute(id))
                },
                onEditTransaction = { transactionId ->
                    navController.navigate(Route.AddTransaction.createRoute(itemId, transactionId = transactionId))
                }
            )
        }

        // ===== 添加/编辑交易记录 =====
        composable(
            route = Route.AddTransaction.path,
            arguments = listOf(
                navArgument("itemId") { type = NavType.StringType },
                navArgument("itemNameCn") {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument("transactionId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId") ?: ""
            val itemNameCn = backStackEntry.arguments?.getString("itemNameCn") ?: ""
            val transactionId = backStackEntry.arguments?.getString("transactionId")
            AddTransactionScreen(
                itemId = itemId,
                itemNameCn = itemNameCn,
                transactionId = transactionId,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // ===== ZIP 批量导入 =====
        composable(Route.ZipImport.path) {
            ZipImportScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // ===== 单独文件导入 =====
        composable(
            route = Route.StandaloneImport.path,
            arguments = listOf(
                navArgument("type") {
                    type = NavType.StringType
                    defaultValue = "items_csv"
                }
            )
        ) {
            StandaloneImportScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // ===== 设置页 =====
        composable(Route.Settings.path) {
            SettingsScreen(
                onAttributeClick = { attributeName, attributeLabel ->
                    navController.navigate(Route.PresetManage.createRoute(attributeName, attributeLabel))
                },
                onGroupingSettingsClick = {
                    navController.navigate(Route.GroupingSettings.path)
                },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // ===== 分组设置页 =====
        composable(Route.GroupingSettings.path) {
            GroupingSettingsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // ===== 预设选项管理页 =====
        composable(
            route = Route.PresetManage.path,
            arguments = listOf(
                navArgument("attributeName") { type = NavType.StringType },
                navArgument("attributeLabel") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { backStackEntry ->
            val attributeName = backStackEntry.arguments?.getString("attributeName") ?: ""
            val attributeLabel = backStackEntry.arguments?.getString("attributeLabel") ?: attributeName
            PresetManageScreen(
                attributeName = attributeName,
                attributeLabel = attributeLabel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // ===== 大图预览 =====
        composable(
            route = Route.ImageViewer.path,
            arguments = listOf(
                navArgument("itemId") { type = NavType.StringType },
                navArgument("startIndex") {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId") ?: ""
            val startIndex = backStackEntry.arguments?.getInt("startIndex") ?: 0
            ImageViewerScreen(
                itemId = itemId,
                startIndex = startIndex,
                onClose = { navController.popBackStack() }
            )
        }
    }
}
