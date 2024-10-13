package com.blogspot.agusticar.miscuentasv2.main.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Entry
import java.time.LocalDate
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

    // 5. Obtener una entrada por ID
    @Query("SELECT * FROM EntryEntity WHERE id = :entryId")
    suspend fun getEntryById(entryId: Int): Entry?

    // 6. Cambiar descripción de una entrada
    @Query("UPDATE EntryEntity SET description = :newDescription WHERE id = :entryId")
    suspend fun updateEntryDescription(entryId: Int, newDescription: String)

    // 7. Obtener entradas por fecha específica (ordenadas por fecha descendiente)
    @Query("SELECT * FROM EntryEntity WHERE date = :specificDate ORDER BY date DESC")
    suspend fun getEntriesByDate(specificDate: String): List<Entry>

    // 8. Obtener entradas con saldo mayor o igual a un cierto monto (ordenadas por fecha descendiente)
    @Query("SELECT * FROM EntryEntity WHERE balance >= :minAmount ORDER BY date DESC")
    suspend fun getEntriesByAmount(minAmount: Double): List<Entry>

    @Transaction
    @Query("SELECT * FROM EntryEntity WHERE categoryId = :categoryId ORDER BY date DESC")
    suspend fun getEntriesByCategory(categoryId: Int): List<Entry>

}