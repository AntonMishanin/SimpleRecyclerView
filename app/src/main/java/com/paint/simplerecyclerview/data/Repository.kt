package com.paint.simplerecyclerview.data

import com.paint.simplerecyclerview.entity.DateUiEntity
import com.paint.simplerecyclerview.entity.TaskEntity

class Repository(
    private val localDataSource: LocalDataSource
) {

    fun addTaskByDateId(taskEntity: TaskEntity, dateId: String) =
        localDataSource.addTaskByDateId(taskEntity, dateId)

    fun getTasksByDateId(id: String): List<TaskEntity> = localDataSource.getTasksByDateId(id)

    fun getDates(): List<DateUiEntity> = localDataSource.getDates()

    fun addDate(dateUiEntity: DateUiEntity) = localDataSource.addDate(dateUiEntity)

    fun getDateById(id: String): DateUiEntity =
        localDataSource.getDateById(id)

    fun deleteTaskByIdAndByDateId(taskId: String, dateId: String) =
        localDataSource.deleteTaskByIdAndByDateId(taskId, dateId)
}