package com.winwang.openeye.model

/**
 * Created by WinWang on 2023/10/23
 * Description:页面状态字段封装
 **/
sealed class ViewState<out T> {
    data class HasInit(val hasInit: Boolean) : ViewState<Boolean>()
    data class Loading(val isLoading: Boolean) : ViewState<Nothing>()
    data class Success<T>(val data: T, val isLoadMore: Boolean) : ViewState<T>()
    data class Empty(val needShow: Boolean) : ViewState<Nothing>()
    data class Failed(val needShow: Boolean, val errorCode: String?, val errorMsg: String?) : ViewState<Nothing>() //请求200但是code不ok
    data class Error(val needShow: Boolean, val exception: Throwable) : ViewState<Nothing>()
}