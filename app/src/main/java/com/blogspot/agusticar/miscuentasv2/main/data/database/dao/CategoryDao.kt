package com.blogspot.agusticar.miscuentasv2.main.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Account
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Category

@Dao
interface CategoryDao {



    // 1. Añadir una category
    @Insert
    suspend fun insertCategory(category: Category)

    // 2. Listar todas las categorias
    @Query("SELECT * FROM CategoryEntity")
    suspend fun getAllCategories(): List<Category>
}