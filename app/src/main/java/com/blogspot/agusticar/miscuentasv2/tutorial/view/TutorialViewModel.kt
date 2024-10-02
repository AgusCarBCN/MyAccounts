package com.blogspot.agusticar.miscuentasv2.tutorial.view


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blogspot.agusticar.miscuentasv2.main.domain.datastoreusecase.GetShowTutorialUseCase
import com.blogspot.agusticar.miscuentasv2.main.domain.datastoreusecase.GetToLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TutorialViewModel @Inject constructor(private val getLoginValue: GetToLoginUseCase,
                                            private val getShowTutorial: GetShowTutorialUseCase
) :
    ViewModel() {


    // LiveData para observar el valor de toLogin
    private val _toLogin = MutableLiveData<Boolean>(false)
    val toLogin: LiveData<Boolean> = _toLogin

    private val _showTutorial = MutableLiveData<Boolean>(true)
    val showTutorial: LiveData<Boolean> = _showTutorial

    init {
        // Launch separate coroutines to manage the two LiveData values independently
        loadToLoginValue()
        loadShowTutorialValue()
    }

    // Load `toLogin` value separately
    private fun loadToLoginValue() {
        viewModelScope.launch {
            try {
                val loginValue = getLoginValue() // Fetch the value using the use case
                _toLogin.value = loginValue
                Log.d("TutorialViewModel", "Success getting toLogin value: $loginValue")
            } catch (e: Exception) {
                Log.e("TutorialViewModel", "Error getting toLogin value: ${e.message}")
            }
        }
    }

    // Load `showTutorial` value separately
    private fun loadShowTutorialValue() {
        viewModelScope.launch {
            try {
                val tutorialValue = getShowTutorial() // Fetch the value using the use case
                _showTutorial.value = tutorialValue
                Log.d("TutorialViewModel", "Success getting showTutorial value: $tutorialValue")
            } catch (e: Exception) {
                Log.e("TutorialViewModel", "Error getting showTutorial value: ${e.message}")
            }
        }
    }


}