package com.github.welblade.todolist.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.github.welblade.todolist.data.TaskDataSource
import com.github.welblade.todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val adapter: TaskListAdapter by lazy { TaskListAdapter() }

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
        initTaskList()
        insertListeners()
    }

    private fun initTaskList(){
        binding.rvTaskList.adapter = adapter
        adapter.submitList(TaskDataSource.getList())
    }

    private fun insertListeners() {
        binding.btCreateTask.setOnClickListener {
            val createTaskIntent = Intent(this, FormTaskActivity::class.java)
            startForResult.launch(createTaskIntent)
        }
    }
}