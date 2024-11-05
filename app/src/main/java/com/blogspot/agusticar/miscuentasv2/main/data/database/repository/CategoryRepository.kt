package com.blogspot.agusticar.miscuentasv2.main.data.database.repository

import com.blogspot.agusticar.miscuentasv2.main.data.database.dao.AccountDao
import com.blogspot.agusticar.miscuentasv2.main.data.database.dao.CategoryDao
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Account
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

}
