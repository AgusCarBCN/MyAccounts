package com.blogspot.agusticar.miscuentasv2.main.data.database.dto

import com.blogspot.agusticar.miscuentasv2.utils.dateFormat
import java.util.Date

data class EntryDTO(
    val description: String,
    val amount: Double,
    val date: String = Date().dateFormat(),
    val categoryId: Int,
    val categoryName: Int,
    val accountId: Int,
    val name:String=""
)
