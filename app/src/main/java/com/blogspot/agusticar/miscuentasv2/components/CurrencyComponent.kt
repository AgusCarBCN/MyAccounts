package com.blogspot.agusticar.miscuentasv2.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material3.Text
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.createaccounts.model.Currency
import com.blogspot.agusticar.miscuentasv2.createaccounts.view.CreateAccountsViewModel
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette


@Composable
fun CurrencySelector(createAccountsViewModel: CreateAccountsViewModel) {
    // Obtener el estado actual de la moneda seleccionada
    val currencyCode by createAccountsViewModel.currencyCode.observeAsState("USD")

    // Obtener la lista de divisas desde el ViewModel
    val currencies = createAccountsViewModel.getListOfCurrencyCode()

    // Inicializar un estado para controlar la visibilidad del selector
    var isExpanded by remember { mutableStateOf(false) }

    // Contenedor principal
    Column(
        modifier = Modifier
            .width(360.dp)
            .background(LocalCustomColorsPalette.current.backgroundPrimary)
            .padding(5.dp)
    ) {
        // Título
        Text(
            text = stringResource(id = R.string.selectcurrency),
            fontSize = 18.sp,
            color = LocalCustomColorsPalette.current.textColor,  // Color del texto
            modifier = Modifier
                .padding(top = 10.dp, bottom = 10.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        // Botón para expandir/colapsar la lista de divisas
        ModelButton(text = "Select Currency",
            R.dimen.text_title_medium,
            modifier = Modifier.fillMaxWidth(),
            onClickButton = { isExpanded = !isExpanded })


        // Mostrar la moneda seleccionada actualmente
        Text(
            text = "${stringResource(id = R.string.selectedcurrency)} $currencyCode",
            fontSize = 18.sp,
            color = LocalCustomColorsPalette.current.textColor,  // Color del texto
            modifier = Modifier
                .padding(top = 10.dp, bottom = 10.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        // Mostrar la lista de divisas si isExpanded es verdadero
        if (isExpanded) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(LocalCustomColorsPalette.current.backgroundPrimary)
            ) {
                items(currencies) { currency ->
                    // Elemento de la lista
                    CurrencyListItem(currency) {
                        // Acción al seleccionar la moneda
                        createAccountsViewModel.onCurrencySelectedChange(currency.currencyCode)
                        // Cambia el estado a colapsado
                        isExpanded = false
                    }
                }
            }
        }
    }
}

@Composable
fun CurrencyListItem(currency: Currency, onCurrencySelected: () -> Unit) {
    // Elemento de lista para mostrar cada moneda
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onCurrencySelected) // Acción al hacer clic
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = currency.flag),
            contentDescription = "${currency.currencyCode} flag",
            modifier = Modifier.size(36.dp)
        )
        Spacer(modifier = Modifier.width(15.dp)) // Espaciador entre la imagen y el texto
        Text(
            text = currency.currencyDescription,
            fontSize = 16.sp,
            color = LocalCustomColorsPalette.current.textColor
        )
    }
}

