package com.example.myapplication.ui.listing.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.remote.api.api.models.ListingsModel.Page.ContentItems.Content
import com.example.myapplication.databinding.ItemListingBinding
import splitties.systemservices.layoutInflater


class ListingAdapter :
    PagedListAdapter<Content, ListingAdapter.ListingViewHolder>(
        ListingDiffCallback
    ) {

    class ListingViewHolder(
        private val binding: ItemListingBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            content: Content
        ) = with(binding) {
            contentModel = content
        }
    }

    /* Creates and inflates view and return ItemListingViewHolder. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListingViewHolder {
        return ListingViewHolder(
            ItemListingBinding.inflate(parent.context.layoutInflater, parent, false)
        )
    }

    /* Gets current `Content` and uses it to bind view. */
    override fun onBindViewHolder(holder: ListingViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}

object ListingDiffCallback :
    DiffUtil.ItemCallback<Content>() {
    override fun areItemsTheSame(
        oldItem: Content,
        newItem: Content
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: Content,
        newItem: Content
    ): Boolean {
        return oldItem.name == newItem.name
    }
}