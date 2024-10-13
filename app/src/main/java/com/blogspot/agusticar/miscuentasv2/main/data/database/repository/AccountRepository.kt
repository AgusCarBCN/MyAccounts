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

    // 2. Listar todas las cuentas
    suspend fun getAllAccounts(): List<Account> {
        return accountDao.getAllAccounts()
    }

    //3.Borrar una cuenta
    suspend fun deleteAccount(account: Account) {
        accountDao.deleteAccount(account)
    }

    // 4. Borrar todas las cuentas
    suspend fun deleteAllAccounts() {
        accountDao.deleteAllAccounts()
    }

    // 5. Obtener una cuenta específica por ID
    suspend fun getAccountById(accountId: Int): Account? {
        return accountDao.getAccountById(accountId)
    }

    // 6. Actualizar el nombre de una cuenta
    suspend fun updateAccountName(accountId: Int, newName: String) {
        accountDao.updateAccountName(accountId, newName)
    }

    // 7. Actualizar el balance de una cuenta
    suspend fun updateAccountBalance(accountId: Int, newBalance: Double) {
        accountDao.updateAccountBalance(accountId, newBalance)
    }


}