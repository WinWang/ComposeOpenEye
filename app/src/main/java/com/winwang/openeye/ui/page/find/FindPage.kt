package com.winwang.openeye.ui.page.find

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.winwang.openeye.base.component.ComposeComponent
import com.winwang.openeye.base.viewmodel.BaseViewModel
import com.winwang.openeye.base.viewmodel.ViewStateMutableLiveData
import com.winwang.openeye.http.apiservice.ApiService
import com.winwang.openeye.model.FollowDataModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by WinWang on 2023/10/25
 * Description:发现页面
 **/
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FindPage(
    viewModel: FindViewModel = hiltViewModel()
) {
    var isLoaded by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        if (!isLoaded) {
            viewModel.getFollowData()
            isLoaded = true
        }
    }
    Scaffold(topBar = { TopAppBar(title = { Text(text = "发现") }) })
    {
        ComposeComponent(viewStateLiveData = viewModel.followLiveData) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(androidx.compose.ui.graphics.Color.Red)
            ) {
                Text(text = "发现")
            }
        }
    }

}

@HiltViewModel
class FindViewModel @Inject constructor(private var api: ApiService) : BaseViewModel() {

    val followLiveData = ViewStateMutableLiveData<FollowDataModel>()

    fun getFollowData() {
        launchApi(
            liveData = followLiveData,
        ) {
            api.getFollowList()
        }
    }


}
