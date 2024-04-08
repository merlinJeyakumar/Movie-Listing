package com.example.myapplication.ui.listing

import android.content.res.Configuration
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.addCallback
import androidx.appcompat.widget.SearchView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.R
import com.example.myapplication.data.remote.api.api.base.Status
import com.example.myapplication.databinding.FragmentListingBinding
import com.example.myapplication.support.FirstRowTopMarginDecorator
import com.example.myapplication.support.loadImageFromAssets
import com.example.myapplication.ui.listing.adapter.ListingAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListingFragment : Fragment() {

    private val viewModel: ListingViewModel by viewModels() //initializing viewmodel

    private val binding by lazy {
        return@lazy FragmentListingBinding.inflate(layoutInflater).also {
            it.lifecycleOwner = this@ListingFragment
        }
    } //lazily initializing binding with active inflater

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.also {

            initListener() //view listeners
            initPreview() //rendering previews
        }.root
    }

    private fun initPreview() = with(binding) {
        binding.listingRecyclerView.layoutManager = GridLayoutManager(
            requireContext(),
            if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                3
            } else {
                6
            }
        )
        binding.listingRecyclerView.adapter = listingsAdapter //setting adapter to recyclerview
        listingRecyclerView.addItemDecoration(
            FirstRowTopMarginDecorator(
                TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    100f,
                    resources.displayMetrics
                ).toInt() + resources.getDimension(R.dimen.dimen_36px).toInt()
            )
        )
        status = Status.EMPTY //default state of ui
    }

    private fun initListener() = with(binding) {
        toolbar.menu.findItem(R.id.menuSearch)
            .setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
                override fun onMenuItemActionExpand(p0: MenuItem): Boolean {
                    return true
                }

                override fun onMenuItemActionCollapse(p0: MenuItem): Boolean {
                    initiatePaginationProcess(null)
                    return true
                }
            })
        searchView.maxWidth = android.R.attr.width
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty()) {
                    initiatePaginationProcess(newText)
                }
                return true
            }
        })

        requireActivity().onBackPressedDispatcher.addCallback { //handle search view backpress
            if (menuSearchItemView.isActionViewExpanded) {
                menuSearchItemView.collapseActionView()
            }
        }
        initiatePaginationProcess(null)
    }

    private fun initiatePaginationProcess(
        searchText: String?
    ) {
        binding.status = Status.LOADING
        viewModel.initListingsFactory(searchText)
            .also { (statusLiveData, pagedLiveData) -> //refreshing the pagination query
                statusLiveData.observe(viewLifecycleOwner) {
                    binding.status = it.first
                    binding.title = it.second
                }//listening ui update
                pagedLiveData.observe(viewLifecycleOwner) {
                    listingsAdapter.submitList(it) //populating pagination items
                }
            }
    }

    private val listingsAdapter: ListingAdapter
        get() {
            return ((binding.listingRecyclerView.adapter as? ListingAdapter)
                ?: ListingAdapter())
        } //reusing adapter from recyclerview or initializing adapter

    private val menuSearchItemView: MenuItem
        get() = binding.toolbar.menu.findItem(R.id.menuSearch)

    private val searchView: SearchView
        get() = ((binding.toolbar.menu.findItem(R.id.menuSearch).actionView) as SearchView)

    object BindingLayoutUtils {
        @JvmStatic
        @BindingAdapter("asset")
        fun loadAsset(imageView: ImageView, fileName: String?) {
            fileName?.let {
                imageView.context.loadImageFromAssets(it, imageView)
            }
        }
    }
}