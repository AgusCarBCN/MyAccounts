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
    "VND" to "₫"  ,     // Dong vietnamita
    "ARS" to "$"   ,     //Peso argentino
    "KRW" to "₩"   ,  //South Korean won
    "HNL"   to  "L"   //Honduras
)
private val currencies = listOf(
    Currency("USD", "US Dollar",R.drawable.us),
    Currency("EUR", "Euro",R.drawable.eu),
    Currency("JPY", "Yen japonés",R.drawable.jp),
    Currency("GBP", "British Pound",R.drawable.gb),
    Currency("AUD", "Australian Dollar",R.drawable.au),
    Currency("CAD", "Canadian Dollar",R.drawable.ca),
    Currency("CHF", "Swiss Franc",R.drawable.ch),
    Currency("CNY", "Yuan chino",R.drawable.cn),
    Currency("SEK", "Swedish Krona",R.drawable.se),
    Currency("NZD", "New Zealand Dollar",R.drawable.nz),
    Currency("MXN", "Peso mexicano",R.drawable.mx),
    Currency("SGD", "Singapore Dollar",R.drawable.sg),
    Currency("HKD", "Hong Kong Dollar",R.drawable.hk),
    Currency("NOK", "Norwegian Krone",R.drawable.no),
    Currency("RUB", "Russian Ruble",R.drawable.ru),
    Currency("INR", "Indian Rupee",R.drawable.`in`),
    Currency("BRL", "Real brasileño",R.drawable.br),
    Currency("ZAR", "South African Rand",R.drawable.za),
    Currency("DKK", "Danish Krone",R.drawable.dk),
    Currency("PLN", "Polish Zloty",R.drawable.pl),
    Currency("THB", "Thai Baht",R.drawable.th),
    Currency("AED", "UAE Dirham",R.drawable.sa),
    Currency("MYR", "Malaysian Ringgit",R.drawable.my),
    Currency("PHP", "Peso filipino",R.drawable.ph),
    Currency("ILS", "Israeli Shekel",R.drawable.il),
    Currency("TRY", "Turkish Lira",R.drawable.tr),
    Currency("CLP", "Peso chileno",R.drawable.cl),
    Currency("COP", "Peso colombiano",R.drawable.co),
    Currency("PEN", "Sol peruano",R.drawable.pe),
    Currency("VND", "Vietnamese Dong",R.drawable.vn),
    Currency("ARS", "Peso Argentino",R.drawable.ar),
    Currency("KRW", "South Korean Won",R.drawable.kr),
    Currency("HNL","Lempira de Honduras",R.drawable.hn)
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
            Text(modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.createaccountmsg),
            fontSize = with(LocalDensity.current) { dimensionResource(id = R.dimen.text_body_medium).toSp() },
            fontWeight = FontWeight.Bold, // Estilo de texto en negrita
            textAlign = TextAlign.Center)

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
