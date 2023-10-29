package com.winwang.openeye.ext

/**
 * Created by WinWang on 2023/10/24
 * Description:通用拓展函数
 **/

/**
 * 判断是否为空 并传入相关操作
 */
inline fun <reified T> T?.notNull(notNullAction: (T) -> Unit, nullAction: () -> Unit = {}) {
    if (this != null) {
        notNullAction.invoke(this)
    } else {
        nullAction.invoke()
    }
}