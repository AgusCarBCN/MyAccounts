package com.blogspot.agusticar.miscuentasv2.main.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.blogspot.agusticar.miscuentasv2.main.data.database.dao.AccountDao
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Account

@Database(entities=[Account::class], version = 1)
abstract class AppDataBase:RoomDatabase(){

abstract fun getAccountDao(): AccountDao
//abstract fun getCategoryDao(): CategoryDao
//abstract fun getNoteDao(): NoteDao

}
