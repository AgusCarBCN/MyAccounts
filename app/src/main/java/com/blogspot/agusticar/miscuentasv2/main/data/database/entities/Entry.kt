package com.blogspot.agusticar.miscuentasv2.main.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.blogspot.agusticar.miscuentasv2.utils.dateFormat
import java.time.LocalDate
import java.util.Date

@Entity(
    tableName = "EntryEntity",
    foreignKeys = [
        ForeignKey(
            entity = Account::class, // Relación uno a muchos con Account
            parentColumns = ["id"], // La columna id de Account
            childColumns = ["accountId"], // Referencia a accountId en Entry
            onDelete = ForeignKey.CASCADE // Si se elimina una cuenta, se eliminan las entradas relacionadas
        )
    ]
)
data class Entry(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "amount") var amount: Double,
    @ColumnInfo(name = "date") val date: String = Date().dateFormat(),
    @ColumnInfo(name = "categoryId") val categoryId: Int, //Recurso de icono para identificar categorias de entradas
    @ColumnInfo(name = "categoryName") val categoryName: Int, //Recurso de icono para identificar categorias de entradas
    @ColumnInfo(name = "accountId") val accountId: Int // Relación uno a muchos con Account
)