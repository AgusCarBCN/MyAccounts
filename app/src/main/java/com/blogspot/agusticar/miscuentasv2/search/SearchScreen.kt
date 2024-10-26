package com.blogspot.agusticar.miscuentasv2.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.components.BoardType
import com.blogspot.agusticar.miscuentasv2.components.ModelButton
import com.blogspot.agusticar.miscuentasv2.components.TextFieldComponent
import com.blogspot.agusticar.miscuentasv2.main.model.IconOptions
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette
import com.blogspot.agusticar.miscuentasv2.utils.dateFormat
import java.util.Date

@Composable
@Preview
fun SearchScreen() {
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
            Column(modifier = Modifier.weight(0.5f)
                .clickable {  },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_calendar_today),
                    contentDescription = "calendar selector",
                    tint = LocalCustomColorsPalette.current.iconColor
                )

                TextFieldComponent(
                    modifier = Modifier
                        .fillMaxWidth()
                        ,
                    stringResource(id = R.string.fromdate),
                    Date().dateFormat(),
                    onTextChange = {  },
                    BoardType.TEXT,
                    false
                )

            }
            Column(modifier = Modifier.weight(0.5f)
                .clickable {  },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_calendar_today),
                    contentDescription = "calendar selector",
                    tint = LocalCustomColorsPalette.current.iconColor
                )

                TextFieldComponent(
                    modifier = Modifier
                        .fillMaxWidth(),
                    stringResource(id = R.string.todate),
                    Date().dateFormat(),
                    onTextChange = {  },
                    BoardType.TEXT,
                    false
                )
            }
        }
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