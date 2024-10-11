package com.blogspot.agusticar.miscuentasv2.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material3.Text
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette

@Composable
fun AccountSelector() {

    // Inicializamos el estado del VerticalPager con el número de páginas igual al tamaño de la lista de monedas.
    val pagerState = rememberPagerState(pageCount = { 10 })

    Column(
        modifier = Modifier
            .width(360.dp)
            .background(LocalCustomColorsPalette.current.backgroundPrimary)
            .padding(5.dp)
    ) {
        Text(
            text = "Select an account",
            fontSize = 20.sp,
            color = LocalCustomColorsPalette.current.textColor,  // Color del texto
            modifier = Modifier
                .padding(top = 10.dp, bottom = 10.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        VerticalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .background(LocalCustomColorsPalette.current.backgroundPrimary)
                .height(70.dp)
        ) { page ->

            // Usamos Box para centrar el contenido vertical y horizontalmente
            Box(
                modifier = Modifier
                    .width(360.dp)
                    .height(60.dp)
                    .background(LocalCustomColorsPalette.current.backgroundPrimary),
                contentAlignment = Alignment.Center // Centramos el contenido en ambas direcciones
            ) {

                    Text(
                        text = "BBVA  $3400 euros", // Descripción de la moneda
                        fontSize = 18.sp,
                        color = LocalCustomColorsPalette.current.textColor, // Color del texto
                        textAlign = TextAlign.Left // Alinear el texto a la izquierda
                    )
                }
            }
        }


    }
