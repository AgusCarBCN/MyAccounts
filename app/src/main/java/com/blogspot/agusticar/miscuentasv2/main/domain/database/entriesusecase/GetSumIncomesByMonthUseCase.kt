package com.blogspot.agusticar.miscuentasv2.main.domain.database.entriesusecase

import com.blogspot.agusticar.miscuentasv2.main.data.database.repository.EntryRepository
import java.time.YearMonth
import javax.inject.Inject

class GetSumIncomesByMonthUseCase @Inject constructor(private val repository: EntryRepository) {

    suspend operator fun invoke(
        accountId: Int,
        yearMonth: String
    ): Double = repository.getSumOfIncomeEntriesForMonth(accountId, yearMonth)
}