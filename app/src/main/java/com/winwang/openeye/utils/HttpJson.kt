package com.winwang.openeye.utils

/**
 * Created by WinWang on 2023/10/30
 * Description:
 **/
class HttpJson {

        companion object {
            /**
            * 格式化json字符串
            *
            * @param jsonStr 需要格式化的json串
            * @return 格式化后的json串
            */
            fun formatJson(jsonStr: String): String {
                var level = 0
                val jsonForMatStr = StringBuilder()
                for (i in 0 until jsonStr.length) {
                    val c = jsonStr[i]
                    if (level > 0 && '\n' == jsonForMatStr[jsonForMatStr.length - 1]) {
                        jsonForMatStr.append(getLevelStr(level))
                    }
                    when (c) {
                        '{', '[' -> {
                            jsonForMatStr.append(c).append("\n")
                            level++
                        }
                        ',' -> jsonForMatStr.append(c).append("\n")
                        '}', ']' -> {
                            jsonForMatStr.append("\n")
                            level--
                            jsonForMatStr.append(getLevelStr(level))
                            jsonForMatStr.append(c)
                        }
                        else -> jsonForMatStr.append(c)
                    }
                }
                return jsonForMatStr.toString()
            }

            /**
            * 获取级别字符串
            *
            * @param level
            * @return
            */
            private fun getLevelStr(level: Int): String {
                val levelStr = StringBuilder()
                for (levelI in 0 until level) {
                    levelStr.append("\t")
                }
                return levelStr.toString()
            }
        }
}