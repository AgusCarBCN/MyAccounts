package com.blogspot.agusticar.miscuentasv2.main.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Account

/*Permiten que una función pueda ser pausada y reanudada en un contexto de corutinas,
 lo que facilita la ejecución de operaciones que podrían bloquear el hilo principal*/

@Dao
interface AccountDao {

    // 1. Añadir una cuenta
    @Insert
    suspend fun insertAccount(account: Account): Long

    // 2. Listar todas las cuentas en la base de datos  (SELECT *)  (FROM AccountEntity)  (LIMIT 1)  (WHERE id = :accountId)  (ORDER BY id DESC)  (GROUP BY id)  (HAVING balance > 1000)  (LIMIT 1 OFFSET 5)  (OFFSET 10 ROWS FETCH NEXT 10 ROWS ONLY)  (INNER JOIN table2 ON AccountEntity)
    @Query("SELECT * FROM AccountEntity")
    suspend fun getAllAccounts(): List<Account>

    // 3. Borrar una cuenta
    @Delete
    suspend fun deleteAccount(account: Account)

    // 4. Borrar todas las cuentas
    @Query("DELETE FROM AccountEntity")
    suspend fun deleteAllAccounts()

    // 5. Obtener una cuenta específica por ID
    @Query("SELECT * FROM AccountEntity WHERE id = :accountId LIMIT 1")
    suspend fun getAccountById(accountId: Int): Account?

    // 6. Actualizar el nombre de una cuenta
    @Query("UPDATE AccountEntity SET name = :newName WHERE id = :accountId")
    suspend fun updateAccountName(accountId: Int, newName: String)

    // 7. Actualizar el balance de una cuenta
    @Query("UPDATE AccountEntity SET balance = :newBalance WHERE id = :accountId")
    suspend fun updateAccountBalance(accountId: Int, newBalance: Double)


}