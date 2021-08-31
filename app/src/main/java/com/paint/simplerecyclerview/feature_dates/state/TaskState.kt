package com.paint.simplerecyclerview.feature_dates.state

import com.paint.simplerecyclerview.feature_dates.entity.TaskUi

sealed class TaskState

data class TaskContent(val listOfTasks: List<TaskUi>) : TaskState()

object TaskEmpty : TaskState()