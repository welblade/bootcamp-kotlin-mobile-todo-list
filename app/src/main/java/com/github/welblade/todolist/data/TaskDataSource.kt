package com.github.welblade.todolist.data

import com.github.welblade.todolist.model.Task

object TaskDataSource {
    private val taskList = arrayListOf<Task>()

    fun getList() = taskList

    fun insertTask(task: Task){
        taskList.add(task.copy(id = taskList.size + 1))
    }
}