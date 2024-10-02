package com.blogspot.agusticar.miscuentasv2.components

import android.graphics.Color
import android.icu.lang.UCharacter.VerticalOrientation
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material3.Text
import com.blogspot.agusticar.miscuentasv2.createaccounts.model.Currency
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette


@Composable
fun CurrencySelector(currencies: List<Currency>) {

    val pagerState = rememberPagerState(pageCount = {
        currencies.size  // Cada página corresponde a un item de la lista
    })

    Column(
        modifier = Modifier.width(360.dp).background(LocalCustomColorsPalette.current.topBarContent)
    ) {
        VerticalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp).
            background(LocalCustomColorsPalette.current.topBarContent).
            height(70.dp)
       ) { page ->
            // Contenido de cada página: aquí mostramos el nombre de la moneda con color negro
            Text(
                text = currencies[page].currencyDescription,  // Accedemos al nombre de la moneda
                fontSize = 24.sp,
                color = LocalCustomColorsPalette.current.textColor,  // Cambiamos el color del texto a negro
                modifier = Modifier
                    .width(360.dp)
                    .padding(top=5.dp)
                    .height(60.dp)
                    .background(LocalCustomColorsPalette.current.textButtonColorPressed),
                textAlign = TextAlign.Center
            )
        }

        // Muestra el nombre de la moneda seleccionada actualmente
        Text(
            text = "Selected: ${currencies[pagerState.currentPage].currencyCode}",
            fontSize = 18.sp,
            color = LocalCustomColorsPalette.current.textColor,  // Color negro para este texto también
            modifier = Modifier
                .padding(top = 10.dp, bottom = 10.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}