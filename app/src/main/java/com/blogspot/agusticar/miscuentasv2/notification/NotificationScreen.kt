package com.blogspot.agusticar.miscuentasv2.notification

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.SnackBarController
import com.blogspot.agusticar.miscuentasv2.SnackBarEvent
import com.blogspot.agusticar.miscuentasv2.main.model.IconOptions
import com.blogspot.agusticar.miscuentasv2.main.view.MainViewModel
import com.blogspot.agusticar.miscuentasv2.setting.SettingViewModel
import com.google.accompanist.permissions.shouldShowRationale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun NotificationScreen(settingViewModel: SettingViewModel,
                       mainViewModel: MainViewModel){


    val permissionState = rememberPermissionState(permission = android.Manifest.permission.POST_NOTIFICATIONS)
    val messagePermissionGranted= stringResource(id = R.string.permissiongranted)
    val messagePermissionDenied = stringResource(id = R.string.permissiondeny)
    val messageNotificationRequired=stringResource(id = R.string.notificationrequired)
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {

        permissionState.launchPermissionRequest()
    }
    if(permissionState.status.isGranted){
        scope.launch(Dispatchers.Main) {
            SnackBarController.sendEvent(event = SnackBarEvent(messagePermissionGranted))
            settingViewModel.onSwitchNotificationsClicked(true)
            mainViewModel.selectScreen(IconOptions.SETTINGS)
        }

    }
    else if(permissionState.status.shouldShowRationale){
        scope.launch(Dispatchers.Main) {
            SnackBarController.sendEvent(event = SnackBarEvent(messageNotificationRequired))
            settingViewModel.onSwitchNotificationsClicked(false)
            mainViewModel.selectScreen(IconOptions.SETTINGS)
        }
    }


    else{
        scope.launch(Dispatchers.Main) {
            SnackBarController.sendEvent(event = SnackBarEvent(messagePermissionDenied))
            settingViewModel.onSwitchNotificationsClicked(false)
            mainViewModel.selectScreen(IconOptions.SETTINGS)
        }
    }

}

