package com.winwang.openeye.constant

/**
 * Created by WinWang on 2022/5/6
 * Description->
 */
object AppConfig {

    /**
     * 调试模式
     */
    private var DEBUG = true

    /**
     * 能否抓包
     */
    private var PROXY = false

    fun isDebug(): Boolean {
        return DEBUG
    }

    fun isProxy(): Boolean {
        return PROXY
    }

}