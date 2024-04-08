package com.example.myapplication.domain.api

import android.content.Context
import com.example.myapplication.data.remote.api.api.base.Resource
import com.example.myapplication.data.remote.api.api.models.ListingsModel
import com.example.myapplication.support.readJsonFromAssets
import com.google.gson.Gson


class IListingsRepositoryImpl(private val context: Context) : IListingsApi {
    override suspend fun searchListings(keyword: String?, page: Int): Resource<ListingsModel> {
        return try {
            val contentListingPage = "CONTENTLISTINGPAGE-PAGE$page.json" //assembling filename
            val listingsModel = Gson().fromJson(
                context.readJsonFromAssets(contentListingPage),
                ListingsModel::class.java
            ) //converting into data class object using gson
            listingsModel.page.contentItems.content = listingsModel.page.contentItems.content.filter { it.name.contains(keyword ?: "",true) } //filtering with keyword
            Resource.success(listingsModel) //sending filtered data
        } catch (ex: Throwable) {
            Resource.error<ListingsModel>("${ex.message}") //catching error
        }
    }

    override suspend fun getListings(keyword: String?, page: Int): Resource<ListingsModel> {
        return try {
            val contentListingPage = "CONTENTLISTINGPAGE-PAGE$page.json"
            val listingsModel = Gson().fromJson(
                context.readJsonFromAssets(contentListingPage),
                ListingsModel::class.java
            )
            Resource.success(listingsModel) //sending parsed data
        } catch (ex: Throwable) {
            Resource.error<ListingsModel>("${ex.message}")
        }
    }
}
