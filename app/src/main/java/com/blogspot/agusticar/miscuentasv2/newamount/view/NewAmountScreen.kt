package com.blogspot.agusticar.miscuentasv2.newamount.view

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
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Entry
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette
import com.blogspot.agusticar.miscuentasv2.utils.dateFormat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

@Composable

fun NewAmount(viewModel:AccountsViewModel)
{
    val scope = rememberCoroutineScope()
    val descriptionEntry by viewModel.name.observeAsState("")
    val amountEntry by viewModel.amount.observeAsState("")
    val categorySelected by viewModel.categorySelected.observeAsState(null)
    val status= categorySelected?.isIncome?:false
    val iconResource=categorySelected?.iconResource?:0
    val titleResource=categorySelected?.name?:0
    //Snackbar messages
    val newIncomeMessage= message(resource = R.string.newincomecreated)
    val newExpenseMessage= message(resource = R.string.newexpensecreated)
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
            onTextChange = { viewModel.onNameChanged(it)},
            BoardType.TEXT,
            false
        )
        TextFieldComponent(
            modifier = Modifier.width(320.dp),
            stringResource(id = R.string.enternote),
            amountEntry,
            onTextChange = {viewModel.onAmountChanged(it) },
            BoardType.DECIMAL,
            false
        )
        AccountSelector(stringResource(id = R.string.selectanaccount),viewModel)
        ModelButton(text = stringResource(id =if(status) R.string.newincome else R.string.newexpense),
            R.dimen.text_title_small,
            modifier = Modifier.width(320.dp),
            true,
            onClickButton = {
                scope.launch (Dispatchers.IO){
                    viewModel.addEntry(Entry(description =descriptionEntry,
                        amount=amountEntry.toDoubleOrNull()?:0.0,
                        date = Date().dateFormat(),
                        categoryId = titleResource,
                        accountId = 1
                        ))

                    SnackBarController.sendEvent(event = SnackBarEvent(if(status) newIncomeMessage
                    else newExpenseMessage))
                }

            }
        )
        ModelButton(text = stringResource(id = R.string.backButton),
            R.dimen.text_title_small,
            modifier = Modifier.width(320.dp),
            true,
            onClickButton = {

            }
        )
    }
}