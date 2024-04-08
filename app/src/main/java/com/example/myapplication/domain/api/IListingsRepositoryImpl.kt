package com.example.myapplication.domain.api

import android.content.Context
import com.example.myapplication.data.remote.api.api.base.Resource
import com.example.myapplication.data.remote.api.api.models.ListingsModel
import com.example.myapplication.support.readJsonFromAssets
import com.google.gson.Gson


class IListingsRepositoryImpl(private val context: Context) : IListingsApi {
    override suspend fun searchListings(keyword: String?, page: Int): Resource<ListingsModel> {
        return try {
            val contentListingPage = "CONTENTLISTINGPAGE-PAGE$page.json"
            val listingsModel = Gson().fromJson(
                context.readJsonFromAssets(contentListingPage),
                ListingsModel::class.java
            )
            listingsModel.page.contentItems.content = listingsModel.page.contentItems.content.filter { it.name.contains(keyword ?: "",true) }
            Resource.success(listingsModel)
        } catch (ex: Throwable) {
            Resource.error<ListingsModel>("${ex.message}")
        }
    }

    override suspend fun getListings(keyword: String?, page: Int): Resource<ListingsModel> {
        return try {
            val contentListingPage = "CONTENTLISTINGPAGE-PAGE$page.json"
            val listingsModel = Gson().fromJson(
                context.readJsonFromAssets(contentListingPage),
                ListingsModel::class.java
            )
            Resource.success(listingsModel)
        } catch (ex: Throwable) {
            Resource.error<ListingsModel>("${ex.message}")
        }
    }
}
