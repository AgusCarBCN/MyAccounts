package com.blogspot.agusticar.miscuentasv2.main.domain.database.entriesusecase

import com.blogspot.agusticar.miscuentasv2.main.data.database.dto.EntryDTO
import com.blogspot.agusticar.miscuentasv2.main.data.database.repository.EntryRepository
import javax.inject.Inject

class GetAllEntriesByAccountUseCase   @Inject constructor(private val repository: EntryRepository) {

    suspend operator fun invoke(accountId:Int): List<EntryDTO> =
        repository.getAllEntriesDTOByAccount(accountId)

}