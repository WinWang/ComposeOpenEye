package com.winwang.openeye.http.apiservice

import com.winwang.openeye.http.model.BaseResult
import com.winwang.openeye.model.FollowDataModel
import com.winwang.openeye.model.HomeDataModel
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by WinWang on 2023/10/25
 * Description:请求接口API-Retrofit接口定义
 **/
interface ApiService {

    /**
     * 获取首页数据
     */
    @GET("api/v2/feed")
    suspend fun getHomeList(
        @Query("num") num: Int,
        @Query("date") date: String
    ): HomeDataModel


    /**
     * 获取发现模块数据
     */
    @GET("api/v4/tabs/follow")
    suspend fun getFollowList(
        @Query("start") num: Int = 1
    ): FollowDataModel

}