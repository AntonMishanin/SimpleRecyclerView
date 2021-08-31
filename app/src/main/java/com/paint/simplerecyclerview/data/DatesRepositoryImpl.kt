package com.paint.simplerecyclerview.data

import com.paint.simplerecyclerview.data.local.toDateDomain
import com.paint.simplerecyclerview.data.local.toDateDto
import com.paint.simplerecyclerview.data.local.toTaskDomain
import com.paint.simplerecyclerview.data.local.toTaskDto
import com.paint.simplerecyclerview.domain.entity.DateDomain
import com.paint.simplerecyclerview.domain.entity.TaskDomain
import com.paint.simplerecyclerview.domain.repository.DatesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DatesRepositoryImpl(
    private val localDataSource: LocalDataSource
) : DatesRepository {

    override fun addTaskByDateId(taskDomain: TaskDomain, dateId: String, onSuccess: (() -> Unit)?) =
        localDataSource.addTaskByDateId(taskDomain.toTaskDto(), dateId, onSuccess)

    override fun getTasksByDateId(id: String, onSuccess: (listOfTasks: List<TaskDomain>) -> Unit) =
        localDataSource.getTasksByDateId(id) { listOfTasksDto ->
            onSuccess(listOfTasksDto.map { taskDto -> taskDto.toTaskDomain() })
        }

    override fun getDates(onSuccess: (List<DateDomain>) -> Unit) =
        localDataSource.getDates { listOfDatesDto ->
            onSuccess(listOfDatesDto.map { dateDto -> dateDto.toDateDomain() })
        }

    override fun observeChangesListOfDates(): Flow<List<DateDomain>> =
        localDataSource.getDatesAsync()
            .map { listOfDatesDto ->
                listOfDatesDto.map { dateDto -> dateDto.toDateDomain() }
            }

    override fun addDate(dateDomain: DateDomain, onSuccess: (() -> Unit)?) =
        localDataSource.addDate(dateDomain.toDateDto(), onSuccess)

    override fun getDateById(id: String): DateDomain =
        localDataSource.getDateById(id)?.toDateDomain()!!

    override fun deleteTaskById(taskId: String, onSuccess: (() -> Unit)?) =
        localDataSource.deleteTaskById(taskId, onSuccess)
}