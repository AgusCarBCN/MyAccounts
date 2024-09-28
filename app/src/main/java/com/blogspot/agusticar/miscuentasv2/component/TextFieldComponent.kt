package com.blogspot.agusticar.miscuentasv2.component


import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.blogspot.agusticar.miscuentasv2.R


@Composable

fun TextFieldComponent(
    modifier: Modifier,
    label: String,
    inputText: String,
    onTextChange: (String) -> Unit,
    type: BoardType,
    textVisible: Boolean=false, // Boolean to determine if password should be visible or not
) {
    var visiblePasswordIcon by rememberSaveable { mutableStateOf(false) }
    TextField(
        value = inputText,
        onValueChange = {
            // Lógica para cambiar "a" por "A"
            //onTextChange(if (it.contains("a")) it.replace("a", "A") else it)
            onTextChange(it)
            //equivale a  newValue -> onTextChange(newValue)
        },
        label = { Text(text = label) },
        shape = RoundedCornerShape(10.dp),
        maxLines = 1,
        singleLine = true, // Limita la altura de la caja de entrada a una sola línea

        modifier = modifier
            .wrapContentHeight() // Altura ajustable
            .padding(16.dp), keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = getBoardType(type) // Set the keyboard type based on the input parameter
        ),
        visualTransformation = if (textVisible && !visiblePasswordIcon) PasswordVisualTransformation() else VisualTransformation.None, // Change display based on boolean

        colors = TextFieldDefaults.colors(
            contentColorFor(backgroundColor = Color.Transparent),
            focusedIndicatorColor = Color.Transparent,  // Sin borde cuando está enfocado
            unfocusedIndicatorColor = Color.Transparent  // Sin borde cuando no está enfocado

        ),
        trailingIcon = {
            if(type==BoardType.PASSWORD){
                if(visiblePasswordIcon) {
                    Icon(painter = painterResource (id=R.drawable.visibility), contentDescription = stringResource(
                        id = R.string.visibily
                    ) )

                }else {
                    Icon(
                        painter = painterResource(id = R.drawable.visibility_off),
                        contentDescription = stringResource(id = R.string.visibilyoff)
                    )
                }
                IconButton(onClick = {visiblePasswordIcon=!visiblePasswordIcon}) {
                }
            }
        }
    )
}

private fun getBoardType(type: BoardType): KeyboardType {
    return when (type) {
        BoardType.EMAIL -> KeyboardType.Email
        BoardType.NUMBER -> KeyboardType.Number
        BoardType.PASSWORD -> KeyboardType.Password
        BoardType.PHONE -> KeyboardType.Phone
        BoardType.TEXT-> KeyboardType.Text
        BoardType.URI -> KeyboardType.Uri
        BoardType.DECIMAL -> KeyboardType.Password

    }
}

