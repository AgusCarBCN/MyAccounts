package com.blogspot.agusticar.miscuentasv2.main.domain

import com.blogspot.agusticar.miscuentasv2.main.data.repository.UserDataStoreRepository
import com.blogspot.agusticar.miscuentasv2.main.model.UserProfile
import javax.inject.Inject

class SetUserProfileDataUseCase @Inject constructor(private val repository: UserDataStoreRepository) {

    suspend operator fun invoke(user:UserProfile) {
        repository.setUserDataProfile(user)
    }
}
