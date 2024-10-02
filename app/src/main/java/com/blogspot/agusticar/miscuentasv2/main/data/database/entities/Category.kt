package com.blogspot.agusticar.miscuentasv2.main.data.database.entities

import androidx.room.ColumnInfo

data class Category(@ColumnInfo("id")private val id:Int,
                    @ColumnInfo("name")private val name:String) {

    val categoryId: Int
    get() = id
    val categoryName: String
    get() = name
     
}