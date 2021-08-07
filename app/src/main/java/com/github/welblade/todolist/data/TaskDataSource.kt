package com.github.welblade.todolist.data

import com.github.welblade.todolist.model.Task

object TaskDataSource {
    private val taskList = arrayListOf<Task>()

    fun getList() = taskList.toList()

    fun insertTask(task: Task){
        if (task.id == 0) {
            taskList.add(task.copy(id = taskList.size + 1))
        } else {
            taskList.remove(task)
            taskList.add(task)
        }

    }
    fun removeTask(task: Task){
        taskList.remove(task)
    }
    fun findById(taskId: Int): Task? {
        return taskList.find {
            it.id == taskId
        }
    }
}