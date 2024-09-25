package com.blogspot.agusticar.miscuentasv2

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.blogspot.agusticar.miscuentasv2.R.*
import com.blogspot.agusticar.miscuentasv2.component.BoardType
import com.blogspot.agusticar.miscuentasv2.component.ModelButton
import com.blogspot.agusticar.miscuentasv2.component.TextFieldComponent
import com.blogspot.agusticar.miscuentasv2.ui.theme.MisCuentasv2Theme
import androidx.compose.ui.res.stringResource
import java.time.LocalTime
import java.util.Date


class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            MisCuentasv2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoginComponent(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun LoginComponent(modifier: Modifier = Modifier) {

    var userName by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }
    var salutation by rememberSaveable {mutableStateOf("")}

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
                .background(colorResource(id = color.orange))
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
                    .background(colorResource(id = color.orange))
            )
        }

        // Caja de login en la parte inferior, ocupando el otro 50% de la altura
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .background(colorResource(id = color.yellow))  // Color de fondo
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
                    fontSize = 20.sp,
                    color = colorResource(color.darkBrown),
                    fontWeight = FontWeight.Bold
                )
                TextFieldComponent(
                    modifier = Modifier.width(320.dp),
                    stringResource(id = string.username),
                    userName,
                    onTextChange = { new -> userName = new },
                    BoardType.TEXT
                )
                TextFieldComponent(
                    modifier = Modifier.width(320.dp),
                    stringResource(id = string.username),
                    password,
                    onTextChange = { password = it },
                    BoardType.PASSWORD,
                    true
                )
                ModelButton(text = stringResource(id = string.loginButton), modifier = Modifier.width(320.dp), enabledLoginButton,
                    onClickButton = {
                        if (validateLoginButton(userName, password)) {
                            Log.d("Login", "Login success")
                        } else {
                            Log.d("Login", "Login failed")
                        }
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
                            fontSize = 12.sp,
                            color = colorResource(id = color.black)
                        )
                    }
                )

            }
            if (enabledTextFieldNewPassword) {
                TextFieldComponent(
                    modifier = Modifier.width(320.dp),
                    stringResource(id = string.requestuser),
                    userName,
                    onTextChange = { new -> userName = new },
                    BoardType.TEXT
                )

                TextFieldComponent(
                    modifier = Modifier.width(320.dp),
                    stringResource(id = string.newpassword),
                    password,
                    onTextChange = { password = it },
                    BoardType.PASSWORD,
                    true
                )
                ModelButton(text = stringResource(id = R.string.confirmButton), modifier = Modifier.width(320.dp), true,
                    onClickButton = {
                        if (validateLoginButton(userName, password)) {
                            Log.d("Login", "Login success")
                        } else {
                            Log.d("Login", "Login failed")
                        }
                    }
                )

                ModelButton(text = stringResource(id = string.backButton), modifier = Modifier.width(320.dp), true,
                    onClickButton = {
                        changeVisibility(
                            enabledTextFieldNewPassword,
                            onChangeVisibility = { enabledTextFieldNewPassword = it })
                    }
                )
            }
        }
    }
}


private fun enableLoginButton(userName: String, password: String): Boolean =

    userName.isNotEmpty() && password.isNotEmpty() && userName.isNotBlank() && password.isNotBlank() && password.length >= 6

private fun validateLoginButton(userName: String, password: String): Boolean =
    userName == "Agus" && password == "nina1971"

private fun changeVisibility(visibility: Boolean, onChangeVisibility: (Boolean) -> Unit) {
    onChangeVisibility(!visibility)
}


@Composable
private fun getGreeting(username:String):String{
    val hour = LocalTime.now().hour
    return  when (hour) {
        in 6..11 -> "${stringResource(id = string.goodmornig)} $username"
        in 12..17 -> "${stringResource(id = string.goodafternoon)} $username"
        else -> "${stringResource(id = string.goodevening)} $username"
    }
}









