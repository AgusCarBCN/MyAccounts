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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.components.AccountCard
import com.blogspot.agusticar.miscuentasv2.components.HeadCard
import com.blogspot.agusticar.miscuentasv2.components.HeadSetting
import com.blogspot.agusticar.miscuentasv2.createaccounts.view.CreateAccountsViewModel
import com.blogspot.agusticar.miscuentasv2.main.model.currencyLocales
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.abs


@Composable
fun HomeScreen(
    createAccountsViewModel: CreateAccountsViewModel
) {
    val income = 3000.43
    val expenses = -1200.78

    // Observa el estado de la lista de cuentas
    val accounts by createAccountsViewModel.listOfAccounts.observeAsState(null)
    // Observa el estado de la lista de cuentas

    val currencyCode by createAccountsViewModel.currencyCode.observeAsState("")
    val locale = currencyLocales[currencyCode] ?: Locale.GERMAN
    // Formatear la cantidad en la moneda especificada
    val numberFormat = NumberFormat.getCurrencyInstance(locale)
    // Iniciar la carga de cuentas solo cuando el Composable se inicia
    LaunchedEffect(Unit) {
        createAccountsViewModel.getAllAccounts()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalCustomColorsPalette.current.backgroundPrimary),
        verticalArrangement = Arrangement.Center,  // Centra los elementos verticalmente
        horizontalAlignment = Alignment.CenterHorizontally  // Centra los elementos horizontalmente
    ) {
        if (accounts.isNullOrEmpty()) {
            Text(text = stringResource(id = R.string.noaccounts),
                color = LocalCustomColorsPalette.current.textColor,
                fontSize = 18.sp)
        }
        else{
            Row(modifier = Modifier.padding(top = 20.dp)) {
                HeadCard(modifier = Modifier.weight(0.5f), numberFormat.format(abs(income)), true)
                Spacer(modifier = Modifier.width(5.dp))  // Espacio entre los dos cards
                HeadCard(modifier = Modifier.weight(0.5f), numberFormat.format(abs(expenses)), false)
            }

            Spacer(modifier = Modifier.width(5.dp))
            HeadSetting(title = stringResource(id = R.string.youraccounts), size = 20)

            // Si la lista de cuentas está cargando o vacía, muestra un indicador de carga o un mensaje


            // Mostrar las cuentas si están disponibles
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(accounts!!) { account -> // Solo utiliza accounts
                    AccountCard(
                        numberFormat.format(abs(account.balance)),
                        account.name, true

                    )  // Crea un card para cada cuenta en la lista
                    Spacer(modifier = Modifier.height(10.dp))  // Espacio entre cada card (separación)
                }
            }
        }
    }
 }
























