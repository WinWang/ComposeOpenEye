@file:OptIn(ExperimentalPagerApi::class, ExperimentalPagerApi::class)

package com.winwang.openeye.ui.page.find

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.winwang.openeye.base.viewmodel.BaseViewModel
import com.winwang.openeye.widget.CommonTopAppBar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by WinWang on 2023/10/25
 * Description:发现页面-承载容器
 **/
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FindPage(
    viewModel: FindViewModel = hiltViewModel()
) {
    val titles = viewModel.tabTitle
    var isLoaded by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = 3, initialPage = 0, initialOffscreenLimit = 3)
    LaunchedEffect(Unit) {
        if (!isLoaded) {
            isLoaded = true
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        CommonTopAppBar(title = "发现", showBackButton = false)
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            modifier = Modifier
                .background(Color.Red)
                .fillMaxWidth()
                .height(50.dp),
            contentColor = Color.White,
            containerColor = Color.Red,
            indicator = { positions ->
                Box(
                    Modifier
                        .fillMaxWidth()
                        .tabIndicatorOffset(positions[pagerState.currentPage])
                        .height(5.dp)
                        .padding(horizontal = 20.dp)
                        .background(color = Color.White, shape = RoundedCornerShape(5.dp))
                )
            }
        ) {
            titles.forEachIndexed { index, title ->
                Tab(
                    modifier = Modifier
                        .fillMaxHeight()
                        .height(50.dp),
                    selected = index == pagerState.currentPage,
                    onClick = { scope.launch { pagerState.scrollToPage(index) } },
                    selectedContentColor = Color.Green,
                    unselectedContentColor = Color.Gray,
                ) {
                    Text(
                        text = title,
                        color = Color.White,
                        fontSize = if (index == pagerState.currentPage) 16.sp else 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
        ) { page ->
            when (page) {
                0 -> FocusPage()
                1 -> CategoryPage()
                2 -> TopicPage()
            }
        }
    }


}

@HiltViewModel
class FindViewModel @Inject constructor() : BaseViewModel() {

    var tabTitle by mutableStateOf(emptyList<String>())

    init {
        tabTitle = listOf("关注", "分类", "专题")
    }

}
