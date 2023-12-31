package com.winwang.openeye.http.interceptor

import com.blankj.utilcode.util.LogUtils
import com.winwang.openeye.utils.HttpJson
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Created by WinWang on 2023/10/30
 * Description:
 **/
class HttpInterceptorLog : HttpLoggingInterceptor.Logger {

    var mMessage = StringBuilder()

    override fun log(message: String) {
        var messageTemp = message
        // 请求或者响应开始
        // 请求或者响应开始
        if (messageTemp.startsWith("--> POST")) {
            mMessage.setLength(0)
        }
        // 以{}或者[]形式的说明是响应结果的json数据，需要进行格式化
        if (messageTemp.startsWith("{") && messageTemp.endsWith("}")
            || messageTemp.startsWith("[") && messageTemp.endsWith("]")
        ) {
            messageTemp = HttpJson.formatJson(messageTemp)
        }

        mMessage.append(
            """
                $messageTemp

                """.trimIndent()
        )
        // 请求或者响应结束，打印整条日志
        if (message.startsWith("<-- END HTTP")) {
            LogUtils.d("网络日志>>>${mMessage.toString()}")
        }
    }
}