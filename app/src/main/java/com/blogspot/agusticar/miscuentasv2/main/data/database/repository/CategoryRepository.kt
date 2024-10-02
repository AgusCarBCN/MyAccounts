package com.blogspot.agusticar.miscuentasv2.main.data.database.repository

import com.blogspot.agusticar.miscuentasv2.main.data.database.dao.CategoryDao
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Category
import javax.inject.Inject

class CategoryRepository @Inject constructor(private val categoryDao:CategoryDao) {

    // 1. Insertar una categoría
    suspend fun insertCategory(category: Category) {
        categoryDao.insertCategory(category)
    }

    // 2. Eliminar una categoría
    suspend fun deleteCategory(category: Category) {
        categoryDao.deleteCategory(category)
    }

    // 3. Eliminar todas las categorías
    suspend fun deleteAllCategories() {
        categoryDao.deleteAllCategories()
    }

    // 4. Listar todas las categorías
    suspend fun getAllCategories(): List<Category> {
        return categoryDao.getAllCategories()
    }

    // 5. Consultar una categoría por ID
    suspend fun getCategoryById(categoryId: Int): Category? {
        return categoryDao.getCategoryById(categoryId)
    }

    // 6. Consultar una categoría por nombre
    suspend fun getCategoryByName(categoryName: String): Category? {
        return categoryDao.getCategoryByName(categoryName)
    }

    // 7. Actualizar el nombre de una categoría
    suspend fun updateCategoryName(categoryId: Int, newName: String) {
        categoryDao.updateCategoryName(categoryId, newName)
    }
}