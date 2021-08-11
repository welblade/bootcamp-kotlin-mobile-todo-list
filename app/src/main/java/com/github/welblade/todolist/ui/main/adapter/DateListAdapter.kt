package com.github.welblade.todolist.ui.main.adapter

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
    var selectDateListener: (date: Date) -> Unit = {}
    private var selectedItem: ItemDateBinding? = null
    private var selectedDate: Date? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position) as Date)
    }

    fun selectDate(date: Date){
        selectedDate = date
    }
    fun getSelectedDate(): Date {
        return selectedDate ?: Date()
    }

    inner class ViewHolder(private val item: ItemDateBinding): RecyclerView.ViewHolder(item.root) {
        fun bind(date: Date) {
            item.tvMonth.text = date.format("MMMM")
            item.tvDayOfWeek.text = date.format("EEE")
            item.tvDayOfMonth.text = date.format("dd")
            if(selectedDate != null && selectedDate?.format() == date.format()) {
                selectThisDate()
            } else if(!item.cvRoot.isEnabled) {
                enableDate(item,true)
            }
            item.cvRoot.setOnClickListener {
                selectThisDate()
                selectDateListener(date)

            }
        }
        private fun enableDate(cvItem: ItemDateBinding, isEnable: Boolean) {
            cvItem.apply {
                cvRoot.isEnabled = isEnable
                tvDayOfMonth.isEnabled = isEnable
                tvDayOfWeek.isEnabled = isEnable
            }
        }
        private fun selectThisDate(){
            if(selectedItem.hashCode() != item.hashCode()){
                selectedItem?.let{
                   enableDate(it,true)
                }
            }
            enableDate(item,false)
            selectedItem = item
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<Date>() {
    override fun areItemsTheSame(oldItem: Date, newItem: Date): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Date, newItem: Date): Boolean =
        oldItem == newItem
}
