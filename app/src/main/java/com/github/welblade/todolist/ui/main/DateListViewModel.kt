package com.github.welblade.todolist.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import java.util.*

class DateListViewModel (private val initialDate: Date) : ViewModel(){

    val dateList = Pager(PagingConfig(7)) {
        com.github.welblade.todolist.data.DatePagingSource(initialDate)
    }.flow
    .cachedIn(viewModelScope)
}