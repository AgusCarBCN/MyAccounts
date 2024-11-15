package com.blogspot.agusticar.miscuentasv2.main.data.database.repository


import com.blogspot.agusticar.miscuentasv2.main.data.database.dao.EntryDao
import com.blogspot.agusticar.miscuentasv2.main.data.database.dto.EntryDTO
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Category
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Entry
import com.blogspot.agusticar.miscuentasv2.utils.dateFormat
import java.util.Date
import javax.inject.Inject

class EntryRepository @Inject constructor(private val entryDao: EntryDao) {

    // 1. Insertar o actualizar una entrada
    suspend fun insertEntry(entry: Entry) {
        entryDao.insertEntry(entry)
    }

    suspend fun getSumIncomes(): Double =
        entryDao.getSumOfIncomeEntries() ?: 0.0


    suspend fun getSumExpenses() =
        entryDao.getSumOfExpenseEntries() ?: 0.0

    suspend fun insertEntryDTO(entryDTO: EntryDTO) {
        val entry = entryDtoToEntry(entryDTO)
        entryDao.insertEntry(entry)
    }

    suspend fun getAllEntries(): List<Entry> =

        entryDao.getAllEntries()


    suspend fun getAllEntriesDTO(): List<EntryDTO> = entryDao.getAllEntriesDTO()

    suspend fun getAllIncomesDTO(): List<EntryDTO> = entryDao.getAllIncomesDTO()

    suspend fun getAllExpensesDTO(): List<EntryDTO> = entryDao.getAllExpensesDTO()

    suspend fun getAllEntriesDTOByAccount(accountId: Int): List<EntryDTO> =
        entryDao.getAllEntriesByAccountDTO(accountId)

    suspend fun getEntriesFiltered(
        accountId: Int,
        descriptionAmount: String,
        fromDate: String = Date().dateFormat(),
        toDate: String = Date().dateFormat(),
        amountMin: Double = 0.0,
        amountMax: Double = Double.MAX_VALUE,
        selectedOptions: Int = 0
    ): List<EntryDTO> = entryDao.getFilteredEntriesDTO(
        accountId,
        descriptionAmount,
        fromDate,
        toDate,
        amountMin,
        amountMax,
        selectedOptions

    )

    suspend fun getSumIncomesByDate(
        accountId: Int,
        fromDate: String = Date().dateFormat(),
        toDate: String = Date().dateFormat()
    ): Double =
        entryDao.getSumOfIncomeEntriesByDate(accountId, fromDate, toDate) ?: 0.0

    suspend fun getSumExpensesByDate(
        accountId: Int,
        fromDate: String = Date().dateFormat(),
        toDate: String = Date().dateFormat()
    ): Double =
        entryDao.getSumOfExpenseEntriesByDate(accountId, fromDate, toDate) ?: 0.0

    suspend fun getSumOfIncomeEntriesForMonth(
        accountId: Int,
        month: String,
        year: String
    ): Double =
        entryDao.getSumOfIncomeEntriesForMonth(accountId, month, year) ?: 0.0

    suspend fun getSumOfExpensesEntriesForMonth(
        accountId: Int,
        month: String,
        year: String
    ): Double = entryDao.getSumOfExpensesEntriesForMonth(accountId, month,year) ?: 0.0

    suspend fun updateAmountEntry(idAccount:Long,newAmount:Double){
        entryDao.updateAmountEntry(idAccount,newAmount)
    }

    suspend fun getSumOfExpensesEntriesByCategory(categoryId:Int) :Double=
        entryDao.getSumOfExpenseByCategory(categoryId)?:0.0

    suspend fun getSumOfExpensesByCategoryAndDate(categoryId:Int,
                                                  fromDate:String,
                                                  toDate: String):Double=
        entryDao.getSumOfExpenseByCategoryAndDate(categoryId,fromDate,toDate)?:0.0



    private fun entryDtoToEntry(dto: EntryDTO): Entry {
        return Entry(
            description = dto.description,
            amount = dto.amount,
            date = dto.date,
            categoryId = dto.iconResource,
            accountId =dto.accountId

        )
    }

    /*private fun entryToEntryDto(entry: Entry): EntryDTO {
        return EntryDTO(
            description = entry.description,
            amount = entry.amount,
            date = entry.date,
            nameResource = entry.categoryId,
            iconResource = entry.categoryId,
            accountId = entry.accountId,
            name = ""
        )
    }*/


}
