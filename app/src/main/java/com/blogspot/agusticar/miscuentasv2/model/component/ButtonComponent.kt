package com.blogspot.agusticar.miscuentasv2.model.component


import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.blogspot.agusticar.miscuentasv2.model.LocalCustomColorsPalette

@Composable
fun ModelButton(
    text: String,
    fontDimen:Int,
    modifier: Modifier = Modifier, // Permitir modificar el botón desde el exterior
    enabledButton: Boolean = true,
    onClickButton: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    // Definir colores
    val defaultContentColor = LocalCustomColorsPalette.current.textButtonColorDefault
    val pressedContentColor = LocalCustomColorsPalette.current.textButtonColorPressed
    val defaultContainerColor = LocalCustomColorsPalette.current.buttonColorDefault
    val pressedContainerColor = LocalCustomColorsPalette.current.buttonColorPressed
    val disabled= LocalCustomColorsPalette.current.disableButton
    Button(
        onClick = {
            onClickButton() // Ejecutar el clic pasado como parámetro
        },
        interactionSource = interactionSource, // Para manejar eventos de interacción
        enabled = enabledButton,
        modifier = modifier
            .wrapContentHeight() // Altura ajustable
            .padding(10.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = if (pressed) pressedContentColor else defaultContentColor,
            containerColor = if (pressed) pressedContainerColor else defaultContainerColor,
            disabledContainerColor= LocalCustomColorsPalette.current.disableButton
        ),

        ) {
        Text(
            text = text,
            fontSize = with(LocalDensity.current) { dimensionResource(id = fontDimen).toSp() },
            fontWeight = FontWeight.Bold, // Estilo de texto en negrita
            textAlign = TextAlign.Center
        )
    }
}
