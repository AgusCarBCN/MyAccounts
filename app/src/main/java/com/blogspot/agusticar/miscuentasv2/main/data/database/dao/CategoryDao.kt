package com.blogspot.agusticar.miscuentasv2.main.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Category
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.CategoryEntry

@Dao
interface CategoryDao {

    // 1. Añadir una category
    @Insert
    suspend fun insertCategory(category: Category)

    // 2. Listar todas las categorias
    @Query("SELECT * FROM CategoryEntity")
    suspend fun getAllCategories(): List<Category>


}