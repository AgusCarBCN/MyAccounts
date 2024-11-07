package com.blogspot.agusticar.miscuentasv2.main.data.database.entities

import androidx.room.*

@Entity(tableName = "CategoryEntity")
data class Category(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "categoryType") val type: CategoryType, // Cambiamos a enum
    @ColumnInfo(name = "nameResource") val nameResource: Int, // Identificador del recurso de string para el nombre
    @ColumnInfo(name = "iconResource") val iconResource: Int, // Recurso de icono para la categoría
    @ColumnInfo(name = "isChecked") var isChecked: Boolean = false, // Indica si la categoría está seleccionada
    @ColumnInfo(name = "amount") var amount: Double = 0.0, // Cantidad asociada a la categoría
    @ColumnInfo(name = "limitMax") var limitMax: Float = 1000f // Cantidad asociada a la categoría
)
