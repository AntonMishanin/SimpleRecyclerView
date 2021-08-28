package com.paint.simplerecyclerview.data.local

import com.paint.simplerecyclerview.entity.DateUi
import com.paint.simplerecyclerview.entity.TaskUi
import io.realm.RealmList

/**
 * to Ui entity
 */

fun DateDto.toDateUi() = DateUi(
    id = this.id,
    tasks = this.tasks.map { taskDto -> taskDto.toTaskUi() }
)

fun TaskDto.toTaskUi() = TaskUi(
    id = this.id,
    viewType = 0,
    isChecked = true
)

/**
 * to date entity
 */

fun DateUi.toDateDto(): DateDto {

    val list = RealmList<TaskDto>()
    this.tasks.forEach { task ->
        list.add(task.toTaskDto())
    }

    return DateDto(
        id = this.id,
        tasks = list
    )
}

fun TaskUi.toTaskDto() = TaskDto(
    id = this.id
)