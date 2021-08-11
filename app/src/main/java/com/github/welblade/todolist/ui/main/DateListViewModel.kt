package com.github.welblade.todolist.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.github.welblade.todolist.data.DatePagingSource
import kotlinx.coroutines.flow.Flow
import java.util.*

class DateListViewModel : ViewModel(){

    private lateinit var _dateList: Flow<PagingData<Date>>

    init {
        dateRangeFrom(Date())
    }
    val dateList: Flow<PagingData<Date>>
        get() = _dateList

    fun dateRangeFrom(date: Date){
        _dateList = Pager(PagingConfig(1)) {
            DatePagingSource(date)
        }.flow.cachedIn(viewModelScope)
    }
}