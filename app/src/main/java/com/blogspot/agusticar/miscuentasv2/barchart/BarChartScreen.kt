package com.blogspot.agusticar.miscuentasv2.barchart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
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

@Composable

fun BarChartScreen(entriesViewModel: EntriesViewModel,
                   accountViewModel: AccountsViewModel,
                   searchViewModel: SearchViewModel

){
    val accountSelected by accountViewModel.accountSelected.observeAsState()


    val listOfMonths= listOf(stringResource(id = R.string.january,
        stringResource(id = R.string.february),
        stringResource(id = R.string.march),
        stringResource(id = R.string.april),
        stringResource(id = R.string.may),
        stringResource(id = R.string.june),
        stringResource(id = R.string.july),
        stringResource(id = R.string.august),
        stringResource(id = R.string.september),
        stringResource(id = R.string.october),
        stringResource(id = R.string.november),
        stringResource(id = R.string.december)
     ))
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Row(
            modifier = Modifier
                .width(300.dp)
                .background(Color.Red),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AccountSelector(200, 10, stringResource(id = R.string.account), accountViewModel)
            YearSelector(searchViewModel)
        }
    }
}