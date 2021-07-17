package com.paint.simplerecyclerview.data

import com.paint.simplerecyclerview.entity.DateUiEntity
import com.paint.simplerecyclerview.entity.TaskUi

interface LocalDataSource {

    fun addTaskByDateId(taskEntity: TaskUi, dateId: String, onSuccess: () -> Unit)

    fun getTasksByDateId(id: String, onSuccess: (listOfTasks: List<TaskUi>) -> Unit)

    fun getDates(onSuccess: (List<DateUiEntity>)->Unit)

    fun addDate(dateUiEntity: DateUiEntity, onSuccess: ()-> Unit)

    fun getDateById(id: String): DateUiEntity

    fun deleteTaskByIdAndByDateId(taskId: String, dateId: String, onSuccess: () -> Unit)
}