package com.blogspot.agusticar.miscuentasv2.transfer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
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
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette

@Composable
@Preview
fun Transfer()
{
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
        //AccountSelector(stringResource(id = R.string.originaccount))
        //AccountSelector(stringResource(id = R.string.destinationaccount))
        
        ModelButton(text = stringResource(id = R.string.confirmButton),
            R.dimen.text_title_small,
            modifier = Modifier.width(320.dp),
            true,
            onClickButton = {
                //navigationController.navigate(Routes.Login.route)
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