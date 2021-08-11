package com.github.welblade.todolist.data

import androidx.room.*
import com.github.welblade.todolist.model.TaskEntity
@Dao
interface TaskDao {
    @Query("SELECT * FROM TaskTable")
    fun getAll(): List<TaskEntity>

    @Query("SELECT * FROM TaskTable WHERE date LIKE :date")
    fun findByDate(date: String): List<TaskEntity>

    @Query("SELECT * FROM TaskTable WHERE id = :taskId LIMIT 1")
    fun findById(taskId: Long): TaskEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(task: TaskEntity)

    @Update
    fun update(task: TaskEntity)

    @Delete
    fun delete(task: TaskEntity)

}
