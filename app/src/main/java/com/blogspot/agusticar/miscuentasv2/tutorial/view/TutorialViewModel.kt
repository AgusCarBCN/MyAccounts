package com.blogspot.agusticar.miscuentasv2.tutorial.view


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blogspot.agusticar.miscuentasv2.MainActivity
import com.blogspot.agusticar.miscuentasv2.main.data.repository.DataStoreRepository
import com.blogspot.agusticar.miscuentasv2.main.data.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TutorialViewModel@Inject constructor(private val repository: UserPreferencesRepository) : ViewModel() {


    // LiveData para observar el valor de toLogin
    private val _toLogin = MutableLiveData<Boolean>(false)

    val toLogin: LiveData<Boolean> = _toLogin

    init {
        // Cargar el valor inicial al inicializar el ViewModel
        getToLogin()

    }

    // Función para cargar el valor desde el repositorio en una corrutina
    private fun getToLogin() {

        viewModelScope.launch {
            try {
                // Llamar al repositorio para obtener el valor almacenado y asignarlo al LiveData
                val loginStatus = repository.getToLogin()
                _toLogin.value = loginStatus
                Log.d("TutorialViewModel", "Success getting toLogin value: $loginStatus")
            } catch (e: Exception) {
                Log.e("TutorialViewModel", "Error getting toLogin value: ${e.message}")
            }

        }

    }


}