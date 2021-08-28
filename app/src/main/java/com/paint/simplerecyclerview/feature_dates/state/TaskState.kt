package com.paint.simplerecyclerview.feature_dates.state

import com.paint.simplerecyclerview.entity.TaskUi

sealed class TaskState

data class TaskContent(val listOfTasks: List<TaskUi>) : TaskState()

object TaskEmpty : TaskState()