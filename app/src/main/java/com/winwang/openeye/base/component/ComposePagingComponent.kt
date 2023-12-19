package com.winwang.openeye.base.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.winwang.openeye.R
import com.winwang.openeye.base.lifecycle.ComposeLifeCycleListener
import com.winwang.openeye.ext.logD
import com.winwang.openeye.ext.notNull
import kotlinx.coroutines.flow.Flow

/**
 * Created by WinWang on 2023/11/10
 * Description:
 **/
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T : Any> ComposePagingComponent(
    modifier: Modifier = Modifier,
    key: String = "",
    enableRefresh: Boolean = true,
    showNoMoreDataFooter: Boolean = true,
    loadDataBlock: () -> Flow<PagingData<T>>,
    retryBlock: (() -> Unit)? = null,
    lazyListState: LazyListState = rememberLazyListState(),
    lazyListContentPadding: PaddingValues = PaddingValues(0.dp),
    refreshBlock: (() -> Unit)? = null,
    lifeCycleListener: ComposeLifeCycleListener? = null,
    customEmptyComponent: @Composable (() -> Unit)? = null,
    customFailComponent: @Composable (() -> Unit)? = null,
    listContent: LazyListScope.(collectAsLazyPagingItems: LazyPagingItems<T>) -> Unit = {},
) {
    //1、监听compose生命周期
    lifeCycleListener?.let { listener ->
        val lifecycleOwner = LocalLifecycleOwner.current
        DisposableEffect(Unit) {
            listener.onEnterCompose(lifecycleOwner)

            val lifecycleEventObserver = LifecycleEventObserver { _, event ->
                when (event) {
                    Lifecycle.Event.ON_CREATE -> {
                        listener.onCreate(lifecycleOwner)
                    }

                    Lifecycle.Event.ON_START -> {
                        listener.onStart(lifecycleOwner)
                    }

                    Lifecycle.Event.ON_RESUME -> {
                        listener.onResume(lifecycleOwner)
                    }

                    Lifecycle.Event.ON_PAUSE -> {
                        listener.onPause(lifecycleOwner)
                    }

                    Lifecycle.Event.ON_STOP -> {
                        listener.onStop(lifecycleOwner)
                    }

                    Lifecycle.Event.ON_DESTROY -> {
                        listener.onDestroy(lifecycleOwner)
                    }

                    else -> {}
                }
            }

            lifecycleOwner.lifecycle.addObserver(lifecycleEventObserver)

            onDispose {
                listener.onExitCompose(lifecycleOwner)
                lifecycleOwner.lifecycle.removeObserver(lifecycleEventObserver)
            }
        }
    }

    //2、页面布局
    val pagingStateHolder: PagingStateHolderViewModel<T> = hiltViewModel(key = key)
    var pagingItems = pagingStateHolder.getPagingDataFlow(loadDataBlock).collectAsLazyPagingItems()
    val refreshState = pagingStateHolder.refreshState
    if (pagingStateHolder.showViewState.value) {
        handleStateComponent(
            collectAsLazyPagingItems = pagingItems,
            showViewState = pagingStateHolder.showViewState,
            retryBlock = retryBlock,
            viewStateContentAlignment = Alignment.Center,
            customEmptyComponent = customEmptyComponent,
            customFailComponent = customFailComponent
        )
    } else {
        SwipeRefresh(
            state = refreshState,
            swipeEnabled = enableRefresh,
            onRefresh = {
                refreshState.isRefreshing = true
                refreshBlock?.notNull(
                    notNullAction = {
                        refreshBlock.invoke()
                        "外部刷新".logD()
                    },
                    nullAction = {
//                        refreshState.isRefreshing = true
                        pagingItems.refresh()
                        "内部刷新".logD()
                    }
                )
            }
        ) {
            if (pagingItems.itemCount == 0) {
                customEmptyComponent?.invoke()
            } else {
                CompositionLocalProvider(LocalOverscrollConfiguration.provides(null)) {
                    LazyColumn(
                        modifier = modifier,
                        contentPadding = lazyListContentPadding,
                        state = lazyListState
                    ) {
                        listContent(pagingItems)
                    }
                }
            }
        }
    }


}


/**
 * 生成列表的状态控制页面
 */
@Composable
private fun <T : Any> handleStateComponent(
    collectAsLazyPagingItems: LazyPagingItems<T>,
    showViewState: MutableState<Boolean>,
    retryBlock: (() -> Unit)? = null,
    viewStateContentAlignment: Alignment = Alignment.Center,
    customEmptyComponent: @Composable (() -> Unit)? = null,
    customFailComponent: @Composable (() -> Unit)? = null,
) {
    var hasShowLoadState by remember {
        mutableStateOf(false)
    }
    collectAsLazyPagingItems.apply {
        when (loadState.refresh) {
            is LoadState.Error -> {
                // 首次加载异常
                val errorMessagePair = getErrorMessagePair((loadState.refresh as LoadState.Error).error)

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    customFailComponent?.invoke()
                        ?: LoadFailedComponent(message = errorMessagePair.first,
                            iconResId = errorMessagePair.second,
                            contentAlignment = viewStateContentAlignment,
                            specialRetryBlock = retryBlock,
                            loadDataBlock = { collectAsLazyPagingItems.retry() })
                }
            }

            is LoadState.NotLoading -> {
                if (collectAsLazyPagingItems.itemCount == 0 && hasShowLoadState) {
                    // 首次加载数据为null
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        customEmptyComponent?.invoke()
                            ?: LoadFailedComponent(message = "暂无数据展示",
                                iconResId = R.drawable.common_empty_img,
                                contentAlignment = viewStateContentAlignment,
                                specialRetryBlock = retryBlock,
                                loadDataBlock = { collectAsLazyPagingItems.refresh() })
                    }
                } else if (collectAsLazyPagingItems.itemCount > 0) {
                    showViewState.value = false
                }
            }

            is LoadState.Loading -> {
                if (collectAsLazyPagingItems.itemCount <= 0) {
                    // 首次加载数据中
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        LoadingComponent(contentAlignment = viewStateContentAlignment)
                    }
                    hasShowLoadState = true
                }
            }
        }
    }

}


/**
 * 底部加载更多失败处理
 * */
@Composable
private fun LoadMoreDataErrorFooter(retry: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp), contentAlignment = Alignment.Center
    ) {
        Text(text = "--加载失败,点击重试--",
            color = Color(0xff666666),
            modifier = Modifier.clickable {
                retry.invoke()
            })
    }
}


/**
 * 列表没有更多数据footer
 */
@Composable
private fun NoMoreDataFooter() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "--没有更多数据啦--",
            color = Color(0xff666666)
        )
    }
}

class PagingStateHolderViewModel<T : Any> : ViewModel() {

    // 刷新状态，记录在PagingStateHolderViewModel中，避免结合horizontalPager切换tab时，由于页面重建，导致refreshState状态丢失
    var refreshState = SwipeRefreshState(false)

    // 分页数据源
    private var pagingDataFlow: Flow<PagingData<T>>? = null

    // 首次进入该组件，数据还没加载成功，显示状态页面
    val showViewState = mutableStateOf(true)

    // 标记进入该组件，LoadState.Loading是否执行过了
    val hasLoadingDone = mutableStateOf(false)

    fun getPagingDataFlow(loadPagingDataFlowBlock: () -> Flow<PagingData<T>>): Flow<PagingData<T>> {
        if (pagingDataFlow == null) {
            pagingDataFlow = loadPagingDataFlowBlock.invoke()
        }
        return pagingDataFlow ?: throw NullPointerException("pagingDataFlow is null")
    }

}

