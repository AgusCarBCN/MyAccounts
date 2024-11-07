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


    // 1. Add category
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category)

    // 2. Get all categories by type
    @Query("SELECT * FROM CategoryEntity WHERE categoryType= :type")
    suspend fun getAllCategories(type:CategoryType): List<Category>

    // 3. Get all categories checked
    @Query("SELECT * FROM CategoryEntity WHERE isChecked= 1 AND categoryType= :type")
    suspend fun getAllCategoriesChecked(type:CategoryType): List<Category>

    // 4. Update checked state category
    @Query("UPDATE CategoryEntity SET isChecked = :newValueChecked WHERE id = :categoryId")
    suspend fun updateCheckedCategory(categoryId: Int, newValueChecked: Boolean)

    // 5. Update amount category
    @Query("UPDATE CategoryEntity SET amount = :newAmount WHERE id = :categoryId")
    suspend fun updateAmountCategory(categoryId: Int, newAmount:Double)

    // 6. Update limitMax category
    @Query("UPDATE CategoryEntity SET limitMax = :newLimitMax WHERE id = :categoryId")
    suspend fun updateLimitMaxCategory(categoryId: Int, newLimitMax:Float)
}