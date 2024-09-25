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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.blogspot.agusticar.miscuentasv2.component.BoardType
import com.blogspot.agusticar.miscuentasv2.component.ModelButton
import com.blogspot.agusticar.miscuentasv2.component.TextFieldComponent


@Composable
fun LoginComponent(modifier: Modifier = Modifier) {
    var userName by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }
    var enabledLoginButton by rememberSaveable { mutableStateOf(false) }
    var enabledTextFieldNewPassword by rememberSaveable { mutableStateOf(false)}
    ConstraintLayout(
        modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.lightYellow))
    ) {
        // Crear referencias para las cajas
        val (imageBox, loginBox) = createRefs()

        // Caja de imagen en la parte superior, ocupando el 50% de la altura
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .background(colorResource(id = R.color.orange))
                .constrainAs(imageBox) {
                    top.linkTo(parent.top)          // Parte superior anclada al padre
                    start.linkTo(parent.start)      // Empieza en el lado izquierdo del padre
                    end.linkTo(parent.end)          // Termina en el lado derecho del padre
                    bottom.linkTo(loginBox.top)     // Parte inferior anclada al loginBox
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Imagen de contabil
            Image(
                painter = painterResource(id = R.drawable.contabilidad),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(250.dp) // Uso de size para mantener la relación de aspecto
                    .background(colorResource(id = R.color.orange))
            )
        }

        // Caja de login en la parte inferior, ocupando el otro 50% de la altura
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .background(colorResource(id = R.color.yellow))  // Color de fondo
                .constrainAs(loginBox) {
                    top.linkTo(imageBox.bottom)          // Parte superior anclada al imageBox
                    start.linkTo(parent.start)           // Empieza en el lado izquierdo del padre
                    end.linkTo(parent.end)               // Termina en el lado derecho del padre
                    bottom.linkTo(parent.bottom)         // Parte inferior anclada al padre
                },

            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Buenas Tardes Agustin",
                fontSize = 20.sp,
                color = colorResource(R.color.darkBrown)
            )
            TextFieldComponent(modifier = Modifier.width(320.dp), "Introduce el nombre" ,userName  ,onTextChange ={new->userName=new}, BoardType.TEXT)
            TextFieldComponent(modifier = Modifier.width(320.dp), "Introduce contraseña",password , onTextChange= {password=it},BoardType.PASSWORD,true)
            ModelButton(text = "Login", modifier = Modifier.width(320.dp),enabledLoginButton)
            Text(
                text = "¿Olvidaste la contraseña?",
                fontSize = 12.sp,
                color = colorResource(id = R.color.black)
            )
        }
    }
}
