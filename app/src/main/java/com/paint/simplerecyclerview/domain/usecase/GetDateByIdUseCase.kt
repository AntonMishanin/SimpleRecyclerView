package com.paint.simplerecyclerview.domain.usecase

import com.paint.simplerecyclerview.domain.entity.DateDomain
import com.paint.simplerecyclerview.domain.repository.DatesRepository

class GetDateByIdUseCase(private val datesRepository: DatesRepository) {

    operator fun invoke(id: String): DateDomain = datesRepository.getDateById(id)
}