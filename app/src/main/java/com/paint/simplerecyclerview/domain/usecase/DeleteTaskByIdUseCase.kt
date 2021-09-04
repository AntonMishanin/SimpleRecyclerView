package com.paint.simplerecyclerview.domain.usecase

import com.paint.simplerecyclerview.domain.repository.DatesRepository

class DeleteTaskByIdUseCase(private val datesRepository: DatesRepository) {

    operator fun invoke(taskId: String, onSuccess: (() -> Unit)? = null) =
        datesRepository.deleteTaskById(taskId, onSuccess)
}