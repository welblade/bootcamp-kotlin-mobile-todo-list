package com.github.welblade.todolist.data

import com.github.welblade.todolist.model.Task

object TaskDataSourceImpl : TaskDataSource {
    override val taskList = arrayListOf<Task>()

    override fun getList() = taskList.toList()

    override fun insertTask(task: Task){
        if (task.id == 0) {
            taskList.add(task.copy(id = taskList.size + 1))
        } else {
            taskList.remove(task)
            taskList.add(task)
        }

    }
    override fun removeTask(task: Task){
        taskList.remove(task)
    }
    override fun findById(taskId: Int): Task? {
        return taskList.find {
            it.id == taskId
        }
    }
    override fun findByDate(taskDate: String): List<Task> {
        return taskList.filter {
            it.date == taskDate
        }
    }
}