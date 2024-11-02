package com.blogspot.agusticar.miscuentasv2.changecurrency

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.components.CurrencySelector
import com.blogspot.agusticar.miscuentasv2.components.HeadSetting
import com.blogspot.agusticar.miscuentasv2.components.IconAnimated
import com.blogspot.agusticar.miscuentasv2.components.ModelButton
import com.blogspot.agusticar.miscuentasv2.createaccounts.view.AccountsViewModel
import com.blogspot.agusticar.miscuentasv2.main.model.IconOptions
import com.blogspot.agusticar.miscuentasv2.main.view.MainViewModel
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette

@Composable


fun ChangeCurrencyScreen(mainViewModel:MainViewModel,
                         accountsViewModel: AccountsViewModel) {
    Column(

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        IconAnimated(
            iconResource = R.drawable.ic_change_currency, sizeIcon = 120,
            LocalCustomColorsPalette.current.imageTutorialInit,
            LocalCustomColorsPalette.current.imageTutorialTarget
        )
        CurrencySelector(accountsViewModel)
        HeadSetting(title = stringResource(id = R.string.changeformattext), 16)
        ModelButton(text = stringResource(id = R.string.changeFormat),
            R.dimen.text_title_medium,
            modifier = Modifier.width(360.dp),
            true,
            onClickButton = {


            }
        )
        HeadSetting(title = stringResource(id = R.string.changecurrencytext), 16)
        ModelButton(text = stringResource(id = R.string.changeCurrency),
            R.dimen.text_title_medium,
            modifier = Modifier.width(360.dp),
            true,
            onClickButton = {


            }
        )
        ModelButton(text = stringResource(id = R.string.backButton),
            R.dimen.text_title_medium,
            modifier = Modifier.width(360.dp),
            true,
            onClickButton = {
                mainViewModel.selectScreen(IconOptions.HOME)
            })
    }
}
