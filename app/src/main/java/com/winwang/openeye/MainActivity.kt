package com.winwang.openeye

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.winwang.openeye.base.BaseActivity
import com.winwang.openeye.route.BottomNavBarView
import com.winwang.openeye.route.RouteName
import com.winwang.openeye.route.Router
import com.winwang.openeye.route.Routes
import com.winwang.openeye.ui.theme.ComposeOpenEyeTheme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterial3Api
@AndroidEntryPoint
@OptIn(ExperimentalAnimationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = rememberSystemUiController()
            systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = false)
            ComposeOpenEyeTheme {
                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = Color.Transparent,
                        darkIcons = true
                    )
                }
                val navCtrl = rememberAnimatedNavController()
                val navBackStackEntry by navCtrl.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                Router.navigationController = navCtrl
                Scaffold(
                    bottomBar = {
                        when (currentDestination?.route) {
                            RouteName.HOME -> BottomNavBarView(navCtrl = navCtrl)
                            RouteName.FIND -> BottomNavBarView(navCtrl = navCtrl)
                            RouteName.HOT -> BottomNavBarView(navCtrl = navCtrl)
                            RouteName.MINE -> BottomNavBarView(navCtrl = navCtrl)
                        }
                    }
                ) { paddingValues ->
                    Routes(navController = navCtrl, paddingValues, RouteName.HOME)
                }
            }
        }
    }
}