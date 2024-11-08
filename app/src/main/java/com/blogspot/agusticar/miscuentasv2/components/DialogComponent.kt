package com.blogspot.agusticar.miscuentasv2.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Category
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette


@Composable
fun ModelDialog(
    title:Int,
    message:Int,
    showDialog: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {

    if (showDialog) {
        AlertDialog(containerColor= LocalCustomColorsPalette.current.drawerColor,
            onDismissRequest = { onDismiss() },

            title={Text(stringResource(id = title),
                fontSize=20.sp,
                fontWeight = FontWeight.Bold,
                color = LocalCustomColorsPalette.current.textColor)},

            text={Text(stringResource(id = message),
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
@Composable
fun NotificationDialog(

    showDialog: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {

    if (showDialog) {
        AlertDialog(containerColor= LocalCustomColorsPalette.current.drawerColor,
            onDismissRequest = { onDismiss() },

            icon= {
                Icon(
                    painter = painterResource(id = R.drawable.notificationoption),
                    contentDescription = "notification icon",
                    tint = LocalCustomColorsPalette.current.textColor,
                    modifier = Modifier
                        .size(24.dp)
                )
            }                           ,

            text={Text(stringResource(id = R.string.notificationrequired),
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

@Composable
fun ModelDialogWithTextField(
    category: Category,
    showDialog: Boolean,
    textFieldValue: String,
    onValueChange: (String) -> Unit,  // Callback para actualizar el valor del TextField
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            containerColor = LocalCustomColorsPalette.current.backgroundPrimary,
            onDismissRequest = { onDismiss() },
            title = {
                Text(
                    stringResource(id=R.string.titledialog)+
                            " "
                            +stringResource(category.nameResource),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = LocalCustomColorsPalette.current.textColor
                )
            },
            text = {
                Column {
                    Text(
                        text = stringResource(R.string.messagedialog),
                        fontSize = 18.sp,
                        color = LocalCustomColorsPalette.current.textColor
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    TextFieldComponent(modifier=Modifier.fillMaxWidth()
                        ,
                        label= stringResource(id = R.string.limitMax),
                        textFieldValue,
                        onTextChange = onValueChange,
                        BoardType.DECIMAL,
                        false
                        )

                }
            },
            confirmButton = {
                ModelButton(
                    text = stringResource(id = R.string.confirmButton),
                    R.dimen.text_body_medium,
                    modifier = Modifier.width(130.dp),
                    true,
                    onClickButton = {
                        onConfirm()
                    }
                )
            },
            dismissButton = {
                ModelButton(
                    text = stringResource(id = R.string.cancelButton),
                    R.dimen.text_body_medium,
                    modifier = Modifier.width(130.dp),
                    true,
                    onClickButton = {
                        onDismiss()
                    }
                )
            }
        )
    }
}
