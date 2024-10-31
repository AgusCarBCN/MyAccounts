package com.blogspot.agusticar.miscuentasv2.search

import android.util.Log
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
import com.blogspot.agusticar.miscuentasv2.components.AccountSelector
import com.blogspot.agusticar.miscuentasv2.components.BoardType
import com.blogspot.agusticar.miscuentasv2.components.DatePickerSearch
import com.blogspot.agusticar.miscuentasv2.components.HeadSetting
import com.blogspot.agusticar.miscuentasv2.components.ModelButton
import com.blogspot.agusticar.miscuentasv2.components.RadioButtonSearch
import com.blogspot.agusticar.miscuentasv2.components.TextFieldComponent
import com.blogspot.agusticar.miscuentasv2.createaccounts.view.AccountsViewModel
import com.blogspot.agusticar.miscuentasv2.main.model.IconOptions
import com.blogspot.agusticar.miscuentasv2.main.view.MainViewModel
import com.blogspot.agusticar.miscuentasv2.newamount.view.EntriesViewModel
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette
import com.blogspot.agusticar.miscuentasv2.utils.dateFormat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date


@Composable
fun SearchScreen(
    accountViewModel: AccountsViewModel,
    searchViewModel: SearchViewModel,
    entriesViewModel: EntriesViewModel,
    mainViewModel: MainViewModel
) {
    val fromAmount by searchViewModel.fromAmount.observeAsState("0.0")
    val toAmount by searchViewModel.toAmount.observeAsState("0.0")
    val toDate by searchViewModel.selectedToDate.observeAsState(Date().dateFormat())
    val fromDate by searchViewModel.selectedFromDate.observeAsState("01/01/1900")
    val entryDescription by searchViewModel.entryDescription.observeAsState("")
    val enableSearchButton by searchViewModel.enableSearchButton.observeAsState(false)
    val selectedAccount by accountViewModel.accountSelected.observeAsState()
    val selectedOption by searchViewModel.selectedOptionIndex.observeAsState()
    val id=selectedAccount?.id?:0
    val scope = rememberCoroutineScope()
    val messageAmountError = stringResource(id = R.string.amountfromoverdateto)
    val messageDateError = stringResource(id = R.string.datefromoverdateto)
    searchViewModel.onEnableSearchButton()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp)
            .background(LocalCustomColorsPalette.current.backgroundPrimary)
            .verticalScroll(
            rememberScrollState()
        ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextFieldComponent(
            modifier = Modifier.width(360.dp),
            stringResource(id = R.string.searchentries),
            entryDescription,
            onTextChange = { searchViewModel.onEntryDescriptionChanged(it) },
            BoardType.TEXT,
            false
        )
        HeadSetting(title = stringResource(id = R.string.daterange), 20)
        Row(
            modifier = Modifier
                .width(360.dp)
                .fillMaxWidth(),
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
        AccountSelector(300,20,stringResource(id = R.string.selectanaccount), accountViewModel)
        RadioButtonSearch(searchViewModel)
        TextFieldComponent(
            modifier = Modifier.width(360.dp),
            stringResource(id = R.string.fromamount),
            fromAmount,
            onTextChange = {
                searchViewModel.onAmountsFieldsChange(it, toAmount)
                scope.launch(Dispatchers.Main) {
                    if (!searchViewModel.validateAmounts(fromAmount, toAmount)) {
                        SnackBarController.sendEvent(
                            event = SnackBarEvent(
                                messageAmountError
                            )
                        )
                    }
                    if(!searchViewModel.validateDates()){
                        SnackBarController.sendEvent(
                            event = SnackBarEvent(
                                messageDateError
                            )
                        )
                    }
                }
            },
            BoardType.DECIMAL,
            false
        )
        TextFieldComponent(
            modifier = Modifier.width(360.dp),
            stringResource(id = R.string.toamount),
            toAmount,
            onTextChange = {
                searchViewModel.onAmountsFieldsChange(fromAmount, it)
                scope.launch(Dispatchers.Main) {
                    if (!searchViewModel.validateAmounts(fromAmount, toAmount)) {
                        SnackBarController.sendEvent(
                            event = SnackBarEvent(
                                messageAmountError
                            )
                        )
                    }
                    if(!searchViewModel.validateDates()){
                        SnackBarController.sendEvent(
                            event = SnackBarEvent(
                                messageDateError
                            )
                        )
                    }
                }
            },
            BoardType.DECIMAL,
            false
        )
        ModelButton(text = stringResource(id = R.string.search),
            R.dimen.text_title_small,
            modifier = Modifier.width(360.dp),
            enableSearchButton,
            onClickButton = {
                entriesViewModel.getFilteredEntries(id,
                    entryDescription,
                    fromDate,
                    toDate,
                    fromAmount.toDoubleOrNull() ?:0.0,
                    toAmount.toDoubleOrNull()?:Double.MAX_VALUE,
                    selectedOption?:0)
                mainViewModel.selectScreen(IconOptions.ENTRIES)
                Log.d("data","fromDate: $fromDate")
                Log.d("data","toDate: $toDate")
                Log.d("data","fromAmount: $fromAmount")
                Log.d("data","toAmount: $toAmount")
                Log.d("data","selectOption: $selectedOption")
            }
        )

    }
}