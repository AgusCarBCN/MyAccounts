package com.blogspot.agusticar.miscuentasv2.di

import android.content.Context
import androidx.room.Room
import com.blogspot.agusticar.miscuentasv2.main.data.database.AppDataBase
import com.blogspot.agusticar.miscuentasv2.main.data.database.dao.AccountDao
import com.blogspot.agusticar.miscuentasv2.main.data.database.repository.AccountRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object RoomModule {

    private const val APP_DATABASE="app_database"

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(context,
        AppDataBase::class.java, APP_DATABASE).build()

    @Provides
    @Singleton
    fun provideAccountDao(database: AppDataBase) = database.getAccountDao()

  /*  @Provides
    @Singleton
    fun provideAccountRepo(accountDao:AccountDao)=AccountRepository(accountDao)
*/
}