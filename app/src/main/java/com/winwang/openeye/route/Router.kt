package com.winwang.openeye.route

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.winwang.openeye.ui.page.HomePage
import com.winwang.openeye.ui.page.HotPage
import com.winwang.openeye.ui.page.MinePage
import com.winwang.openeye.ui.page.find.FindPage


/**
 * Created by WinWang on 2023/10/25
 * Description->注册路由表信息
 */

object RouteName {
    const val HOME = "home"
    const val FIND = "find"
    const val HOT = "hot"
    const val MINE = "mine"
    const val WEB_VIEW = "web_view"
}

@Composable
fun Routes(
    navController: NavHostController,
    paddingValues: PaddingValues = PaddingValues(top = 0.dp, bottom = 0.dp, start = 0.dp, end = 0.dp),
    startDestination: String = RouteName.HOME
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier.padding(top = 0.dp, bottom = paddingValues.calculateBottomPadding())
    ) {
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