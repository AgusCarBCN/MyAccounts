package com.blogspot.agusticar.miscuentasv2.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blogspot.agusticar.miscuentasv2.main.domain.datastore.GetEnableDarkThemUseCase
import com.blogspot.agusticar.miscuentasv2.main.domain.datastore.GetEnableNotificationsUseCase
import com.blogspot.agusticar.miscuentasv2.main.domain.datastore.GetEnableTutorialUseCase
import com.blogspot.agusticar.miscuentasv2.main.domain.datastore.GetShowTutorialUseCase
import com.blogspot.agusticar.miscuentasv2.main.domain.datastore.SetEnableDarkThemeUseCase
import com.blogspot.agusticar.miscuentasv2.main.domain.datastore.SetEnableNotificationsUseCase
import com.blogspot.agusticar.miscuentasv2.main.domain.datastore.SetEnableTutorialUseCase
import com.blogspot.agusticar.miscuentasv2.main.domain.datastore.SetShowTutorialUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class SettingViewModel @Inject constructor(
    private val getShowTutorial: GetShowTutorialUseCase,
    private val setShowTutorial: SetShowTutorialUseCase,
    private val getSwitchTutorial: GetEnableTutorialUseCase,
    private val changeSwitchTutorial: SetEnableTutorialUseCase,
    private val getSwitchDarkTheme: GetEnableDarkThemUseCase,
    private val changeSwitchDarkTheme: SetEnableDarkThemeUseCase,
    private val getNotificationsTutorial: GetEnableNotificationsUseCase,
    private val changeSwitchNotifications: SetEnableNotificationsUseCase

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
        getSwitchDarkTheme()
        getSwitchNotifications()
        getShowTutorial()
    }

    // Método para cambiar el valor del switch
    fun onSwitchTutorialClicked(checked: Boolean) {
        viewModelScope.launch {
            setShowTutorial.invoke(checked)
            changeSwitchTutorial(checked)
            _switchTutorial.value = checked

        }
    }

    fun onSwitchDarkThemeClicked(checked: Boolean) {
        viewModelScope.launch {
            changeSwitchDarkTheme(checked)
            _switchDarkTheme.value = checked
        }


    }

    fun onSwitchNotificationsClicked(checked: Boolean) {
        viewModelScope.launch {
            changeSwitchNotifications(checked)
            _switchNotifications.value = checked
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
            _switchTutorial.value = getSwitchTutorial.invoke()
        }
    }
    // Obtener el estado del switch del tutorial
    fun getSwitchDarkTheme() {
        viewModelScope.launch {
            _switchDarkTheme.value = getSwitchDarkTheme.invoke()
        }
    }
    // Obtener el estado del switch del tutorial
    fun getSwitchNotifications() {
        viewModelScope.launch {
            _switchNotifications.value = getNotificationsTutorial.invoke()
        }
    }
}