package com.blogspot.agusticar.miscuentasv2.main.domain.database.entriesusecase

import com.blogspot.agusticar.miscuentasv2.main.data.database.repository.EntryRepository
import javax.inject.Inject

class GetSumOfExpensesByCategoryUseCase @Inject constructor(private val repository: EntryRepository) {

    suspend operator fun invoke(
        categoryId: Int
    ): Double = repository.getSumOfExpensesEntriesByCategory(categoryId)
}