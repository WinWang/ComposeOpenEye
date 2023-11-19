package com.winwang.openeye.ui.page.find

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.winwang.openeye.base.component.ComposePagingComponent
import com.winwang.openeye.base.viewmodel.BaseViewModel
import com.winwang.openeye.ext.buildPager
import com.winwang.openeye.http.apiservice.ApiService
import com.winwang.openeye.ui.theme.color_333
import com.winwang.openeye.ui.theme.color_888
import com.winwang.openeye.widget.CommonNetworkImage
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

            hasInit = true
        }
    }
    Column {
        ComposePagingComponent(key = "focus", loadDataBlock = { viewModel.getFollowData() }) {
            items(it.itemCount) { index ->
                val item = it[index]
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CommonNetworkImage(
                            url = item?.data?.header?.icon, modifier = Modifier
                                .height(50.dp)
                                .width(50.dp)
                                .clip(RoundedCornerShape(25.dp))
                        )
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 10.dp)
                        ) {
                            Text(
                                text = item?.data?.header?.title ?: "",
                                color = color_333,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                                fontSize = 16.sp
                            )
                            Text(
                                text = item?.data?.header?.description ?: "",
                                color = color_888,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                                fontSize = 13.sp,
                                modifier = Modifier.padding(top = 6.dp)
                            )
                        }
                    }
                    LazyRow(modifier = Modifier.height(200.dp)) {
                        items(item?.data?.itemList?.size ?: 0) { innerIndex ->
                            val innerItem = item?.data?.itemList?.get(innerIndex)
                            Box(
                                modifier = Modifier
                                    .height(200.dp)
                                    .width(300.dp)
                                    .padding(start = 12.dp)
                                    .clip(RoundedCornerShape(10.dp))
                            ) {
                                CommonNetworkImage(
                                    url = innerItem?.data?.cover?.feed ?: "",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp)
                                        .clip(RoundedCornerShape(10.dp))
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
                                        text = innerItem?.data?.category ?: "",
                                        color = Color.White,
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}

@HiltViewModel
class FocusViewModel @Inject constructor(private var api: ApiService) : BaseViewModel() {
    fun getFollowData() = buildPager(
        transformListBlock = { it?.itemList ?: emptyList() },
    ) { pageIndex, _ ->
        api.getFollowList(pageIndex)
    }

}
