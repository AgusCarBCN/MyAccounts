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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette



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
                .fillMaxSize()
        )
    }
}

@Composable
fun HeadCard(modifier:Modifier,amount:String,isIncome:Boolean,onClickCard:() -> Unit) {

    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardColors(
            containerColor = LocalCustomColorsPalette.current.drawerColor,
            contentColor = if(isIncome)LocalCustomColorsPalette.current.incomeColor else LocalCustomColorsPalette.current.expenseColor,
            disabledContainerColor = LocalCustomColorsPalette.current.drawerColor,
            disabledContentColor = LocalCustomColorsPalette.current.incomeColor

        ),
        modifier = Modifier
            .size(width = 180.dp, height = 120.dp)
    ) {
        Text(
            text =amount,
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(20.dp)) // Espacio entre el texto y el botón

        TextButton(
            onClick = {
                onClickCard()
            },
            content = {
                Text(modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth(),
                    text = stringResource(id =if(isIncome) R.string.seeincome else  R.string.seeexpense),
                    fontSize = with(LocalDensity.current) { dimensionResource(id = R.dimen.text_body_large).toSp() },
                    textAlign = TextAlign.Center,
                    color = LocalCustomColorsPalette.current.textHeadColor
                )
            }
        )

    }
}
@Composable
fun AccountCard(amount:String,name:String,isNegative:Boolean,onClickCard: () -> Unit){

    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardColors(
            containerColor = LocalCustomColorsPalette.current.drawerColor,
            contentColor = if(isNegative )LocalCustomColorsPalette.current.incomeColor else LocalCustomColorsPalette.current.expenseColor,
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
                fontSize = 22.sp,
                color= LocalCustomColorsPalette.current.textHeadColor
            )
            Spacer(modifier = Modifier.height(12.dp)) // Espacio entre el texto y el botón
            Text(
                text =amount,
                modifier = Modifier
                    .padding(10.dp)
                    .weight(0.4f),
                textAlign = TextAlign.End,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(8.dp)) // Espacio entre el texto y el botón

        TextButton(
            onClick = {
            onClickCard()
            },
            content = {
                Text(modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth(),
                    text = stringResource(id =R.string.seeall),
                    fontSize = with(LocalDensity.current) { dimensionResource(id = R.dimen.text_body_large).toSp() },
                    textAlign = TextAlign.Start,
                    color = LocalCustomColorsPalette.current.textHeadColor
                )
            }
        )

    }

}