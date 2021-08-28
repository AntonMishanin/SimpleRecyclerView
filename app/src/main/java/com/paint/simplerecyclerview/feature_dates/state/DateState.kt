package com.paint.simplerecyclerview.feature_dates.state

import com.paint.simplerecyclerview.entity.DateUi

sealed class DateState{
    data class Content(val listOfDates: List<DateUi>): DateState()
    object Empty: DateState()
    object Progress: DateState()
    data class Error(val throwable: Throwable): DateState()
}