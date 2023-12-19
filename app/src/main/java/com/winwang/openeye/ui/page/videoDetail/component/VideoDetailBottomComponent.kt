package com.winwang.openeye.ui.page.videoDetail.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import com.winwang.openeye.ui.page.videoDetail.VideoDetailViewModel
import com.winwang.openeye.ui.theme.color_333
import com.winwang.openeye.ui.theme.color_888
import com.winwang.openeye.widget.CommonNetworkImage

/**
 *
 * @Desc     -视频详情底部组件
 * @author   WinWang
 * @date     2023/12/19
 * @version
 */

@Composable
fun VideoDetailBottomComponent(
    viewmodel: VideoDetailViewModel = hiltViewModel(),
    itemClickCallback: ((String) -> Unit)? = null
) {
    var playIndex by remember { viewmodel.playIndex }
    ComposeComponent(viewStateLiveData = viewmodel.videoDetailLiveData, loadDataBlock = {
        viewmodel.getVideoDetail()
    }) { result ->
        LazyColumn {
            items(result.itemList?.count() ?: 0) { index ->
                val item = result.itemList?.get(index)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(horizontal = 10.dp)
                        .clickable {
                            itemClickCallback?.invoke(item?.data?.playUrl ?: "")
                            playIndex = index
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CommonNetworkImage(
                        url = item?.data?.cover?.feed,
                        modifier = Modifier
                            .width(150.dp)
                            .height(80.dp)
                            .clip(RoundedCornerShape(5.dp))
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(vertical = 10.dp, horizontal = 10.dp)
                    ) {
                        Text(
                            text = item?.data?.title ?: "", maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = if (playIndex == index) Color.Red else color_333,
                            fontSize = 15.sp
                        )
                        Text(
                            text = item?.data?.description ?: "", maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = if (playIndex == index) Color.Red else color_888,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 10.dp)
                        )
                    }
                }
            }
        }
    }
}



