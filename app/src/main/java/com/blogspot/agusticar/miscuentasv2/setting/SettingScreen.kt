package com.blogspot.agusticar.miscuentasv2.setting

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.documentfile.provider.DocumentFile
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.SnackBarController
import com.blogspot.agusticar.miscuentasv2.SnackBarEvent
import com.blogspot.agusticar.miscuentasv2.components.HeadSetting
import com.blogspot.agusticar.miscuentasv2.components.RowComponent
import com.blogspot.agusticar.miscuentasv2.components.SwitchComponent
import com.blogspot.agusticar.miscuentasv2.createaccounts.view.AccountsViewModel
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Entry
import com.blogspot.agusticar.miscuentasv2.main.model.IconOptions
import com.blogspot.agusticar.miscuentasv2.main.model.Routes
import com.blogspot.agusticar.miscuentasv2.main.view.MainViewModel
import com.blogspot.agusticar.miscuentasv2.newamount.view.EntriesViewModel
import com.blogspot.agusticar.miscuentasv2.setting.model.EntryCSV
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette
import com.blogspot.agusticar.miscuentasv2.utils.Utils
import com.blogspot.agusticar.miscuentasv2.utils.dateFormat
import kotlinx.coroutines.launch
import java.util.Date


@Composable

fun SettingScreen(settingViewModel: SettingViewModel,
                  mainViewModel: MainViewModel,
                  accountsViewModel: AccountsViewModel,
                  entriesViewModel: EntriesViewModel,
                  navToCreateAccounts:()->Unit
                  ) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val switchTutorial by settingViewModel.switchTutorial.observeAsState(true)
    val switchDarkTheme by settingViewModel.switchDarkTheme.observeAsState(false)
    val switchNotifications by settingViewModel.switchNotifications.observeAsState(false)
    val entries by entriesViewModel.listOfEntriesDataBase.observeAsState(listOf())
    val entriesCSV= toEntryCSV(entries)
    val date= Date().dateFormat()
    val fileName="backup$date"
    val messageExport= stringResource(id = R.string.exportData)+fileName
    val pickerExportLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
               val directory = DocumentFile.fromTreeUri(context, uri) // Direct assignment
                if (directory != null && directory.isDirectory) {
                    Utils.writeCsvFile(entriesCSV,context,fileName,directory)
                    scope.launch { SnackBarController.sendEvent(event = SnackBarEvent(messageExport))}
                }
            }
        }
    }
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

        SpacerApp()

        HeadSetting(title = stringResource(id = R.string.backup), 20)

        RowComponent(title = stringResource(id = R.string.createbackup),
            description = stringResource(id = R.string.desbackup),
            iconResource = R.drawable.backup,
            onClick = {val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
                pickerExportLauncher.launch(intent)})
        RowComponent(title = stringResource(id = R.string.loadbackup),
            description = stringResource(id = R.string.desload),
            iconResource = R.drawable.download,
            onClick = {})


        SpacerApp()

        HeadSetting(title = stringResource(id = R.string.accountsetting), 20)

        RowComponent(title = stringResource(id = R.string.add_an_account),
            description = stringResource(id = R.string.desadd_an_account),
            iconResource = R.drawable.add,
            onClick = { navToCreateAccounts()
            accountsViewModel.onDisableCurrencySelector()})
        RowComponent(title = stringResource(id = R.string.edit_account),
            description = stringResource(id = R.string.desedit_account),
            iconResource = R.drawable.edit,
            onClick = {
                settingViewModel.onSelectAccountOption(false)
                mainViewModel.selectScreen(IconOptions.SETTING_ACCOUNTS)
            })
        RowComponent(title = stringResource(id = R.string.delete_an_account),
            description = stringResource(id = R.string.desdelete_an_account),
            iconResource = R.drawable.baseline_delete_24,
            onClick = {
                settingViewModel.onSelectAccountOption(true)
                mainViewModel.selectScreen(IconOptions.SETTING_ACCOUNTS)
            }
        )
        RowComponent(title = stringResource(id = R.string.changecurrency),
            description = stringResource(id = R.string.deschangecurrency),
            iconResource = R.drawable.exchange,
            onClick = {mainViewModel.selectScreen(IconOptions.CHANGE_CURRENCY)})
    }
}

@Composable
fun SpacerApp() {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .background(LocalCustomColorsPalette.current.textColor.copy(alpha = 0.2f)) // Ajusta el valor alpha para la opacidad
            .height(1.dp) // Cambié a height para que la línea sea horizontal, ajusta si es necesario
    )
}
@Composable
fun toEntryCSV(entries:List<Entry>):MutableList<EntryCSV>{

    val entriesCSV= mutableListOf<EntryCSV>()
    entries.forEach { entry ->
        entriesCSV.add(EntryCSV(entry.description,
            stringResource(id = entry.categoryName),
            entry.amount,
            entry.date,
            entry.categoryId,
            entry.categoryName,
            entry.accountId))
    }
    return entriesCSV
}