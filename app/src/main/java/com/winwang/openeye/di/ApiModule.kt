package com.winwang.openeye.di

import com.winwang.openeye.http.apiservice.ApiService
import com.winwang.openeye.http.url.HttpUrl
import com.winwang.openeye.http.RetrofitManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by WinWang on 2023/10/25
 * Description:依赖注入的网络请求模块
 **/

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun provideApiService(retrofit: RetrofitManager): ApiService {
        return retrofit.setBaseUrl(HttpUrl.BaseUrl).create(ApiService::class.java)
    }

}