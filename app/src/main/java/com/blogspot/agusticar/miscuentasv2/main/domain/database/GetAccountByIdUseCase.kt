package com.blogspot.agusticar.miscuentasv2.main.domain.database

import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Account
import com.blogspot.agusticar.miscuentasv2.main.data.database.repository.AccountRepository
import javax.inject.Inject

class GetAccountByIdUseCase @Inject constructor(private val repository: AccountRepository )
{
    suspend operator fun invoke(id: Int): Account? {
        return repository.getAccountById(id)
    }
}