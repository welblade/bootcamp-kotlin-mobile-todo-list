package com.github.welblade.todolist.ui.main.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.welblade.todolist.data.repository.TaskRepository
import com.github.welblade.todolist.extensions.format
import com.github.welblade.todolist.data.model.Task
import java.util.*

class TaskListViewModel (private val taskRepository: TaskRepository): ViewModel() {
    companion object{
        private const val TAG = "TaskListViewModel"
    }
    private var _taskList = MutableLiveData<List<Task>>()
    val taskList: LiveData<List<Task>>
        get() = _taskList

    init {
        getList(Date())
    }

    fun getList(date: Date){
        Thread {
            try {
                _taskList.postValue(taskRepository.findByDate(date.format()))
            } catch (exception: Exception) {
                Log.d(TAG, exception.message.toString())
            }
        }.start()
    }
    fun remove(task: Task) = taskRepository.removeTask(task)
}