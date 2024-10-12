package com.blogspot.agusticar.miscuentasv2.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.createaccounts.view.CreateAccountsViewModel
import com.blogspot.agusticar.miscuentasv2.main.model.currencyLocales
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.abs


@Composable

fun UserImage(uri: Uri,size:Int)
{
    Card(
        modifier = Modifier
            .size(size.dp)
            .padding(10.dp),
        shape = CircleShape
    )

    {
        Image(
            painter =if(uri== Uri.EMPTY) painterResource(id = R.drawable.contabilidad)
            else rememberAsyncImagePainter(uri), // Carga la imagen desde el Uri ,
            contentDescription = "Profile Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize() // La imagen ocupa todo el Card
        )
    }
}

@Composable
fun HeadCard(modifier:Modifier,amount:Double,viewModel: CreateAccountsViewModel) {

    val currencyCode by viewModel.currencyCode.observeAsState("")
    val locale = currencyLocales[currencyCode] ?: Locale.GERMAN
    // Formatear la cantidad en la moneda especificada
    val numberFormat = NumberFormat.getCurrencyInstance(locale)

    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardColors(
            containerColor = LocalCustomColorsPalette.current.drawerColor,
            contentColor = if(amount>=0.0)LocalCustomColorsPalette.current.incomeColor else LocalCustomColorsPalette.current.expenseColor,
            disabledContainerColor = LocalCustomColorsPalette.current.drawerColor,
            disabledContentColor = LocalCustomColorsPalette.current.incomeColor

        ),
        modifier = Modifier
            .size(width = 180.dp, height = 100.dp)
    ) {
        Text(
            text =numberFormat.format(abs(amount)),
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp)) // Espacio entre el texto y el botón
        Text(
            text = "Ver ingresos",
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            color= LocalCustomColorsPalette.current.textHeadColor
        )
    }
}
@Composable
fun AccountCard(amount:Double,name:String,viewModel: CreateAccountsViewModel){

    val currencyCode by viewModel.currencyCode.observeAsState("")
    val locale = currencyLocales[currencyCode] ?: Locale.GERMAN
    // Formatear la cantidad en la moneda especificada
    val numberFormat = NumberFormat.getCurrencyInstance(locale)

    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardColors(
            containerColor = LocalCustomColorsPalette.current.drawerColor,
            contentColor = if(amount>=0.0)LocalCustomColorsPalette.current.incomeColor else LocalCustomColorsPalette.current.expenseColor,
            disabledContainerColor = LocalCustomColorsPalette.current.drawerColor,
            disabledContentColor = LocalCustomColorsPalette.current.incomeColor

        ),
        modifier = Modifier
            .size(width = 360.dp, height = 120.dp)
    ) {
        Row(modifier = Modifier.padding(5.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = name,
                modifier = Modifier
                    .padding(10.dp)
                    .weight(0.6f),
                textAlign = TextAlign.Start,
                fontSize = 24.sp,
                color= LocalCustomColorsPalette.current.textHeadColor
            )
            Spacer(modifier = Modifier.height(8.dp)) // Espacio entre el texto y el botón
            Text(
                text =numberFormat.format(abs(amount)),
                modifier = Modifier
                    .padding(10.dp)
                    .weight(0.4f),
                textAlign = TextAlign.End,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(8.dp)) // Espacio entre el texto y el botón
        Text(
            text = "Ver ingresos y gastos",
            modifier = Modifier
                .padding(start=10.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Start,
            fontSize = 16.sp,
            color= LocalCustomColorsPalette.current.textHeadColor
        )
    }

}