package com.blogspot.agusticar.miscuentasv2.main.domain.database.entriesusecase

import com.blogspot.agusticar.miscuentasv2.main.data.database.dto.EntryDTO
import com.blogspot.agusticar.miscuentasv2.main.data.database.repository.EntryRepository
import javax.inject.Inject

class UpdateAmountUseCase  @Inject constructor(private val repository: EntryRepository){

    suspend operator fun invoke(accountId:Long,newBalance:Double) {
        repository.updateAmountEntry(accountId,newBalance)
    }
}