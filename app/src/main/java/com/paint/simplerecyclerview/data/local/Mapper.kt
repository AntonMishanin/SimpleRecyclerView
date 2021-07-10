package com.paint.simplerecyclerview.data

import com.paint.simplerecyclerview.entity.DateUiEntity
import com.paint.simplerecyclerview.data.local.dto.DateDto
import com.paint.simplerecyclerview.data.local.dto.TaskDto
import com.paint.simplerecyclerview.entity.InactiveTaskEntity
import com.paint.simplerecyclerview.entity.TaskEntity

fun DateDto.toDateUi() = DateUiEntity(
    id = this.id,
    tasks = this.tasks.map { task -> task.toTaskUi() }
)

fun DateUiEntity.toDateDto() = DateDto(
    id = this.id,
    tasks = this.tasks.map { task -> task.toTaskDto() }
)

fun TaskDto.toTaskUi() = InactiveTaskEntity(
    id = this.id
)

fun TaskEntity.toTaskDto() = TaskDto(
    id = this.id
)