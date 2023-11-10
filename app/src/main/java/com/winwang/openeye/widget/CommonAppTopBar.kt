package com.winwang.openeye.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.material.animation.ArgbEvaluatorCompat

/**
 * Created by WinWang on 2021/12/4
 *
 * compose版本通用TopAppBar
 * @param title: 标题
 * @param rightIconResId: 右边按钮资源id  和rightText只存在一个，同时存在只显示rightIconResId
 * @param rightText: 右边按钮文本  和rightIconResId只存在一个，同时存在只显示rightIconResId
 * @param customRightLayout: 自定义右边布局  如果设置了customRightLayout，rightIconResId和rightText不生效
 * @param leftClick: 左边按钮点击回调
 * @param rightClick: 右边按钮点击回调
 * @param backgroundColor: 背景颜色，默认白色
 * @param statusBarColor: 状态栏颜色
 * @param contentColor: 文本等内容颜色
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonTopAppBar(
    title: String = "",
    statusBarColor: TopBarStyle = TopBarStyle.SolidColor(Color.Red),
    statusBarDarkIcons: Boolean? = null,
    backgroundColor: TopBarStyle = TopBarStyle.SolidColor(Color.Red),
    contentColor: TopBarStyle = TopBarStyle.SolidColor(Color.White),
    leftIconColor: TopBarStyle = TopBarStyle.SolidColor(Color.White),
    rightIconResId: Int = -1,
    rightText: String = "",
    customRightLayout: (@Composable () -> Unit)? = null,
    leftClick: (() -> Unit)? = null,
    rightClick: (() -> Unit)? = null,
    showBottomDivider: Boolean = false,
    showBackButton: Boolean = true
) {
    val topAppBarColors = TopAppBarDefaults.smallTopAppBarColors(
        containerColor = backgroundColor.color(),
        titleContentColor = contentColor.color()
    )
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = statusBarColor.color(),
            darkIcons = statusBarDarkIcons ?: false
        )
        systemUiController.setNavigationBarColor(
            color = Color.White,
            darkIcons = false
        )
    }
    Column {
        CenterAlignedTopAppBar(
            colors = topAppBarColors,
            navigationIcon = {
                if (showBackButton) {
                    IconButton(onClick = {
                        leftClick?.invoke()
                    }) {
                        Icon(
                            Icons.Default.ArrowBack, contentDescription = "Back", modifier = Modifier
                                .size(24.dp),
                            tint = leftIconColor.color()
                        )
                    }
                }
            },
            title = {
                Text(
                    text = title,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = contentColor.color(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            actions = {
                if (customRightLayout != null) {
                    customRightLayout.invoke()
                } else {
                    if (rightIconResId != -1) {
                        Image(
                            painter = painterResource(id = rightIconResId),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(10.dp)
                                .width(48.dp)
                                .height(48.dp)
                                .clickable { rightClick?.invoke() }
                        )
                    } else if (rightText.isNotBlank()) {
                        Text(
                            text = rightText,
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center,
                            color = contentColor.color(),
                            maxLines = 1,
                            modifier = Modifier
                                .padding(20.dp)
                                .clickable { rightClick?.invoke() }
                        )
                    }
                }
            }
        )
        if (showBottomDivider) {
            Divider(
                color = Color(0xFFE5E5E5), thickness = 0.5.dp, modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

sealed class TopBarStyle {
    data class SolidColor(val color: Color) : TopBarStyle()
    data class DynamicChangeColor(val fraction: Float, val startColor: Color, val endColor: Color) : TopBarStyle()
}

fun TopBarStyle.color(): Color {
    return when (this) {
        is TopBarStyle.SolidColor -> color
        is TopBarStyle.DynamicChangeColor -> Color(
            ArgbEvaluatorCompat.getInstance().evaluate(if (fraction > 1) 1f else fraction, startColor.toArgb(), endColor.toArgb())
        )
    }
}
