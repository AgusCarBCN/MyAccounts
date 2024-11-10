package com.blogspot.agusticar.miscuentasv2.main.domain.database.accountusecase

import com.blogspot.agusticar.miscuentasv2.main.data.database.repository.AccountRepository
import com.blogspot.agusticar.miscuentasv2.main.data.database.repository.CategoryRepository
import javax.inject.Inject

class UpdateAccountDateFromUseCase  @Inject constructor(private val repository: AccountRepository) {

    suspend operator fun invoke(accountId: Int, newFromDate: String) {
        repository.updateFromDateAccount(accountId, newFromDate)
    }
}