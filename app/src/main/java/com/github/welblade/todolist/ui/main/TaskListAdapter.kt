package com.github.welblade.todolist.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.welblade.todolist.R
import com.github.welblade.todolist.databinding.ItemTaskBinding
import com.github.welblade.todolist.model.Task
import java.util.*

class TaskListAdapter: ListAdapter<Task, TaskListAdapter.ViewHolder>(DiffCallBack()) {
    var editTaskListener: (Task) -> Unit = {}
    var removeTaskListener: (Task) -> Unit = {}

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

    inner class ViewHolder(private val item: ItemTaskBinding): RecyclerView.ViewHolder(item.root) {
        @SuppressLint("SetTextI18n")
        fun bind(task: Task) {
            item.tvTitle.text = task.title
            item.tvDescription.text = task.description
            item.tvHour.text = task.hour
            item.ivMore.setOnClickListener {
                showPopUp(task)
            }
        }
        private fun showPopUp(task: Task) {
            val ivMore = item.ivMore
            val popupMenu = PopupMenu(ivMore.context, ivMore)
            popupMenu.menuInflater.inflate(R.menu.menu_task_pop_up, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.it_edit_task -> editTaskListener(task)
                    R.id.it_remove_task -> removeTaskListener(task)
                }
                return@setOnMenuItemClickListener true
            }
            popupMenu.show()
        }
    }

    class DiffCallBack: DiffUtil.ItemCallback<Task>(){
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean
            = oldItem == newItem

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean
            = oldItem.id == newItem.id
                && oldItem.date == newItem.date
                && oldItem.hour == newItem.hour
                && oldItem.title == newItem.title
                && oldItem.description == newItem.description
    }
}