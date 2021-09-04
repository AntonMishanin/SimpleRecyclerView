package com.paint.simplerecyclerview.feature_dates.entity

import com.paint.simplerecyclerview.domain.entity.TaskDomain

data class TaskUi(
    val id: String,
    val viewType: Int,
    val isChecked: Boolean
)

fun TaskDomain.toTaskUi() = TaskUi(
    id = this.id,
    viewType = 0,
    isChecked = false
)

fun TaskUi.toTaskDomain() = TaskDomain(
    id = this.id
)