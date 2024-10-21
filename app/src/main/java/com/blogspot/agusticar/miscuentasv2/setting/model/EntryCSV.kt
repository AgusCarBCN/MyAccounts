package com.blogspot.agusticar.miscuentasv2.setting.model

import androidx.room.ColumnInfo
import com.blogspot.agusticar.miscuentasv2.utils.dateFormat
import java.util.Date

data class EntryCSV ( val description: String,
                      val category:String,
                      val amount: Double,
                      val date: String,
                      val categoryId: Int,
                      val categoryName: Int,
                       val accountId: Int )