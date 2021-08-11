package com.github.welblade.todolist.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.github.welblade.todolist.App
import com.github.welblade.todolist.R
import com.github.welblade.todolist.databinding.ActivityMainBinding
import com.github.welblade.todolist.extensions.format
import com.github.welblade.todolist.ui.form_task.FormTaskActivity
import com.google.android.material.card.MaterialCardView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val adapter: TaskListAdapter by lazy { TaskListAdapter() }

    private val dateViewModel: DateListViewModel by lazy {
        DateListViewModel(Date())
    }
    private lateinit var taskListViewModel: TaskListViewModel

    private val dateAdapter: DateListAdapter by lazy { DateListAdapter() }
    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    )
    { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            taskListViewModel.getList(dateAdapter.getSelectedDate())
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //val taskDataSource = TaskDataSourceInMemoryImpl
        //val taskRepository = TaskRepository(taskDataSource)
        taskListViewModel = TaskListViewModel((application as App).repository)
        initRVDate(Date())
        initTaskList()
        insertListeners()
    }

    private fun initTaskList(){
        binding.rvTaskList.adapter = adapter

        taskListViewModel.taskList.observe(this, {
            list -> run {
                if (list.isEmpty()) {
                    binding.emptyStateLayout.root.visibility = View.VISIBLE
                } else {
                    binding.emptyStateLayout.root.visibility = View.GONE
                }
                adapter.submitList(list)
            }
        })
    }
    private fun initRVDate(date: Date){
        binding.rvDateList.apply {
            setHasFixedSize(true)
            adapter = dateAdapter
            addItemDecoration(HorizontalSpaceItemDecorator(8))
        }
        initDateList(date)
    }
    private fun initDateList(date: Date){
        dateViewModel.dateRangeFrom(date)
        dateAdapter.selectDate(date)
        lifecycleScope.launch {
            dateViewModel.dateList.collectLatest { source -> dateAdapter.submitData(source) }
        }
    }

    private fun insertListeners() {
        binding.btCreateTask.setOnClickListener {
            val createTaskIntent = Intent(this, FormTaskActivity::class.java)
            createTaskIntent.putExtra(FormTaskActivity.TASK_DATE, dateAdapter.getSelectedDate().format())
            startForResult.launch(createTaskIntent)
        }
        adapter.editTaskListener = {
            val editTaskIntent = Intent(this, FormTaskActivity::class.java)
            editTaskIntent.putExtra(FormTaskActivity.TASK_ID, it.id)
            startForResult.launch(editTaskIntent)
        }
        adapter.removeTaskListener = {
            taskListViewModel.remove(it)
            taskListViewModel.getList(dateAdapter.getSelectedDate())
        }
        dateAdapter.selectDateListener = {
            dateAdapter.selectDate(it)
            taskListViewModel.getList(it)
        }
        binding.rvDateList.addOnChildAttachStateChangeListener( object: RecyclerView.OnChildAttachStateChangeListener{
            override fun onChildViewAttachedToWindow(view: View) {
                val item = view as MaterialCardView
                val month = item.findViewById<TextView>(R.id.tv_month).text
                if(month != binding.tvDateListTitle.text)
                    binding.tvDateListTitle.text = month
            }
            override fun onChildViewDetachedFromWindow(view: View) {}
        })
    }
}