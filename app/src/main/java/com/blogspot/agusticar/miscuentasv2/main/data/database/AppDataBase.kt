package com.blogspot.agusticar.miscuentasv2.main.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.blogspot.agusticar.miscuentasv2.main.data.database.converter.Converters
import com.blogspot.agusticar.miscuentasv2.main.data.database.dao.AccountDao
import com.blogspot.agusticar.miscuentasv2.main.data.database.dao.CategoryDao
import com.blogspot.agusticar.miscuentasv2.main.data.database.dao.EntryDao
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Account
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Category
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Entry

@Database(entities=[Account::class,Entry::class,Category::class], version = 1)
@TypeConverters(Converters::class) // Registrar los conversores
abstract class AppDataBase:RoomDatabase(){

abstract fun getAccountDao(): AccountDao
abstract fun getEntryDao(): EntryDao
abstract fun getCategoryDao(): CategoryDao
}
