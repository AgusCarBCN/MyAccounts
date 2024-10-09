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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class SettingViewModel @Inject constructor(
    private val getShowTutorial: GetShowTutorialUseCase,
    private val setShowTutorial: SetShowTutorialUseCase,
    private val getSwitchTutorial: GetEnableTutorialUseCase,
    private val changeSwitchTutorial: SetEnableTutorialUseCase
) : ViewModel() {

    //Live data del switch para habilitar/deshabilitar tutorial de inicio
    private val _switchTutorial = MutableLiveData<Boolean>()
    val switchTutorial: LiveData<Boolean> = _switchTutorial

    //Live data del switch para habilitar/deshabilitar tema oscuro desde la aplicación
    private val _switchDarkTheme = MutableLiveData<Boolean>()
    val switchDarkTheme: LiveData<Boolean> = _switchDarkTheme

    //Live data del switch para habilitar/deshabilitar notificaciones
    private val _switchNotifications = MutableLiveData<Boolean>()
    val switchNotifications: LiveData<Boolean> = _switchNotifications

    // Este LiveData controla el estado de si mostrar el tutorial
    private val _showTutorial = MutableLiveData<Boolean>()
    val showTutorial: LiveData<Boolean> = _showTutorial
    init {

        getSwitchTutorial()
        getShowTutorial()
    }

    // Método para cambiar el valor del switch
    fun onSwitchTutorialClicked(checked:Boolean) {
        viewModelScope.launch {
            setShowTutorial.invoke(checked)
            changeSwitchTutorial(checked)
            _switchTutorial.value=checked
            Log.d("Checked",checked.toString())
            Log.d("SwitchChecked",_switchTutorial.value.toString())
            Log.d("TutorialChecked",_showTutorial.value.toString())
        }
    }

    fun onSwitchDarkThemeClicked(checked:Boolean) {

            _switchDarkTheme.value = checked

    }
    fun onSwitchNotificationsClicked(checked:Boolean) {

        _switchNotifications.value = checked

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
            _switchTutorial.value = getSwitchTutorial.invoke()
        }
    }


}