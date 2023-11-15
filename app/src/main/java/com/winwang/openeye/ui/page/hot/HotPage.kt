package com.winwang.openeye.ui.page.hot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.winwang.openeye.widget.CommonTopAppBar

/**
 * Created by WinWang on 2023/10/25
 * Description:热门页面
 **/
@Composable
fun HotPage() {
    Column {
        CommonTopAppBar(title = "热门", showBackButton = false)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .background(androidx.compose.ui.graphics.Color.Red)
        ) {

        }
    }


}