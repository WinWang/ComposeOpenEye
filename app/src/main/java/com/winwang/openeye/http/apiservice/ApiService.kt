package com.winwang.openeye.http.apiservice

import com.winwang.openeye.model.CategoryDataModel
import com.winwang.openeye.model.FollowDataModel
import com.winwang.openeye.model.HomeDataModel
import com.winwang.openeye.model.HotDataModel
import com.winwang.openeye.model.TopicDataModel
import com.winwang.openeye.model.VideoDetailDataModel
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
     * 获取关注模块数据
     */
    @GET("api/v4/tabs/follow")
    suspend fun getFollowList(
        @Query("start") num: Int = 1
    ): FollowDataModel


    /**
     * 获取分类数据
     */
    @GET("api/v4/categories")
    suspend fun getCategoryList(): CategoryDataModel


    /**
     * 获取专题数据
     */
    @GET("api/v3/specialTopics")
    suspend fun getTopicList(@Query("start") num: Int = 1): TopicDataModel


    /**
     * 获取热门数据
     */
    @GET("api/v4/rankList/videos")
    suspend fun getRankList(@Query("strategy") strategy: String): HotDataModel


    /**
     * 获取关联视频数据
     */
    @GET("api/v4/video/related")
    suspend fun getRelatedVideoList(@Query("id") id: Int): VideoDetailDataModel


}