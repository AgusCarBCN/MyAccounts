package com.blogspot.agusticar.miscuentasv2.search

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.components.AccountSelector
import com.blogspot.agusticar.miscuentasv2.components.BoardType
import com.blogspot.agusticar.miscuentasv2.components.DatePickerSearch

import com.blogspot.agusticar.miscuentasv2.components.ModelButton
import com.blogspot.agusticar.miscuentasv2.components.TextFieldComponent
import com.blogspot.agusticar.miscuentasv2.createaccounts.view.AccountsViewModel
import com.blogspot.agusticar.miscuentasv2.main.model.IconOptions
import com.blogspot.agusticar.miscuentasv2.main.view.MainViewModel
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette
import com.blogspot.agusticar.miscuentasv2.utils.dateFormat
import java.util.Date


@Composable
fun SearchScreen(accountViewModel:AccountsViewModel,
                 searchViewModel: SearchViewModel,
                 mainViewModel: MainViewModel) {
    val fromAmount by searchViewModel.fromAmount.observeAsState("")
    val toAmount by searchViewModel.toAmount.observeAsState("")
    val toDate by searchViewModel.selectedToDate.observeAsState(Date().dateFormat())
    val fromDate by searchViewModel.selectedFromDate.observeAsState(Date().dateFormat())
    val entryDescription by searchViewModel.entryDescription.observeAsState("")

    Column( modifier = Modifier
        .fillMaxWidth()
        .padding(top = 30.dp)
        .background(LocalCustomColorsPalette.current.backgroundPrimary),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        TextFieldComponent(
            modifier = Modifier.width(360.dp),
            stringResource(id = R.string.searchentries),
            entryDescription,
            onTextChange = {searchViewModel.onEntryDescriptionChanged(it) },
            BoardType.TEXT,
            false
        )

        Row(modifier = Modifier
            .width(360.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically) {
           DatePickerSearch(modifier = Modifier.weight(0.5f),R.string.fromdate,searchViewModel,true)
            DatePickerSearch(modifier = Modifier.weight(0.5f),R.string.todate,searchViewModel,false)
        }
        AccountSelector(stringResource(id = R.string.selectanaccount), accountViewModel)
        TextFieldComponent(
            modifier = Modifier.width(360.dp),
            stringResource(id = R.string.fromamount),
            fromAmount,
            onTextChange = { searchViewModel.onFromAmountChanged(it) },
            BoardType.DECIMAL,
            false
        )
        TextFieldComponent(
            modifier = Modifier.width(360.dp),
            stringResource(id = R.string.toamount),
            toAmount,
            onTextChange = { searchViewModel.onToAmountChanged(it) },
            BoardType.DECIMAL,
            false
        )
        ModelButton(text = stringResource(id = R.string.search),
            R.dimen.text_title_small,
            modifier = Modifier.width(360.dp),
            true,
            onClickButton = {

            }
        )
        ModelButton(text = stringResource(id = R.string.backButton),
            R.dimen.text_title_small,
            modifier = Modifier.width(360.dp),
            true,
            onClickButton = {
            mainViewModel.selectScreen(IconOptions.HOME)
            }
        )

    }
}