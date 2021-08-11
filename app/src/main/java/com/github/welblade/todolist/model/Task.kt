package com.github.welblade.todolist.model

import java.util.zip.CRC32

data class Task(
    val title: String,
    val description: String,
    val date: String,
    val hour: String,
    val id: Long = 0L
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Task

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return "$id$title$description$date$hour".hashCode()
    }
}
