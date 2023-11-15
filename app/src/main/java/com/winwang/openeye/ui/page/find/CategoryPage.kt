package com.winwang.openeye.ui.page.find

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.winwang.openeye.base.component.ComposeComponent
import com.winwang.openeye.base.viewmodel.BaseViewModel
import com.winwang.openeye.base.viewmodel.ViewStateMutableLiveData
import com.winwang.openeye.ext.replaceImageUrl
import com.winwang.openeye.http.apiservice.ApiService
import com.winwang.openeye.model.CategoryDataModel
import com.winwang.openeye.widget.CommonNetworkImage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by WinWang on 2023/11/1
 * Description:分类页面
 **/

@Composable
fun CategoryPage(
    viewModel: CategoryViewModel = hiltViewModel()
) {
    var hasInit by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        if (!hasInit) {
            viewModel.getCategoryData()
            hasInit = true
        }
    }
    Box {
        ComposeComponent(viewStateLiveData = viewModel.categoryLiveData, loadDataBlock = { viewModel.getCategoryData() }) { items ->
            val chunkedItems = items.chunked(2) // 将列表分割成每个包含2个元素的子列表
            LazyColumn {
                items(chunkedItems.size) { index ->
                    Row(
                        modifier = if (chunkedItems[index].size == 2) Modifier.fillMaxWidth() else Modifier
                            .fillMaxWidth(fraction = 0.5f),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        for (chunkedItem in chunkedItems[index]) {
                            Box(
                                modifier = Modifier
                                    .weight(0.5f)
                                    .aspectRatio(1f)
                                    .padding(5.dp)
                                    .clip(RoundedCornerShape(5.dp))
                                    .background(Color.LightGray)

                            ) {
                                CommonNetworkImage(url = chunkedItem.bgPicture?.replaceImageUrl(), modifier = Modifier.fillMaxSize())
                                Text(
                                    text = chunkedItem.name ?: "",
                                    color = Color.White,
                                    fontSize = 20.sp,
                                    modifier = Modifier
                                        .align(Alignment.Center)

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
class CategoryViewModel @Inject constructor(private var api: ApiService) : BaseViewModel() {

    val categoryLiveData = ViewStateMutableLiveData<CategoryDataModel>()

    fun getCategoryData() {
        launchApi(
            liveData = categoryLiveData,
        ) {
            api.getCategoryList()
        }
    }

}
