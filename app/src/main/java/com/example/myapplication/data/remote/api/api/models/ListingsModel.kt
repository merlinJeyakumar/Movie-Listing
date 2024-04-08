package com.example.myapplication.data.remote.api.api.models


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ListingsModel(
    @SerializedName("page")
    val page: Page
) {
    @Keep
    data class Page(
        @SerializedName("content-items")
        var contentItems: ContentItems,
        @SerializedName("page-num")
        val pageNum: String,
        @SerializedName("page-size")
        val pageSize: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("total-content-items")
        val totalContentItems: String
    ) {
        @Keep
        data class ContentItems(
            @SerializedName("content")
            var content: List<Content>
        ) {
            @Keep
            data class Content(
                @SerializedName("name")
                val name: String,
                @SerializedName("poster-image")
                val posterImage: String
            )
        }
    }
}