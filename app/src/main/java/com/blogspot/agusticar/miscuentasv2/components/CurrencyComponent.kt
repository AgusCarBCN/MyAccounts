package com.blogspot.agusticar.miscuentasv2.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material3.Text

import com.blogspot.agusticar.miscuentasv2.createaccounts.model.Currency
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette



@Composable
fun CurrencySelector(currencies: List<Currency>) {
    // Inicializamos el estado del VerticalPager con el número de páginas igual al tamaño de la lista de monedas.
    val pagerState = rememberPagerState(pageCount = { currencies.size })

    Column(
        modifier = Modifier
            .width(360.dp)
            .background(LocalCustomColorsPalette.current.backgroundPrimary)
            .padding(5.dp)
    ) {
        VerticalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .background(LocalCustomColorsPalette.current.topBarContent)
                .height(70.dp)
        ) { page ->
            // Usamos Box para centrar el contenido vertical y horizontalmente
            Box(
                modifier = Modifier
                    .width(360.dp)
                    .height(60.dp)
                    .background(LocalCustomColorsPalette.current.focusedContainerTextField),
                contentAlignment = Alignment.Center // Centramos el contenido en ambas direcciones
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically, // Alinear verticalmente el contenido
                    horizontalArrangement = Arrangement.Center // Alinear horizontalmente el contenido
                ) {
                    // Asegúrate de que currency.iconResId sea el recurso drawable correcto
                    Image(
                        painter = painterResource(id =currencies[page].flag), // Usa el recurso de imagen
                        contentDescription = "${currencies[page].currencyCode} Icon", // Descripción para accesibilidad
                        modifier = Modifier.size(48.dp) // Ajusta el tamaño de la imagen
                    )
                    Spacer(modifier = Modifier.width(15.dp)) // Espaciador entre la imagen y el texto
                    Text(
                        text = currencies[page].currencyDescription, // Descripción de la moneda
                        fontSize = 24.sp,
                        color = LocalCustomColorsPalette.current.textColor, // Color del texto
                        textAlign = TextAlign.Left // Alinear el texto a la izquierda
                    )
                }
            }
        }

        // Muestra el nombre de la moneda seleccionada actualmente
        Text(
            text = "Selected currency: ${currencies[pagerState.currentPage].currencyCode}",
            fontSize = 18.sp,
            color = LocalCustomColorsPalette.current.textColor,  // Color del texto
            modifier = Modifier
                .padding(top = 10.dp, bottom = 10.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}


