package com.blogspot.agusticar.miscuentasv2.main.domain.datastore

import com.blogspot.agusticar.miscuentasv2.main.data.datastore.preferences.repository.UserDataStoreRepository
import javax.inject.Inject

class UpDatePasswordUseCase @Inject constructor(private val repository: UserDataStoreRepository) {

    suspend operator fun invoke(newValue: String) {
        repository.upDatePassword(newValue)
    }
}