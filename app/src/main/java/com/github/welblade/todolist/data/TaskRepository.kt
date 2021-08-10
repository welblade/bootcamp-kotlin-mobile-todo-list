package com.github.welblade.todolist.data

import com.github.welblade.todolist.model.Task

class TaskRepository(private val taskDataSource: TaskDataSource) {
    fun getList() = taskDataSource.getList()
    fun findById(taskId: Int) = taskDataSource.findById(taskId)
    fun findByDate(taskDate: String) = taskDataSource.findByDate(taskDate)
    fun insertTask(task: Task) = taskDataSource.insertTask(task)
    fun removeTask(task: Task) = taskDataSource.removeTask(task)
}