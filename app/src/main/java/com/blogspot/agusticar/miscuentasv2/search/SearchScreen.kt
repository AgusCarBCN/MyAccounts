package com.blogspot.agusticar.miscuentasv2.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.components.AccountSelector
import com.blogspot.agusticar.miscuentasv2.components.BoardType
import com.blogspot.agusticar.miscuentasv2.components.DatePickerDocked

import com.blogspot.agusticar.miscuentasv2.components.ModelButton
import com.blogspot.agusticar.miscuentasv2.components.TextFieldComponent
import com.blogspot.agusticar.miscuentasv2.createaccounts.view.AccountsViewModel
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette


@Composable
fun SearchScreen(accountViewModel:AccountsViewModel) {
    Column( modifier = Modifier
        .fillMaxWidth()
        .padding(top = 30.dp)
        .background(LocalCustomColorsPalette.current.backgroundPrimary),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        TextFieldComponent(
            modifier = Modifier.width(360.dp),
            stringResource(id = R.string.searchentries),
            "entrada",
            onTextChange = {  },
            BoardType.TEXT,
            false
        )

        Row(modifier = Modifier
            .width(360.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically) {
           DatePickerDocked(modifier = Modifier.weight(0.5f),R.string.fromdate)
            DatePickerDocked(modifier = Modifier.weight(0.5f),R.string.todate)
        }
        AccountSelector(stringResource(id = R.string.selectanaccount), accountViewModel)
        TextFieldComponent(
            modifier = Modifier.width(360.dp),
            stringResource(id = R.string.fromamount),
            "entrada",
            onTextChange = {  },
            BoardType.DECIMAL,
            false
        )
        TextFieldComponent(
            modifier = Modifier.width(360.dp),
            stringResource(id = R.string.toamount),
            "entrada",
            onTextChange = {  },
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

            }
        )

    }
}