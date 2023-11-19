package com.winwang.openeye.ui.page.find

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.winwang.openeye.base.component.ComposePagingComponent
import com.winwang.openeye.base.viewmodel.BaseViewModel
import com.winwang.openeye.constant.AppPagingConfig
import com.winwang.openeye.ext.buildPager
import com.winwang.openeye.ext.replaceImageUrl
import com.winwang.openeye.http.apiservice.ApiService
import com.winwang.openeye.widget.CommonNetworkImage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by WinWang on 2023/11/1
 * Description:主题页面
 **/

@Composable
fun TopicPage(
    viewModel: TopicViewModel = hiltViewModel()
) {
    ComposePagingComponent(key = "topic", loadDataBlock = { viewModel.getTopicData() }) {
        items(it.itemCount) { index ->
            val item = it[index]
            CommonNetworkImage(
                url = item?.data?.image.replaceImageUrl(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 0.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }
    }

}

@HiltViewModel
class TopicViewModel @Inject constructor(private var api: ApiService) : BaseViewModel() {

    var pageIndex = 1

    fun getTopicData() = buildPager(
        config = AppPagingConfig(defaultPageIndex = pageIndex),
        transformListBlock = {
            pageIndex += it?.itemList?.count() ?: 0
            it?.itemList ?: emptyList()
        }
    ) { _, _ ->
        api.getTopicList(pageIndex)
    }


}
