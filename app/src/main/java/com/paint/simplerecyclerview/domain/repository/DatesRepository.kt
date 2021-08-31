package com.paint.simplerecyclerview.domain.repository

import com.paint.simplerecyclerview.domain.entity.DateDomain
import com.paint.simplerecyclerview.domain.entity.TaskDomain
import kotlinx.coroutines.flow.Flow

interface DatesRepository {

    fun addTaskByDateId(taskDomain: TaskDomain, dateId: String, onSuccess: (() -> Unit)?)

    fun getTasksByDateId(id: String, onSuccess: (listOfTasks: List<TaskDomain>) -> Unit)

    fun getDates(onSuccess: (List<DateDomain>) -> Unit)

    fun observeChangesListOfDates(): Flow<List<DateDomain>>

    fun addDate(dateDomain: DateDomain, onSuccess: (() -> Unit)?)

    fun getDateById(id: String): DateDomain

    fun deleteTaskById(taskId: String, onSuccess: (() -> Unit)?)
}