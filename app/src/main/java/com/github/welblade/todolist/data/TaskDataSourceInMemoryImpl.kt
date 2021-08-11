package com.github.welblade.todolist.data

import com.github.welblade.todolist.model.Task

object TaskDataSourceInMemoryImpl : TaskDataSource {
    private val taskList = arrayListOf<Task>()

    override fun getList() = taskList.toList()

    override fun insertTask(task: Task){
        if (task.id == 0L) {
            taskList.add(task.copy(id = (taskList.size + 1 * 1L)))
        } else {
            taskList.remove(task)
            taskList.add(task)
        }

    }
    override fun removeTask(task: Task){
        taskList.remove(task)
    }
    override fun findById(taskId: Long): Task? {
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