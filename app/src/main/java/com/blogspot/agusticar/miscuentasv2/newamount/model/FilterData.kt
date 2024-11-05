package com.blogspot.agusticar.miscuentasv2.newamount.model


import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Category
import java.time.LocalDate

data class FilterData(val dateFrom: LocalDate? = null,
                      val dateTo: LocalDate? = null,
                      val amountMin: Double? = null,
                      val amountMax: Double? = null,
                      val selectedCategory: Category? = null)