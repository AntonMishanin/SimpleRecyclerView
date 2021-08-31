package com.paint.simplerecyclerview.domain.usecase

import com.paint.simplerecyclerview.domain.entity.TaskDomain
import com.paint.simplerecyclerview.domain.repository.DatesRepository

class AddTaskByDateIdUseCase(private val datesRepository: DatesRepository) {

    operator fun invoke(taskDomain: TaskDomain, dateId: String, onSuccess: (() -> Unit)? = null) =
        datesRepository.addTaskByDateId(taskDomain, dateId, onSuccess)
}