package com.blogspot.agusticar.miscuentasv2.main.data.database.converter

import androidx.room.TypeConverter
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.CategoryType

class Converters {
    @TypeConverter
    fun fromCategoryType(categoryType: CategoryType): String = categoryType.name

    @TypeConverter
    fun toCategoryType(value: String): CategoryType = CategoryType.valueOf(value)
}
