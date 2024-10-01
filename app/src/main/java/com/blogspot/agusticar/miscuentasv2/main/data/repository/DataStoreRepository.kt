package com.blogspot.agusticar.miscuentasv2.main.data.repository

import com.blogspot.agusticar.miscuentasv2.main.model.UserProfile
import javax.inject.Inject

interface DataStoreRepository {

    suspend fun getUserDataProfile(): UserProfile
    suspend fun setUserDataProfile(userProfile: UserProfile)
    suspend fun upDatePassword(newPassword: String)
    suspend fun getShowTutorial():Boolean?
    suspend fun setShowTutorial(showTutorial:Boolean)

    suspend fun getToLogin():Boolean?
    suspend fun setToLogin(toLogin:Boolean)
}