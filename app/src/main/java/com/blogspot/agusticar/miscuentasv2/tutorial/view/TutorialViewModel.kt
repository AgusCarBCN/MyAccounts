package com.blogspot.agusticar.miscuentasv2.tutorial.view


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blogspot.agusticar.miscuentasv2.main.data.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TutorialViewModel @Inject constructor(private val repository: UserPreferencesRepository) :
    ViewModel() {


    // LiveData para observar el valor de toLogin
    private val _toLogin = MutableLiveData<Boolean>(false)
    val toLogin: LiveData<Boolean> = _toLogin

    private val _showTutorial = MutableLiveData<Boolean>(false)
    val showTutorial: LiveData<Boolean> = _showTutorial


    init {
        // Cargar el valor inicial al inicializar el ViewModel
        getToLogin()

    }

    // Función para cargar el valor desde el repositorio en una corrutina
    private fun getToLogin() {

        viewModelScope.launch {
            try {
                // Llamar al repositorio para obtener el valor almacenado y asignarlo al LiveData
                _toLogin.value = repository.getToLogin()

                Log.d("TutorialViewModel", "Success getting toLogin value: $_toLogin.value")
            } catch (e: Exception) {
                Log.e("TutorialViewModel", "Error getting toLogin value: ${e.message}")
            }

        }

    }
    fun getShowTutorial():Boolean{

        viewModelScope.launch {
            try {
                // Llamar al repositorio para obtener el valor almacenado y asignarlo al LiveData
                _showTutorial.value = repository.getShowTutorial()
                Log.d("TutorialViewModel", "Success getting toLogin value: ${_showTutorial.value}")
            } catch (e: Exception) {
                Log.e("TutorialViewModel", "Error getting toLogin value: ${e.message}")
            }

        }
        return _showTutorial.value?:true
    }

}