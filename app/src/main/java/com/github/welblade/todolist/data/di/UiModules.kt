package com.github.welblade.todolist.data.di

import com.github.welblade.todolist.ui.form_task.view_model.FormTaskViewModel
import com.github.welblade.todolist.ui.main.view_model.TaskListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module


object UiModules {

    fun load(){
        loadKoinModules(viewModelModules())
    }

    private fun viewModelModules(): Module {
        return module {
            viewModel {
                TaskListViewModel(get())
            }
            viewModel {
                FormTaskViewModel(get())
            }
        }
    }
}