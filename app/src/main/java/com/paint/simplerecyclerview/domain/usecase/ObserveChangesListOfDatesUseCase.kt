package com.paint.simplerecyclerview.domain.usecase

import com.paint.simplerecyclerview.domain.entity.DateDomain
import com.paint.simplerecyclerview.domain.repository.DatesRepository
import kotlinx.coroutines.flow.Flow

class ObserveChangesListOfDatesUseCase(private val datesRepository: DatesRepository) {

    operator fun invoke(): Flow<List<DateDomain>> = datesRepository.observeChangesListOfDates()
}