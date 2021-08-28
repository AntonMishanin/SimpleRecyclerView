package com.paint.simplerecyclerview.data

import com.paint.simplerecyclerview.data.local.DateDto
import com.paint.simplerecyclerview.data.local.TaskDto
import com.paint.simplerecyclerview.entity.DateUi
import com.paint.simplerecyclerview.entity.TaskUi
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    fun addTaskByDateId(task: TaskDto, dateId: String, onSuccess: (() -> Unit)?)

    fun getTasksByDateId(id: String, onSuccess: (listOfTasks: List<TaskDto>) -> Unit)

    fun getDates(onSuccess: (List<DateDto>)->Unit)

    fun getDatesAsync(): Flow<List<DateDto>>

    fun addDate(date: DateDto, onSuccess: (() -> Unit)?)

    fun getDateById(id: String): DateDto?

    fun getDateByIdAsync(id: String): Flow<DateDto?>

    fun deleteTaskById(taskId: String, onSuccess: (() -> Unit)?)
}