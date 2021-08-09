package com.github.welblade.todolist.extensions

import java.text.SimpleDateFormat
import java.util.*
val locate =  Locale("pt", "BR")
fun Date.format(pattern: String = "dd/MM/yyyy"): String {
    return SimpleDateFormat(pattern, locate).format(this)
}
fun Date.plusDays(amount: Int): Date{
    val c = Calendar.getInstance()
    c.time = this
    c.add(Calendar.DAY_OF_YEAR, amount)
    return c.time
}
