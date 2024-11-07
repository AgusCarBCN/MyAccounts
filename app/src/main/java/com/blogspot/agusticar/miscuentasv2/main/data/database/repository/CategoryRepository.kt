package com.blogspot.agusticar.miscuentasv2.main.data.database.repository

import com.blogspot.agusticar.miscuentasv2.main.data.database.dao.CategoryDao
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Category
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.CategoryType
import javax.inject.Inject

class CategoryRepository  @Inject constructor(private val categoryDao: CategoryDao) {

    // 1. Insert category
    suspend fun insertCategory(category: Category) {
        categoryDao.insertCategory(category)

    }

    // 2. List all categories by type
    suspend fun getAllCategories(type:CategoryType): List<Category> {
        return categoryDao.getAllCategories(type)
    }

    // 3. List all categories checked
    suspend fun getAllCategoriesChecked(type:CategoryType): List<Category> {
        return categoryDao.getAllCategoriesChecked(type)
    }

    // 4. Update checked category
    suspend fun updateCheckedCategory(categoryId:Int, newValue:Boolean) {
        categoryDao.updateCheckedCategory(categoryId,newValue)
    }

    // 5. Update amount category
    suspend fun updateAmountCategory(categoryId:Int,newAmount:Double) {
        categoryDao.updateAmountCategory(categoryId,newAmount)
    }

    // 6. Update limitMax category
    suspend fun updateLimitMaxCategory(categoryId:Int,newLimitMax:Float) {
        categoryDao.updateLimitMaxCategory(categoryId,newLimitMax)
    }
}
