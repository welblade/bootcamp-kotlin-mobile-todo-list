package com.github.welblade.todolist.data.di.db

import com.github.welblade.todolist.data.TaskDataSource
import com.github.welblade.todolist.data.db.AppDatabase
import com.github.welblade.todolist.data.db.TaskDataSourceLocalImpl
import com.github.welblade.todolist.data.repository.TaskRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object DataBaseModule {
    fun load(){
        loadKoinModules(databaseModules())
    }
    private fun databaseModules(): Module {
        return module {
            single {
                AppDatabase.getDatabase(androidContext())
            }
            single{
                get<AppDatabase>().taskDao()
            }
            single<TaskDataSource>{
                TaskDataSourceLocalImpl(get())
            }
            factory {
                TaskRepository(get())
            }
        }
    }
}