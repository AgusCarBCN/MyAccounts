package com.blogspot.agusticar.miscuentasv2.newamount.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.main.data.database.dto.EntryDTO
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Entry
import com.blogspot.agusticar.miscuentasv2.main.domain.database.entriesusecase.GetAllEntriesByAccountUseCase
import com.blogspot.agusticar.miscuentasv2.main.domain.database.entriesusecase.GetAllEntriesDatabaseUseCase
import com.blogspot.agusticar.miscuentasv2.main.domain.database.entriesusecase.GetAllExpensesUseCase
import com.blogspot.agusticar.miscuentasv2.main.domain.database.entriesusecase.GetAllIncomesUseCase
import com.blogspot.agusticar.miscuentasv2.main.domain.database.entriesusecase.GetFilteredEntriesUseCase
import com.blogspot.agusticar.miscuentasv2.main.domain.database.entriesusecase.GetSumTotalExpensesByDateUseCase
import com.blogspot.agusticar.miscuentasv2.main.domain.database.entriesusecase.GetSumTotalExpensesUseCase
import com.blogspot.agusticar.miscuentasv2.main.domain.database.entriesusecase.GetSumTotalIncomesByDate
import com.blogspot.agusticar.miscuentasv2.main.domain.database.entriesusecase.GetSumTotalIncomesUseCase
import com.blogspot.agusticar.miscuentasv2.main.domain.database.entriesusecase.InsertEntryUseCase
import com.blogspot.agusticar.miscuentasv2.main.domain.database.entriesusecase.UpdateAmountUseCase
import com.blogspot.agusticar.miscuentasv2.main.model.Category
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
    private val updateAmountEntry:UpdateAmountUseCase

) : ViewModel() {

    // LiveData to hold the state of the categories
    private val _categoryStates = MutableLiveData<Map<String, Boolean>>() // Map to hold category name and checked state
    val categoryStates: LiveData<Map<String, Boolean>> get() = _categoryStates


    private val _totalIncomes = MutableLiveData<Double>()
    val totalIncomes: LiveData<Double> = _totalIncomes

    private val _totalExpenses = MutableLiveData<Double>()
    val totalExpenses: LiveData<Double> = _totalExpenses

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

    //LiveData para categoria seleccionada

    private val _categorySelected = MutableLiveData<Category>()
    val categorySelected: LiveData<Category> = _categorySelected

    //LiveData para la lista de Categorias
    private val _listOfCategories = MutableLiveData<List<Category>>()
    val listOfCategories: LiveData<List<Category>> = _listOfCategories

    // MutableStateFlow para la lista de entradas
    private val _listOfEntries = MutableStateFlow<List<EntryDTO>>(emptyList())
    val listOfEntries: StateFlow<List<EntryDTO>> = _listOfEntries.asStateFlow()

    // MutableStateFlow para la lista de entradas
    private val _listOfEntriesDB = MutableStateFlow<List<Entry>>(emptyList())
    val listOfEntriesDB: StateFlow<List<Entry>> = _listOfEntriesDB.asStateFlow()


    init{
        getTotal()
        _categoryStates.value = emptyMap()
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

    fun onCategorySelected(categorySelected: Category) {
        _categorySelected.value = categorySelected
    }

    fun getCategories(status: Boolean) {
        if (status) {
            _listOfCategories.value = incomeCategories
        } else {
            _listOfCategories.value = expenseCategories
        }
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
    // Function to handle checkbox state changes
    fun onCategoryCheckChanged(category: Category, isChecked: Boolean) {
        // Get the current state
        val currentStates = _categoryStates.value ?: emptyMap()

        // Update the state with the new checked status for the specific category
        val updatedStates = currentStates.toMutableMap().apply {
            put(category.name.toString(), isChecked) // Update the specific category
        }

        // Post the updated state back to LiveData
        _categoryStates.value = updatedStates
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

    private val incomeCategories = listOf(
        Category(
            iconResource = R.drawable.ic_category_salary,
            name = R.string.salary,  // Usando el recurso de string
            isIncome = true
        ),
        Category(
            iconResource = R.drawable.ic_category_dividens,
            name = R.string.dividens,  // Usando el recurso de string
            isIncome = true
        ),
        Category(
            iconResource = R.drawable.ic_category_rent,
            name = R.string.rental,  // Usando el recurso de string
            isIncome = true
        ),
        Category(
            iconResource = R.drawable.ic_category_freelances,
            name = R.string.freelance,  // Usando el recurso de string
            isIncome = true
        ),
        Category(
            iconResource = R.drawable.ic_category_sales,
            name = R.string.sales,  // Usando el recurso de string
            isIncome = true
        ),
        Category(
            iconResource = R.drawable.ic_category_donation,
            name = R.string.subsidies,  // Usando el recurso de string
            isIncome = true
        ),
        Category(
            iconResource = R.drawable.ic_category_lotery,
            name = R.string.lotery,  // Usando el recurso de string
            isIncome = true
        ),
        Category(
            iconResource = R.drawable.ic_category_otherincomes,
            name = R.string.otherincomes,  // Usando el recurso de string
            isIncome = true
        ),
        Category(
            iconResource = R.drawable.ic_category_premium,
            name = R.string.awards,  // Usando el recurso de string
            isIncome = true
        ),
        Category(
            iconResource = R.drawable.ic_category_winasset,
            name = R.string.benefit_assets,  // Usando el recurso de string
            isIncome = true
        ),
    )
    private val expenseCategories = listOf(
        Category(
            iconResource = R.drawable.ic_category_grocery,
            name = R.string.food,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_mortgage,
            name = R.string.morgage,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_electricity,
            name = R.string.electricitybill,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_water,
            name = R.string.waterbill,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_gasbill,
            name = R.string.gasbill,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_publictansport,
            name = R.string.publictransport,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_fuelcar,
            name = R.string.fuel_title,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_otherinsurance,
            name = R.string.insurances,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_healthbill,
            name = R.string.health,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_leiure,
            name = R.string.entertainment,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_subscriptions,
            name = R.string.subscriptions,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_vacation,
            name = R.string.vacations_travel_title,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_clothing,
            name = R.string.clothing,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_education,
            name = R.string.courses_books_materials_title,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_house,
            name = R.string.house,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_repaircar,
            name = R.string.car,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_sport,
            name = R.string.gym,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_pet,
            name = R.string.pets,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_personalcare,
            name = R.string.personal_care_title,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_gif,
            name = R.string.gifts_title,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_donation,
            name = R.string.donations_title,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_lostasset,
            name = R.string.lost_assets,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_books,
            name = R.string.books,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_music,
            name = R.string.music,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_hobies,
            name = R.string.hobbies,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_tax,
            name = R.string.taxes,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_loan,
            name = R.string.loans,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_electronic,
            name = R.string.electronics,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_coffe,
            name = R.string.coffee,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_tabac,
            name = R.string.tobacco,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_sportsuplement,
            name = R.string.supplements,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_bike,
            name = R.string.motorcycle,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_garden,
            name = R.string.garden,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_teraphy,
            name = R.string.therapies,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_alcohol,
            name = R.string.alcohol,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_game,
            name = R.string.gambling,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_restaurant,
            name = R.string.restaurants,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_kids,
            name = R.string.children,  // Usando el recurso de string
            isIncome = false
        ),


        Category(
            iconResource = R.drawable.ic_category_otherincomes,
            name = R.string.other_expenses,  // Usando el recurso de string
            isIncome = false
        )
    )
}
