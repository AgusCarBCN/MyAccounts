package com.blogspot.agusticar.miscuentasv2.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.components.HeadSetting
import com.blogspot.agusticar.miscuentasv2.components.RowComponent
import com.blogspot.agusticar.miscuentasv2.main.model.IconOptions
import com.blogspot.agusticar.miscuentasv2.main.view.MainViewModel
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette

@Composable

fun AboutScreen(mainViewModel: MainViewModel)
{
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 25.dp)
            .verticalScroll(
                rememberScrollState()
            )
    )
    {
        HeadSetting(title = stringResource(id = R.string.aboutapp), 20)

        RowComponent(title = stringResource(id = R.string.about),
            description = stringResource(id = R.string.desaboutapp),
            iconResource = R.drawable.info,
            onClick = {
                mainViewModel.selectScreen(IconOptions.ABOUT_DESCRIPTION)
            })
        RowComponent(title = stringResource(id = R.string.share),
            description = stringResource(id = R.string.desshare),
            iconResource = R.drawable.share,
            onClick = {})
        RowComponent(title = stringResource(id = R.string.rate),
            description = stringResource(id = R.string.desrate),
            iconResource = R.drawable.star_rate,
            onClick = {})
        RowComponent(title = stringResource(id = R.string.contactme),
            description = stringResource(id = R.string.desemail),
            iconResource = R.drawable.emai,
            onClick = {})
        RowComponent(title = stringResource(id = R.string.othersapp),
            description = stringResource(id = R.string.desstore),
            iconResource = R.drawable.apps,
            onClick = {})
        RowComponent(title = stringResource(id = R.string.privacy),
            description = stringResource(id = R.string.despolicy),
            iconResource = R.drawable.privacy,
            onClick = {})
    }
}
@Composable
@Preview(showBackground = true)

fun AboutApp() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 25.dp)
            .verticalScroll(
                rememberScrollState()
            )
    )
    {
    HeadSetting(title = stringResource(id = R.string.app_name), 20)
    Text(
        text = stringResource(id = R.string.description),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, end = 20.dp, start = 20.dp),
        fontSize = 18.sp,
        color = LocalCustomColorsPalette.current.textColor
    )
}
}



