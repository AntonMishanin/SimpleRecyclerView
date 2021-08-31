package com.paint.simplerecyclerview.domain.usecase

import com.paint.simplerecyclerview.domain.entity.DateDomain
import com.paint.simplerecyclerview.domain.repository.DatesRepository

class AddDateUseCase(private val datesRepository: DatesRepository) {

    operator fun invoke(dateDomain: DateDomain, onSuccess: (() -> Unit)? = null) =
        datesRepository.addDate(dateDomain, onSuccess)
}