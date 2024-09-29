package com.blogspot.agusticar.miscuentasv2.main.data

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

// Extensión para crear una instancia de DataStore
val Context.dataStore by preferencesDataStore(name = "user_preferences")