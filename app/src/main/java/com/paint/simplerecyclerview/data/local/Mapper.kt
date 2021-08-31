package com.paint.simplerecyclerview.data.local

import com.paint.simplerecyclerview.domain.entity.DateDomain
import com.paint.simplerecyclerview.domain.entity.TaskDomain
import io.realm.RealmList

/**
 * to Ui entity
 */

fun DateDto.toDateDomain() = DateDomain(
    id = this.id,
    tasks = this.tasks.map { taskDto -> taskDto.toTaskDomain() }
)

fun TaskDto.toTaskDomain() = TaskDomain(
    id = this.id
)

/**
 * to date entity
 */

fun DateDomain.toDateDto(): DateDto {

    val list = RealmList<TaskDto>()
    this.tasks.forEach { task ->
        list.add(task.toTaskDto())
    }

    return DateDto(
        id = this.id,
        tasks = list
    )
}

fun TaskDomain.toTaskDto() = TaskDto(
    id = this.id
)