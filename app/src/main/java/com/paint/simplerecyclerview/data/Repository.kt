package com.paint.simplerecyclerview.data

import com.paint.simplerecyclerview.entity.DateUiEntity
import com.paint.simplerecyclerview.entity.TaskEntity

class Repository(
    private val localDataSource: LocalDataSource
) {

    fun addTaskByDateId(taskEntity: TaskEntity, dateId: String, onSuccess: () -> Unit) =
        localDataSource.addTaskByDateId(taskEntity, dateId, onSuccess)

    fun getTasksByDateId(id: String, onSuccess: (listOfTasks: List<TaskEntity>) -> Unit) =
        localDataSource.getTasksByDateId(id, onSuccess)

    fun getDates(onSuccess: (List<DateUiEntity>) -> Unit) =
        localDataSource.getDates(onSuccess = onSuccess)

    fun addDate(dateUiEntity: DateUiEntity, onSuccess: () -> Unit) =
        localDataSource.addDate(dateUiEntity, onSuccess)

    fun getDateById(id: String): DateUiEntity =
        localDataSource.getDateById(id)

    fun deleteTaskByIdAndByDateId(taskId: String, dateId: String, onSuccess: () -> Unit) =
        localDataSource.deleteTaskByIdAndByDateId(taskId, dateId, onSuccess)
}