package com.blogspot.agusticar.miscuentasv2.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.components.HeadSetting
import com.blogspot.agusticar.miscuentasv2.components.RowComponent
import com.blogspot.agusticar.miscuentasv2.components.SwitchComponent
import com.blogspot.agusticar.miscuentasv2.tutorial.view.TutorialViewModel
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette


@Composable

fun SettingScreen(tutorialViewModel: TutorialViewModel, settingViewModel: SettingViewModel) {

    val switchTutorial by settingViewModel.switchTutorial.observeAsState(true)
    val switchDarkTheme by settingViewModel.switchDarkTheme.observeAsState(false)
    val switchNotifications by settingViewModel.switchNotifications.observeAsState(false)

    val showTutorial by tutorialViewModel.showTutorial.observeAsState(true)
    settingViewModel.getSwitchTutorial()
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 25.dp)
            .verticalScroll(
                rememberScrollState()
            )
    )
    {
        HeadSetting(title = stringResource(id = R.string.appsettings), 20)
        SwitchComponent(
            title = stringResource(id = R.string.theme),
            description = stringResource(id = R.string.destheme),
            switchDarkTheme,
            onClickSwitch = {settingViewModel.onSwitchDarkThemeClicked(it)}
        )
        SwitchComponent(
            title = stringResource(id = R.string.enableonboarding),
            description = stringResource(id = R.string.desenableonboarding),
            switchTutorial,
            onClickSwitch = {settingViewModel.onSwitchTutorialClicked(it)}
        )
        SwitchComponent(
            title = stringResource(id = R.string.enablenotifications),
            description = stringResource(id = R.string.desenableonboarding),
            switchNotifications,
            onClickSwitch = {settingViewModel.onSwitchNotificationsClicked(it)}
        )

        SpacerSetting()

        HeadSetting(title = stringResource(id = R.string.backup), 20)

        RowComponent(title = stringResource(id = R.string.createbackup),
            description = stringResource(id = R.string.desbackup),
            iconResource = R.drawable.backup,
            onClick = {})
        RowComponent(title = stringResource(id = R.string.loginButton),
            description = stringResource(id = R.string.desload),
            iconResource = R.drawable.download,
            onClick = {})


        SpacerSetting()

        HeadSetting(title = stringResource(id = R.string.accountsetting), 20)

        RowComponent(title = stringResource(id = R.string.add_an_account),
            description = stringResource(id = R.string.desadd_an_account),
            iconResource = R.drawable.add,
            onClick = {})
        RowComponent(title = stringResource(id = R.string.rename_account),
            description = stringResource(id = R.string.desrename_account),
            iconResource = R.drawable.edit,
            onClick = {})
        RowComponent(title = stringResource(id = R.string.delete_data_account),
            description = stringResource(id = R.string.desdelete_data_account),
            iconResource = R.drawable.baseline_delete_24,
            onClick = {})

        RowComponent(title = stringResource(id = R.string.changecurrency),
            description = stringResource(id = R.string.deschangecurrency),
            iconResource = R.drawable.exchange,
            onClick = {})
    }
}

@Composable
private fun SpacerSetting() {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .background(LocalCustomColorsPalette.current.textColor.copy(alpha = 0.2f)) // Ajusta el valor alpha para la opacidad
            .height(1.dp) // Cambié a height para que la línea sea horizontal, ajusta si es necesario
    )
}
