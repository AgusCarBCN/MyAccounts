package com.blogspot.agusticar.miscuentasv2.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette

@Composable

fun CategoryEntries(title:String,iconResource:Int,modifier:Modifier,onClickItem:() -> Unit) {

        // Asegúrate de que la columna ocupe todo el espacio de la tarjeta
        Column(
            modifier
                .size(120.dp)
                .clip(shape = CircleShape)
                .clickable{onClickItem()}
                , // Ocupa todo el espacio de la Card
            horizontalAlignment = Alignment.CenterHorizontally, // Alinea todo al centro horizontalmente
            verticalArrangement = Arrangement.Center // Alinea todo al centro verticalmente
        ) {

            IconAnimated(iconResource, 60 )
            Text(
                text = title, // Corrige 'Dividens' a 'Dividends'
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = LocalCustomColorsPalette.current.textColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

    }

