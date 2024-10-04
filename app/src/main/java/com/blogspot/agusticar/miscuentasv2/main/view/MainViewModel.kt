package com.blogspot.agusticar.miscuentasv2.main.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blogspot.agusticar.miscuentasv2.main.model.IconOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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


    // Función que permite cambiar la pantalla seleccionada.
    // Utiliza viewModelScope para lanzar una corrutina y emitir un nuevo valor.
    fun selectScreen(screen: IconOptions){
        viewModelScope.launch {
            // Emitir el nuevo valor al StateFlow.
            _selectedScreen.emit(screen)
        }
    }
}

