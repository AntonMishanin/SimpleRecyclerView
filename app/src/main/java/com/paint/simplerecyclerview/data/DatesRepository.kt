package com.paint.simplerecyclerview.data

import com.paint.simplerecyclerview.data.local.toDateDto
import com.paint.simplerecyclerview.data.local.toDateUi
import com.paint.simplerecyclerview.data.local.toTaskDto
import com.paint.simplerecyclerview.data.local.toTaskUi
import com.paint.simplerecyclerview.entity.DateUi
import com.paint.simplerecyclerview.entity.TaskUi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map

class DatesRepository(
    private val localDataSource: LocalDataSource
) {

    fun addTaskByDateId(taskUi: TaskUi, dateId: String, onSuccess: (() -> Unit)? = null) =
        localDataSource.addTaskByDateId(taskUi.toTaskDto(), dateId, onSuccess)

    fun getTasksByDateId(id: String, onSuccess: (listOfTasks: List<TaskUi>) -> Unit) =
        localDataSource.getTasksByDateId(id) { listOfTasksDto ->
            onSuccess(listOfTasksDto.map { taskDto -> taskDto.toTaskUi() })
        }

    fun getDates(onSuccess: (List<DateUi>) -> Unit) =
        localDataSource.getDates { listOfDatesDto ->
            onSuccess(listOfDatesDto.map { dateDto -> dateDto.toDateUi() })
        }

    fun observeChangesListOfDates(): Flow<List<DateUi>> =
        localDataSource.getDatesAsync()
            .map { listOfDatesDto ->
                listOfDatesDto.map { dateDto -> dateDto.toDateUi() }
            }


    fun addDate(dateUi: DateUi, onSuccess: (() -> Unit)? = null) =
        localDataSource.addDate(dateUi.toDateDto(), onSuccess)

    fun getDateById(id: String): DateUi =
        localDataSource.getDateById(id)?.toDateUi()!!

    fun deleteTaskById(taskId: String, onSuccess: (() -> Unit)? = null) =
        localDataSource.deleteTaskById(taskId, onSuccess)
}