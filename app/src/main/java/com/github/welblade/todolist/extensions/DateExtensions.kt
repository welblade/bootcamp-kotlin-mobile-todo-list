package com.github.welblade.todolist.extensions

import java.text.SimpleDateFormat
import java.util.*
val locate =  Locale("pt", "BR")
fun Date.format(): String {
    return SimpleDateFormat("dd/MM/yyyy", locate).format(this)
}
fun Date.plusDays(amount: Int): Date{
    val c = Calendar.getInstance()
    c.time = this
    c.add(Calendar.DATE, amount)
    return c.time
}