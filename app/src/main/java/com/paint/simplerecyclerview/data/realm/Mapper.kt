package com.paint.simplerecyclerview.data.realm

import com.paint.simplerecyclerview.entity.DateUiEntity
import com.paint.simplerecyclerview.entity.InactiveTaskEntity
import com.paint.simplerecyclerview.entity.TaskEntity
import io.realm.RealmList

fun DateDto.toDateUi() = DateUiEntity(
    id = this.id,
    tasks = this.tasks.map { taskDto -> taskDto.toTaskUi() }
)

fun TaskDto.toTaskUi() = InactiveTaskEntity(
    id = this.id
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

fun TaskEntity.toTaskDto() = TaskDto(
    id = this.id
)