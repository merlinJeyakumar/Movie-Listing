package com.example.myapplication.ui.listing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.myapplication.data.remote.api.api.base.Status
import com.example.myapplication.data.remote.api.api.models.ListingsModel
import com.example.myapplication.domain.api.IListingsApi
import com.example.myapplication.ui.listing.datasource.ListingItemsDataSourceFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.concurrent.Executors
import javax.inject.Inject

@HiltViewModel
class ListingViewModel @Inject constructor(private val serviceClient: IListingsApi) :
    ViewModel() {

    fun initListingsFactory(keyword: String?): Pair<LiveData<Pair<Status, String?>>, LiveData<PagedList<ListingsModel.Page.ContentItems.Content>>> {
        val config = PagedList.Config.Builder().setEnablePlaceholders(true)
            .setInitialLoadSizeHint(10)
            .setPageSize(10)
            .setPrefetchDistance(7)
            .build() //initializing page configuration with minimal settings

        val listingsDataSourceFactory = ListingItemsDataSourceFactory(
            serviceClient,
            keyword
        ) //passing rest service and searching query

        val executor = Executors.newFixedThreadPool(5)


        return listingsDataSourceFactory.statusLiveData.switchMap { it.loadStateLiveData } to LivePagedListBuilder<Int, ListingsModel.Page.ContentItems.Content>(
            listingsDataSourceFactory, config
        ).setFetchExecutor(executor).build().map {
            it
        }
    }

}