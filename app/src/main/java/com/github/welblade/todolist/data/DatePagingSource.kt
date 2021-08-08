package com.github.welblade.todolist.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.github.welblade.todolist.extensions.plusDays
import java.time.LocalDate
import java.util.*

object DatePagingSource : PagingSource<Long, Date>(){
    private const val INITIAL_PAGE_INDEX = 0L

    override fun getRefreshKey(state: PagingState<Long, Date>): Long? = null

    override suspend fun load(params: LoadParams<Long>): LoadResult<Long, Date> {
        val position = params.key ?: INITIAL_PAGE_INDEX
        val dates:MutableList<Date> = arrayListOf()
        dates.add(Date(position))
        for(i in 1..6){
            dates.add(dates.last().plusDays(1))
        }
        return LoadResult.Page(
            data = dates.toList(),
            prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
            nextKey = if (dates.isNullOrEmpty()) null else position + 1
        )
    }

}