package com.blogspot.agusticar.miscuentasv2.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.components.HeadSwitch
import com.blogspot.agusticar.miscuentasv2.components.SwitchComponent

@Composable

fun SettingScreen(){

Column(){
    HeadSwitch(title = stringResource(id = R.string.appsettings))
    SwitchComponent(title= stringResource(id = R.string.theme), description = stringResource(id = R.string.destheme),false)
    SwitchComponent(title= stringResource(id = R.string.enableonboarding), description = stringResource(id = R.string.desenableonboarding),false)
    // Aquí se agregarán las vistas de la sección de ajustes

}
}