package com.github.welblade.todolist.ui

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.welblade.todolist.R
import com.github.welblade.todolist.data.TaskDataSource
import com.github.welblade.todolist.databinding.ActivityFormTaskBinding
import com.github.welblade.todolist.extensions.format
import com.github.welblade.todolist.model.Task
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class FormTaskActivity: AppCompatActivity() {
    private val binding: ActivityFormTaskBinding by lazy {
        ActivityFormTaskBinding.inflate(layoutInflater)
    }
    private var currentTaskId:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        insertListeners()
        if(intent.hasExtra(TASK_ID)){
            currentTaskId = intent.getIntExtra(TASK_ID, 0)
            TaskDataSource.findById(currentTaskId)?.let {
                binding.tilTitle.editText?.setText( it.title)
                binding.tilDate.editText?.setText( it.date)
                binding.tilTime.editText?.setText( it.hour)
            }
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
        binding.btSave.setOnClickListener {
            val task = Task(
                title = binding.tilTitle.editText?.text.toString(),
                date = binding.tilDate.editText?.text.toString(),
                hour = binding.tilTime.editText?.text.toString(),
                id = this.currentTaskId
            )
            TaskDataSource.insertTask(task)
            setResult(Activity.RESULT_OK)
            finish()
        }
    }
    companion object {
        const val TASK_ID = "task_id"
    }
}