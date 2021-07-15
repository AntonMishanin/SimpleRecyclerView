package com.paint.simplerecyclerview.data.realm

import com.paint.simplerecyclerview.data.LocalDataSource
import com.paint.simplerecyclerview.entity.DateUiEntity
import com.paint.simplerecyclerview.entity.TaskEntity
import io.realm.Realm

class TasksLocalDataSource(
    private val realm: Realm
) : LocalDataSource {

    override fun addTaskByDateId(taskEntity: TaskEntity, dateId: String, onSuccess: () -> Unit) {
        realm.executeTransactionAsync({ realm ->
            val taskDto = realm.createObject(TaskDto::class.java)
            taskDto.id = taskEntity.id

            val date = realm.where(DateDto::class.java).equalTo("id", dateId).findFirst()
            date?.tasks?.add(taskDto)
        }, {
            onSuccess()
        }, { throwable ->

        })
    }

    override fun getTasksByDateId(id: String, onSuccess: (listOfTasks: List<TaskEntity>) -> Unit) {
        onSuccess(realm.where(DateDto::class.java).equalTo("id", id)
            .findFirst()?.tasks?.map { taskDto -> taskDto.toTaskUi() } ?: emptyList())
    }

    override fun getDates(onSuccess: (List<DateUiEntity>) -> Unit) {
        onSuccess(realm.where(DateDto::class.java).findAll().map { dateDto -> dateDto.toDateUi() })
    }

    override fun addDate(dateUiEntity: DateUiEntity, onSuccess: () -> Unit) {
        realm.executeTransactionAsync({ realm ->
            realm.copyToRealm(dateUiEntity.toDateDto())
        }, {
            onSuccess()
        }, { throwable ->

        })
    }

    override fun getDateById(id: String): DateUiEntity =
        realm.where(DateDto::class.java).equalTo("id", id).findFirst()?.toDateUi()!!

    override fun deleteTaskByIdAndByDateId(taskId: String, dateId: String, onSuccess: () -> Unit) {
        realm.executeTransactionAsync({ realm ->
            realm.where(TaskDto::class.java).equalTo("id", taskId)
                .findFirst()?.deleteFromRealm()
        }, {
            onSuccess()
        }, { throwable ->

        })
    }
}