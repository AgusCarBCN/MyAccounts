package com.blogspot.agusticar.miscuentasv2.tutorial.view


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blogspot.agusticar.miscuentasv2.main.data.repository.UserDataStoreRepository
import com.blogspot.agusticar.miscuentasv2.main.domain.GetShowTutorialUseCase
import com.blogspot.agusticar.miscuentasv2.main.domain.GetToLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TutorialViewModel @Inject constructor(private val getLoginValue:GetToLoginUseCase,
    private val getShowTutorial:GetShowTutorialUseCase) :
    ViewModel() {


    // LiveData para observar el valor de toLogin
    private val _toLogin = MutableLiveData<Boolean>(false)
    val toLogin: LiveData<Boolean> = _toLogin

    private val _showTutorial = MutableLiveData<Boolean>(true)
    val showTutorial: LiveData<Boolean> = _showTutorial

    init {
        // Cargar el valor inicial al inicializar el ViewModel
        viewModelScope.launch {
            try {
                // Llamar al repositorio para obtener el valor almacenado y asignarlo al LiveData
                _toLogin.value = getLoginValue()
                _showTutorial.value = getShowTutorial()

                Log.d("TutorialViewModel", "Success getting toLogin value: ${_toLogin.value}")
                Log.d("TutorialViewModel", "Success getting showTutorial value: ${_showTutorial.value}")
            } catch (e: Exception) {
                Log.e("TutorialViewModel", "Error getting toLogin value: ${e.message}")
            }

        }

    }


}