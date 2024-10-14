package com.blogspot.agusticar.miscuentasv2.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.createaccounts.view.AccountsViewModel
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette


@Composable
fun AccountSelector(title:String,accountViewModel:AccountsViewModel) {

    accountViewModel.getAllAccounts()
    // Observa el estado de la lista de cuentas
    val accounts by accountViewModel.listOfAccounts.observeAsState(listOf())   // Observa el estado de la lista de cuentas
    val accountSelected by accountViewModel.accountSelected.observeAsState()
    // Inicializamos el estado del VerticalPager con el número de páginas igual al tamaño de la lista de monedas.

    val pagerState = rememberPagerState(pageCount = { accounts.size })
    var previousPage by remember { mutableStateOf(0) }
    val toDown = R.drawable.arrow_down
    val toUp = R.drawable.arrow_up
    var isDraggingUp by remember { mutableStateOf(true) } // Inicializamos la flecha apuntando hacia arriba

    Column(
        modifier = Modifier
            .width(320.dp)
            .background(LocalCustomColorsPalette.current.backgroundPrimary)
            .padding(5.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Row(
            modifier = Modifier
                .padding(5.dp)
                .background(LocalCustomColorsPalette.current.backgroundPrimary),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Muestra la flecha basada en la dirección del desplazamiento
            Icon(
                painter = painterResource(
                    id = if (isDraggingUp) toUp else toDown
                ),
                contentDescription = "",
                tint = LocalCustomColorsPalette.current.textColor, // Color del icono
                modifier = Modifier
                    .width(36.dp)
                    .padding(end = 8.dp) // Espacio entre el icono y el texto
            )

            // Coloca el texto al lado del icono
            Text(
                text = title,
                fontSize = 20.sp,
                color = LocalCustomColorsPalette.current.textColor,  // Color del texto
                modifier = Modifier
                    .padding(vertical = 10.dp),
                textAlign = TextAlign.Start // Alinea el texto a la izquierda, cerca del ícono
            )
        }
        Card(
            modifier = Modifier.width(300.dp),
            shape = RoundedCornerShape(16.dp)
        ) {

            VerticalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(LocalCustomColorsPalette.current.backgroundSecondary)
                    .height(60.dp),

                userScrollEnabled = true // Asegúrate de que el scroll esté habilitado
            ) { page ->
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    //Se actualiza la cuenta seleccionada en viewModel
                    accountViewModel.onAccountSelected(accounts[page])
                    val balanceFormat=accountViewModel.numberFormat(accounts[page].balance)
                    // Texto de la moneda
                    Text(
                        text = "${accounts[page].name}          $balanceFormat", // Descripción de la moneda
                        fontSize = 18.sp,
                        color = LocalCustomColorsPalette.current.textColor, // Color del texto
                        textAlign = TextAlign.Center // Alinear el texto a la izquierda
                    )
                }
            }

            // Detectar el desplazamiento y actualizar el estado de la flecha
            LaunchedEffect(pagerState) {
                snapshotFlow { pagerState.currentPage }.collect { page ->

                    if (page == 0) {
                        isDraggingUp = true
                    } else if (page == 9) {
                        isDraggingUp = false
                    } else if (previousPage <= page) {
                        isDraggingUp = true
                    } else isDraggingUp = false

                    previousPage = page



                }
            }
        }
    }
}

