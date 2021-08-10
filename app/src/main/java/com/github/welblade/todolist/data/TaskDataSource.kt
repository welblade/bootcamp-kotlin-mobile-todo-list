package com.github.welblade.todolist.data

import com.github.welblade.todolist.model.Task

interface TaskDataSource {
    val taskList: ArrayList<Task>
    fun getList(): List<Task>
    fun insertTask(task: Task)
    fun removeTask(task: Task)
    fun findById(taskId: String): Task?
    fun findByDate(taskDate: String): List<Task>
}