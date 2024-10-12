package com.blogspot.agusticar.miscuentasv2.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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

@Composable

fun UserImage(uri: Uri)
{
    Card(
        modifier = Modifier
            .size(80.dp)
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
fun ElevatedCardExample(amount:String) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardColors(
            containerColor = LocalCustomColorsPalette.current.drawerColor,
            contentColor = LocalCustomColorsPalette.current.incomeColor,
            disabledContainerColor = LocalCustomColorsPalette.current.drawerColor,
            disabledContentColor = LocalCustomColorsPalette.current.drawerColor
        ),
        modifier = Modifier
            .size(width = 220.dp, height = 100.dp)
    ) {
        Text(
            text = amount,
            modifier = Modifier
                .padding(top=12.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = LocalCustomColorsPalette.current.incomeColor
        )
        Spacer(modifier = Modifier.height(8.dp)) // Espacio entre el texto y el botón
        Text(
            text = "Ver ingresos",
            modifier = Modifier
                .padding(5.dp).fillMaxWidth(),
            textAlign = TextAlign.Center,
            color= LocalCustomColorsPalette.current.textHeadColor
        )
    }
}