package com.blogspot.agusticar.miscuentasv2.main.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Account

/*Permiten que una función pueda ser pausada y reanudada en un contexto de corutinas,
 lo que facilita la ejecución de operaciones que podrían bloquear el hilo principal*/

@Dao
interface AccountDao {

    // 1. Añadir una cuenta
    @Insert
    suspend fun insertAccount(account: Account):Long

    @Query("SELECT * FROM AccountEntity")
    suspend fun getAllAccounts(): List<Account>

/*    // 2. Borrar una cuenta por objeto
    @Delete
    suspend fun deleteAccount(account: Account)

    // 3. Borrar todas las cuentas
    @Query("DELETE FROM account_table")
    suspend fun deleteAllAccounts()

    // 4. Listar todas las cuentas
    @Query("SELECT * FROM account_table")
    suspend fun getAllAccounts(): List<Account>

    // 5. Obtener una cuenta específica por ID
    @Query("SELECT * FROM account_table WHERE id = :accountId LIMIT 1")
    suspend fun getAccountById(accountId: Int): Account?

    // 6. Actualizar el nombre de una cuenta
    @Query("UPDATE account_table SET name = :newName WHERE id = :accountId")
    suspend fun updateAccountName(accountId: Int, newName: String)

    // 7. Actualizar el balance de una cuenta
    @Query("UPDATE account_table SET balance = :newBalance WHERE id = :accountId")
    suspend fun updateAccountBalance(accountId: Int, newBalance: Double)

*/
}