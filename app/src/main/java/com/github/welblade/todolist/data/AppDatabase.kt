package com.github.welblade.todolist.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.github.welblade.todolist.model.TaskEntity

@Database(entities = [TaskEntity::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao() : TaskDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                val db = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "task_db"
                ).fallbackToDestructiveMigration().build()
                instance = db
                db
            }
        }
    }
}
