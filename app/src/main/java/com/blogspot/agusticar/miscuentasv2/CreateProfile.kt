package com.blogspot.agusticar.miscuentasv2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.blogspot.agusticar.miscuentasv2.component.BoardType
import com.blogspot.agusticar.miscuentasv2.component.ModelButton
import com.blogspot.agusticar.miscuentasv2.component.TextFieldComponent
import com.blogspot.agusticar.miscuentasv2.ui.theme.MisCuentasv2Theme

class CreateProfile : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MisCuentasv2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                        }
                    }
                }
            }
        }
    }

}

@Composable
fun CreateProfileComponent() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.yellow)) // Reemplaza con tu color de fondo
    ) {
        val (profileImage, box) = createRefs()

        Column(modifier = Modifier
            .constrainAs(profileImage) {
                top.linkTo(parent.top)
                end.linkTo(parent.end)               // Termina en el lado derecho del padre
                start.linkTo(parent.start)
                bottom.linkTo(box.top)
                // Parte inferior anclada al padre
            }) {

            ProfileImageWithCamera()
        }
        Column(modifier = Modifier
            .constrainAs(box) {
                top.linkTo(profileImage.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }

        ) {

            TextFieldComponent(
                modifier = Modifier.width(360.dp),
                stringResource(id = R.string.username),
                "",
                onTextChange = { it },
                BoardType.TEXT,
                false
            )
            TextFieldComponent(
                modifier = Modifier.width(360.dp),
                stringResource(id = R.string.name),
                "",
                onTextChange = { it },
                BoardType.TEXT,
                false
            )
            TextFieldComponent(
                modifier = Modifier.width(360.dp),
                stringResource(id = R.string.password),
                "",
                onTextChange = { it },
                BoardType.PASSWORD,
                true
            )
            TextFieldComponent(
                modifier = Modifier.width(360.dp),
                stringResource(id = R.string.repeatpassword),
                "",
                onTextChange = { it },
                BoardType.PASSWORD,
                true
            )
            ModelButton(text = stringResource(id = R.string.confirmButton),
                R.dimen.text_title_medium,
                modifier = Modifier.width(360.dp),
                false,
                onClickButton = {

                }
            )

            ModelButton(text = stringResource(id = R.string.backButton),
                R.dimen.text_title_medium,
                modifier = Modifier.width(360.dp),
                true,
                onClickButton = {

                }
            )
        }


    }
}

@Composable
@Preview
fun ProfileImageWithCamera() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(), // Para que se ajuste al contenido
        contentAlignment = Alignment.Center
    ) {
        // Imagen de perfil dentro de una Card circular
        Card(
            modifier = Modifier
                .size(250.dp),
            shape = CircleShape, // Hace que el Card sea circular
            // Reemplaza lightYellow
        ) {
            // Imagen de perfil
            Image(
                painter = painterResource(id = R.drawable.contabilidad), // Reemplaza con tu recurso
                contentDescription = "Profile Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize() // La imagen ocupa todo el Card
            )
        }

        // Ícono de cámara superpuesto en la esquina inferior izquierda
        Card(
            modifier = Modifier
                .size(50.dp) // Tamaño del ícono de cámara
                .align(Alignment.Center) // Posición en la esquina inferior izquierda
                .offset(x = 80.dp, y = (100).dp),
            shape = CircleShape,


            ) {
            Image(
                painter = painterResource(id = R.drawable.camera), // Reemplaza con tu ícono de cámara
                contentDescription = "Camera Icon",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id=R.color.orange))
            )
        }
    }
}




