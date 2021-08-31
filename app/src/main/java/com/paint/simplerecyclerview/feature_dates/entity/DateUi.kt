package com.paint.simplerecyclerview.feature_dates.entity

import com.paint.simplerecyclerview.domain.entity.DateDomain

data class DateUi(
    val id: String,
    val tasks: List<TaskUi>
)

fun DateDomain.toDateUi() = DateUi(
    id = this.id,
    tasks = this.tasks.map { taskDomain -> taskDomain.toTaskUi() }
)

fun DateUi.toDateDomain() = DateDomain(
    id = this.id,
    tasks = this.tasks.map { taskUi -> taskUi.toTaskDomain() }
)