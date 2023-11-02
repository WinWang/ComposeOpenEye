package com.winwang.openeye.ui.page

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.blankj.utilcode.util.LogUtils
import com.winwang.openeye.http.apiservice.ApiService
import com.winwang.openeye.base.component.ComposeComponent
import com.winwang.openeye.base.viewmodel.BaseViewModel
import com.winwang.openeye.base.viewmodel.ViewStateMutableLiveData
import com.winwang.openeye.model.HomeDataModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by WinWang on 2023/10/25
 * Description:首页页面
 *
 **/
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    viewModel: HomeViewModel = hiltViewModel()
) {
    var isLoaded by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        LogUtils.d(">>>>$isLoaded")
        if (!isLoaded) {
            LogUtils.d("HomePage")
            viewModel.onStart()
            isLoaded = true
        }
    }
    Column {
        CenterAlignedTopAppBar(title = { Text(text = "首页") })
        ComposeComponent(viewStateLiveData = viewModel.homeLiveData) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.Red)
            )
        }
    }
}


@HiltViewModel
class HomeViewModel @Inject constructor(private val api: ApiService) : BaseViewModel() {

    private var date: String = ""

    val homeLiveData by lazy {
        ViewStateMutableLiveData<HomeDataModel>()
    }

    fun onStart() {
        LogUtils.d("HomeViewModel")
        getHomeList()
    }

    private fun getHomeList() {
        launchApi(liveData = homeLiveData) {
            api.getHomeList(0, "")
        }
    }


}
