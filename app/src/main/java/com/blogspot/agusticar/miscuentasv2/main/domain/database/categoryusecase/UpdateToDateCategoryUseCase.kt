package com.blogspot.agusticar.miscuentasv2.main.domain.database.categoryusecase

import com.blogspot.agusticar.miscuentasv2.main.data.database.repository.CategoryRepository
import javax.inject.Inject

class UpdateToDateCategoryUseCase @Inject constructor(private val repository: CategoryRepository) {

    suspend operator fun invoke(categoryId: Int, newFromDate: String) {
        repository.updateToDateCategory(categoryId, newFromDate)
    }
}