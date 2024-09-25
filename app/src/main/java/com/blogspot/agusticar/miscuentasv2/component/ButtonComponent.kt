package com.blogspot.agusticar.miscuentasv2.component

import android.app.Activity
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blogspot.agusticar.miscuentasv2.R
@Composable
fun ModelButton(
    text: String,    
    modifier: Modifier = Modifier, // Permitir modificar el botón desde el exterior
    enabledButton: Boolean = true
    ) {
    var state by remember {
        mutableStateOf(true)}

    val activity = LocalContext.current as Activity


        Button(

            onClick = { state = !state
                       activity.finish()  // Cierra la actividad cuando el botón es presionado
            },enabled=enabledButton,
            modifier = modifier
                .wrapContentHeight() // Altura ajustable
                .padding(16.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = if(state)colorResource(id = R.color.black) else colorResource(id = R.color.white) ,
                containerColor = if(state)  colorResource(id = R.color.orange) else colorResource(id = R.color.darkOrange)
            )
        ) {
            Text(
                text = text,
                fontSize = 20.sp, // Ajusta según @dimen/large
                fontWeight = FontWeight.Bold, // Estilo de texto en negrita
                textAlign = TextAlign.Center,
            )
        }
    }
