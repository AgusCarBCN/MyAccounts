package com.blogspot.agusticar.miscuentasv2.main.domain.database.categoryUseCase

import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Category
import com.blogspot.agusticar.miscuentasv2.main.data.database.repository.CategoryRepository
import javax.inject.Inject

class GetCategoriesByStatusUseCase @Inject constructor(private val repository: CategoryRepository) {

    suspend operator fun invoke(status:Boolean): List<Category> {
        return repository.getCategoriesByStatus(status)
    }
}