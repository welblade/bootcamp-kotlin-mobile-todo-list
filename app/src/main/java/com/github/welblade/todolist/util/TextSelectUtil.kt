package com.github.welblade.todolist.util

import android.content.Context
import com.github.welblade.todolist.R
import com.github.welblade.todolist.extensions.format
import java.text.SimpleDateFormat
import java.util.*

object TextSelectUtil {
    fun getEmptyStateText(context: Context, date: Date): String {
        val value = compare(date)
        return when {
            value == 1L -> context.getString(R.string.ooops_you_don_t_have_any_task_tomorrow)
            value in 2L..6L -> context.getString(R.string.ooops_you_don_t_have_any_task_the_next, date.format("EEEE"))
            value > 6L -> context.getString(R.string.ooops_you_don_t_have_any_task_on_day, date.format())
            value == -1L -> context.getString(R.string.ooops_you_had_any_task_yesterday)
            value in -6L..-2L -> context.getString(R.string.ooops_you_had_any_task_last, date.format("EEEE"))
            value < -6L -> context.getString(R.string.ooops_you_had_any_task_on_day, date.format())
            else -> context.getString(R.string.ooops_you_don_t_have_any_task_today)
        }
    }
    fun getTaskOfDayText(context: Context, date: Date): String{
        val value = compare(date)
        return when {
            value == 1L -> context.getString(R.string.tomorrow_tasks)
            value in 2L..6L -> context.getString(R.string.week_tasks, date.format("EEEE").replaceFirstChar { it.uppercase() })
            value > 6L -> context.getString(R.string.future_tasks, date.format())
            value == -1L -> context.getString(R.string.yesterday_tasks)
            value in -6L..-2L -> context.getString(R.string.week_tasks, date.format("EEEE").replaceFirstChar { it.uppercase() })
            value < -6L -> context.getString(R.string.past_tasks, date.format())
            else -> context.getString(R.string.day_tasks)
        }
    }


    private fun compare(date: Date): Long{
        val today = convertToDays(Date())
        val otherDay = convertToDays(date)
        return otherDay - today
    }
    private fun convertToDays(date: Date):Long{
        val days = SimpleDateFormat("D").format(date).toLong()
        val years = SimpleDateFormat("yyyy").format(date).toLong()
        return years * 365 + days
    }
}
