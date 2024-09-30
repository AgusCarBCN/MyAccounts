package com.blogspot.agusticar.miscuentasv2.createprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blogspot.agusticar.miscuentasv2.main.data.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateProfileViewModel @Inject constructor(private val repository: UserPreferencesRepository) : ViewModel() {

    // LiveData para observar el valor de toLogin
    private val _toLogin = MutableLiveData<Boolean>()
    val toLogin: LiveData<Boolean> = _toLogin

    init {
        // Cargar el valor inicial de toLogin desde el repositorio al inicializar el ViewModel
        viewModelScope.launch {
            _toLogin.value = repository.getToLogin()
        }
    }

    // Función para setear un nuevo valor para toLogin en el repositorio
    fun setToLogin(newValue: Boolean) {
        viewModelScope.launch {
            // Llamar a la función suspend de tu repositorio
            repository.setToLogin(newValue)
            // Actualizar el valor en el LiveData para reflejar el cambio en la UI
            _toLogin.value = newValue
        }
    }

}