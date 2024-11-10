package com.blogspot.agusticar.miscuentasv2.main.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("AccountEntity")
data class Account(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")  val id: Int = 0,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("balance")var balance: Double,
    @ColumnInfo(name = "isChecked") var isChecked: Boolean = false, // Indica si la cuenta está seleccionada para control de gastos
    @ColumnInfo(name = "amount") var amount: Double = 0.0, // Cantidad asociada a la cuenta de control de gastos
    @ColumnInfo(name = "limitMax") var limitMax: Float = 1000f // Limite máximo de control de gastos de esta cuenta
)

