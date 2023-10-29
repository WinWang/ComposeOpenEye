package com.winwang.openeye.apiservice

import com.winwang.openeye.http.BaseResult
import retrofit2.http.Field
import retrofit2.http.GET

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
        @Field("num") num: Int,
        @Field("date") date: String
    ): BaseResult<Any>

}