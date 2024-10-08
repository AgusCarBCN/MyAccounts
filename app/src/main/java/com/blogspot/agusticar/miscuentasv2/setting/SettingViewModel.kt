package com.blogspot.agusticar.miscuentasv2.setting

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blogspot.agusticar.miscuentasv2.main.domain.datastoreusecase.GetEnableTutorialUseCase
import com.blogspot.agusticar.miscuentasv2.main.domain.datastoreusecase.GetShowTutorialUseCase
import com.blogspot.agusticar.miscuentasv2.main.domain.datastoreusecase.SetEnableTutorialUseCase
import com.blogspot.agusticar.miscuentasv2.main.domain.datastoreusecase.SetShowTutorialUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class SettingViewModel @Inject constructor(
    private val getShowTutorial: GetShowTutorialUseCase,
    private val setShowTutorial: SetShowTutorialUseCase,
    private val getSwitchTutorial: GetEnableTutorialUseCase,
    private val changeSwitchTutorial: SetEnableTutorialUseCase
) : ViewModel() {

    // Exposición del StateFlow para el switch
    private val _switch = MutableLiveData<Boolean>()
    val switch: LiveData<Boolean> = _switch

    // Este LiveData controla el estado de si mostrar el tutorial
    private val _showTutorial = MutableLiveData<Boolean>()
    val showTutorial: LiveData<Boolean> = _showTutorial
    init {

        getSwitchTutorial()
        getShowTutorial()
    }

    // Método para cambiar el valor del switch
    fun setValuesTutorial(checked:Boolean) {
        viewModelScope.launch {
            setShowTutorial.invoke(checked)
            changeSwitchTutorial(checked)
            _switch.value=checked
            Log.d("Checked",checked.toString())
            Log.d("SwitchChecked",_switch.value.toString())
            Log.d("TutorialChecked",_showTutorial.value.toString())
        }
    }


    // Obtener el estado del tutorial cuando inicie la aplicación
    fun getShowTutorial() {
        viewModelScope.launch {
            _showTutorial.value = getShowTutorial.invoke()
        }
    }

    // Obtener el estado del switch del tutorial
    fun getSwitchTutorial() {
        viewModelScope.launch {
            _switch.value = getSwitchTutorial.invoke()
        }
    }

}