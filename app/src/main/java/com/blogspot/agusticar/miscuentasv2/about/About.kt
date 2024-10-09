package com.blogspot.agusticar.miscuentasv2.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.components.HeadSetting
import com.blogspot.agusticar.miscuentasv2.components.RowComponent

@Composable
@Preview
fun AboutScreen()
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
            onClick = {})
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



