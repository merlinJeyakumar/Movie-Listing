package com.example.myapplication.domain.api

import com.example.myapplication.data.remote.api.api.base.Resource
import com.example.myapplication.data.remote.api.api.models.ListingsModel

interface IListingsApi {

    suspend fun searchListings(
        keyword: String?,
        page: Int
    ): Resource<ListingsModel> //retrieve the listings with search keyword

    suspend fun getListings(
        keyword: String?,
        page: Int
    ): Resource<ListingsModel> //retrieve the listings
}