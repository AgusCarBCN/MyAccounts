package com.blogspot.agusticar.miscuentasv2.profile

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.SnackBarController
import com.blogspot.agusticar.miscuentasv2.SnackBarEvent
import com.blogspot.agusticar.miscuentasv2.components.BoardType
import com.blogspot.agusticar.miscuentasv2.components.ModelButton
import com.blogspot.agusticar.miscuentasv2.components.TextFieldComponent
import com.blogspot.agusticar.miscuentasv2.createprofile.ProfileImageWithCamera
import com.blogspot.agusticar.miscuentasv2.createprofile.ProfileViewModel
import com.blogspot.agusticar.miscuentasv2.main.model.UserProfile
import kotlinx.coroutines.launch


@Composable

fun ProfileScreen(createViewModel: ProfileViewModel) {
    val updatedMessages = listOf(
        stringResource(id = R.string.userNameUpdated),  // Aquí obtienes el texto real del recurso
        stringResource(id = R.string.nameUpdated),
        stringResource(id = R.string.passwordUpdated),
        stringResource(id = R.string.photoUpdated)
    )



    val name by createViewModel.name.observeAsState("")
    val userName by createViewModel.username.observeAsState("")
    val password by createViewModel.password.observeAsState("")
    val selectedImageUri by createViewModel.selectedImageUri.observeAsState(null)
    val enableChangeImageButton by createViewModel.enableChangeImage.observeAsState(false)
    val enableNameButton by createViewModel.enableNameButton.observeAsState(false)
    val enableUserNameButton by createViewModel.enableUserNameButton.observeAsState(false)
    val enablePasswordButton by createViewModel.enablePasswordButton.observeAsState(false)

    val scope = rememberCoroutineScope()



    val snackBarHostState = remember { SnackbarHostState() }


    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {

        Log.d("imageUriFromProfile", selectedImageUri.toString())


        ProfileImageWithCamera(createViewModel)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            ModelButton(
                text = "Change Photo",
                R.dimen.text_title_small, modifier = Modifier.width(220.dp),
                enableChangeImageButton,
                onClickButton = {
                    scope.launch {
                        selectedImageUri?.let { createViewModel.saveImageUri(it) }
                        SnackBarController.sendEvent(event = SnackBarEvent(updatedMessages[3]))
                        createViewModel.buttonState(false,enableUserNameButton,enableNameButton,enablePasswordButton)
                    }

                    Log.d("SaveFromChange", selectedImageUri.toString())
                }
            )


        }

        NewInputComponent(
            title = stringResource(id = R.string.userName),
            inputNewText = userName,
            R.string.userName,
            onNameTextFieldChanged = { createViewModel.onUserNameChanged(it) },
            type = BoardType.TEXT,
            sizeFontButton = R.dimen.text_title_small,
            enableUserNameButton,
            onChangeButtonClick = {
                scope.launch {
                    createViewModel.setUserDataProfile(UserProfile(name, userName, password))
                    SnackBarController.sendEvent(event = SnackBarEvent(updatedMessages[0]))
                    createViewModel.buttonState(enableChangeImageButton,false,enableNameButton,enablePasswordButton)
                }

            }
        )
        NewInputComponent(
            title = stringResource(id = R.string.name),
            inputNewText = name,
            R.string.name,
            onNameTextFieldChanged = { createViewModel.onNameChanged(it) },
            type = BoardType.TEXT,
            sizeFontButton = R.dimen.text_title_small,
            enableNameButton,
            onChangeButtonClick = {
                scope.launch {
                    createViewModel.setUserDataProfile(UserProfile(name, userName, password))
                    SnackBarController.sendEvent(event = SnackBarEvent(updatedMessages[1]))
                    createViewModel.buttonState(enableChangeImageButton,enableUserNameButton,false,enablePasswordButton)
                }
            }
        )
        NewInputComponent(
            title = stringResource(id = R.string.password),
            inputNewText = password,
            R.string.password,
            onNameTextFieldChanged = { createViewModel.onPasswordChanged(it) },
            type = BoardType.PASSWORD,
            sizeFontButton = R.dimen.text_title_small,
            enablePasswordButton,
            onChangeButtonClick = {
                scope.launch {
                    createViewModel.setUserDataProfile(UserProfile(name, userName, password))
                    SnackBarController.sendEvent(event = SnackBarEvent(updatedMessages[2]))
                    createViewModel.buttonState(enableChangeImageButton,enableUserNameButton,enableNameButton,false)
                }
            },
            true
        )

    }
    SnackbarHost(hostState = snackBarHostState)
}


@Composable
fun NewInputComponent(
    title: String,
    inputNewText: String,
    label:Int,
    onNameTextFieldChanged: (String) -> Unit,
    type: BoardType,
    sizeFontButton: Int,
    enableInputButton: Boolean,
    onChangeButtonClick: () -> Unit,
    isPassword: Boolean = false
) {
    Column(verticalArrangement = Arrangement.Center) {

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, start = 30.dp),
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 15.dp, start = 15.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextFieldComponent(
                modifier = Modifier.weight(0.60f),
                label = stringResource(label),
                inputText = inputNewText,
                onTextChange = onNameTextFieldChanged,
                type,
                isPassword
            )
            ModelButton(
                text = stringResource(id = R.string.change),
                sizeFontButton,
                modifier = Modifier.weight(0.40f),
                enableInputButton,
                onClickButton = onChangeButtonClick
            )
        }
    }
}
