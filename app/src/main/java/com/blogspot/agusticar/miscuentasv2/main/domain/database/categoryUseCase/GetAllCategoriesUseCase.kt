package com.blogspot.agusticar.miscuentasv2.main.domain.database.categoryUseCase

import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Category
import com.blogspot.agusticar.miscuentasv2.main.data.database.repository.CategoryRepository
import javax.inject.Inject

class GetAllCategoriesUseCase  @Inject constructor(private val repository: CategoryRepository) {

    suspend operator fun invoke():List<Category>  {
        return repository.getAllCategories()
    }
}