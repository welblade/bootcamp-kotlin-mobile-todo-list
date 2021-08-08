package com.github.welblade.todolist.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.welblade.todolist.databinding.ItemDateBinding
import com.github.welblade.todolist.extensions.format
import java.util.*

class DateListAdapter: PagingDataAdapter<Date, DateListAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDateBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position) as Date)
    }

    class ViewHolder(private val item: ItemDateBinding): RecyclerView.ViewHolder(item.root) {
        fun bind(date: Date){
            item.tvDayOfWeek.text = date.format("EEE")
            item.tvDayOfMonth.text = date.format("dd")
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<Date>() {
    override fun areItemsTheSame(oldItem: Date, newItem: Date): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Date, newItem: Date): Boolean =
        oldItem == newItem
}
