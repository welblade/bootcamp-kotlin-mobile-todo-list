package com.github.welblade.todolist

import android.app.Application
import com.github.welblade.todolist.data.di.db.DataBaseModule
import com.github.welblade.todolist.data.di.UiModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            //inject Android context
            androidContext(this@App)
            DataBaseModule.load()
            UiModules.load()
        }
    }
}