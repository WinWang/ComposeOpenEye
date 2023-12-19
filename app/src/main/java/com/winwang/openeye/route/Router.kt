package com.winwang.openeye.route

import android.annotation.SuppressLint
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.winwang.openeye.ui.page.find.FindPage
import com.winwang.openeye.ui.page.home.HomePage
import com.winwang.openeye.ui.page.hot.HotPage
import com.winwang.openeye.ui.page.mine.MinePage
import com.winwang.openeye.ui.page.videoDetail.VideoDetailPage
import java.net.URLDecoder
import java.net.URLEncoder


/**
 * Created by WinWang on 2023/10/25
 * Description->注册路由表信息
 */

object RouteName {
    const val HOME = "home"
    const val FIND = "find"
    const val HOT = "hot"
    const val MINE = "mine"
    const val VIDEO_DETAIL = "video_detail"
    const val WEB_VIEW = "web_view"
}

object Router {

    @SuppressLint("StaticFieldLeak")
    lateinit var navigationController: NavHostController

    fun push(routeName: String) {
        navigationController.navigate(routeName)
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Routes(
    navController: NavHostController,
    paddingValues: PaddingValues = PaddingValues(top = 0.dp, bottom = 0.dp, start = 0.dp, end = 0.dp),
    startDestination: String = RouteName.HOME
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier.padding(top = 0.dp, bottom = paddingValues.calculateBottomPadding()),
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(500)
            )
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { fullWidth -> -fullWidth },
                animationSpec = tween(500)
            )
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { fullWidth -> -fullWidth },
                animationSpec = tween(500)
            )
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(500)
            )
        }
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
         * 视频详情页面
         */
        composable(
            route = "${RouteName.VIDEO_DETAIL}/{relationID}/{playUrl}",
            arguments = listOf(navArgument("relationID") { type = NavType.IntType })
        ) {
            val relationID = it.arguments?.getInt("relationID") ?: -1
            val playUrl = it.arguments?.getString("playUrl") ?: ""
            val url = URLDecoder.decode(playUrl)
            VideoDetailPage(relationID, url)
        }

        /**
         * 文章详情页面
         */
        composable(route = RouteName.WEB_VIEW) {

        }
    }
}