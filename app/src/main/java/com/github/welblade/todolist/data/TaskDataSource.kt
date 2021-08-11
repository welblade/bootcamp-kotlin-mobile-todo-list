package com.github.welblade.todolist.data

import com.github.welblade.todolist.model.Task

interface TaskDataSource {
    fun getList(): List<Task>
    fun insertTask(task: Task)
    fun removeTask(task: Task)
    fun findById(taskId: Long): Task?
    fun findByDate(taskDate: String): List<Task>
}