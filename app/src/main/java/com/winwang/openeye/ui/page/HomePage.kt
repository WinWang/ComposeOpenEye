package com.winwang.openeye.ui.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.blankj.utilcode.util.LogUtils

/**
 * Created by WinWang on 2023/10/25
 * Description:首页页面
 *
 **/
@Composable
fun HomePage() {

    var isLoaded by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        LogUtils.d(">>>>$isLoaded")
        if (!isLoaded) {
            LogUtils.d("HomePage")
            isLoaded = true
        }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
            .background(androidx.compose.ui.graphics.Color.Red)
    ) {

    }


}