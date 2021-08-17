package com.library.dialogutils.searchdialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.library.dialogutils.R
import com.library.dialogutils.databinding.SearchItemLayoutBinding

internal class SearchRecyclerViewAdapter(
    private val itemClickCallback: ((SearchEntry) -> Unit)? = null
) : ListAdapter<SearchEntry, SearchRecyclerViewAdapter.ViewHolder>(object :
    DiffUtil.ItemCallback<SearchEntry>() {
    override fun areItemsTheSame(oldItem: SearchEntry, newItem: SearchEntry): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SearchEntry, newItem: SearchEntry): Boolean {
        return oldItem == newItem
    }
}) {

    inner class ViewHolder(val binding: SearchItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.txtViewDropDown.setOnClickListener {
                itemClickCallback?.invoke(getItem(adapterPosition))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: SearchItemLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.search_item_layout,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.txtViewDropDown.text = getItem(position).name
        holder.binding.executePendingBindings()
    }


}