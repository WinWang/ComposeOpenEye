package com.winwang.openeye.http

import com.google.gson.Gson
import com.winwang.openeye.constant.AppConfig
import com.winwang.openeye.http.interceptor.HttpInterceptorLog
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.Proxy
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by WinWang on 2022/5/6
 * Description->
 */
@Module
@InstallIn(SingletonComponent::class)
object HttpManager {

    @Provides
    @Singleton
    fun provideRetrofitClient(
        sslSocketFactoryParams: HttpSSLUtils.SSLParams
    ): RetrofitManager {
        val proxyStatus = AppConfig.isProxy()
        val builder = OkHttpClient.Builder().apply {
            writeTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            proxy(if (proxyStatus) null else Proxy.NO_PROXY)
            connectTimeout(30, TimeUnit.SECONDS)
                .addNetworkInterceptor(
                    HttpLoggingInterceptor(HttpInterceptorLog())
                        .setLevel(if (AppConfig.isDebug()) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)
                )
            sslSocketFactoryParams.sSLSocketFactory?.let {
                sslSocketFactoryParams.trustManager?.let { it1 ->
                    sslSocketFactory(it, it1).addInterceptor(
                        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
                }
            }
        }
        return RetrofitManager(
            Retrofit.Builder().client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
        )
    }

    @Provides
    @Singleton
    fun provideSsl(): HttpSSLUtils.SSLParams {
        //无证书校验，全信任
        return HttpSSLUtils.getSslSocketFactory(null, null, null)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }


}