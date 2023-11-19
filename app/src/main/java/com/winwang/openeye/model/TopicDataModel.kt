package com.winwang.openeye.model

import com.winwang.openeye.http.model.IResultBean

data class TopicDataModel(
    val adExist: Boolean?,
    val count: Int?,
    val itemList: List<Item>?,
    val nextPageUrl: String?,
    val total: Int?
) : IResultBean<TopicDataModel> {
    data class Item(
        val adIndex: Int?,
        val `data`: Data?,
        val id: Int?,
        val tag: Any?,
        val trackingData: Any?,
        val type: String?
    ) {
        data class Data(
            val actionUrl: String?,
            val adTrack: List<Any?>?,
            val autoPlay: Boolean?,
            val dataType: String?,
            val description: String?,
            val header: Any?,
            val id: Int?,
            val image: String?,
            val label: Label?,
            val labelList: List<Any?>?,
            val shade: Boolean?,
            val title: String?
        ) {
            data class Label(
                val card: String?,
                val detail: Any?,
                val text: String?
            )
        }
    }

    override fun httpData(): TopicDataModel? {
        return this
    }
}