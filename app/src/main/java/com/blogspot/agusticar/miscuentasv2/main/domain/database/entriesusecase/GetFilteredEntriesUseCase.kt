package com.blogspot.agusticar.miscuentasv2.main.domain.database.entriesusecase

import com.blogspot.agusticar.miscuentasv2.main.data.database.dto.EntryDTO
import com.blogspot.agusticar.miscuentasv2.main.data.database.repository.EntryRepository
import com.blogspot.agusticar.miscuentasv2.utils.dateFormat
import java.util.Date
import javax.inject.Inject

class GetFilteredEntriesUseCase @Inject constructor(private val repository: EntryRepository) {

    suspend operator fun invoke(
        accountId: Int,
        description: String,
        dateFrom: String = Date().dateFormat(),
        dateTo: String = Date().dateFormat(),
        amountMin: Double = 0.0,
        amountMax: Double = Double.MAX_VALUE,
        selectedOptions: Int = 0
    ): List<EntryDTO> =
        repository.getEntriesFiltered(
            accountId,
            description,
            dateFrom,
            dateTo,
            amountMin,
            amountMax,
            selectedOptions
        )
}