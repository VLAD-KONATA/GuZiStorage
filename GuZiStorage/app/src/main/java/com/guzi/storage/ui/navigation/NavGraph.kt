package com.guzi.storage.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.guzi.storage.ui.add.AddItemScreen
import com.guzi.storage.ui.home.HomeScreen

/**
 * 应用导航图
 *
 * 页面流转:
 * HomeScreen → GroupScreen → DetailScreen → TransactionScreen
 * HomeScreen → AddItemScreen (添加)
 * DetailScreen → AddItemScreen (编辑)
 */
@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: String = Route.Home.path
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // 主页
        composable(Route.Home.path) {
            HomeScreen(
                onGroupClick = { ipName ->
                    navController.navigate(Route.Group.createRoute(ipName))
                },
                onAddClick = {
                    navController.navigate(Route.AddItem.createRoute())
                }
            )
        }

        // 添加/编辑物品
        composable(
            route = Route.AddItem.path,
            arguments = listOf(
                navArgument("itemId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId")
            AddItemScreen(
                itemId = itemId,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // 分组子页面 (阶段三实现)
        composable(
            route = Route.Group.path,
            arguments = listOf(
                navArgument("ipName") { type = NavType.StringType }
            )
        ) {
            // GroupScreen - 阶段三实现
        }

        // 详情页 (阶段四实现)
        composable(
            route = Route.Detail.path,
            arguments = listOf(
                navArgument("itemId") { type = NavType.StringType }
            )
        ) {
            // DetailScreen - 阶段四实现
        }

        // 交易记录页 (阶段五实现)
        composable(
            route = Route.Transaction.path,
            arguments = listOf(
                navArgument("itemId") { type = NavType.StringType }
            )
        ) {
            // TransactionScreen - 阶段五实现
        }
    }
}
