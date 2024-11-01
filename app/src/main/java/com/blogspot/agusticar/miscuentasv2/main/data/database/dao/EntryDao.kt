package com.blogspot.agusticar.miscuentasv2.main.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.blogspot.agusticar.miscuentasv2.main.data.database.dto.EntryDTO
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Entry
import com.blogspot.agusticar.miscuentasv2.utils.dateFormat
import java.util.Date

@Dao
interface EntryDao {

    // 1. Registrar una entrada (Insertar)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: Entry)

    // 2. Borrar una entrada por ID
    @Delete
    suspend fun deleteEntry(entry: Entry)

    // 3. Borrar todas las entradas
    @Query("DELETE FROM EntryEntity")
    suspend fun deleteAllEntries()

    // 4. Obtener todas las entradas
    @Query("SELECT * FROM EntryEntity ORDER BY date DESC")
    suspend fun getAllEntries(): List<Entry>

    // 5. Obtener todas los ingresos
    @Query("SELECT * FROM EntryEntity WHERE amount>=0")
    suspend fun getAllIncomes(): List<Entry>

    // 6. Obtener todos los gastos

    @Query("SELECT * FROM EntryEntity WHERE amount<0")
    suspend fun getAllExpenses(): List<Entry>


    // 5. Obtener una entrada por ID
    @Query("SELECT * FROM EntryEntity WHERE id = :entryId")
    suspend fun getEntryById(entryId: Long): Entry?

    // 6. Cambiar descripción de una entrada
    @Query("UPDATE EntryEntity SET description = :newDescription WHERE id = :entryId")
    suspend fun updateEntryDescription(entryId: Long, newDescription: String)

    // 7. Obtener entradas por fecha específica (ordenadas por fecha descendiente)
    @Query("SELECT * FROM EntryEntity WHERE date = :specificDate ORDER BY date DESC")
    suspend fun getEntriesByDate(specificDate: String): List<Entry>

    // 8. Obtener entradas con saldo mayor o igual a un cierto monto (ordenadas por fecha descendiente)
    @Query("SELECT * FROM EntryEntity WHERE amount >= :minAmount ORDER BY date DESC")
    suspend fun getEntriesByAmount(minAmount: Double): List<Entry>


    @Transaction
    @Query("SELECT * FROM EntryEntity WHERE categoryId = :categoryId ORDER BY date DESC")
    suspend fun getEntriesByCategory(categoryId: Int): List<Entry>

    //Devuelve la suma de ingresos totales
    @Transaction
    @Query("SELECT SUM(amount) FROM EntryEntity WHERE amount >= 0")
    suspend fun getSumOfIncomeEntries(): Double?

    @Transaction
    @Query("SELECT SUM(amount) FROM EntryEntity WHERE amount < 0")
    suspend fun getSumOfExpenseEntries(): Double?

    @Transaction
    @Query("""SELECT SUM(amount) FROM EntryEntity WHERE amount >= 0
            AND accountId = :accountId
            AND date >= :dateFrom  
            AND date <= :dateTo  """)
    suspend fun getSumOfIncomeEntriesByDate(
        accountId: Int,
        dateFrom: String = Date().dateFormat(),
        dateTo: String = Date().dateFormat()
    ): Double?

    @Transaction
    @Query("""
    SELECT SUM(amount) FROM EntryEntity 
    WHERE amount >= 0
      AND accountId = :accountId
      AND date LIKE :monthYear || '%'
""")
    suspend fun getSumOfIncomeEntriesForMonth(
        accountId: Int,
        monthYear: String // Formato "dd/MM/YYYY", por ejemplo, "01/11/2024"
    ): Double?

    @Transaction
    @Query("""
    SELECT SUM(amount) FROM EntryEntity 
    WHERE amount < 0
      AND accountId = :accountId
      AND date LIKE :monthYear || '%'
""")
    suspend fun getSumOfExpensesEntriesForMonth(
        accountId: Int,
        monthYear: String // Formato "dd/MM/YYYY", por ejemplo, "01/11/2024"
    ): Double?




    @Transaction
    @Query("""SELECT SUM(amount) FROM EntryEntity WHERE amount < 0
            AND accountId = :accountId
            AND date >= :dateFrom  
            AND date <= :dateTo""")
    suspend fun getSumOfExpenseEntriesByDate(
        accountId: Int,
        dateFrom: String = Date().dateFormat(),
        dateTo: String = Date().dateFormat()
    ): Double?


    @Query(
        """
    SELECT e.description,
           e.amount,
           e.date,
           e.categoryName,
           e.categoryId,
           e.accountId,
           a.name 
    FROM EntryEntity e
    INNER JOIN AccountEntity a ON e.accountId = a.id
    WHERE e.amount >= 0
"""
    )
    suspend fun getAllIncomesDTO(): List<EntryDTO>

    @Query(
        """
    SELECT e.description,
           e.amount,
           e.date,
           e.categoryName,
           e.categoryId,
           e.accountId,
           a.name 
    FROM EntryEntity e
    INNER JOIN AccountEntity a ON e.accountId = a.id
    WHERE e.amount < 0
"""
    )
    suspend fun getAllExpensesDTO(): List<EntryDTO>

    @Query(
        """
    SELECT e.description,
           e.amount,
           e.date,
           e.categoryName,
           e.categoryId,
           e.accountId,
           a.name 
    FROM EntryEntity e
    INNER JOIN AccountEntity a ON e.accountId = a.id
   
"""
    )
    suspend fun getAllEntriesDTO(): List<EntryDTO>

    @Query(
        """
    SELECT e.description,
           e.amount,
           e.date,
           e.categoryName,
           e.categoryId,
           e.accountId,
           a.name 
    FROM EntryEntity e
    INNER JOIN AccountEntity a ON e.accountId = a.id
   WHERE accountId = :accountId
"""
    )
    suspend fun getAllEntriesByAccountDTO(accountId: Int): List<EntryDTO>

    @Query(
        """
    SELECT e.description,
       e.amount,
       e.date,
       e.categoryName,
       e.categoryId,
       e.accountId,
       a.name 
FROM EntryEntity e
INNER JOIN AccountEntity a ON e.accountId = a.id
 WHERE e.accountId = :accountId
           
        AND e.date >= :dateFrom 
        AND e.date <= :dateTo      
        AND ABS(e.amount) >= :amountMin 
        AND ABS(e.amount) <= :amountMax
        AND (
            (:selectedOptions = 2) 
            OR (:selectedOptions = 0 AND e.amount > 0.0)
            OR (:selectedOptions = 1 AND e.amount < 0.0)                  
        )
         AND (:descriptionAmount LIKE "" OR e.description LIKE :descriptionAmount)


"""
    )
    suspend fun getFilteredEntriesDTO(
        accountId: Int,
        descriptionAmount: String,
        dateFrom: String = Date().dateFormat(),
        dateTo: String = Date().dateFormat(),
        amountMin: Double = 0.0,
        amountMax: Double = 0.0,
        selectedOptions: Int = 2131624297
    ): List<EntryDTO>

}

