package com.github.welblade.todolist.data

import com.github.welblade.todolist.model.Task
import com.github.welblade.todolist.model.TaskEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class TaskDataSourceLocal(private val dao: TaskDao): TaskDataSource {
    override fun getList(): List<Task> {
        return dao.getAll().map { it.toTask() }
    }

    override fun insertTask(task: Task) {
        runBlocking {
            launch(Dispatchers.IO) {
                if (task.id == 0L){
                    dao.insert(TaskEntity.from(task))
                }else{
                    dao.update(TaskEntity.from(task))
                }
            }
        }
    }

    override fun removeTask(task: Task) {
        runBlocking {
            launch(Dispatchers.IO) {
                if (task.id > 0L){
                    dao.delete(TaskEntity.from(task))
                }
            }
        }
    }

    override fun findById(taskId: Long): Task? {
        return dao.findById(taskId).toTask()
    }

    override fun findByDate(taskDate: String): List<Task> {
        return dao.findByDate(taskDate).map { it.toTask() }
    }
}