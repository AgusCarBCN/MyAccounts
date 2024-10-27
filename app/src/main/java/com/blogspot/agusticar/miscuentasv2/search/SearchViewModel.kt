package com.blogspot.agusticar.miscuentasv2.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blogspot.agusticar.miscuentasv2.utils.Utils
import javax.inject.Inject

class SearchViewModel @Inject constructor() : ViewModel() {

    private val _showDatePicker = MutableLiveData<Boolean>()
    val showDatePicker: LiveData<Boolean> = _showDatePicker

    private val _selectedDateFrom = MutableLiveData<String>()
    val selectedDateFrom: LiveData<String> = _selectedDateFrom

    private val _selectedDateTo = MutableLiveData<String>()
    val selectedDateTo: LiveData<String> = _selectedDateTo

    private val _entryDescription = MutableLiveData<String>()
    val entryDescription: LiveData<String> = _entryDescription

    private val _amountFrom = MutableLiveData<String>()
    val amountFrom: LiveData<String> = _amountFrom

    private val _amountTo = MutableLiveData<String>()
    val amountTo: LiveData<String> = _amountTo


    fun onShowDatePicker(newValue: Boolean) {
        _showDatePicker.value = newValue
    }

    fun onDateFromSelected(date: String) {
        _selectedDateFrom.value = date
    }

    fun onDateToSelected(date: String) {
        _selectedDateTo.value = date
    }

    fun onAmountFromChanged(newAmount: String) {
        // Validar y actualizar el valor de amount
        if (Utils.isValidDecimal(newAmount)) {
            _amountFrom.value = newAmount
        }
    }
    fun onAmountToChanged(newAmount: String) {
        // Validar y actualizar el valor de amount
        if (Utils.isValidDecimal(newAmount)) {
            _amountTo.value = newAmount
        }
    }
    fun onDescriptionEntryChanged(newDescription: String) {
        _entryDescription.value = newDescription
    }

}