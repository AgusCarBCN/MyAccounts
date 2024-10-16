package com.blogspot.agusticar.miscuentasv2.transfer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.blogspot.agusticar.miscuentasv2.components.AccountSelector
import com.blogspot.agusticar.miscuentasv2.components.BoardType
import com.blogspot.agusticar.miscuentasv2.components.IconAnimated
import com.blogspot.agusticar.miscuentasv2.components.ModelButton
import com.blogspot.agusticar.miscuentasv2.components.TextFieldComponent
import com.blogspot.agusticar.miscuentasv2.components.message
import com.blogspot.agusticar.miscuentasv2.createaccounts.view.AccountsViewModel
import com.blogspot.agusticar.miscuentasv2.main.data.database.dto.EntryDTO
import com.blogspot.agusticar.miscuentasv2.main.model.IconOptions
import com.blogspot.agusticar.miscuentasv2.main.view.MainViewModel
import com.blogspot.agusticar.miscuentasv2.newamount.view.EntriesViewModel
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette
import com.blogspot.agusticar.miscuentasv2.utils.dateFormat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

@Composable

fun Transfer(
    mainViewModel: MainViewModel,
    accountViewModel: AccountsViewModel,
    entryViewModel: EntriesViewModel
) {

    val accountFrom by accountViewModel.accountSelected.observeAsState()
    val accountTo by accountViewModel.destinationAccount.observeAsState()
    val amountTransfer by entryViewModel.entryAmount.observeAsState("")
    val confirmButton by entryViewModel.enableConfirmTransferButton.observeAsState(false)
    val scope = rememberCoroutineScope()
    val idAccountFrom = accountFrom?.id ?: 1
    val idAccountTo = accountTo?.id ?: 1
    val amount = amountTransfer.toDoubleOrNull() ?: 0.0
    val negativeAmount = (-1) * (amountTransfer.toDoubleOrNull() ?: 0.0)
    //accountViewModel.isValidTransfer()
    val transferFrom = stringResource(id = R.string.transferfrom)
    val transferTo = stringResource(id = R.string.transferTo)
    //SnackBarMessage
    val amountOverBalanceMessage = message(resource = R.string.overbalance)
    val transferSuccessMessage = message(resource = R.string.transferdone)
    var operationStatus = 1
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconAnimated(
            R.drawable.transferoption,
            120,
            initColor = LocalCustomColorsPalette.current.buttonColorDefault,
            targetColor = LocalCustomColorsPalette.current.buttonColorPressed
        )

        TextFieldComponent(
            modifier = Modifier.width(320.dp),
            stringResource(id = R.string.amountentrie),
            amountTransfer,
            onTextChange = { entryViewModel.onAmountChanged(idAccountTo,idAccountFrom ,it) },
            BoardType.DECIMAL,
            false
        )
        AccountSelector(stringResource(id = R.string.originaccount), accountViewModel)
        AccountSelector(stringResource(id = R.string.destinationaccount), accountViewModel, true)

        ModelButton(text = stringResource(id = R.string.confirmButton),
            R.dimen.text_title_small,
            modifier = Modifier.width(320.dp),
            confirmButton,
            onClickButton = {
                operationStatus = if (accountViewModel.isValidExpense(amount)) {
                    1
                } else {
                    -1
                }
                scope.launch(Dispatchers.IO) {
                    if (operationStatus == 1) {
                        entryViewModel.addEntry(
                            EntryDTO(
                                transferFrom,
                                negativeAmount,
                                Date().dateFormat(),
                                R.drawable.transferoption,
                                R.string.transferfrom,
                                idAccountFrom
                            )
                        )
                        entryViewModel.addEntry(
                            EntryDTO(
                                description = transferTo,
                                amount = amount,
                                date = Date().dateFormat(),
                                categoryId = R.drawable.transferoption,
                                R.string.transferTo,
                                accountId = idAccountTo
                            )
                        )
                        accountViewModel.transferAmount(idAccountFrom, idAccountTo, amount)
                        entryViewModel.onChangeTransferButton(false)
                    }
                    if (operationStatus == 1) {
                        SnackBarController.sendEvent(event = SnackBarEvent(transferSuccessMessage))
                    } else if (operationStatus == -1) {
                        SnackBarController.sendEvent(event = SnackBarEvent(amountOverBalanceMessage))
                    }

                }
            }
        )
        ModelButton(text = stringResource(id = R.string.backButton),
            R.dimen.text_title_small,
            modifier = Modifier.width(320.dp),
            true,
            onClickButton = {
                mainViewModel.selectScreen(IconOptions.HOME)
            }

        )
    }
}