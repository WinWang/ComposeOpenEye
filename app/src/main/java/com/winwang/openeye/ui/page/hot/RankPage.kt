package com.winwang.openeye.ui.page.hot

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.winwang.openeye.base.viewmodel.BaseViewModel
import com.winwang.openeye.http.apiservice.ApiService
import javax.inject.Inject

/**
 * Created by WinWang on 2023/11/17
 * Description:
 **/
@Composable
fun RankPage(viewModel: RankViewModel = hiltViewModel()) {

}

class RankViewModel @Inject constructor(private val api: ApiService) : BaseViewModel() {

    var tabTitle by mutableStateOf(emptyList<String>())

    init {
        tabTitle = listOf("周排行", "月排行", "年排行")
    }

}

