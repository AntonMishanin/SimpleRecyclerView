package com.paint.simplerecyclerview.data.realm

import com.paint.simplerecyclerview.data.LocalDataSource
import com.paint.simplerecyclerview.entity.DateUiEntity
import com.paint.simplerecyclerview.entity.TaskEntity
import io.realm.Realm

class TasksLocalDataSource(
    private val realm: Realm
) : LocalDataSource {

    override fun addTaskByDateId(taskEntity: TaskEntity, dateId: String) {
        realm.executeTransactionAsync({ realm ->
            val taskDto = realm.createObject(TaskDto::class.java)
            taskDto.id = taskEntity.id

            val date = realm.where(DateDto::class.java).equalTo("id", dateId).findFirst()
            date?.tasks?.add(taskDto)
        }, {

        }, { throwable ->

        })
    }

    override fun getTasksByDateId(id: String): List<TaskEntity> {
        //realm.where(DateDto::class.java).findAllAsync()
    }

    override fun getDates(): List<DateUiEntity> {
        TODO("Not yet implemented")
    }

    override fun addDate(dateUiEntity: DateUiEntity) {
        TODO("Not yet implemented")
    }

    override fun getDateById(id: String): DateUiEntity {
        TODO("Not yet implemented")
    }

    override fun deleteTaskByIdAndByDateId(taskId: String, dateId: String) {
        TODO("Not yet implemented")
    }
}