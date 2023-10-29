package com.winwang.openeye.http

/**
 * Created by WinWang on 2023/10/24
 * Description:返回结果接口
 **/
interface IResultBean<T> {
    fun httpCode(): String
    fun httpMsg(): String
    fun httpData(): T?
    fun requestOk(): Boolean
    fun requestSpecialEmpty(): Boolean = false
}