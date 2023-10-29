package com.winwang.openeye.http

/**
 * Created by WinWang on 2023/10/24
 * Description:
 **/
data class BaseResult<T>(
    var data: T?,
    var errorCode: Int,
    var errorMsg: String
) : IResultBean<T> {
    override fun httpCode(): String {
        return errorCode.toString()
    }

    override fun httpMsg(): String {
        return errorMsg
    }

    override fun httpData(): T? {
        return data
    }

    override fun requestOk(): Boolean {
        return errorCode == 0
    }
}