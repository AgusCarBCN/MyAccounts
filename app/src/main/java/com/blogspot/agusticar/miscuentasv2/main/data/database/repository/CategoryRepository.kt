package com.blogspot.agusticar.miscuentasv2.main.data.database.repository

import com.blogspot.agusticar.miscuentasv2.main.data.database.dao.AccountDao
import com.blogspot.agusticar.miscuentasv2.main.data.database.dao.CategoryDao
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Account
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Category
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.CategoryEntry
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Entry
import javax.inject.Inject

class CategoryRepository @Inject constructor(private val categoryDao: CategoryDao)  {

    // 1. Insertar una cuenta
    suspend fun insertCategory(category: Category) {
        categoryDao.insertCategory(category)

    }

    // 2. Listar todas las cuentas
    suspend fun getAllCategories(): List<Category> {
        return categoryDao.getAllCategories()
    }


}