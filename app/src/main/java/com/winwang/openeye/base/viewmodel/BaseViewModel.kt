package com.winwang.openeye.base.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataScope
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.LogUtils
import com.winwang.openeye.base.lifecycle.AppLifecycleObserver
import com.winwang.openeye.http.model.IResultBean
import com.winwang.openeye.model.ViewState
import kotlinx.coroutines.launch

/**
 * Created by WinWang on 2023/10/23
 * Description:BaseViewModel的基类
 **/
typealias ViewStateMutableLiveData<T> = MutableLiveData<ViewState<T>>
typealias ViewStateLiveData<T> = LiveData<ViewState<T>>
typealias Block<T> = suspend () -> T     //可以在别名类指定类型，例如suspend CoroutineScope.() -> Unit -----但是此时的block不需要调用invoke 了，直接block（）
typealias Error = suspend (e: Exception) -> Unit
typealias Cancel = suspend (e: Exception) -> Unit
typealias EmitBlock<T> = suspend LiveDataScope<T>.() -> T

open class BaseViewModel : ViewModel(), AppLifecycleObserver {


    /**
     * 创建请求接口API的协程
     */
    fun <T> launchApi(
        liveData: ViewStateMutableLiveData<T>,
        isLoadMore: Boolean = false,
        judgeEmpty: ((T) -> Boolean)? = null,
        handleResult: ((T) -> Unit)? = null,
        block: suspend () -> IResultBean<T>
    ) {
        viewModelScope.launch {
            runCatching {
                liveData.value = ViewState.Loading(true)
                block()
            }.onSuccess { result ->
                if (result.requestOk()) {
                    val httpData = result.httpData()
                    if (httpData == null || judgeEmpty?.invoke(httpData) == true) {
                        liveData.value = ViewState.Empty(true)
                    } else {
                        handleResult?.invoke(httpData)
                        liveData.value = ViewState.Success(httpData, isLoadMore)
                    }
                } else {
                    liveData.value = ViewState.Failed(true, result.httpCode(), result.httpMsg())
                }
            }.onFailure { e ->
                liveData.value = ViewState.Failed(true, "-1", e.message ?: "未知错误")
            }
        }
    }


    override fun onCreate(source: LifecycleOwner) {
        LogUtils.d("ViewModel", "onCreate>>>>")
    }

    override fun onPause(source: LifecycleOwner) {
        LogUtils.d("ViewModel", "onPause>>>>")
    }

    override fun onResume(source: LifecycleOwner) {
        LogUtils.d("ViewModel", "onResume>>>>")
    }

    override fun onDestroy(source: LifecycleOwner) {
        LogUtils.d("ViewModel", "onDestroy>>>>")
    }

}