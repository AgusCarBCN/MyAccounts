package com.blogspot.agusticar.miscuentasv2.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.blogspot.agusticar.miscuentasv2.components.AccountCard
import com.blogspot.agusticar.miscuentasv2.components.HeadCard
import com.blogspot.agusticar.miscuentasv2.components.UserImage
import com.blogspot.agusticar.miscuentasv2.createaccounts.view.CreateAccountsViewModel
import com.blogspot.agusticar.miscuentasv2.createprofile.CreateProfileViewModel
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette

@Composable

fun HomeScreen(createAccountsViewModel: CreateAccountsViewModel, createProfile:CreateProfileViewModel)
{

    val income=3000.43
    val expenses=-1200.78
    val selectedImageUri by createProfile.selectedImageUriSaved.observeAsState()


    Column(modifier = Modifier
        .fillMaxSize()
        .background(LocalCustomColorsPalette.current.backgroundPrimary),
            verticalArrangement = Arrangement.Top,  // Centra los elementos verticalmente
            horizontalAlignment = Alignment.CenterHorizontally  // Centra los elementos horizontalmente
            ){
        selectedImageUri?.let { UserImage(uri = it, 220) }
        Row(modifier = Modifier.padding(10.dp)) {

            HeadCard(modifier = Modifier.weight(0.5f),income,createAccountsViewModel)
            Spacer(modifier = Modifier.width(5.dp))  // Espacio entre los dos cards
            HeadCard(modifier = Modifier.weight(0.5f),expenses,createAccountsViewModel)

        }
        Spacer(modifier = Modifier.width(5.dp))
        AccountCard(5600.43, createAccountsViewModel)

    }
}

/*@Composable
fun HomeLazyColumn() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),  // Ocupa toda la pantalla
        verticalArrangement = Arrangement.Center,  // Centra verticalmente el contenido
        horizontalAlignment = Alignment.CenterHorizontally  // Centra horizontalmente cada elemento
    ) {
        items(cars) { car ->
            CarItem(car)
        }
    }
}*/
