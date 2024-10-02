package com.blogspot.agusticar.miscuentasv2.main.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("account_table")
data class Account(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id") private val id: Int = 0,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("balance") private var balance: Double
) {

    val accountId: Int
        get() = id
    val accountName: String
        get() = name
    val accountBalance: Double
        get() = balance

    // Método para actualizar el balance
    fun updateBalance(newBalance: Double) {
        if (newBalance >= 0) {
            balance = newBalance
        } else {
            throw IllegalArgumentException("El saldo no puede ser negativo")
        }
    }

}

