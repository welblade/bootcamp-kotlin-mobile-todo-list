package com.github.welblade.todolist.ui.form_task

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.github.welblade.todolist.R
import com.github.welblade.todolist.data.TaskDataSourceInMemoryImpl
import com.github.welblade.todolist.data.TaskRepository
import com.github.welblade.todolist.databinding.ActivityFormTaskBinding
import com.github.welblade.todolist.extensions.format
import com.github.welblade.todolist.model.Task
import com.github.welblade.todolist.ui.main.TaskListViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class FormTaskActivity: AppCompatActivity() {
    private val binding: ActivityFormTaskBinding by lazy {
        ActivityFormTaskBinding.inflate(layoutInflater)
    }
    private lateinit var taskListViewModel: TaskListViewModel
    private var currentTaskId:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val taskDataSource = TaskDataSourceInMemoryImpl
        val taskRepository = TaskRepository(taskDataSource)
        taskListViewModel = TaskListViewModel(taskRepository)
        insertListeners()
        if(intent.hasExtra(TASK_DATE)){
            binding.tilDate.editText?.setText(
                intent.getStringExtra(TASK_DATE)
            )
        }
        if(intent.hasExtra(TASK_ID)){
            currentTaskId = intent.getIntExtra(TASK_ID, 0)
            taskListViewModel.findById(currentTaskId)?.let {
                binding.tilTitle.editText?.setText( it.title)
                binding.tilDescription.editText?.setText(it.description)
                binding.tilDate.editText?.setText( it.date)
                binding.tilTime.editText?.setText( it.hour)
            }
            binding.toolbar.title = getString(R.string.edit_task)
            binding.btSave.text = getString(R.string.save_changes)
        }
    }



    private fun insertListeners() {
        binding.tilDate.editText?.setOnClickListener {
           MaterialDatePicker.Builder.datePicker().build().apply {
               addOnPositiveButtonClickListener {
                   val timeZone = TimeZone.getDefault()
                   val offset = timeZone.getOffset(Date().time) * -1
                   binding.tilDate.editText?.setText(Date(it + offset).format())
               }
           }.show(supportFragmentManager,"DATE_PICKER")
        }
        binding.tilTime.editText?.setOnClickListener {
            MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H).build().apply {
                addOnPositiveButtonClickListener {
                    binding.tilTime.editText?.setText(
                        getString(
                            R.string.hour_minute,
                            this.hour.toString().padStart(2, '0'),
                            this.minute.toString().padStart(2, '0')
                        )
                    )
                }
            }.show(supportFragmentManager, "TIME_PICKER")
        }
        binding.btCancel.setOnClickListener {
            finish()
        }
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
        binding.btSave.setOnClickListener {
            if(validateFields()){
                val task = Task(
                    title = binding.tilTitle.editText?.text.toString(),
                    description = binding.tilDescription.editText?.text.toString(),
                    date = binding.tilDate.editText?.text.toString(),
                    hour = binding.tilTime.editText?.text.toString(),
                    id = this.currentTaskId
                )
                taskListViewModel.insert(task)
                setResult(Activity.RESULT_OK)
                finish()
            }
        }
    }
    private fun validateFields(): Boolean{
        var isValid = true
        binding.tvTitleError.visibility = View.GONE
        binding.tvDateError.visibility = View.GONE
        binding.tvTimeError.visibility = View.GONE
        if(binding.tilTitle.editText?.text.isNullOrBlank()){
            isValid = false
            binding.tvTitleError.visibility = View.VISIBLE
        }
        if(binding.tilDate.editText?.text.isNullOrBlank()){
            isValid = false
            binding.tvDateError.visibility = View.VISIBLE
        }
        if(binding.tilTime.editText?.text.isNullOrBlank()){
            isValid = false
            binding.tvTimeError.visibility = View.VISIBLE
        }
        return isValid
    }
    companion object {
        const val TASK_ID = "task_id"
        const val TASK_DATE = "task_date"
    }
}