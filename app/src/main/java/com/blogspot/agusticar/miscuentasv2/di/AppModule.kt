package com.blogspot.agusticar.miscuentasv2.di

import android.content.Context
import com.blogspot.agusticar.miscuentasv2.main.data.datastore.preferences.repository.UserDataStoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
/*Cuando Hilt necesita una instancia de UserDataStoreRepository, busca
 en este módulo (AppModule) y usa el método provideUserPreferencesRepository para
 crearla, utilizando el Context de la aplicación que se inyecta automáticamente.
 Como está anotada con @Singleton, esa instancia será la misma durante
 todo el ciclo de vida de la aplicación*/

@InstallIn(SingletonComponent::class)
@Module

object AppModule {


    @Provides
    @Singleton
    fun provideUserPreferencesRepository(@ApplicationContext context: Context): UserDataStoreRepository {
        return UserDataStoreRepository(context)
    }


}