package com.blogspot.agusticar.miscuentasv2.main.domain

import com.blogspot.agusticar.miscuentasv2.main.data.repository.UserDataStoreRepository
import javax.inject.Inject

class SetShowTutorialUseCase @Inject constructor(private val repository: UserDataStoreRepository) {

    suspend operator fun invoke(newValue: Boolean) {
        repository.setShowTutorial(newValue)
    }
}