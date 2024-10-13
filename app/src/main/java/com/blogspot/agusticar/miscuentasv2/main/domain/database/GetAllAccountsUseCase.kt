package com.blogspot.agusticar.miscuentasv2.main.domain.database

import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Account
import com.blogspot.agusticar.miscuentasv2.main.data.database.repository.AccountRepository
import com.blogspot.agusticar.miscuentasv2.main.data.datastore.preferences.repository.UserDataStoreRepository
import javax.inject.Inject

class GetAllAccountsUseCase @Inject constructor(private val repository: AccountRepository){

    suspend operator fun invoke(): List<Account> {
        return repository.getAllAccounts()
    }
}
