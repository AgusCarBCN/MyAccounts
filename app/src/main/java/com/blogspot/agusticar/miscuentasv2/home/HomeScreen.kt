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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.components.AccountCard
import com.blogspot.agusticar.miscuentasv2.components.EntryList
import com.blogspot.agusticar.miscuentasv2.components.HeadCard
import com.blogspot.agusticar.miscuentasv2.components.HeadSetting
import com.blogspot.agusticar.miscuentasv2.createaccounts.view.AccountsViewModel
import com.blogspot.agusticar.miscuentasv2.main.model.IconOptions
import com.blogspot.agusticar.miscuentasv2.main.view.MainViewModel
import com.blogspot.agusticar.miscuentasv2.newamount.view.EntriesViewModel
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette
import com.blogspot.agusticar.miscuentasv2.utils.Utils


@Composable
fun HomeScreen(
    mainViewModel: MainViewModel,
    accountsViewModel: AccountsViewModel,
    entriesViewModel: EntriesViewModel
) {
    val incomes by entriesViewModel.totalIncomes.observeAsState(0.0)
    val expenses by entriesViewModel.totalExpenses.observeAsState(0.0)
    val currencyCode by accountsViewModel.currencyCode.observeAsState("USD")

    // Observa el estado de la lista de cuentas
    val accounts by accountsViewModel.listOfAccounts.observeAsState(null)   // Observa el estado de la lista de cuentas

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
                HeadCard(modifier = Modifier.weight(0.5f),
                    Utils.numberFormat(incomes,currencyCode),
                    true,
                    onClickCard={mainViewModel.selectScreen(IconOptions.ENTRIES)
                    entriesViewModel.getAllIncomes()
                    })
                Spacer(modifier = Modifier.width(5.dp))  // Espacio entre los dos cards
                HeadCard(modifier = Modifier.weight(0.5f),
                    Utils.numberFormat(expenses,currencyCode),
                    false,
                    onClickCard={mainViewModel.selectScreen(IconOptions.ENTRIES)
                    entriesViewModel.getAllExpenses()
                    })
            }

            Spacer(modifier = Modifier.width(5.dp))
            HeadSetting(title = stringResource(id = R.string.youraccounts),
                size = 22)

            // Mostrar las cuentas si están disponibles
            LazyColumn(
                modifier = Modifier.fillMaxSize(),

                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,

            ) {
                items(accounts!!) { account -> // Solo utiliza accounts
                    AccountCard(
                        Utils.numberFormat(account.balance,currencyCode),
                        account.name,
                        R.string.seeall,
                        onClickCard = { mainViewModel.selectScreen(IconOptions.ENTRIES)
                            entriesViewModel.getAllEntriesByAccount(account.id)
                        }
                    )  // Crea un card para cada cuenta en la lista
                    Spacer(modifier = Modifier.height(20.dp))  // Espacio entre cada card (separación)
                }
            }
        }
    }
 }
























