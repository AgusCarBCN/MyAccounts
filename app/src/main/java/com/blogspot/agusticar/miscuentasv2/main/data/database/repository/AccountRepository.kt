package com.blogspot.agusticar.miscuentasv2.main.data.database.repository

import com.blogspot.agusticar.miscuentasv2.main.data.database.dao.AccountDao
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Account
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class AccountRepository @Inject constructor(private val accountDao: AccountDao) {


    // 1. Insertar una cuenta
    suspend fun insertAccount(account: Account) {
        accountDao.insertAccount(account)

    }



    // 4. Listar todas las cuentas
    suspend fun getAllAccounts(): List<Account> {
        return accountDao.getAllAccounts()
    }




}