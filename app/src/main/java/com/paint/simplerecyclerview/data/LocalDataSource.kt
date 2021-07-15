package com.paint.simplerecyclerview.data

import com.paint.simplerecyclerview.entity.DateUiEntity
import com.paint.simplerecyclerview.entity.TaskEntity

interface LocalDataSource {

    fun addTaskByDateId(taskEntity: TaskEntity, dateId: String, onSuccess: () -> Unit)

    fun getTasksByDateId(id: String, onSuccess: (listOfTasks: List<TaskEntity>) -> Unit)

    fun getDates(onSuccess: (List<DateUiEntity>)->Unit)

    fun addDate(dateUiEntity: DateUiEntity, onSuccess: ()-> Unit)

    fun getDateById(id: String): DateUiEntity

    fun deleteTaskByIdAndByDateId(taskId: String, dateId: String, onSuccess: () -> Unit)
}