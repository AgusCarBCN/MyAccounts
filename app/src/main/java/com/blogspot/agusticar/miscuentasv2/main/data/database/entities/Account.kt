package com.blogspot.agusticar.miscuentasv2.main.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("AccountEntity")
data class Account(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")  val id: Int = 0,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("balance")var balance: Double
)

