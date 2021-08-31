package com.paint.simplerecyclerview.data.local

import com.paint.simplerecyclerview.data.LocalDataSource
import io.realm.Realm
import io.realm.kotlin.toFlow
import kotlinx.coroutines.flow.Flow

/**
 * Для обновления объекта использовать realm.insertOrUpdate()
 */

class LocalDataSourceImpl(
    private val realm: Realm
) : LocalDataSource {

    override fun addTaskByDateId(task: TaskDto, dateId: String, onSuccess: (() -> Unit)?) {
        realm.executeTransactionAsync({ realm ->
            realm.insert(task)

            val date = realm.where(DateDto::class.java).equalTo("id", dateId).findFirst()
            date?.tasks?.add(task)
        }, {
            onSuccess?.invoke()
        }, { throwable ->
            throwable.printStackTrace()
        })
    }

    override fun getTasksByDateId(id: String, onSuccess: (listOfTasks: List<TaskDto>) -> Unit) {
        val date = realm.where(DateDto::class.java).equalTo("id", id).findFirst()
        onSuccess(date?.tasks ?: emptyList())
    }

    override fun getDates(onSuccess: (List<DateDto>) -> Unit) {
        onSuccess(realm.where(DateDto::class.java).findAll())
    }

    override fun getDatesAsync(): Flow<List<DateDto>> = realm.where(DateDto::class.java).findAll().toFlow()

    override fun addDate(date: DateDto, onSuccess: (() -> Unit)?) {
        realm.executeTransactionAsync({ realm ->
            realm.insert(date)
        }, {
            onSuccess?.invoke()
        }, { throwable ->
            throwable.printStackTrace()
        })
    }

    override fun getDateById(id: String): DateDto? =
        realm.where(DateDto::class.java).equalTo("id", id).findFirst()

    override fun getDateByIdAsync(id: String): Flow<DateDto?> =
        realm.where(DateDto::class.java).equalTo("id", id).findFirstAsync().toFlow()

    override fun deleteTaskById(taskId: String, onSuccess: (() -> Unit)?) {
        realm.executeTransactionAsync({ realm ->
            val task = realm.where(TaskDto::class.java).equalTo("id", taskId).findFirst()
            task?.deleteFromRealm()
        }, {
            onSuccess?.invoke()
        }, { throwable ->
            throwable.printStackTrace()
        })
    }
}