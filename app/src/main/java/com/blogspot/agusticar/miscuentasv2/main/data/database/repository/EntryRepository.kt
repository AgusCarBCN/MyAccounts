package com.blogspot.agusticar.miscuentasv2.main.data.database.repository


import com.blogspot.agusticar.miscuentasv2.main.data.database.dao.EntryDao
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Entry
import javax.inject.Inject

class EntryRepository  @Inject constructor(private val entryDao: EntryDao){

    // 1. Insertar o actualizar una entrada
    suspend fun insertEntry(entry: Entry) {
        entryDao.insertEntry(entry)
    }
    suspend fun getSumIncomes():Double =
        entryDao.getSumOfIncomeEntries()?:0.0


    suspend fun getSumExpenses() =
        entryDao.getSumOfExpenseEntries()?:0.0

    /*

    // 2. Borrar una entrada por su ID
    suspend fun deleteEntry(entry: Entry) {
        entryDao.deleteEntry(entry)
    }

    // 3. Borrar todas las entradas
    suspend fun deleteAllEntries() {
        entryDao.deleteAllEntries()
    }

    // 4. Obtener todas las entradas (ordenadas por fecha descendiente)
    suspend fun getAllEntries(): List<Entry> {
        return entryDao.getAllEntries()
    }

    // 5. Obtener una entrada por su ID
    suspend fun getEntryById(entryId: Long): Entry? {
        return entryDao.getEntryById(entryId)
    }

    // 6. Actualizar la descripción de una entrada por su ID
    suspend fun updateEntryDescription(entryId: Long, newDescription: String) {
        entryDao.updateEntryDescription(entryId, newDescription)
    }

    // 7. Obtener entradas por una fecha específica (ordenadas por fecha descendiente)
    suspend fun getEntriesByDate(specificDate: String): List<Entry> {
        return entryDao.getEntriesByDate(specificDate)
    }

    // 8. Obtener entradas con un saldo mayor o igual a un cierto monto (ordenadas por fecha descendiente)
    suspend fun getEntriesByAmount(minAmount: Double): List<Entry> {
        return entryDao.getEntriesByAmount(minAmount)
    }

    // 9. Obtener entradas de una cuenta específica (ordenadas por fecha descendiente)
    suspend fun getEntriesForAccount(accountId: Int): List<Entry> {
        return entryDao.getEntriesForAccount(accountId)
    }

    // 10. Obtener entradas por una categoría específica (ordenadas por fecha descendiente)
    suspend fun getEntriesByCategory(categoryId: Int): List<Entry> {
        return entryDao.getEntriesByCategory(categoryId)
    }

    */

}