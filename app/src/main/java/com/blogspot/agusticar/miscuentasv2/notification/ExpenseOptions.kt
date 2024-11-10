package com.blogspot.agusticar.miscuentasv2.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.components.AccountSelector
import com.blogspot.agusticar.miscuentasv2.components.BoardType
import com.blogspot.agusticar.miscuentasv2.components.DatePickerSearch
import com.blogspot.agusticar.miscuentasv2.components.HeadSetting
import com.blogspot.agusticar.miscuentasv2.components.ModelButton
import com.blogspot.agusticar.miscuentasv2.components.TextFieldComponent
import com.blogspot.agusticar.miscuentasv2.createaccounts.view.AccountsViewModel
import com.blogspot.agusticar.miscuentasv2.main.model.IconOptions
import com.blogspot.agusticar.miscuentasv2.main.view.MainViewModel
import com.blogspot.agusticar.miscuentasv2.newamount.view.EntriesViewModel
import com.blogspot.agusticar.miscuentasv2.search.SearchViewModel
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette

@Composable
fun SelectOptionsExpense(
    searchViewModel: SearchViewModel,
    accountsViewModel: AccountsViewModel

){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp)
            .background(LocalCustomColorsPalette.current.backgroundPrimary)
            ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeadSetting(title = stringResource(id = R.string.limitMax), 20)
        TextFieldComponent(
            modifier = Modifier.width(360.dp),
            stringResource(id = R.string.searchentries),
            "",
            onTextChange = {  },
            BoardType.DECIMAL,
            false
        )
        HeadSetting(title = stringResource(id = R.string.daterange), 20)
        Row(
            modifier = Modifier
                .width(360.dp)
                .fillMaxWidth()
                .padding(bottom=20.dp, top = 20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            DatePickerSearch(
                modifier = Modifier.weight(0.5f),
                R.string.fromdate,
                searchViewModel,
                true
            )
            DatePickerSearch(
                modifier = Modifier.weight(0.5f),
                R.string.todate,
                searchViewModel,
                false
            )
        }
        AccountSelector(300, 20, stringResource(id = R.string.selectanaccount), accountsViewModel)
        ModelButton(text = stringResource(id = R.string.confirmButton),
            R.dimen.text_title_medium,
            modifier = Modifier.width(360.dp)
                .padding(top=20.dp),
            true,
            onClickButton = {


            }
        )
    }
}