package com.winwang.openeye.ext

/**
 * Created by WinWang on 2023/10/25
 * Description:String的拓展函数
 **/

/**
 * 替换图片链接
 */
fun String?.replaceImageUrl(): String {
    val regex = """^(https?://)img\.(.*)$""".toRegex()
    val replacement = "$1ali-img.$2"
    return this?.replace(regex, replacement) ?: ""
}







