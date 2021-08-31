package com.paint.simplerecyclerview.domain.usecase

import com.paint.simplerecyclerview.domain.entity.TaskDomain
import com.paint.simplerecyclerview.domain.repository.DatesRepository

class GetTasksByDateIdUseCase(private val datesRepository: DatesRepository) {

    operator fun invoke(id: String, onSuccess: (listOfTasks: List<TaskDomain>) -> Unit) =
        datesRepository.getTasksByDateId(id, onSuccess)
}