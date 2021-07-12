package com.paint.simplerecyclerview.data

import com.paint.simplerecyclerview.entity.DateUiEntity
import com.paint.simplerecyclerview.entity.TaskEntity

interface LocalDataSource {

    fun addTaskByDateId(taskEntity: TaskEntity, dateId: String)

    fun getTasksByDateId(id: String): List<TaskEntity>

    fun getDates(): List<DateUiEntity>

    fun addDate(dateUiEntity: DateUiEntity)

    fun getDateById(id: String): DateUiEntity

    fun deleteTaskByIdAndByDateId(taskId: String, dateId: String)
}