package com.blogspot.agusticar.miscuentasv2.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class SettingViewModel @Inject constructor() : ViewModel() {

    // LiveData para observar el valor de checked de switch que habilita o deshabilita tutorial de inicio
    private val _enableSwitchTutorial = MutableLiveData<Boolean>(true)
    val enableSwitchTutorial: LiveData<Boolean> = _enableSwitchTutorial



}