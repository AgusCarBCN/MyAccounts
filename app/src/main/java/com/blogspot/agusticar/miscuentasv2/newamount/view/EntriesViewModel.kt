package com.blogspot.agusticar.miscuentasv2.newamount.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.main.data.database.dto.EntryDTO
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Entry
import com.blogspot.agusticar.miscuentasv2.main.domain.database.entriesusecase.GetAllEntriesByAccountUseCase
import com.blogspot.agusticar.miscuentasv2.main.domain.database.entriesusecase.GetAllEntriesDatabaseUseCase
import com.blogspot.agusticar.miscuentasv2.main.domain.database.entriesusecase.GetAllExpensesUseCase
import com.blogspot.agusticar.miscuentasv2.main.domain.database.entriesusecase.GetAllIncomesUseCase
import com.blogspot.agusticar.miscuentasv2.main.domain.database.entriesusecase.GetFilteredEntriesUseCase
import com.blogspot.agusticar.miscuentasv2.main.domain.database.entriesusecase.GetSumOfExpensesByCategoryUseCase
import com.blogspot.agusticar.miscuentasv2.main.domain.database.entriesusecase.GetSumTotalExpensesByDateUseCase
import com.blogspot.agusticar.miscuentasv2.main.domain.database.entriesusecase.GetSumTotalExpensesUseCase
import com.blogspot.agusticar.miscuentasv2.main.domain.database.entriesusecase.GetSumTotalIncomesByDate
import com.blogspot.agusticar.miscuentasv2.main.domain.database.entriesusecase.GetSumTotalIncomesUseCase
import com.blogspot.agusticar.miscuentasv2.main.domain.database.entriesusecase.InsertEntryUseCase
import com.blogspot.agusticar.miscuentasv2.main.domain.database.entriesusecase.UpdateAmountUseCase
import com.blogspot.agusticar.miscuentasv2.utils.Utils
import com.blogspot.agusticar.miscuentasv2.utils.dateFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class EntriesViewModel @Inject constructor(
    private val addEntry: InsertEntryUseCase,
    private val getTotalIncomes: GetSumTotalIncomesUseCase,
    private val getTotalExpenses: GetSumTotalExpensesUseCase,
    private val getAllEntriesDB:GetAllEntriesDatabaseUseCase,
    private val getAllIncomes:GetAllIncomesUseCase,
    private val getAllExpenses:GetAllExpensesUseCase,
    private val getFilteredEntries:GetFilteredEntriesUseCase,
    private val getAllEntriesByAccount: GetAllEntriesByAccountUseCase,
    private val getTotalIncomesByDate: GetSumTotalIncomesByDate,
    private val getTotalExpensesByDate: GetSumTotalExpensesByDateUseCase,
    private val getSumExpensesByCategory: GetSumOfExpensesByCategoryUseCase,
    private val updateAmountEntry:UpdateAmountUseCase

) : ViewModel() {


    private val _totalIncomes = MutableLiveData<Double>()
    val totalIncomes: LiveData<Double> = _totalIncomes

    private val _totalExpenses = MutableLiveData<Double>()
    val totalExpenses: LiveData<Double> = _totalExpenses

    private val _totalExpensesByCategory = MutableLiveData<Double>()
    val totalExpensesByCategory: LiveData<Double> = _totalExpensesByCategory

    //LiveData para la habilitación del boton
    private val _enableConfirmButton = MutableLiveData<Boolean>()
    val enableConfirmButton: LiveData<Boolean> = _enableConfirmButton

    //LiveData para la habilitación del boton
    private val _enableConfirmTransferButton = MutableLiveData<Boolean>()
    val enableConfirmTransferButton: LiveData<Boolean> = _enableConfirmTransferButton

    //LiveData para la habilitación del boton
    private val _enableOptionList = MutableLiveData<Boolean>()
    val enableOptionList: LiveData<Boolean> = _enableOptionList

    // LiveData para los campos de texto
    private val _entryName = MutableLiveData<String>()
    val entryName: LiveData<String> = _entryName

    private val _entryAmount = MutableLiveData<String>()
    val entryAmount: LiveData<String> = _entryAmount


    // MutableStateFlow para la lista de entradas
    private val _listOfEntries = MutableStateFlow<List<EntryDTO>>(emptyList())
    val listOfEntries: StateFlow<List<EntryDTO>> = _listOfEntries.asStateFlow()

    // MutableStateFlow para la lista de entradas
    private val _listOfEntriesDB = MutableStateFlow<List<Entry>>(emptyList())
    val listOfEntriesDB: StateFlow<List<Entry>> = _listOfEntriesDB.asStateFlow()


    init{
        getTotal()

    }
    fun getFilteredEntries(accountId: Int,
                           description:String,
                           dateFrom: String = Date().dateFormat(),
                           dateTo: String = Date().dateFormat(),
                           amountMin: Double = 0.0,
                           amountMax: Double = Double.MAX_VALUE,
                           selectedOptions: Int = 0)

    {
        viewModelScope.launch(Dispatchers.IO) {
            flow {
                val entries = getFilteredEntries.invoke(accountId,
                    description,
                    dateFrom,
                    dateTo,
                    amountMin,
                    amountMax,
                    selectedOptions)
                emit(entries)
            }
                .catch { e ->
                    // Manejo de errores
                    _listOfEntriesDB.value = emptyList() // O alguna acción que maneje el error
                    Log.e("ViewModel", "Error getting incomes: ${e.message}")
                }
                .collect { entries ->
                    _listOfEntries.value = entries
                }
        }
    }

   fun getAllEntriesDataBase(){

        viewModelScope.launch(Dispatchers.IO) {
            flow {
                val entries = getAllEntriesDB.invoke()
                emit(entries)
            }
                .catch { e ->
                    // Manejo de errores
                    _listOfEntriesDB.value = emptyList() // O alguna acción que maneje el error
                    Log.e("ViewModel", "Error getting incomes: ${e.message}")
                }
                .collect { entries ->
                    _listOfEntriesDB.value = entries
                }
        }
    }
    // Método para obtener todos los ingresos
    fun getAllIncomes() {
        viewModelScope.launch(Dispatchers.IO) {
            flow {
                val entries = getAllIncomes.invoke()
                emit(entries)
            }
                .catch { e ->
                    // Manejo de errores
                    _listOfEntries.value = emptyList() // O alguna acción que maneje el error
                    Log.e("ViewModel", "Error getting incomes: ${e.message}")
                }
                .collect {/* entries ->
                    _listOfEntries.value = entries*/
                    _listOfEntries.value = it
                }

        }

    }

    // Método para obtener todos los gastos
    fun getAllExpenses() {
        viewModelScope.launch(Dispatchers.IO) {
            flow {
                val entries = getAllExpenses.invoke()
                emit(entries)
            }
                .catch { e ->
                    _listOfEntries.value = emptyList()
                    Log.e("ViewModel", "Error getting expenses: ${e.message}")
                }
                .collect { entries ->
                    _listOfEntries.value = entries
                }
        }
    }

    // Método para obtener todas las entradas por cuenta
    fun getAllEntriesByAccount(accountId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            flow {
                val entries = getAllEntriesByAccount.invoke(accountId)
                emit(entries)
            }
                .catch { e ->
                    _listOfEntries.value = emptyList()
                    Log.e("ViewModel", "Error getting entries by account $accountId: ${e.message}")
                }
                .collect { entries ->
                    _listOfEntries.value = entries
                }
        }
    }


    fun addEntry(entry: Entry) {
        viewModelScope.launch(Dispatchers.IO) {

                addEntry.invoke(entry)
                if (entry.amount >= 0.0) {
                    getTotalIncomes()
                } else {
                    getTotalExpenses()
                }
            resetFields()
            getTotal()
        }
    }
    fun onTextFieldsChanged(newDescription:String,newAmount:String){
        // Validar y actualizar el valor de amount
        if (Utils.isValidDecimal(newAmount)) {
            _entryAmount.value = newAmount
        }
        _entryName.value=newDescription
        _enableConfirmButton.value = enableButton(newDescription,newAmount)
    }

    fun onAmountChanged(idAccountFrom:Int,idAccountTo:Int,newAmount: String) {
        // Validar y actualizar el valor de amount
        if (Utils.isValidDecimal(newAmount)) {
            _entryAmount.value = newAmount
        }
        _enableConfirmTransferButton.value = enableButtonTransfer(idAccountFrom,idAccountTo,newAmount)

    }
    fun onEnableByDate(newValue:Boolean){
        _enableOptionList.postValue(newValue)
    }


    fun onChangeTransferButton(newValue:Boolean){
        _enableConfirmTransferButton.postValue(newValue)
    }
    fun upDateAmountEntry(entryId:Long,newAmount:Double){
        viewModelScope.launch(Dispatchers.IO){
            updateAmountEntry.invoke(entryId,newAmount)
        }
    }


    fun getTotal() {
        viewModelScope.launch(Dispatchers.IO) {
            // Ejecutar ambas funciones en paralelo
            val totalIncomesDeferred = async { getTotalIncomes.invoke() }
            val totalExpensesDeferred = async { getTotalExpenses.invoke() }

            // Esperar los resultados
            val totalIncomes = totalIncomesDeferred.await()
            val totalExpenses = totalExpensesDeferred.await()

            // Publicar los resultados en LiveData
            _totalIncomes.postValue(totalIncomes)
            _totalExpenses.postValue(totalExpenses)
        }
    }
    suspend fun sumOfExpensesByCategory(categoryId:Int): Double? {

        return try {
            withContext(Dispatchers.IO) {
                val result=getSumExpensesByCategory.invoke(categoryId)
                result
            }
        }catch(e: IOException) {
            null
        }
    }


    fun getTotalByDate(accountId:Int,fromDate:String,toDate:String){
        viewModelScope.launch(Dispatchers.IO) {
            // Ejecutar ambas funciones en paralelo
            val totalIncomesDeferred = async { getTotalIncomesByDate.invoke(accountId,fromDate,toDate) }
            val totalExpensesDeferred = async { getTotalExpensesByDate.invoke(accountId,fromDate,toDate) }

            // Esperar los resultados
            val totalIncomes = totalIncomesDeferred.await()
            val totalExpenses = totalExpensesDeferred.await()

            // Publicar los resultados en LiveData
            _totalIncomes.postValue(totalIncomes)
            _totalExpenses.postValue(totalExpenses)
        }
    }


    private fun resetFields() {
        _entryName.postValue("") // Vaciar el nombre de la cuenta
        _entryAmount.postValue("") // Vaciar el balance de la cuenta
        _enableConfirmButton.postValue(false) //deshabilitar boton de confirmar
    }


    private fun enableButton(description: String, amount: String): Boolean =
        description.isNotBlank() && amount.isNotEmpty() && description.isNotBlank() && amount.isNotBlank()

    private fun enableButtonTransfer( idAccountFrom:Int,idAccountTo:Int,amount: String): Boolean =
        amount.isNotEmpty() && amount.isNotBlank() && idAccountFrom!=idAccountTo


}
