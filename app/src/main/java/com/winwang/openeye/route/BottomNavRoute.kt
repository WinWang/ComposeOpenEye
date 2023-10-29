package com.winwang.openeye.route

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.winwang.openeye.R

sealed class BottomNavRoute(
    var routeName: String,
    @StringRes var stringId: Int,
    var icon: ImageVector,
    var index: Int

) {
    object Home : BottomNavRoute(RouteName.HOME, R.string.home, Icons.Default.Home, 0)
    object Find : BottomNavRoute(RouteName.FIND, R.string.find, Icons.Default.Menu, 1)
    object Hot : BottomNavRoute(RouteName.HOT, R.string.hot, Icons.Default.Favorite, 2)
    object Mine : BottomNavRoute(RouteName.MINE, R.string.mine, Icons.Default.Person, 3)
}