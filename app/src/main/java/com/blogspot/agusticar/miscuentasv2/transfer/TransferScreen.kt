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
import com.blogspot.agusticar.miscuentasv2.components.AccountSelector
import com.blogspot.agusticar.miscuentasv2.components.BoardType
import com.blogspot.agusticar.miscuentasv2.components.HeadSetting
import com.blogspot.agusticar.miscuentasv2.components.IconAnimated
import com.blogspot.agusticar.miscuentasv2.components.ModelButton
import com.blogspot.agusticar.miscuentasv2.components.TextFieldComponent
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
    val scope = rememberCoroutineScope()
    val idOrigin=originAccount?.id?:1
    val idDestination=destinationAccount?.id?:1
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
        AccountSelector(stringResource(id = R.string.originaccount),viewModel,origin = true)
        AccountSelector(stringResource(id = R.string.destinationaccount),viewModel, destination = true)
        
        ModelButton(text = stringResource(id = R.string.confirmButton),
            R.dimen.text_title_small,
            modifier = Modifier.width(320.dp),
            true,
            onClickButton = {
                scope.launch (Dispatchers.IO) {
                    viewModel.transferEntry(
                        Entry(description =  "transfer",
                            amount=-2000.0,
                            date = Date().dateFormat(),
                            categoryId = R.drawable.transferoption,
                            accountId = idOrigin),
                        Entry(description =  "transfer",
                            amount=+2000.0,
                            date = Date().dateFormat(),
                            categoryId = R.drawable.transferoption,
                            accountId = idDestination))
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