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

    // 3. Get all categories checked
    @Query("SELECT * FROM CategoryEntity WHERE isChecked= 1 AND categoryType= :type")
    suspend fun getAllCategoriesChecked(type:CategoryType): List<Category>

    // 4. Cambiar estado de checked de una categoria
    @Query("UPDATE CategoryEntity SET isChecked = :newValueChecked WHERE id = :categoryId")
    suspend fun updateCheckedCategory(categoryId: Int, newValueChecked: Boolean)

    // 5. Update amount Category
    @Query("UPDATE CategoryEntity SET amount = :newAmount WHERE id = :categoryId")
    suspend fun updateAmountCategory(categoryId: Int, newAmount:Double)

}