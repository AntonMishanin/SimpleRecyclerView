package com.paint.simplerecyclerview.data.realm

import com.paint.simplerecyclerview.entity.DateUiEntity
import com.paint.simplerecyclerview.entity.TaskUi
import io.realm.RealmList

fun DateDto.toDateUi() = DateUiEntity(
    id = this.id,
    tasks = this.tasks.map { taskDto -> taskDto.toTaskUi() }
)

fun TaskDto.toTaskUi() = TaskUi(
    id = this.id,
    viewType = 0,
    isChecked = true
)

fun DateUiEntity.toDateDto(): DateDto {

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