package com.github.welblade.todolist.model

data class Task(
    val title: String,
    val date: String,
    val hour: String,
    val id: Int = 0
)
