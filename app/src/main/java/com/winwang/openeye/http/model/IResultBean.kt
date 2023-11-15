package com.winwang.openeye.http.model

import com.winwang.openeye.model.HomeDataModel

/**
 * Created by WinWang on 2023/10/24
 * Description:返回结果接口
 **/
interface IResultBean<T> {
    fun httpCode(): String = "0"
    fun httpMsg(): String = ""
    fun httpData(): T?
    fun requestOk(): Boolean = true
    fun requestSpecialEmpty(): Boolean = false
}