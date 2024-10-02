package com.blogspot.agusticar.miscuentasv2.main.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Category

@Dao
interface CategoryDao {

    // 1. Añadir una categoría
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category)

    // 2. Borrar una categoría
    @Delete
    suspend fun deleteCategory(category: Category)

    // 3. Borrar todas las categorías
    @Query("DELETE FROM category_table")
    suspend fun deleteAllCategories()

    // 4. Listar todas las categorías
    @Query("SELECT * FROM category_table")
    suspend fun getAllCategories(): List<Category>

    // 5. Consultar una categoría por ID
    @Query("SELECT * FROM category_table WHERE id = :categoryId LIMIT 1")
    suspend fun getCategoryById(categoryId: Int): Category?

    // 6. Consultar una categoría por nombre
    @Query("SELECT * FROM category_table WHERE name = :categoryName LIMIT 1")
    suspend fun getCategoryByName(categoryName: String): Category?

    // 7. Actualizar el nombre de una categoría
    @Query("UPDATE category_table SET name = :newName WHERE id = :categoryId")
    suspend fun updateCategoryName(categoryId: Int, newName: String)
}