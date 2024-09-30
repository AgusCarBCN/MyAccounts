package com.blogspot.agusticar.miscuentasv2.login

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.main.data.repository.UserPreferencesRepository
import com.blogspot.agusticar.miscuentasv2.main.model.UserProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: UserPreferencesRepository):ViewModel () {

    private val _user = MutableLiveData<UserProfile>()
    val user: LiveData<UserProfile> = _user

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _enableButton = MutableLiveData<Boolean>()
    val enableButton: LiveData<Boolean> = _enableButton

    private val _validateLoginButton = MutableLiveData<Boolean>()
    val validateLoginButton: LiveData<Boolean> = _validateLoginButton

    private val _enableNewPassword = MutableLiveData<Boolean>()
    val enableNewPassword: LiveData<Boolean> = _enableNewPassword

    init {
        // Cargar el valor inicial de toLogin desde el repositorio al inicializar el ViewModel
        viewModelScope.launch {
            _user.value = repository.getUserDataProfile()
        }
    }

    fun onLoginChanged(userName: String, password: String) {
        _userName.value = userName
        _password.value = password

        Log.d("Datos leidos: ", "Usuario en _user: ${_user.value?.profileName}, Comparado con userName: ${_userName.value}")

        _enableButton.value = enableLoginButton(userName, password)

        // Si los datos del perfil no han sido cargados aún, no intentamos validar
        if (_user.value != null) {
            _validateLoginButton.value = validateLoginButton(userName, password)
        } else {
            _validateLoginButton.value = false
            Log.d("Login", "Datos de usuario aún no disponibles")
        }
    }



    fun onEnableNewPassword(enableNewPassword: Boolean) {
        _enableNewPassword.value = enableNewPassword
    }

    @Composable
    fun getGreeting(): String {
        val hour = LocalTime.now().hour

        val greeting = when (hour) {
            in 6..11 -> stringResource(id = R.string.goodmornig)
            in 12..17 -> stringResource(id = R.string.goodafternoon)
            else -> stringResource(id = R.string.goodevening)
        }

        return "$greeting, ${_user.value?.name}!" // Concatenar el saludo con el nombre
    }

    private fun enableLoginButton(userName: String, password: String): Boolean =
        userName.isNotEmpty() && password.isNotEmpty() && userName.isNotBlank() && password.isNotBlank() && password.length >= 6

    private fun validateLoginButton(userName: String, password: String): Boolean {
        val userProfile = _user.value
        return if (userProfile != null) {
            userName == userProfile.profileName && password == userProfile.profilePass
        } else {
            false
        }

    }
}