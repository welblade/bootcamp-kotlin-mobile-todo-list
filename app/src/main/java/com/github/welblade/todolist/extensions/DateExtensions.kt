package com.github.welblade.todolist.extensions

import java.text.SimpleDateFormat
import java.util.*
val locate =  Locale("pt", "BR")
fun Date.format(): String {
    return SimpleDateFormat("dd/MM/yyyy", locate).format(this)
}