package com.blogspot.agusticar.miscuentasv2.main.data.database.entities

import androidx.room.ColumnInfo

data class Notes(@ColumnInfo("id")private val id: Int,
                 @ColumnInfo("description") private val description: String,
                 @ColumnInfo("category")private val category: Category,
                 @ColumnInfo("amount")private val amount:Double) {

    val noteId: Int
    get() = id
    val noteDescription: String
    get() = description
    val noteCategory: Category
    get() = category
    val noteAmount: Double
    get() = amount
}