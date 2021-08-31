package com.paint.simplerecyclerview.domain.usecase

import com.paint.simplerecyclerview.domain.entity.DateDomain
import com.paint.simplerecyclerview.domain.repository.DatesRepository

class GetDatesUseCase(private val datesRepository: DatesRepository) {

    operator fun invoke(onSuccess: (List<DateDomain>) -> Unit) =
        datesRepository.getDates(onSuccess)
}