package com.blogspot.agusticar.miscuentasv2.login


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.R.color
import com.blogspot.agusticar.miscuentasv2.R.drawable
import com.blogspot.agusticar.miscuentasv2.R.string
import com.blogspot.agusticar.miscuentasv2.components.BoardType
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette

import com.blogspot.agusticar.miscuentasv2.components.ModelButton
import com.blogspot.agusticar.miscuentasv2.components.TextFieldComponent
import kotlinx.coroutines.launch


@Composable
fun LoginComponent(loginViewModel: LoginViewModel,modifier: Modifier,navToMain:()->Unit) {

    val name by loginViewModel.name.observeAsState("")
    val userName by loginViewModel.userName.observeAsState("")
    val password by loginViewModel.password.observeAsState("")

    val userNameNewPassword by loginViewModel.userNameNewPassword.observeAsState("")
    val newPassword by loginViewModel.newPassword.observeAsState("")

    val enableLoginButton by loginViewModel.enableLoginButton.observeAsState(false)
    val enableConfirmButton by loginViewModel.enableConfirmButton.observeAsState(false)

    val enableNewPasswordFields by loginViewModel.enableNewPasswordFields.observeAsState(false)

    val validateLogin by loginViewModel.validateLoginButton.observeAsState(false)
    val validateConfirm by loginViewModel.validateConfirmButton.observeAsState(false)

    val scope = rememberCoroutineScope()
    /* Se usa para gestionar el estado del Snackbar. Esto te permite mostrar y controlar el Snackbar
     desde cualquier parte de tu UI.*/
    val snackbarHostState = remember { SnackbarHostState() }


    ConstraintLayout(
        modifier
            .fillMaxSize()
            .background(colorResource(id = color.lightYellow))
    ) {
        // Crear referencias para las cajas
        val (imageBox, loginBox) = createRefs()

        // Caja de imagen en la parte superior, ocupando el 50% de la altura
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .background(LocalCustomColorsPalette.current.imageBackground)
                .constrainAs(imageBox) {
                    top.linkTo(parent.top)          // Parte superior anclada al padre
                    start.linkTo(parent.start)      // Empieza en el lado izquierdo del padre
                    end.linkTo(parent.end)          // Termina en el lado derecho del padre
                    bottom.linkTo(loginBox.top)     // Parte inferior anclada al loginBox
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = drawable.contabilidad),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(250.dp) // Uso de size para mantener la relación de aspecto
                    .background(LocalCustomColorsPalette.current.imageBackground)
            )
        }

        // Caja de login en la parte inferior, ocupando el otro 50% de la altura
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .background(LocalCustomColorsPalette.current.backgroundPrimary)  // Color de fondo
                .constrainAs(loginBox) {
                    top.linkTo(imageBox.bottom)          // Parte superior anclada al imageBox
                    start.linkTo(parent.start)           // Empieza en el lado izquierdo del padre
                    end.linkTo(parent.end)               // Termina en el lado derecho del padre
                    bottom.linkTo(parent.bottom)         // Parte inferior anclada al padre
                },

            verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!enableNewPasswordFields) {
                Text(
                    text = loginViewModel.getGreeting(name),
                    fontSize =  with(LocalDensity.current) { dimensionResource(id = R.dimen.text_title_medium).toSp() },
                    color = LocalCustomColorsPalette.current.textColor,
                    fontWeight = FontWeight.Bold
                )
                TextFieldComponent(
                    modifier = Modifier.width(360.dp),
                    stringResource(id = string.username),
                    userName,
                    onTextChange = { loginViewModel.onLoginChanged(it,password)},
                    BoardType.TEXT
                )
                TextFieldComponent(
                    modifier = Modifier.width(360.dp),
                    stringResource(id = string.password),
                    password,
                    onTextChange = { loginViewModel.onLoginChanged(userName,it)},
                    BoardType.PASSWORD,
                    true
                )
                ModelButton(text = stringResource(id = string.loginButton),
                    R.dimen.text_title_medium,
                    modifier = Modifier.width(360.dp),
                    enableLoginButton,
                    onClickButton = {
                        if(validateLogin) {
                            navToMain()
                        }else
                            scope.launch {
                                snackbarHostState.showSnackbar("Login no valido",duration =SnackbarDuration.Short)
                            }
                    }
                )

                TextButton(
                    onClick = {
                        loginViewModel.onEnableNewPasswordFields(true)
                    },
                    content = {
                        Text(
                            text = stringResource(id = string.forgotpassword),
                            fontSize = with(LocalDensity.current) { dimensionResource(id = R.dimen.text_body_medium).toSp() },
                            color = LocalCustomColorsPalette.current.textColor
                        )
                    }
                )

            }
            if (enableNewPasswordFields) {
                TextFieldComponent(
                    modifier = Modifier.width(360.dp),
                    stringResource(id = string.requestuser),
                    userNameNewPassword,
                    onTextChange = { loginViewModel.onUpdatePasswordChange(it,newPassword) },
                    BoardType.TEXT
                )

                TextFieldComponent(
                    modifier = Modifier.width(360.dp),
                    stringResource(id = string.newpassword),
                    newPassword,
                    onTextChange = { loginViewModel.onUpdatePasswordChange(userNameNewPassword,it) },
                    BoardType.PASSWORD,
                    true
                )
                ModelButton(text = stringResource(id = string.confirmButton),
                    R.dimen.text_title_medium,
                    modifier = Modifier.width(360.dp),
                    enableConfirmButton,
                    onClickButton = {
                        if(validateConfirm) {
                            try{
                            scope.launch {
                                loginViewModel.updatePassword(newPassword)
                                snackbarHostState.showSnackbar("contraseña actualizada $newPassword",duration =SnackbarDuration.Short)
                                loginViewModel.onEnableNewPasswordFields(false)
                            }
                                 } catch (e: Exception) {
                                Log.e("DataStore", "Error Updating password", e)
                                }
                            }
                         else {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    "usuario no valido",
                                    duration = SnackbarDuration.Short
                                )
                            }
                            loginViewModel.onClearFields()
                        }
                    }
                )

                ModelButton(text = stringResource(id = string.backButton),
                    R.dimen.text_title_medium,
                    modifier = Modifier.width(360.dp),
                    true,
                    onClickButton = {
                        loginViewModel.onEnableNewPasswordFields(false)
                    }
                )


            }
        }
    }
    /*Se agregó SnackbarHost al final de LoginComponent, que es necesario para mostrar el Snackbar.
    El SnackbarHost debe estar en el árbol de composables.*/
    SnackbarHost(hostState = snackbarHostState)
}















