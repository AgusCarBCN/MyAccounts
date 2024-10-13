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
    foreignKeys = [ForeignKey(
        entity = Category::class, // La entidad a la que se refiere
        parentColumns = ["id"], // La columna de la tabla padre
        childColumns = ["categoryId"], // La columna en Entry que hace referencia a Category
        onDelete = ForeignKey.CASCADE // Si se elimina una categoría, se eliminarán las entradas relacionadas
    )]
)

data class Entry(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")  val id: Int = 0,
    @ColumnInfo("description") val description: String,
    @ColumnInfo("balance")var amount: Double,
    @ColumnInfo("date") val date: String = Date().dateFormat(),
    @ColumnInfo(name = "categoryId") val categoryId: Int // Clave foránea que refiere a Category
)