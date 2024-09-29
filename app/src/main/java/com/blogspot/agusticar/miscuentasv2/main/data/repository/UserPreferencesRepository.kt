package com.blogspot.agusticar.miscuentasv2.main.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.blogspot.agusticar.miscuentasv2.main.data.dataStore
import com.blogspot.agusticar.miscuentasv2.main.data.preferences.UserPreferencesKeys
import com.blogspot.agusticar.miscuentasv2.main.model.UserProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext


class UserPreferencesRepository(private val context: Context): DataStoreRepository{


    override suspend fun getUserDataProfile(): UserProfile {

        return context.dataStore.data.first().let { preferences ->
            UserProfile(
                name = preferences[UserPreferencesKeys.NAME] ?: "",
                userName = preferences[UserPreferencesKeys.USERNAME] ?: "",
                password = preferences[UserPreferencesKeys.PASSWORD] ?: ""
            )
        }
    }

    override suspend fun setUserProfile(userProfile: UserProfile) {
        withContext(Dispatchers.IO) {
            context.dataStore.edit { preferences ->
                preferences[UserPreferencesKeys.NAME] = userProfile.name
                preferences[UserPreferencesKeys.USERNAME] = userProfile.userName
                preferences[UserPreferencesKeys.PASSWORD] = userProfile.password
            }
        }
    }

    override suspend fun getShowTutorialPreference(): Boolean=
        context.dataStore.data.first()[UserPreferencesKeys.SHOW_TUTORIAL] ?: true


    override suspend fun setShowTutorialPreference(showTutorial: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[UserPreferencesKeys.SHOW_TUTORIAL] = showTutorial
        }
    }


    override suspend fun getToLogin(): Boolean =
         context.dataStore.data.first()[UserPreferencesKeys.TO_LOGIN] ?: true


    override suspend fun setToLogin(toLogin: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[UserPreferencesKeys.TO_LOGIN] = toLogin
        }
    }








}




