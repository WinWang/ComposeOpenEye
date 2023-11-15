@file:OptIn(ExperimentalMaterial3Api::class)

package com.winwang.openeye.ui.page.mine

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.winwang.openeye.widget.CommonTopAppBar

/**
 * Created by WinWang on 2023/10/25
 * Description:我的页面
 **/
@Composable
fun MinePage() {
    Column {
        CommonTopAppBar(title = "我的")
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .background(Color.Red)
        ) {

        }
    }


}