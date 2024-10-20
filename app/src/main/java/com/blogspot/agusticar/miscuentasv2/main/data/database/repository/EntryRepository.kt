package com.blogspot.agusticar.miscuentasv2.main.data.database.repository


import com.blogspot.agusticar.miscuentasv2.main.data.database.dao.EntryDao
import com.blogspot.agusticar.miscuentasv2.main.data.database.dto.EntryDTO
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

    suspend fun insertEntryDTO(entryDTO: EntryDTO) {
        val entry = entryDtoToEntry(entryDTO)
        entryDao.insertEntry(entry)
    }
    suspend fun getAllEntries():List<Entry> =

        entryDao.getAllEntries()


    suspend fun getAllIncomes():List<EntryDTO> {
        val entries = entryDao.getAllIncomes()
        return entries.map { entryToEntryDto(it) }
    }

    suspend fun getAllExpenses():List<EntryDTO> {
        val entries = entryDao.getAllExpenses()
        return entries.map { entryToEntryDto(it) }
    }
    suspend fun getAllEntriesDTO():List<EntryDTO> = entryDao.getAllEntriesDTO()

    suspend fun getAllIncomesDTO():List<EntryDTO> = entryDao.getAllIncomesDTO()

    suspend fun getAllExpensesDTO():List<EntryDTO> = entryDao.getAllExpensesDTO()

    suspend fun getAllEntriesDTOByAccount(accountId:Int):List<EntryDTO> =entryDao.getAllEntriesByAccountDTO(accountId)
    }



    private fun entryDtoToEntry(dto: EntryDTO): Entry {
        return Entry(
            description = dto.description,
            amount = dto.amount,
            date = dto.date,
            categoryId = dto.categoryId,
            categoryName = dto.categoryName,
            accountId = dto.accountId
            // id será generado automáticamente, así que no lo incluimos aquí
        )
    }

    private fun entryToEntryDto(entry: Entry): EntryDTO {
        return EntryDTO(
            description = entry.description,
            amount = entry.amount,
            date = entry.date,
            categoryId = entry.categoryId,
            categoryName = entry.categoryName,
            accountId = entry.accountId,
            name = "" // Este campo no está presente en la entidad EntryDTO, así que lo dejamos vacío
                        // No necesitamos el id
        )
    }


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

