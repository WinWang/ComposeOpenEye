package com.winwang.openeye.ui.page.hot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.winwang.openeye.base.component.ComposeComponent
import com.winwang.openeye.base.viewmodel.BaseViewModel
import com.winwang.openeye.base.viewmodel.ViewStateMutableLiveData
import com.winwang.openeye.http.apiservice.ApiService
import com.winwang.openeye.model.HotDataModel
import com.winwang.openeye.ui.theme.color_e5e5e5
import com.winwang.openeye.widget.CoilImage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by WinWang on 2023/11/17
 * Description:
 * [rankType] 0:周排行 1:月排行 2:总排行
 **/
@Composable
fun RankPage(
    rankType: String = "weekly",
    viewModel: RankViewModel = hiltViewModel(key = "rank_$rankType")
) {
    var hasInit by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        if (!hasInit) {
            viewModel.getRankData(rankType)
            hasInit = true
        }
    }

    ComposeComponent(viewStateLiveData = viewModel.rankLiveData, loadDataBlock = { viewModel.getRankData(rankType) }) { data ->
        LazyColumn(content = {
            items(data.itemList?.size ?: 0) { index ->
                val item = data.itemList?.get(index)
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    Box {
                        CoilImage(
                            url = item?.data?.cover?.feed
                                ?: "",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        )
                        Box(
                            modifier = Modifier
                                .width(50.dp)
                                .height(20.dp)
                                .clip(RoundedCornerShape(0.dp, 0.dp, 20.dp, 0.dp))
                                .background(Color.Red),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                modifier = Modifier
                                    .height(20.dp)
                                    .align(Alignment.Center),
                                text = item?.data?.category ?: "",
                                color = Color.White,
                                fontSize = 12.sp
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CoilImage(
                            url = item?.data?.author?.icon ?: "",
                            modifier = Modifier
                                .height(40.dp)
                                .width(40.dp)
                                .clip(RoundedCornerShape(20.dp))
                        )
                        Column(modifier = Modifier.padding(start = 10.dp)) {
                            Text(
                                text = item?.data?.author?.name ?: "",
                                fontSize = 15.sp
                            )
                            Text(
                                text = item?.data?.author?.description ?: "",
                                fontSize = 12.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis

                            )
                        }
                    }
                }
            }
            item {
                Row(modifier = Modifier.align(Alignment.Center)) {
                    Divider(
                        modifier = Modifier
                            .width(30.dp)
                            .background(color_e5e5e5)
                            .height(1.dp)
                    )
                    Text(text = "我是有底线的")
                    Divider(
                        modifier = Modifier
                            .width(30.dp)
                            .background(color_e5e5e5)
                            .height(1.dp)
                    )
                }
            }
        })
    }
}

@HiltViewModel
class RankViewModel @Inject constructor(private val api: ApiService) : BaseViewModel() {

    val rankLiveData = ViewStateMutableLiveData<HotDataModel>()

    fun getRankData(rankType: String) {
        launchApi(
            liveData = rankLiveData,
        ) {
            api.getRankList(rankType)
        }
    }


}

