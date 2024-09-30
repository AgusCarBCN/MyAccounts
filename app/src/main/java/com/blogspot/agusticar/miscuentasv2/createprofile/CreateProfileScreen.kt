package com.blogspot.agusticar.miscuentasv2.createprofile


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.components.BoardType

import com.blogspot.agusticar.miscuentasv2.components.ModelButton
import com.blogspot.agusticar.miscuentasv2.components.TextFieldComponent
import com.blogspot.agusticar.miscuentasv2.main.data.repository.UserPreferencesRepository
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette
import com.blogspot.agusticar.miscuentasv2.main.model.Routes
import com.blogspot.agusticar.miscuentasv2.main.model.UserProfile
import kotlinx.coroutines.launch
import javax.inject.Inject


@Composable
fun CreateProfileComponent(createViewModel:CreateProfileViewModel, navigationController: NavHostController) {


    val scope = rememberCoroutineScope()


    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalCustomColorsPalette.current.backgroundPrimary) // Reemplaza con tu color de fondo
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
                stringResource(id = R.string.name),
                createViewModel.name.observeAsState("").value,
                onTextChange = {createViewModel.setName(it) },
                BoardType.TEXT,
                false
            )
            TextFieldComponent(
                modifier = Modifier.width(360.dp),
                stringResource(id = R.string.username),
                createViewModel.username.observeAsState("").value,
                onTextChange = { createViewModel.setUsername(it) },
                BoardType.TEXT,
                false
            )
            TextFieldComponent(
                modifier = Modifier.width(360.dp),
                stringResource(id = R.string.password),
                createViewModel.password.observeAsState("").value,
                onTextChange = { createViewModel.setPassword(it) },
                BoardType.PASSWORD,
                true
            )
            TextFieldComponent(
                modifier = Modifier.width(360.dp),
                stringResource(id = R.string.repeatpassword),
                createViewModel.repeatPassword.observeAsState("").value,
                onTextChange = {createViewModel.setRepeatPassword(it)  },
                BoardType.PASSWORD,
                true
            )
            ModelButton(text = stringResource(id = R.string.confirmButton),
                R.dimen.text_title_medium,
                modifier = Modifier.width(360.dp),
                true,
                onClickButton = {
                    navigationController.navigate(Routes.CreateAccounts.route)
                    try{
                    scope.launch { createViewModel.setToLogin(true)
                    createViewModel.setUserDataProfile(UserProfile(
                        createViewModel.name.value!!,
                        createViewModel.username.value!!, createViewModel.password.value!!
                    ))}
                        Log.d( "Datastore","dato grabado")
                    }catch (e: Exception) {
                        Log.e("DataStore", "Error writing to DataStore", e)
                    }
                }
            )

            ModelButton(text = stringResource(id = R.string.backButton),
                R.dimen.text_title_medium,
                modifier = Modifier.width(360.dp),
                true,
                onClickButton = {
                    navigationController.navigate(Routes.Login.route)
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
                    .background(LocalCustomColorsPalette.current.buttonColorPressed)
            )
        }
    }
}




