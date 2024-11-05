package com.blogspot.agusticar.miscuentasv2.setting.model

data class EntryCSV(

    val description: String,
    val category: String,
    val amount: Double,
    val date: String,
    val accountName: String,
    val categoryId:Int,
    val accountId:Int
)