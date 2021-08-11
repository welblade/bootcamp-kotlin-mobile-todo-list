package com.github.welblade.todolist

import android.app.Application
import com.github.welblade.todolist.data.db.AppDatabase
import com.github.welblade.todolist.data.db.TaskDataSourceLocalImpl
import com.github.welblade.todolist.data.repository.TaskRepository

class App : Application() {
    private val database: AppDatabase by lazy {
        AppDatabase.getDatabase(this)
    }
    val repository: TaskRepository by lazy {
        TaskRepository(TaskDataSourceLocalImpl(database.taskDao()))
    }
}