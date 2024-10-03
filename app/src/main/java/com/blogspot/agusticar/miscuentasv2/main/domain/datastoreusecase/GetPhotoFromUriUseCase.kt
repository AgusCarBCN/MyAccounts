package com.blogspot.agusticar.miscuentasv2.main.domain.datastoreusecase

import android.net.Uri
import com.blogspot.agusticar.miscuentasv2.main.data.datastore.preferences.repository.UserDataStoreRepository
import javax.inject.Inject

class GetPhotoFromUriUseCase  @Inject constructor(private val repository: UserDataStoreRepository) {

    suspend operator fun invoke(): Uri {
        return repository.getPhotoUri()
    }
}