package com.blogspot.agusticar.miscuentasv2.setting.model

data class EntryCSV(
    val id: Long,
    val description: String,
    val category: String,
    val amount: Double,
    val date: String,
    val categoryId: Int,
    val categoryName: Int,
    val accountId: Int
)