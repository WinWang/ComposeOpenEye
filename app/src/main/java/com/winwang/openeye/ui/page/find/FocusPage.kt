package com.winwang.openeye.ui.page.find

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
 * Created by WinWang on 2023/11/1
 * Description:关注页面
 **/

@Composable
fun FocusPage(
    viewModel: FocusViewModel = hiltViewModel()
) {
    var hasInit by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(hasInit) {
        if (!hasInit) {
            viewModel.getFollowData()
            hasInit = true
        }
    }
    Column {
        ComposeComponent(viewStateLiveData = viewModel.followLiveData) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.Red)
            ) {
                Text(text = "发现")
            }
        }
    }

}

@HiltViewModel
class FocusViewModel @Inject constructor(private var api: ApiService) : BaseViewModel() {

    val followLiveData = ViewStateMutableLiveData<FollowDataModel>()

    fun getFollowData() {
        launchApi(
            liveData = followLiveData,
        ) {
            api.getFollowList()
        }
    }

}
