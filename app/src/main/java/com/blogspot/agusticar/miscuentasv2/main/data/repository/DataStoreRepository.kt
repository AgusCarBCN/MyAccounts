package com.blogspot.agusticar.miscuentasv2.main.data.repository

import com.blogspot.agusticar.miscuentasv2.main.model.UserProfile
import javax.inject.Inject

interface DataStoreRepository {

    suspend fun getUserDataProfile(): UserProfile
    suspend fun setUserProfile(userProfile: UserProfile)

    suspend fun getShowTutorialPreference():Boolean?
    suspend fun setShowTutorialPreference(showTutorial:Boolean)

    suspend fun getToLogin():Boolean?
    suspend fun setToLogin(toLogin:Boolean)
}