package com.blogspot.agusticar.miscuentasv2.createprofile

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blogspot.agusticar.miscuentasv2.main.domain.datastoreusecase.GetPhotoFromUriUseCase
import com.blogspot.agusticar.miscuentasv2.main.domain.datastoreusecase.GetUserProfileDataUseCase
import com.blogspot.agusticar.miscuentasv2.main.domain.datastoreusecase.SaveUriUseCase
import com.blogspot.agusticar.miscuentasv2.main.domain.datastoreusecase.SetToLoginUseCase
import com.blogspot.agusticar.miscuentasv2.main.domain.datastoreusecase.SetUserProfileDataUseCase
import com.blogspot.agusticar.miscuentasv2.main.model.UserProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class CreateProfileViewModel @Inject constructor(
    private val setProfileData: SetUserProfileDataUseCase,
    private val getProfileData:GetUserProfileDataUseCase,
    private val setLoginTo: SetToLoginUseCase,
    private val saveUri: SaveUriUseCase,
    private val getUri:GetPhotoFromUriUseCase
) : ViewModel() {

    // LiveData para los campos de texto
    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _repeatPassword = MutableLiveData<String>()
    val repeatPassword: LiveData<String> = _repeatPassword

    //LiveData para habilitar boton de confirmar
    private val _enableButton = MutableLiveData<Boolean>()
    val enableButton: LiveData<Boolean> = _enableButton

    //LiveData para habilitar boton de cambiar imagen
    private val _enableChangeImage = MutableLiveData<Boolean>()
    val enableChangeImage: LiveData<Boolean> = _enableChangeImage

    //LiveData para habilitar boton de cambiar imagen
    private val _enableNameButton = MutableLiveData<Boolean>()
    val enableNameButton: LiveData<Boolean> = _enableNameButton




    // Definimos selectedImageUri como un LiveData
    private val _selectedImageUri = MutableLiveData<Uri?>()
    val selectedImageUri: LiveData<Uri?> = _selectedImageUri

    init{
        viewModelScope.launch {
            _selectedImageUri.value = getUri()
            val user = getProfileData.invoke()
            _name.value = user.profileName
            _username.value = user.profileUserName
            _password.value = user.profilePass

        }
    }

    fun onTextFieldsChanged(name: String, userName: String, password: String, newPassword: String) {
        _name.value = name
        _username.value = userName
        _password.value = password
        _repeatPassword.value = newPassword
        //Verificaciones para activar boton de confirmar
        _enableButton.value = enableConfirmButton(name, userName, password, newPassword)
    }
    fun onNameChanged(newName: String){
        _name.value = newName



    }


    fun onImageSelected(selectedImage:Uri)
    {
        _selectedImageUri.value = selectedImage
        _enableChangeImage.value=true
    }
    fun onImageNoSelected(){
        _enableChangeImage.value=false
    }
    fun saveImageUri(uri:Uri){
        viewModelScope.launch {
            saveUri(uri)
            _selectedImageUri.value=getUri()
        }
    }
    fun loadImageUri(){
        viewModelScope.launch {
            _selectedImageUri.value = getUri()
        }
    }


    // Función para setear un nuevo valor para toLogin en el repositorio

    // Función para setear un nuevo valor para toLogin en el repositorio
    fun setUserDataProfile(newProfile: UserProfile) {
        viewModelScope.launch {
            // Llamar a la función suspend de tu repositorio
            setProfileData(newProfile)
            setLoginTo(true)

            // Actualizar el valor en el LiveData para reflejar el cambio en la UI

        }
    }


    private fun enableConfirmButton(
        name: String,
        userName: String,
        password: String,
        newPassword: String
    ): Boolean {
        // Verifica que los campos no estén en blanco
        if (name.isBlank() || userName.isBlank() || password.isBlank() || newPassword.isBlank()) {
            return false
        }

        // Verifica que el nombre de usuario tenga al menos 3 caracteres (opcional)
        if (userName.length < 3) {
            return false
        }

        // Verifica que la contraseña tenga al menos 6 caracteres
        if (password.length < 6) {
            return false
        }

        // Verifica que la nueva contraseña coincida con la original
        if (password != newPassword) {
            return false
        }

        return true
    }


}