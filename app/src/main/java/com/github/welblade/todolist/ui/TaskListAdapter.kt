package com.github.welblade.todolist.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.welblade.todolist.databinding.ItemTaskBinding
import com.github.welblade.todolist.model.Task

class TaskListAdapter: ListAdapter<Task, TaskListAdapter.ViewHolder>(DiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val item: ItemTaskBinding): RecyclerView.ViewHolder(item.root) {
        @SuppressLint("SetTextI18n")
        fun bind(task: Task) {
            item.tvTitle.text = task.title
            item.tvHour.text = task.date + " " + task.hour
        }

    }

    class DiffCallBack: DiffUtil.ItemCallback<Task>(){
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean
            = oldItem == newItem

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean
            = oldItem.id == newItem.id
    }
}