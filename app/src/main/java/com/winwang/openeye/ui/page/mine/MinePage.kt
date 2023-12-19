@file:OptIn(ExperimentalMaterial3Api::class)

package com.winwang.openeye.ui.page.mine

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.winwang.openeye.base.viewmodel.BaseViewModel
import com.winwang.openeye.widget.CommonTopAppBar
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by WinWang on 2023/10/25
 * Description:我的页面
 **/
@Composable
fun MinePage(viewModel: MineViewModel = hiltViewModel()) {
    LaunchedEffect(key1 = Unit, block = {

    })

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .background(Color.Red)
        ) {

        }
    }

}

@HiltViewModel
class MineViewModel @Inject constructor() : BaseViewModel() {


}


