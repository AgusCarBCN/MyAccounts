package com.blogspot.agusticar.miscuentasv2.createprofile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.referentialEqualityPolicy
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blogspot.agusticar.miscuentasv2.main.data.repository.UserPreferencesRepository
import com.blogspot.agusticar.miscuentasv2.main.model.UserProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateProfileViewModel @Inject constructor(private val repository: UserPreferencesRepository) : ViewModel() {

    // LiveData para observar el valor de toLogin
    private val _toLogin = MutableLiveData<Boolean>()
    val toLogin: LiveData<Boolean> = _toLogin

    private val _user = MutableLiveData<UserProfile>()
    val user: LiveData<UserProfile> = _user

    // LiveData para los campos de texto
    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _repeatPassword = MutableLiveData<String>()
    val repeatPassword: LiveData<String> = _repeatPassword

    init {
        // Cargar el valor inicial de toLogin desde el repositorio al inicializar el ViewModel
        viewModelScope.launch {
            _toLogin.value = repository.getToLogin()
            _user.value= repository.getUserDataProfile()
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
    // Función para setear un nuevo valor para toLogin en el repositorio
    fun setUserDataProfile(newProfile:UserProfile) {
        viewModelScope.launch {
            // Llamar a la función suspend de tu repositorio
            repository.setUserDataProfile(newProfile)
            // Actualizar el valor en el LiveData para reflejar el cambio en la UI
            _user.value=newProfile
        }
    }
    // Funciones para actualizar los campos de texto
    fun setUsername(newValue: String) {
        _username.value = newValue
    }

    fun setName(newValue: String) {
        _name.value = newValue
    }

    fun setPassword(newValue: String) {
        _password.value = newValue
    }

    fun setRepeatPassword(newValue: String) {
        _repeatPassword.value = newValue
    }

}