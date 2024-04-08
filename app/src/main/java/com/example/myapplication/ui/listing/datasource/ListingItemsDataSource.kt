package com.example.myapplication.ui.listing.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.myapplication.data.remote.api.api.base.Status
import com.example.myapplication.data.remote.api.api.models.ListingsModel
import com.example.myapplication.domain.api.IListingsApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class ListingItemsDataSource(
    private val serviceClient: IListingsApi,
    private val keyword: String?
) : PageKeyedDataSource<Int, ListingsModel.Page.ContentItems.Content>() {

    private val dataSourceJob = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Main + dataSourceJob)
    val loadStateLiveData: MutableLiveData<Pair<Status, String?>> = MutableLiveData()

    companion object {
        const val PAGE_SIZE = 15
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ListingsModel.Page.ContentItems.Content>
    ) {
        scope.launch {
            loadStateLiveData.postValue(Status.LOADING to null)

            val response = serviceClient.getListings(
                keyword,
                1
            ) //get first page with keyword search
            when (response.status) {
                Status.ERROR -> loadStateLiveData.postValue(Status.ERROR to null)
                Status.EMPTY -> loadStateLiveData.postValue(Status.EMPTY to null)
                else -> {
                    response.data?.let {
                        callback.onResult(it.page.contentItems.content, null, 2)
                        loadStateLiveData.postValue(Status.SUCCESS to it.page.title)
                    }
                }
            } //to update a status
        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, ListingsModel.Page.ContentItems.Content>
    ) {
        scope.launch {
            val response = serviceClient.getListings(
                keyword,
                params.key
            ) //get next page with keyword search
            response.data?.let {
                callback.onResult(it.page.contentItems.content, params.key + 1)
            }
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, ListingsModel.Page.ContentItems.Content>
    ) {

    }
}