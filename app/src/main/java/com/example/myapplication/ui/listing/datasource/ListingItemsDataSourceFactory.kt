package com.example.myapplication.ui.listing.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.myapplication.data.remote.api.api.models.ListingsModel
import com.example.myapplication.domain.api.IListingsApi


class ListingItemsDataSourceFactory(
    private val serviceClient: IListingsApi,
    private val keyword: String?
) : DataSource.Factory<Int, ListingsModel.Page.ContentItems.Content>() {

    val statusLiveData: MutableLiveData<ListingItemsDataSource> =
        MutableLiveData() //live data used to update status

    override fun create(): DataSource<Int, ListingsModel.Page.ContentItems.Content> {
        return ListingItemsDataSource(
            serviceClient,
            keyword
        ).also {
            statusLiveData.postValue(it)
        } //initializing data source and attaching status livedata
    }
}