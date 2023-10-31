package com.winwang.openeye.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.winwang.openeye.ui.page.find.FindPage
import com.winwang.openeye.ui.page.HomePage
import com.winwang.openeye.ui.page.HotPage
import com.winwang.openeye.ui.page.MinePage


/**
 * Created by WinWang on 2023/10/25
 * Description->注册路由表信息
 */

object RouteName {
    const val HOME = "home"
    const val FIND = "find"
    const val HOT = "hot"
    const val MINE = "mine"
    const val COLLECTION = "collection"
    const val PROFILE = "profile"
    const val WEB_VIEW = "web_view"
    const val LOGIN = "login"
    const val ARTICLE_SEARCH = "article_search"
}

@Composable
fun routes(
    navController: NavHostController,
    startDestination: String = RouteName.HOME
) {
    NavHost(navController = navController, startDestination = startDestination) {
        /**
         * 首页
         */
        composable(route = RouteName.HOME) {
            HomePage()
        }
        /**
         * 发现
         */
        composable(route = RouteName.FIND) {
            FindPage()
        }

        /**
         * 热门
         */
        composable(route = RouteName.HOT) {
            HotPage()
        }

        /**
         * 我的
         */
        composable(route = RouteName.MINE) {
            MinePage()
        }

        /**
         * 文章详情页面
         */
        composable(route = RouteName.WEB_VIEW) {

        }
    }
}