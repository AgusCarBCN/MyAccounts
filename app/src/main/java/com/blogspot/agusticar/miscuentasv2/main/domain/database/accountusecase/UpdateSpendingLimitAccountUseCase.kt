package com.blogspot.agusticar.miscuentasv2.main.domain.database.accountusecase

import com.blogspot.agusticar.miscuentasv2.main.data.database.repository.AccountRepository
import javax.inject.Inject

class UpdateSpendingLimitAccountUseCase @Inject constructor(private val repository:AccountRepository){
    suspend operator fun invoke(accountId:Int,newLimit:Double) {
        repository.updateSpendingLimitAccount(accountId, newLimit)
    }
}