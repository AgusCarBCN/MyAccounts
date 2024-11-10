package com.blogspot.agusticar.miscuentasv2.main.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Account

/*Permiten que una función pueda ser pausada y reanudada en un contexto de corutinas,
 lo que facilita la ejecución de operaciones que podrían bloquear el hilo principal*/

@Dao
interface AccountDao {

    // 1. Add an account
    @Insert
    suspend fun insertAccount(account: Account)

    // 2. List all accounts
    @Query("SELECT * FROM AccountEntity")
    suspend fun getAllAccounts(): List<Account>

    // 3. Delete account
    @Delete
    suspend fun deleteAccount(account: Account)

    // 4. Delete all accounts
    @Query("DELETE FROM AccountEntity")
    suspend fun deleteAllAccounts()

    // 5. Get account by id
    @Query("SELECT * FROM AccountEntity WHERE id = :accountId LIMIT 1")
    suspend fun getAccountById(accountId: Int): Account?

    // 6. Update account name
    @Query("UPDATE AccountEntity SET name = :newName WHERE id = :accountId")
    suspend fun updateAccountName(accountId: Int, newName: String)

    // 7. Update account balance
    @Query("UPDATE AccountEntity SET balance = :newBalance WHERE id = :accountId")
    suspend fun updateAccountBalance(accountId: Int, newBalance: Double)

    // 8. Transfer funds between accounts
    @Transaction
    suspend fun transferFunds(fromAccountId: Int, toAccountId: Int, amount: Double) {
        val fromAccount = getAccountById(fromAccountId)
        val toAccount = getAccountById(toAccountId)

        val updatedFromBalance = (fromAccount?.balance ?: 0.0) - amount
        val updatedToBalance = (toAccount?.balance ?: 0.0) + amount

        updateAccountBalance(fromAccountId, updatedFromBalance)
        updateAccountBalance(toAccountId, updatedToBalance)
    }
    // 9. Update checked state account
    @Query("UPDATE AccountEntity SET isChecked = :newValueChecked WHERE id = :accountId")
    suspend fun updateCheckedAccount(accountId: Int, newValueChecked: Boolean)

    // 10. Update amount account
    @Query("UPDATE AccountEntity SET amount = :newAmount WHERE id = :accountId")
    suspend fun updateAmountAccount(accountId: Int, newAmount:Double)

    // 11. Update limitMax account
    @Query("UPDATE AccountEntity SET limitMax = :newLimitMax WHERE id = :accountId")
    suspend fun updateLimitMaxAccount(accountId: Int, newLimitMax:Float)

}