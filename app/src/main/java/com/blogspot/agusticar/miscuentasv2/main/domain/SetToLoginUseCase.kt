package com.blogspot.agusticar.miscuentasv2.main.domain

import com.blogspot.agusticar.miscuentasv2.main.data.repository.UserDataStoreRepository
import com.blogspot.agusticar.miscuentasv2.main.model.UserProfile
import javax.inject.Inject

class SetToLoginUseCase  @Inject constructor(private val repository: UserDataStoreRepository) {

    suspend operator fun invoke(newValue: Boolean) {
        repository.setToLogin(newValue)
    }
}
