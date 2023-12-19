@file:OptIn(ExperimentalPagerApi::class)

package com.winwang.openeye.ui.page.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.paging.PagingData
import com.blankj.utilcode.util.LogUtils
import com.google.accompanist.pager.ExperimentalPagerApi
import com.winwang.openeye.base.component.ComposePagingComponent
import com.winwang.openeye.base.viewmodel.BaseViewModel
import com.winwang.openeye.base.viewmodel.ViewStateMutableLiveData
import com.winwang.openeye.ext.buildPager
import com.winwang.openeye.ext.logD
import com.winwang.openeye.ext.replaceImageUrl
import com.winwang.openeye.http.apiservice.ApiService
import com.winwang.openeye.model.HomeDataModel
import com.winwang.openeye.route.RouteName
import com.winwang.openeye.route.Router
import com.winwang.openeye.widget.BannerData
import com.winwang.openeye.widget.CoilImage
import com.winwang.openeye.widget.CommonBanner
import com.winwang.openeye.widget.CommonTopAppBar
import dagger.hilt.android.lifecycle.HiltViewModel
import java.net.URLEncoder
import java.util.concurrent.Flow
import javax.inject.Inject

/**
 * Created by WinWang on 2023/10/25
 * Description:首页页面
 *
 **/
@OptIn(ExperimentalPagerApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomePage(
    viewModel: HomeViewModel = hiltViewModel()
) {
    var isLoaded by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        LogUtils.d(">>>>$isLoaded")
        if (!isLoaded) {
            LogUtils.d("HomePage")
            isLoaded = true
        }
    }
    Column {
        CommonTopAppBar(title = "首页", showBackButton = false)
        ComposePagingComponent(
            modifier = Modifier,
            key = "home",
            loadDataBlock = { viewModel.getHomePagingList() },
            refreshBlock = { viewModel.getHomePagingList() }
        ) {
            items(it.itemCount) { index ->
                val item = it[index]
                if (item?.type == "banner") {
                    CommonBanner(list = item.bannerList, onClick = { link, title ->

                    })
                } else {
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .clickable {
                                val encodedUrl = URLEncoder.encode("${item?.data?.playUrl}")
                                Router.push("${RouteName.VIDEO_DETAIL}/${item?.data?.id}/$encodedUrl")
                            }
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
            }
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
        getHomePagingList()
    }

    private fun getHomeList() {
        launchApi(liveData = homeLiveData) {
            api.getHomeList(0, "")
        }
    }

    /**
     * 获取首页数据
     */
    fun getHomePagingList() = buildPager(
        transformListBlock = { result ->
            date = result?.nextPageUrl?.split("?")?.get(1)?.split("&")?.get(0)?.split("=")?.get(1) ?: ""
            val items: MutableList<HomeDataModel.Issue.Item>? = result?.issueList?.get(0)?.itemList ?: mutableListOf()
            val bannerList = items?.filter { it.type == "banner2" }
            val bannerData = mutableListOf<BannerData>()
            bannerList?.forEach {
                bannerData.add(BannerData(imageUrl = it.data?.image.replaceImageUrl()))
            }
            if (bannerData.isNotEmpty()) {
                items?.add(0, HomeDataModel.Issue.Item(bannerList = bannerData, type = "banner"))
            }
            var dataList = items?.filter { it.type == "video" || it.type == "banner" }
            dataList
        },
    ) { page, _ ->
        api.getHomeList(page, date)
    }


}
