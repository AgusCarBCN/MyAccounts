package com.blogspot.agusticar.miscuentasv2


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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import androidx.navigation.NavHostController
import com.blogspot.agusticar.miscuentasv2.R.color
import com.blogspot.agusticar.miscuentasv2.R.drawable
import com.blogspot.agusticar.miscuentasv2.R.string
import com.blogspot.agusticar.miscuentasv2.component.BoardType
import com.blogspot.agusticar.miscuentasv2.component.LocalCustomColorsPalette

import com.blogspot.agusticar.miscuentasv2.component.ModelButton
import com.blogspot.agusticar.miscuentasv2.component.TextFieldComponent
import com.blogspot.agusticar.miscuentasv2.model.Routes
import kotlinx.coroutines.launch
import java.time.LocalTime


@Composable
fun LoginComponent(modifier: Modifier,navigationController: NavHostController) {

    var userName by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }

    val scope = rememberCoroutineScope()
    /* Se usa para gestionar el estado del Snackbar. Esto te permite mostrar y controlar el Snackbar
     desde cualquier parte de tu UI.*/
    val snackbarHostState = remember { SnackbarHostState() }

    var enabledLoginButton by rememberSaveable { mutableStateOf(false) }
    var enabledTextFieldNewPassword by rememberSaveable { mutableStateOf(false) }
    enabledLoginButton = enableLoginButton(userName, password)
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
                .background(LocalCustomColorsPalette.current.backgroundPrimary)
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
                    .background(LocalCustomColorsPalette.current.backgroundPrimary)
            )
        }

        // Caja de login en la parte inferior, ocupando el otro 50% de la altura
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .background(LocalCustomColorsPalette.current.backgroundSecondary)  // Color de fondo
                .constrainAs(loginBox) {
                    top.linkTo(imageBox.bottom)          // Parte superior anclada al imageBox
                    start.linkTo(parent.start)           // Empieza en el lado izquierdo del padre
                    end.linkTo(parent.end)               // Termina en el lado derecho del padre
                    bottom.linkTo(parent.bottom)         // Parte inferior anclada al padre
                },

            verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!enabledTextFieldNewPassword) {
                Text(
                    text = getGreeting(username = "Agustin"),
                    fontSize =  with(LocalDensity.current) { dimensionResource(id = R.dimen.text_body_big).toSp() },
                    color = colorResource(color.darkBrown),
                    fontWeight = FontWeight.Bold
                )
                TextFieldComponent(
                    modifier = Modifier.width(360.dp),
                    stringResource(id = string.username),
                    userName,
                    onTextChange = { new -> userName = new },
                    BoardType.TEXT
                )
                TextFieldComponent(
                    modifier = Modifier.width(360.dp),
                    stringResource(id = string.password),
                    password,
                    onTextChange = { password = it },
                    BoardType.PASSWORD,
                    true
                )
                ModelButton(text = stringResource(id = string.loginButton),
                     R.dimen.text_title_medium,
                    modifier = Modifier.width(360.dp),
                    enabledLoginButton,
                    onClickButton = {
                        navigationController.navigate(Routes.Home.route)
                    }
                )

                TextButton(
                    onClick = {
                        changeVisibility(
                            enabledTextFieldNewPassword,
                            onChangeVisibility = { enabledTextFieldNewPassword = it })
                    },
                    content = {
                        Text(
                            text = stringResource(id = string.forgotpassword),
                            fontSize = with(LocalDensity.current) { dimensionResource(id = R.dimen.text_body_medium).toSp() },
                            color = colorResource(id = color.black)
                        )
                    }
                )

            }
            if (enabledTextFieldNewPassword) {
                TextFieldComponent(
                    modifier = Modifier.width(360.dp),
                    stringResource(id = string.requestuser),
                    userName,
                    onTextChange = { new -> userName = new },
                    BoardType.TEXT
                )

                TextFieldComponent(
                    modifier = Modifier.width(360.dp),
                    stringResource(id = string.newpassword),
                    password,
                    onTextChange = { password = it },
                    BoardType.PASSWORD,
                    true
                )
                ModelButton(text = stringResource(id = string.confirmButton),R.dimen.text_title_medium,
                    modifier = Modifier.width(360.dp),
                    true,
                    onClickButton = {
                            scope.launch {
                                snackbarHostState.showSnackbar("campo vacio",duration =SnackbarDuration.Short)
                            }

                    }
                )

                ModelButton(text = stringResource(id = string.backButton),
                    R.dimen.text_title_medium,
                    modifier = Modifier.width(360.dp),
                    true,
                    onClickButton = {
                        changeVisibility(
                            enabledTextFieldNewPassword,
                            onChangeVisibility = { enabledTextFieldNewPassword = it })
                    }
                )


            }
        }
    }
    /*Se agregó SnackbarHost al final de LoginComponent, que es necesario para mostrar el Snackbar.
    El SnackbarHost debe estar en el árbol de composables.*/
    SnackbarHost(hostState = snackbarHostState)
}


private fun enableLoginButton(userName: String, password: String): Boolean =

    userName.isNotEmpty() && password.isNotEmpty() && userName.isNotBlank() && password.isNotBlank() && password.length >= 6

private fun validateLoginButton(userName: String, password: String): Boolean =
    userName == "Agus" && password == "nina1971"

private fun changeVisibility(visibility: Boolean, onChangeVisibility: (Boolean) -> Unit) {
    onChangeVisibility(!visibility)
}


@Composable
private fun getGreeting(username: String): String {
    val hour = LocalTime.now().hour
    return when (hour) {
        in 6..11 -> "${stringResource(id = string.goodmornig)} $username"
        in 12..17 -> "${stringResource(id = string.goodafternoon)} $username"
        else -> "${stringResource(id = string.goodevening)} $username"
    }
}









