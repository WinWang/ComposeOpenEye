package com.winwang.openeye

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.winwang.openeye.base.BaseActivity
import com.winwang.openeye.route.RouteName
import com.winwang.openeye.route.routes
import com.winwang.openeye.route.BottomNavBarView
import com.winwang.openeye.ui.theme.ComposeOpenEyeTheme

@ExperimentalMaterial3Api
class MainActivity : BaseActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeOpenEyeTheme {
                val navCtrl = rememberNavController()
                val navBackStackEntry by navCtrl.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                Scaffold(
                    bottomBar = {
                        when (currentDestination?.route) {
                            RouteName.HOME -> BottomNavBarView(navCtrl = navCtrl)
                            RouteName.FIND -> BottomNavBarView(navCtrl = navCtrl)
                            RouteName.HOT -> BottomNavBarView(navCtrl = navCtrl)
                            RouteName.MINE -> BottomNavBarView(navCtrl = navCtrl)
                        }
                    }
                ) {
                    routes(navController = navCtrl, RouteName.HOME)
                }
            }
        }
    }
}