package com.blogspot.agusticar.miscuentasv2.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blogspot.agusticar.miscuentasv2.utils.Utils
import javax.inject.Inject

class SearchViewModel @Inject constructor() : ViewModel() {

    private val _showDatePicker = MutableLiveData<Boolean>()
    val showDatePicker: LiveData<Boolean> = _showDatePicker

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


    fun onShowDatePicker(newValue: Boolean) {
        _showDatePicker.value = newValue
    }

    fun onFromDateSelected(date: String) {
        _selectedFromDate.value = date
    }

    fun onToDateSelected(date: String) {
        _selectedToDate.value = date
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

}