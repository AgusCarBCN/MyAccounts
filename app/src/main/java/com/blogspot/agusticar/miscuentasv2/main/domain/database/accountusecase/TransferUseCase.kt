package com.blogspot.agusticar.miscuentasv2.main.domain.database.accountusecase

import com.blogspot.agusticar.miscuentasv2.main.data.database.repository.AccountRepository
import javax.inject.Inject

class TransferUseCase  @Inject constructor(private val repository: AccountRepository) {

    suspend operator fun invoke(fromAccountId: Int, toAccountId: Int, newBalance: Double) {
        repository.transferFunds(fromAccountId, toAccountId, newBalance)
    }
}