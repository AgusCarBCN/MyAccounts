package com.blogspot.agusticar.miscuentasv2.components

import android.widget.Switch
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette





@Preview
@Composable
fun SwitchComponent() {
    var switchCheckedState by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Switch(
            checked = switchCheckedState,
            onCheckedChange = { switchCheckedState = it },
            /*
            The "checkedIconColor" and "uncheckedIconColor" excluded from this list
            because you need to set an icon first, use the "thumbContent" parameter.
            You can find an example in the next section.
             */
            colors = SwitchColors(
                checkedThumbColor = LocalCustomColorsPalette.current.thumbCheckedColor, // Color del thumb activado
                checkedTrackColor = LocalCustomColorsPalette.current.trackCheckedColor,      // Color del track activado
                checkedBorderColor = LocalCustomColorsPalette.current.trackCheckedColor,         // Color del borde activado
                checkedIconColor = LocalCustomColorsPalette.current.backgroundSwitch,          // Color del icono activado

                uncheckedThumbColor = LocalCustomColorsPalette.current.thumbDefaultColor,  // Color del thumb desactivado
                uncheckedTrackColor = LocalCustomColorsPalette.current.trackDefaultColor,       // Color del track desactivado
                uncheckedBorderColor = LocalCustomColorsPalette.current.backgroundSwitch,          // Color del borde desactivado
                uncheckedIconColor = LocalCustomColorsPalette.current.backgroundSwitch,           // Color del icono desactivado

                disabledCheckedThumbColor = LocalCustomColorsPalette.current.backgroundSwitch, // Color del thumb activado cuando está deshabilitado
                disabledCheckedTrackColor = LocalCustomColorsPalette.current.backgroundSwitch, // Color del track activado cuando está deshabilitado
                disabledCheckedBorderColor = LocalCustomColorsPalette.current.backgroundSwitch,    // Color del borde activado cuando está deshabilitado
                disabledCheckedIconColor = LocalCustomColorsPalette.current.backgroundSwitch,     // Color del icono activado cuando está deshabilitado

                disabledUncheckedThumbColor = LocalCustomColorsPalette.current.backgroundSwitch, // Color del thumb desactivado cuando está deshabilitado
                disabledUncheckedTrackColor = LocalCustomColorsPalette.current.backgroundSwitch, // Color del track desactivado cuando está deshabilitado
                disabledUncheckedBorderColor = LocalCustomColorsPalette.current.backgroundSwitch,    // Color del borde desactivado cuando está deshabilitado
                disabledUncheckedIconColor = LocalCustomColorsPalette.current.backgroundSwitch      // Color del icono desactivado cuando está deshabilitado
            )





        )
    }
}



