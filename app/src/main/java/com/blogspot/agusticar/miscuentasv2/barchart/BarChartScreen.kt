package com.blogspot.agusticar.miscuentasv2.barchart

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.blogspot.agusticar.miscuentasv2.createaccounts.view.AccountsViewModel
import com.blogspot.agusticar.miscuentasv2.newamount.view.EntriesViewModel
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.components.AccountSelector
import com.blogspot.agusticar.miscuentasv2.components.YearSelector
import com.blogspot.agusticar.miscuentasv2.search.SearchViewModel
import java.util.Calendar

@Composable

fun BarChartScreen(entriesViewModel: EntriesViewModel,
                   accountViewModel: AccountsViewModel,
                   barChartViewModel: BarChartViewModel

){
    val year = Calendar.getInstance().get(Calendar.YEAR)
    val accountSelected by accountViewModel.accountSelected.observeAsState()
    val yearSelected by barChartViewModel.selectedYear.observeAsState(year)
    val barChartData by barChartViewModel.barChartData.observeAsState(mutableListOf())


    val idAccount = accountSelected?.id ?: 1
    LaunchedEffect(idAccount) {
        barChartViewModel.barChartDataByMonth(idAccount)
    }
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Log.d("data",barChartData.toString())

        Row(
            modifier = Modifier
                .width(300.dp)
                .background(Color.Red),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AccountSelector(200, 10, stringResource(id = R.string.account), accountViewModel)
            YearSelector(barChartViewModel)
        }
    }
}