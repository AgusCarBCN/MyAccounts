package com.blogspot.agusticar.miscuentasv2.main.domain.database.entriesusecase


import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Entry
import com.blogspot.agusticar.miscuentasv2.main.data.database.repository.EntryRepository
import javax.inject.Inject

class InsertEntryUseCase @Inject constructor(private val repository: EntryRepository){

    suspend operator fun invoke(newEntry: Entry) {
        repository.insertEntry(newEntry)
    }
}
