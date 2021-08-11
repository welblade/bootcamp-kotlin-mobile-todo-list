package com.github.welblade.todolist

import android.app.Application
import com.github.welblade.todolist.data.AppDatabase
import com.github.welblade.todolist.data.TaskDataSourceLocal
import com.github.welblade.todolist.data.TaskRepository

class App : Application() {
    private val database: AppDatabase by lazy {
        AppDatabase.getDatabase(this)
    }
    val repository: TaskRepository by lazy {
        TaskRepository(TaskDataSourceLocal(database.taskDao()))
    }
}