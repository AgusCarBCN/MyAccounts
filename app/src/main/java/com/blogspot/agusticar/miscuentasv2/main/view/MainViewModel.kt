package com.blogspot.agusticar.miscuentasv2.main.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.main.model.IconOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// Anotación para que Hilt pueda proporcionar esta clase como ViewModel.
@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    // MutableStateFlow que contiene la pantalla seleccionada.
    // Inicialmente se establece en "Home".
    private val _selectedScreen = MutableStateFlow(IconOptions.HOME)

    // Exposición pública del StateFlow, permite a los composables observar cambios en el estado.
    // StateFlow es un flujo que siempre mantiene su último valor emitido.
    val selectedScreen: MutableStateFlow<IconOptions> = _selectedScreen

    private val _selectedIcon = MutableStateFlow(R.drawable.ic_category_salary)
    private val _selectedTitle= MutableStateFlow(R.string.salary)
    private val _isIncome= MutableStateFlow(true)
    private val _showExitDialog=MutableStateFlow(false)

    val selectedIcon: MutableStateFlow<Int> = _selectedIcon
    val selectedTitle: MutableStateFlow<Int> = _selectedTitle
    val isIncome: MutableStateFlow<Boolean> = _isIncome
    val showExitDialog: MutableStateFlow<Boolean> = _showExitDialog

        // Función que permite cambiar la pantalla seleccionada.
    // Utiliza viewModelScope para lanzar una corrutina y emitir un nuevo valor.
    fun selectScreen(screen: IconOptions){
        viewModelScope.launch {
            // Emitir el nuevo valor al StateFlow.
            _selectedScreen.emit(screen)

        }
    }

    fun selectResources(iconResource:Int,stringResource:Int,isIncome:Boolean){

        viewModelScope.launch {
            _selectedIcon.emit(iconResource)
            _selectedTitle.emit(stringResource)
            _isIncome.emit(isIncome)
        }
    }
    fun showExitDialog(newValue:Boolean){
        viewModelScope.launch {
            _showExitDialog.emit(newValue)
        }
    }

}

