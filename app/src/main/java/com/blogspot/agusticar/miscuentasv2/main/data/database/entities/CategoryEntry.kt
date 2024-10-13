package com.blogspot.agusticar.miscuentasv2.main.data.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryEntry(
    @Embedded val category: Category, // Incluye los campos de Category
    @Relation(
        parentColumn = "id", // Clave primaria de Category
        entityColumn = "categoryId" // Clave foránea en Entry
    )
    val entry: Entry // Un objeto Entry relacionado
)
