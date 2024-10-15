package com.blogspot.agusticar.miscuentasv2.transfer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
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

fun Transfer(viewModel: AccountsViewModel)
{
    val originAccount by viewModel.accountSelected.observeAsState()
    val destinationAccount by viewModel.accountSelected.observeAsState()
    val amountTransfer by viewModel.amount.observeAsState("")
    val scope = rememberCoroutineScope()
    val idOrigin=originAccount?.id?:1
    val idDestination=destinationAccount?.id?:1

    val transferFrom= stringResource(id = R.string.transferfrom)
    val transferTo= stringResource(id = R.string.transferTo)
    //SnackBarMessage
    val amountOverBalanceMessage= message(resource = R.string.overbalance)
    val transferSuccessMessage= message(resource = R.string.transferdone)
    var operationStatus=1
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
       IconAnimated(R.drawable.transferoption,
           120,
           initColor = LocalCustomColorsPalette.current.buttonColorDefault ,
           targetColor = LocalCustomColorsPalette.current.buttonColorPressed )

        TextFieldComponent(
            modifier = Modifier.width(320.dp),
            stringResource(id = R.string.amountentrie),
            "",
            onTextChange = { },
            BoardType.DECIMAL,
            false
        )
        AccountSelector(stringResource(id = R.string.originaccount),viewModel)
        AccountSelector(stringResource(id = R.string.destinationaccount),viewModel,true)
        
        ModelButton(text = stringResource(id = R.string.confirmButton),
            R.dimen.text_title_small,
            modifier = Modifier.width(320.dp),
            viewModel.isValidTransfer(),
            onClickButton = {
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