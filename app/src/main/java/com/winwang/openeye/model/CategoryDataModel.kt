package com.winwang.openeye.model

import com.winwang.openeye.http.model.IResultBean


class CategoryDataModel : ArrayList<CategoryModelItem>(), IResultBean<CategoryDataModel> {

    override fun httpData(): CategoryDataModel? {
        return this
    }

}


data class CategoryModelItem(
    val alias: Any?,
    val bgColor: String?,
    val bgPicture: String?,
    val defaultAuthorId: Int?,
    val description: String?,
    val headerImage: String?,
    val id: Int?,
    val name: String?,
    val tagId: Int?
)
