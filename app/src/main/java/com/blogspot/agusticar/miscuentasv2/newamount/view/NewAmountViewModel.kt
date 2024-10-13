package com.blogspot.agusticar.miscuentasv2.newamount.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Entry
import com.blogspot.agusticar.miscuentasv2.main.domain.database.entriesusecase.InsertEntryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewAmountViewModel @Inject constructor(
    private val insertEntry:InsertEntryUseCase
): ViewModel() {

    // LiveData para los campos de texto
    private val _description = MutableLiveData<String>()
    val description: LiveData<String> = _description

    private val _amount = MutableLiveData<String>()
    val amount: LiveData<String> = _amount

    fun addEntry(entry: Entry){
        viewModelScope.launch(Dispatchers.IO) {
            insertEntry(entry)
        }
    }
    fun ondescriptionChanged(newDescription: String) {
        _description.value = newDescription

    }

    fun onAmountChanged(newAmount: String) {
        _amount.value = newAmount

    }
    private fun resetFields() {
        _description.postValue("") // Vaciar el nombre de la cuenta
        _amount.postValue("") // Vaciar el balance de la cuenta
    }
}














