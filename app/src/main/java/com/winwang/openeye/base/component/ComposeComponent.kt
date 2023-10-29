package com.winwang.openeye.base.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.winwang.openeye.base.lifecycle.ComposeLifeCycleListener
import com.winwang.openeye.base.viewmodel.ViewStateLiveData
import com.winwang.openeye.model.ViewState

/**
 * Created by WinWang on 2023/10/24
 * Description:
 **/
@Composable
fun <T> ComposeComponent(
    modifier: Modifier = Modifier,
    refreshFlag: Int = 0,
    viewStateLiveData: ViewStateLiveData<T>?,
    lifeCycleListener: ComposeLifeCycleListener? = null,
    loadDataBlock: (() -> Unit)? = null,
    specialRetryBlock: (() -> Unit)? = null,
    viewStateComponentModifier: Modifier = Modifier.fillMaxSize(),
    viewStateContentAlignment: Alignment = Alignment.Center,
    customEmptyComponent: @Composable (() -> Unit)? = null,
    customFailComponent: @Composable ((errorMessage: String?) -> Unit)? = null,
    customErrorComponent: @Composable ((errorMessage: Pair<String, Int>) -> Unit)? = null,
    contentView: @Composable BoxScope.(data: T) -> Unit
) {

    // 监听compose生命周期
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

    //加载成功获取的数据
    val successData = remember {
        mutableStateOf<T?>(null)
    }

    // 监听viewStateLiveData
    viewStateLiveData?.let {
        val viewState by viewStateLiveData.observeAsState()
        if (refreshFlag == 0) {
            //只负责渲染数据
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                when (viewState) {
                    //加载中
                    is ViewState.Loading -> {
                        LoadingComponent(
                            modifier = viewStateComponentModifier,
                            contentAlignment = viewStateContentAlignment
                        )
                    }

                    //加载成功
                    is ViewState.Success -> {
                        successData.value = (viewState as ViewState.Success<T>).data
                        successData.value?.let {
                            contentView(it)
                        }
                    }

                    //加载空数据
                    is ViewState.Empty -> {
                        customEmptyComponent?.invoke() ?: LoadFailedComponent(
                            loadDataBlock = loadDataBlock,
                            specialRetryBlock = specialRetryBlock,
                            contentAlignment = viewStateContentAlignment,
                            modifier = viewStateComponentModifier
                        )
                    }

                    //加载失败
                    is ViewState.Failed -> {
                        customFailComponent?.invoke((viewState as ViewState.Failed).errorMsg)
                            ?: LoadFailedComponent(
                                message = "${(viewState as ViewState.Failed).errorMsg} 点我重试",
                                loadDataBlock = loadDataBlock,
                                specialRetryBlock = specialRetryBlock,
                                contentAlignment = viewStateContentAlignment,
                                modifier = viewStateComponentModifier
                            )
                    }

                    //加载错误
                    is ViewState.Error -> {
                        if (customErrorComponent != null) {
                            customErrorComponent.invoke(getErrorMessagePair((viewState as ViewState.Error).exception))
                        } else {
                            val errorMessagePair =
                                getErrorMessagePair((viewState as ViewState.Error).exception)
                            LoadFailedComponent(
                                message = errorMessagePair.first,
                                iconResId = errorMessagePair.second,
                                loadDataBlock = loadDataBlock,
                                specialRetryBlock = specialRetryBlock,
                                contentAlignment = viewStateContentAlignment,
                                modifier = viewStateComponentModifier
                            )
                        }
                    }

                    else -> {
                        loadDataBlock?.invoke()
                    }
                }
            }
        } else {
            LaunchedEffect(refreshFlag) {
                loadDataBlock?.invoke()
            }

            if (viewState is ViewState.Success) {
                successData.value = (viewState as ViewState.Success<T>).data
            }

            successData.value?.let {
                Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    contentView(it)
                }
            }

        }
    }


}