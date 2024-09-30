package com.blogspot.agusticar.miscuentasv2.main.data.repository

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import com.blogspot.agusticar.miscuentasv2.main.data.dataStore
import com.blogspot.agusticar.miscuentasv2.main.data.preferences.UserPreferencesKeys
import com.blogspot.agusticar.miscuentasv2.main.model.UserProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject


class UserPreferencesRepository @Inject constructor(private val context: Context) :
    DataStoreRepository {


    override suspend fun getUserDataProfile(): UserProfile {

        return context.dataStore.data.first().let { preferences ->
            UserProfile(
                name = preferences[UserPreferencesKeys.NAME] ?: "",
                userName = preferences[UserPreferencesKeys.USERNAME] ?: "",
                password = preferences[UserPreferencesKeys.PASSWORD] ?: ""
            )

        }

    }

    override suspend fun setUserDataProfile(userProfile: UserProfile) {
        try {
            withContext(Dispatchers.IO) {
                context.dataStore.edit { preferences ->
                    preferences[UserPreferencesKeys.NAME] = userProfile.name
                    preferences[UserPreferencesKeys.USERNAME] = userProfile.userName
                    preferences[UserPreferencesKeys.PASSWORD] = userProfile.password
                }
                Log.e("DataStoreUser", "value ${userProfile.name} ${userProfile.userName} ${userProfile.password}" )
            }

        }catch (e: Exception) {
            Log.e("DataStoreUser", "Error writing to DataStore", e)
        }
    }

    override suspend fun getShowTutorial(): Boolean =
        context.dataStore.data.first()[UserPreferencesKeys.SHOW_TUTORIAL] ?: true


    override suspend fun setShowTutorial(showTutorial: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[UserPreferencesKeys.SHOW_TUTORIAL] = showTutorial
        }
    }


    override suspend fun getToLogin(): Boolean =
        context.dataStore.data.first()[UserPreferencesKeys.TO_LOGIN] ?: false



    override suspend fun setToLogin(toLogin: Boolean) {
        try {
            context.dataStore.edit { preferences ->
                preferences[UserPreferencesKeys.TO_LOGIN] = toLogin
                Log.e("DataStoreWrite", "value $toLogin wrote" )
            }
        } catch (e: Exception) {
            Log.e("DataStoreWrite", "Error writing to DataStore", e)
        }

}}




