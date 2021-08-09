package com.github.welblade.todolist.ui.main

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.github.welblade.todolist.data.TaskDataSource
import com.github.welblade.todolist.databinding.ActivityMainBinding
import com.github.welblade.todolist.ui.form_task.FormTaskActivity
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
    private val dateAdapter: DateListAdapter by lazy { DateListAdapter() }
    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    )
    { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            initTaskList()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initDateList(Date())
        initTaskList()
        insertListeners()
    }

    private fun initTaskList(){
        binding.rvTaskList.adapter = adapter
        val list = TaskDataSource.getList()
        binding.emptyStateLayout.root.visibility =
            if(list.isEmpty()) View.VISIBLE else View.GONE
        adapter.submitList(list)
    }

    private fun initDateList(date: Date){
        dateViewModel.dateRangeFrom(date)
        dateAdapter.selectDate(date)
        binding.rvDateList.apply {
            setHasFixedSize(true)
            adapter = dateAdapter
            addItemDecoration(HorizontalSpaceItemDecorator(8))
        }
        lifecycleScope.launch {
            dateViewModel.dateList.collectLatest { source -> dateAdapter.submitData(source) }
        }
    }

    private fun insertListeners() {
        binding.btCreateTask.setOnClickListener {
            val createTaskIntent = Intent(this, FormTaskActivity::class.java)
            startForResult.launch(createTaskIntent)
        }
        adapter.editTaskListener = {
            val editTaskIntent = Intent(this, FormTaskActivity::class.java)
            editTaskIntent.putExtra(FormTaskActivity.TASK_ID, it.id)
            startForResult.launch(editTaskIntent)
        }
        adapter.removeTaskListener = {
            TaskDataSource.removeTask(it)
            initTaskList()
        }
    }
}