package com.github.welblade.todolist.ui.form_task

import androidx.lifecycle.ViewModel
import com.github.welblade.todolist.data.model.Task
import com.github.welblade.todolist.data.repository.TaskRepository

class FormTaskViewModel (private val taskRepository: TaskRepository): ViewModel() {

    fun findById(taskId: Long) = taskRepository.findById(taskId)
    fun insert(task: Task) = taskRepository.insertTask(task)
}