package com.paint.simplerecyclerview.data.local

import com.paint.simplerecyclerview.data.*
import com.paint.simplerecyclerview.entity.DateUiEntity
import com.paint.simplerecyclerview.data.local.dto.DateDto
import com.paint.simplerecyclerview.entity.TaskEntity
import com.paint.simplerecyclerview.exception.NonExistentIdException
import kotlin.collections.ArrayList

class LocalDataSourceImpl : LocalDataSource {

    private val dates: MutableList<DateDto> = ArrayList()

    override fun addTaskByDateId(taskEntity: TaskEntity, dateId: String) {
        for (i in dates.indices) {
            if (dates[i].id == dateId) {
                val taskDto = taskEntity.toTaskDto()
                val list = dates[i].tasks.toMutableList()
                list.add(taskDto)
                dates[i] = dates[i].copy(tasks = list.toList())
            }
        }
    }

    fun removeById(id: String) {

    }

    override fun getTasksByDateId(id: String): List<TaskEntity> {
        var date: DateDto? = null
        dates.forEach { dateDto ->
            if (dateDto.id == id) {
                date = dateDto
            }
        }
        return date?.tasks?.map { taskDto -> taskDto.toTaskUi() }
            ?: throw NonExistentIdException("Non-existent id = $id")
    }

    override fun getDates(): List<DateUiEntity> = dates.map { dateDto -> dateDto.toDateUi() }

    override fun addDate(dateUiEntity: DateUiEntity) {
        dates.add(dateUiEntity.toDateDto())
    }

    override fun getDateById(id: String): DateUiEntity {
        var date: DateDto? = null
        dates.forEach { dateDto ->
            if (dateDto.id == id) {
                date = dateDto
            }
        }
        return date?.toDateUi() ?: throw NonExistentIdException("Non-existent id = $id")
    }

    override fun deleteTaskByIdAndByDateId(taskId: String, dateId: String) {
        var datePosition = 0
        for (i in dates.indices) {
            if (dates[i].id == dateId) {
                datePosition = i
            }
        }

        for (i in dates[datePosition].tasks.indices) {
            if (dates[datePosition].tasks[i].id == taskId) {
                val mutableList = dates[datePosition].tasks.toMutableList()
                mutableList.removeAt(i)
                dates[datePosition] = dates[datePosition].copy(tasks = mutableList.toList())
                return
            }
        }
    }
}