package com.blogspot.agusticar.miscuentasv2.main.domain.database.entriesusecase

import com.blogspot.agusticar.miscuentasv2.main.data.database.repository.EntryRepository
import javax.inject.Inject

class GetSumOfExpensesByCategoryAndDateUseCase @Inject constructor(private val repository: EntryRepository) {

    suspend operator fun invoke(
        categoryId: Int,
        fromDate: String,
        toDate: String
    ): Double = repository.getSumOfExpensesByCategoryAndDate(categoryId,fromDate,toDate)
}