package com.blogspot.agusticar.miscuentasv2.login

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blogspot.agusticar.miscuentasv2.R
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor():ViewModel () {


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

    fun onLoginChanged(userName: String, password: String) {
        _userName.value = userName
        _password.value = password
        _enableButton.value = enableLoginButton(userName, password)
        _validateLoginButton.value = validateLoginButton(userName, password)
    }

    fun onEnableNewPassword(enableNewPassword: Boolean) {
        _enableNewPassword.value = enableNewPassword
    }

    @Composable
    fun getGreeting(): String {
        val hour = LocalTime.now().hour
        return when (hour) {
            in 6..11 -> stringResource(id = R.string.goodmornig)
            in 12..17 -> stringResource(id = R.string.goodafternoon)
            else -> stringResource(id = R.string.goodevening)
        }
    }

    private fun enableLoginButton(userName: String, password: String): Boolean =
        userName.isNotEmpty() && password.isNotEmpty() && userName.isNotBlank() && password.isNotBlank() && password.length >= 6

    private fun validateLoginButton(userName: String, password: String): Boolean =
        userName == "Agus" && password == "nina1971"
}
