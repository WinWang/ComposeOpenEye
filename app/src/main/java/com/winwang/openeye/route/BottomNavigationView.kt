package com.winwang.openeye.route

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.blankj.utilcode.util.LogUtils

/**
 * Created by WinWang on 2022/5/11
 * Description->
 */

/**
 * 准备用过首页全部都用路由实现的BottomBarview，配合NaviController实现路由切换
 */
@Composable
fun BottomNavBarView(navCtrl: NavHostController) {
    val bottomNavList = listOf(
        BottomNavRoute.Home,
        BottomNavRoute.Find,
        BottomNavRoute.Hot,
        BottomNavRoute.Mine
    )
     NavigationBar {
        val navBackStackEntry by navCtrl.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        bottomNavList.forEach { destination ->
            val selected = currentDestination?.hierarchy?.any { it.route == destination.routeName } == true
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = destination.icon,
                        contentDescription = null
                    )
                },
                label = { Text(text = stringResource(destination.stringId)) },
                selected = selected,
                onClick = {
                    runCatching {
                        LogUtils.d("当前路由名称>>>>>>${destination.routeName}")
                        if (currentDestination?.route != destination.routeName) {
                            navCtrl.navigate(destination.routeName) {
                                popUpTo(navCtrl.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                })
        }
    }
}