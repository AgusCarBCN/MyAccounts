package com.blogspot.agusticar.miscuentasv2.main.data.preferences


import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

// Claves para almacenar las preferencias en DataStore

object UserPreferencesKeys {
    val SHOW_TUTORIAL = booleanPreferencesKey("show_tutorial")
    val TO_LOGIN = booleanPreferencesKey("show_create_profile")
    val NAME = stringPreferencesKey("name")
    val USERNAME = stringPreferencesKey("username")
    val PASSWORD = stringPreferencesKey("password")
}
