package com.blogspot.agusticar.miscuentasv2.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.blogspot.agusticar.miscuentasv2.components.AccountCard
import com.blogspot.agusticar.miscuentasv2.components.HeadCard
import com.blogspot.agusticar.miscuentasv2.components.HeadSetting
import com.blogspot.agusticar.miscuentasv2.createaccounts.view.CreateAccountsViewModel
import com.blogspot.agusticar.miscuentasv2.createprofile.CreateProfileViewModel
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Account
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette

@Composable

fun HomeScreen(
    createAccountsViewModel: CreateAccountsViewModel,
    createProfile: CreateProfileViewModel
) {

    val income = 3000.43
    val expenses = -1200.78



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalCustomColorsPalette.current.backgroundPrimary),
        verticalArrangement = Arrangement.Center,  // Centra los elementos verticalmente
        horizontalAlignment = Alignment.CenterHorizontally  // Centra los elementos horizontalmente
    ) {

        Row(modifier = Modifier.padding(top = 20.dp)) {

            HeadCard(modifier = Modifier.weight(0.5f), income, createAccountsViewModel)
            Spacer(modifier = Modifier.width(5.dp))  // Espacio entre los dos cards
            HeadCard(modifier = Modifier.weight(0.5f), expenses, createAccountsViewModel)

        }
        Spacer(modifier = Modifier.width(5.dp))
        HeadSetting(title = "Tus cuentas", size = 24)
        LazyColumn(
            modifier = Modifier.fillMaxSize(),  // Ocupa toda la pantalla
            verticalArrangement = Arrangement.Center,  // Centra verticalmente el contenido
            horizontalAlignment = Alignment.CenterHorizontally  // Centra horizontalmente cada elemento
        ) {
            items(listOfAccounts) { item ->
                AccountCard(
                    item.accountBalance,
                    item.accountName,
                    createAccountsViewModel
                )  // Crea un card para cada cuenta en la lista
                Spacer(modifier = Modifier.height(10.dp))  // Espacio entre cada card (separación)
            }
        }


    }
}

val listOfAccounts = listOf(
    Account(name = "Cuenta1", balance = 20000.02),
    Account(name = "Cuenta2", balance = 10050.02),
    Account(name = "Cuenta3", balance = 5050.02)
)
























