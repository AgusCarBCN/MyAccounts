package com.blogspot.agusticar.miscuentasv2.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.utils.Utils
import javax.inject.Inject

class SearchViewModel @Inject constructor() : ViewModel() {

    // Lista de opciones como identificadores de recursos
     val options = listOf(R.string.incomeoption, R.string.expenseoption, R.string.alloption)

    // LiveData para la opción seleccionada
    private val _selectedOption = MutableLiveData(options[0])
    val selectedOption: LiveData<Int> = _selectedOption

    private val _showDatePickerFrom = MutableLiveData<Boolean>()
    val showDatePickerFrom: LiveData<Boolean> = _showDatePickerFrom

    private val _showDatePickerTo = MutableLiveData<Boolean>()
    val showDatePickerTo: LiveData<Boolean> = _showDatePickerTo

    private val _selectedFromDate = MutableLiveData<String>()
    val selectedFromDate: LiveData<String> = _selectedFromDate

    private val _selectedToDate = MutableLiveData<String>()
    val selectedToDate: LiveData<String> = _selectedToDate

    private val _entryDescription = MutableLiveData<String>()
    val entryDescription: LiveData<String> = _entryDescription

    private val _fromAmount = MutableLiveData<String>()
    val fromAmount: LiveData<String> = _fromAmount

    private val _toAmount = MutableLiveData<String>()
    val toAmount: LiveData<String> = _toAmount


    fun onShowDatePicker(newValue: Boolean,isDateFrom:Boolean) {
        if(isDateFrom){
            _showDatePickerFrom.value = newValue
        }else{
            _showDatePickerTo.value = newValue
        }
    }


    fun onSelectedDate(date: String,isDateFrom:Boolean) {
       if(isDateFrom) {
           _selectedFromDate.value = date
       }else{
           _selectedToDate.value = date
       }
    }



    fun onFromAmountChanged(newAmount: String) {
        // Validar y actualizar el valor de amount
        if (Utils.isValidDecimal(newAmount)) {
            _fromAmount.value = newAmount
        }
    }
    fun onToAmountChanged(newAmount: String) {
        // Validar y actualizar el valor de amount
        if (Utils.isValidDecimal(newAmount)) {
            _toAmount.value = newAmount
        }
    }
    fun onEntryDescriptionChanged(newDescription: String) {
        _entryDescription.value = newDescription
    }
    // Método para actualizar la opción seleccionada
    fun onOptionSelected(option: Int) {
        _selectedOption.value = option
    }
}