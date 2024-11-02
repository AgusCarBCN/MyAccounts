package com.blogspot.agusticar.miscuentasv2.changecurrency

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.SnackBarController
import com.blogspot.agusticar.miscuentasv2.SnackBarEvent
import com.blogspot.agusticar.miscuentasv2.components.CurrencySelector
import com.blogspot.agusticar.miscuentasv2.components.HeadSetting
import com.blogspot.agusticar.miscuentasv2.components.IconAnimated
import com.blogspot.agusticar.miscuentasv2.components.ModelButton
import com.blogspot.agusticar.miscuentasv2.createaccounts.view.AccountsViewModel
import com.blogspot.agusticar.miscuentasv2.main.model.IconOptions
import com.blogspot.agusticar.miscuentasv2.main.view.MainViewModel
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable


fun ChangeCurrencyScreen(mainViewModel:MainViewModel,
                         accountsViewModel: AccountsViewModel) {
    val scope = rememberCoroutineScope()
    val currencyCode by accountsViewModel.currencyCodeShowed.observeAsState("EUR")
    val currencyCodeSelected by accountsViewModel.currencyCodeSelected.observeAsState("EUR")
    val messageFormatCurrencyChange = stringResource(id = R.string.currencyformatchange)
    val messageCurrencyChange = stringResource(id = R.string.currencychange)
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

                scope.launch(Dispatchers.Main) {
                    accountsViewModel.setCurrencyCode(currencyCode)
                    SnackBarController.sendEvent(event = SnackBarEvent(messageFormatCurrencyChange))
                }
            }
        )
        HeadSetting(title = stringResource(id = R.string.changecurrencytext), 16)
        ModelButton(text = stringResource(id = R.string.changeCurrency),
            R.dimen.text_title_medium,
            modifier = Modifier.width(360.dp),
            true,
            onClickButton = {

            scope.launch(Dispatchers.Main) {
                SnackBarController.sendEvent(event = SnackBarEvent(messageCurrencyChange))
            }
            }
        )
        ModelButton(text = stringResource(id = R.string.backButton),
            R.dimen.text_title_medium,
            modifier = Modifier.width(360.dp),
            true,
            onClickButton = {
                accountsViewModel.onCurrencyShowedChange(currencyCodeSelected)
                mainViewModel.selectScreen(IconOptions.HOME)
            })
    }
}
