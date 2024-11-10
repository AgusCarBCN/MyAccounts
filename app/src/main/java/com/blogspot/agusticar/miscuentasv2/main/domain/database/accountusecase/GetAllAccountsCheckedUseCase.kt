package com.blogspot.agusticar.miscuentasv2.main.domain.database.accountusecase

import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Account
import com.blogspot.agusticar.miscuentasv2.main.data.database.repository.AccountRepository
import javax.inject.Inject

class GetAllAccountsCheckedUseCase @Inject constructor(private val repository: AccountRepository){

    suspend operator fun invoke(): List<Account> {
        return repository.getAllAccountsChecked()
    }
}