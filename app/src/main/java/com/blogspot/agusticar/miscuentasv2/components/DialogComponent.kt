package com.blogspot.agusticar.miscuentasv2.components

import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette



@Composable
fun ExitAppDialog(
    showDialog: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {

    if (showDialog) {
        AlertDialog(containerColor= LocalCustomColorsPalette.current.drawerColor,
            onDismissRequest = { onDismiss() },

            title={Text(stringResource(id = R.string.titleexit),
                fontSize=20.sp,
                fontWeight = FontWeight.Bold,
                color = LocalCustomColorsPalette.current.textColor)},

            text={Text(stringResource(id = R.string.exitinfo),
                fontSize=18.sp,
                color = LocalCustomColorsPalette.current.textColor)}
            ,
            confirmButton = {
                ModelButton(text = stringResource(id = R.string.confirmButton),
                    R.dimen.text_body_medium,
                    modifier = Modifier.width(130.dp),
                    true,
                    onClickButton = {
                        onConfirm()
                    } )
            },
            dismissButton = {
                ModelButton(text = stringResource(id = R.string.cancelButton),
                    R.dimen.text_body_medium,
                    modifier = Modifier.width(130.dp),
                    true,
                    onClickButton = {
                        onDismiss()
                    } )
            }
        )
    }
}
