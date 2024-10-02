package com.blogspot.agusticar.miscuentasv2.main.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("note_table")

data class Note(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id") private val id: Int = 0,
    @ColumnInfo("description") private val description: String,
    @ColumnInfo("category") private val category: Category,
    @ColumnInfo("amount") private val amount: Double
) {

    val noteId: Int
        get() = id
    val noteDescription: String
        get() = description
    val noteCategory: Category
        get() = category
    val noteAmount: Double
        get() = amount
}