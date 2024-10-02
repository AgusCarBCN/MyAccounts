package com.blogspot.agusticar.miscuentasv2.createaccounts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.components.BoardType
import com.blogspot.agusticar.miscuentasv2.components.CurrencySelector
import com.blogspot.agusticar.miscuentasv2.components.ModelButton

import com.blogspot.agusticar.miscuentasv2.components.TextFieldComponent
import com.blogspot.agusticar.miscuentasv2.createaccounts.model.Currency

import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette
import com.blogspot.agusticar.miscuentasv2.main.model.Routes


//Mapa de divisas y simbolos
private val currencySymbols = mapOf(
    "USD" to "$",       // Dólar estadounidense
    "EUR" to "€",       // Euro
    "JPY" to "¥",       // Yen japonés
    "GBP" to "£",       // Libra esterlina
    "AUD" to "A$",      // Dólar australiano
    "CAD" to "C$",      // Dólar canadiense
    "CHF" to "CHF",     // Franco suizo
    "CNY" to "¥",       // Yuan chino
    "SEK" to "kr",      // Corona sueca
    "NZD" to "NZ$",     // Dólar neozelandés
    "MXN" to "$",       // Peso mexicano
    "SGD" to "S$",      // Dólar de Singapur
    "HKD" to "HK$",     // Dólar de Hong Kong
    "NOK" to "kr",      // Corona noruega
    "RUB" to "₽",       // Rublo ruso
    "INR" to "₹",       // Rupia india
    "BRL" to "R$",      // Real brasileño
    "ZAR" to "R",       // Rand sudafricano
    "DKK" to "kr",      // Corona danesa
    "PLN" to "zł",      // Zloty polaco
    "THB" to "฿",       // Baht tailandés
    "AED" to "د.إ",     // Dirham de los Emiratos Árabes Unidos
    "MYR" to "RM",      // Ringgit malayo
    "PHP" to "₱",       // Peso filipino
    "ILS" to "₪",       // Shekel israelí
    "TRY" to "₺",       // Lira turca
    "CLP" to "$",       // Peso chileno
    "COP" to "$",       // Peso colombiano
    "PEN" to "S/.",     // Sol peruano
    "VND" to "₫"        // Dong vietnamita
)
private val currencies = listOf(
    Currency("USD", "US Dollar"),
    Currency("EUR", "Euro"),
    Currency("JPY", "Yen japonés"),
    Currency("GBP", "British Pound"),
    Currency("AUD", "Australian Dollar"),
    Currency("CAD", "Canadian Dollar"),
    Currency("CHF", "Swiss Franc"),
    Currency("CNY", "Yuan chino"),
    Currency("SEK", "Swedish Krona"),
    Currency("NZD", "New Zealand Dollar"),
    Currency("MXN", "Peso mexicano"),
    Currency("SGD", "Singapore Dollar"),
    Currency("HKD", "Hong Kong Dollar"),
    Currency("NOK", "Norwegian Krone"),
    Currency("RUB", "Russian Ruble"),
    Currency("INR", "Indian Rupee"),
    Currency("BRL", "Real brasileño"),
    Currency("ZAR", "South African Rand"),
    Currency("DKK", "Danish Krone"),
    Currency("PLN", "Polish Zloty"),
    Currency("THB", "Thai Baht"),
    Currency("AED", "UAE Dirham"),
    Currency("MYR", "Malaysian Ringgit"),
    Currency("PHP", "Peso filipino"),
    Currency("ILS", "Israeli Shekel"),
    Currency("TRY", "Turkish Lira"),
    Currency("CLP", "Peso chileno"),
    Currency("COP", "Peso colombiano"),
    Currency("PEN", "Sol peruano"),
    Currency("VND", "Vietnamese Dong")
)


@Composable

fun CreateAccountsComponent(navToLogin:()->Unit,navToBack:()->Unit){

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalCustomColorsPalette.current.backgroundPrimary) // Reemplaza con tu color de fondo
    ){
        val (titleCreateAccount, inputData) = createRefs()

        Text(modifier = Modifier
            .padding(top = 30.dp)
            .constrainAs(titleCreateAccount) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(inputData.top)
            },
            text = stringResource(id = R.string.createAccount),
            fontSize = with(LocalDensity.current) { dimensionResource(id = R.dimen.text_title_medium).toSp() },
            fontWeight = FontWeight.Bold, // Estilo de texto en negrita
            textAlign = TextAlign.Center

            )
        Column(modifier = Modifier
            .constrainAs(inputData){
            top.linkTo(titleCreateAccount.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)},
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            TextFieldComponent(
                modifier = Modifier.width(360.dp),
                stringResource(id = R.string.amountName),
                "",
                onTextChange = {  },
                BoardType.TEXT,
                false
            )
            TextFieldComponent(
                modifier = Modifier.width(360.dp),
                stringResource(id = R.string.enteramount),
                "",
                onTextChange = {  },
                BoardType.TEXT,
                false
            )
            ModelButton(text = stringResource(id = R.string.addAccount),
                R.dimen.text_title_medium,
                modifier = Modifier.width(360.dp),
                true,
                onClickButton = {

                }
            )
           CurrencySelector(currencies)
            Text(modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.createaccountmsg),
                fontSize = with(LocalDensity.current) { dimensionResource(id = R.dimen.text_body_medium).toSp() },
                fontWeight = FontWeight.Bold, // Estilo de texto en negrita
                textAlign = TextAlign.Center)


            ModelButton(text = stringResource(id = R.string.loginButton),
                R.dimen.text_title_medium,
                modifier = Modifier.width(360.dp),
                true,
                onClickButton = {
                navToLogin()
                //navigationController.navigate(Routes.Home.route)
                }
            )

            ModelButton(text = stringResource(id = R.string.backButton),
                R.dimen.text_title_medium,
                modifier = Modifier.width(360.dp),
                true,
                onClickButton = {
                navToBack()
                // navigationController.navigate(Routes.CreateProfile.route)
                }
            )

        }
    }

}
