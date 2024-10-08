package com.blogspot.agusticar.miscuentasv2.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blogspot.agusticar.miscuentasv2.main.domain.datastoreusecase.GetEnableTutorialUseCase
import com.blogspot.agusticar.miscuentasv2.main.domain.datastoreusecase.SetEnableTutorialUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val getEnable: GetEnableTutorialUseCase,
    changeEnable: SetEnableTutorialUseCase
) : ViewModel() {

    // LiveData para observar el valor de checked de switch que habilita o deshabilita tutorial de inicio
    private val _enableTutorial = MutableLiveData<Boolean>(true)
    val enableTutorial: LiveData<Boolean> = _enableTutorial

    fun changeEnableTutorial(newValue: Boolean) {
        _enableTutorial.value = newValue

    }


}