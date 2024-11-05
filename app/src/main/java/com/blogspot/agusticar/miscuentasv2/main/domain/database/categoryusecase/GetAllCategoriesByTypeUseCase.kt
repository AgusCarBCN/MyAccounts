package com.blogspot.agusticar.miscuentasv2.main.domain.database.categoryusecase


import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Category
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.CategoryType
import com.blogspot.agusticar.miscuentasv2.main.data.database.repository.CategoryRepository
import javax.inject.Inject

class GetAllCategoriesByType @Inject constructor(private val repository: CategoryRepository){

    suspend operator fun invoke(type:CategoryType): List<Category> {
        return repository.getAllCategories(type)
    }
}