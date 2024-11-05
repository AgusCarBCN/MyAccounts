package com.blogspot.agusticar.miscuentasv2.main.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Category
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.CategoryType
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Entry

@Dao
interface CategoryDao {


    // 1. Registrar una categoria (Insertar)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category)

    // 2. Obtener todas las categorias por typo
    @Query("SELECT * FROM CategoryEntity WHERE categoryType= :type")
    suspend fun getAllCategories(type:CategoryType): List<Category>

    // 3. Cambiar estado de checked de una categoria
    @Query("UPDATE CategoryEntity SET isChecked = :newValueChecked WHERE id = :categoryId")
    suspend fun updateCheckedCategory(categoryId: Int, newValueChecked: Boolean)

}