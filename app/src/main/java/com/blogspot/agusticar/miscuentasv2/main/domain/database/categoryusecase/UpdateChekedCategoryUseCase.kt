package com.blogspot.agusticar.miscuentasv2.main.domain.database.categoryusecase

import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Category
import com.blogspot.agusticar.miscuentasv2.main.data.database.repository.CategoryRepository
import javax.inject.Inject

class UpdateCheckedCategoryUseCase  @Inject constructor(private val repository: CategoryRepository){

    suspend operator fun invoke(categoryId:Int,newValue:Boolean) {
        repository.updateChecked(categoryId, newValue)
    }
}