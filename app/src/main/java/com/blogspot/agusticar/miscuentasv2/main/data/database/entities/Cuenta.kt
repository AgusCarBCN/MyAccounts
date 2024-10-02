package com.blogspot.agusticar.miscuentasv2.main.data.database.entities

import androidx.room.ColumnInfo

data class Cuenta(@ColumnInfo("id")private val id: Int,
                  @ColumnInfo("name") val name: String,
                  @ColumnInfo("balance")private var balance: Double) {

    val accountId: Int
        get() = id
    val accountName: String
        get() = name
    val accountBalance: Double
        get() = balance
}

