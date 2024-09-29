package com.blogspot.agusticar.miscuentasv2.tutorial.view


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blogspot.agusticar.miscuentasv2.main.MainActivity
import com.blogspot.agusticar.miscuentasv2.main.data.repository.DataStoreRepository
import com.blogspot.agusticar.miscuentasv2.main.data.repository.UserPreferencesRepository


class TutorialViewModel(mainActivity: MainActivity) : ViewModel() {


    private val repository:DataStoreRepository=UserPreferencesRepository(mainActivity)
    
    private val _toLogin = MutableLiveData<Boolean>()
    val toLogin: LiveData<Boolean> = _toLogin

    suspend fun getToLogin() {
        _toLogin.value = repository.getToLogin()
    }

}