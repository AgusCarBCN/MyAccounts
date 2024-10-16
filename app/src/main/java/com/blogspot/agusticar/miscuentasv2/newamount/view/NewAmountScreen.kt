package com.blogspot.agusticar.miscuentasv2.newamount.view

import android.util.Log
import androidx.activity.compose.BackHandler
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
import com.blogspot.agusticar.miscuentasv2.components.HeadSetting
import com.blogspot.agusticar.miscuentasv2.components.IconAnimated
import com.blogspot.agusticar.miscuentasv2.components.ModelButton
import com.blogspot.agusticar.miscuentasv2.components.TextFieldComponent
import com.blogspot.agusticar.miscuentasv2.components.message
import com.blogspot.agusticar.miscuentasv2.createaccounts.view.AccountsViewModel
import com.blogspot.agusticar.miscuentasv2.main.data.database.dto.EntryDTO
import com.blogspot.agusticar.miscuentasv2.main.model.IconOptions
import com.blogspot.agusticar.miscuentasv2.main.view.MainViewModel
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette
import com.blogspot.agusticar.miscuentasv2.utils.dateFormat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

@Composable

fun NewAmount(mainViewModel: MainViewModel,
              entryViewModel:EntriesViewModel,
              accountViewModel:AccountsViewModel)

{
    val scope = rememberCoroutineScope()
    val descriptionEntry by entryViewModel.entryName.observeAsState("")
    val amountEntry by entryViewModel.entryAmount.observeAsState("")
    val categorySelected by entryViewModel.categorySelected.observeAsState(null)
    val enableConfirmButton by entryViewModel.enableConfirmButton.observeAsState(false)
    val accountSelected by accountViewModel.accountSelected.observeAsState()

    accountViewModel.getAllAccounts()
    val idAccount=accountSelected?.id?:1
    val status= categorySelected?.isIncome?:false
    val iconResource=categorySelected?.iconResource?:0
    val titleResource=categorySelected?.name?:0
    val amount=amountEntry.toDoubleOrNull() ?: 0.0
    val negativeAmount=(-1)*(amountEntry.toDoubleOrNull() ?: 0.0)
    //Snackbar messages
    val newIncomeMessage= message(resource = R.string.newincomecreated)
    val newExpenseMessage= message(resource = R.string.newexpensecreated)
    val amountOverBalanceMessage= message(resource = R.string.overbalance)
    var operationStatus: Int

    val initColor=
        if(status) LocalCustomColorsPalette.current.iconIncomeInit
        else LocalCustomColorsPalette.current.iconExpenseInit
    val targetColor= if(status) LocalCustomColorsPalette.current.iconIncomeTarget
    else LocalCustomColorsPalette.current.iconExpenseTarget
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconAnimated(iconResource = iconResource, sizeIcon =120,initColor, targetColor  )
        HeadSetting(title = stringResource(id = titleResource), 24)
        TextFieldComponent(
            modifier = Modifier.width(320.dp),
            stringResource(id = R.string.desamount),
            descriptionEntry,
            onTextChange = { entryViewModel.onTextFieldsChanged(it, amountEntry) },
            BoardType.TEXT,
            false
        )
        TextFieldComponent(
            modifier = Modifier.width(320.dp),
            stringResource(id = R.string.enternote),
            amountEntry,
            onTextChange = { entryViewModel.onTextFieldsChanged(descriptionEntry,it)},
            BoardType.DECIMAL,
            false
        )
        AccountSelector(stringResource(id = R.string.selectanaccount),accountViewModel)
        ModelButton(text = stringResource(id =if(status) R.string.newincome else R.string.newexpense),
            R.dimen.text_title_small,
            modifier = Modifier.width(320.dp),
            enableConfirmButton,
            onClickButton = {
                operationStatus = if(!status){
                    if(accountViewModel.isValidExpense(amountEntry.toDoubleOrNull()?:0.0)){
                        1
                    }else {
                        0
                    }
                }else{
                    1
                }
                Log.d("Update","status: $status")
                    scope.launch(Dispatchers.IO) {
                        if(operationStatus==1) {
                            entryViewModel.addEntry(
                                EntryDTO(
                                    descriptionEntry,
                                    if (status) amount
                                    else negativeAmount,
                                    date = Date().dateFormat(),
                                    categoryId = iconResource,
                                    categoryName = titleResource,
                                    accountId = idAccount
                                )
                            )
                            accountViewModel.updateEntry(idAccount,if(status)amount else negativeAmount,false)

                            if (status) {
                                SnackBarController.sendEvent(event = SnackBarEvent(newIncomeMessage))
                            } else {
                                SnackBarController.sendEvent(event = SnackBarEvent(newExpenseMessage))
                            }
                        }else{
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