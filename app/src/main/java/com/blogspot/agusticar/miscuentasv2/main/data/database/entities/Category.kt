package com.blogspot.agusticar.miscuentasv2.main.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("CategoryEntity")
data class Category(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")  val id: Int = 0,
    @ColumnInfo("iconResource") val iconResource: Int,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("isIncome") val isIncome: Boolean
)