package com.blogspot.agusticar.miscuentasv2.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blogspot.agusticar.miscuentasv2.main.model.Category
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette

@Composable

fun CategoryEntries(category: Category, modifier:Modifier, onClickItem:() -> Unit) {
    val initColor=
        if(category.isIncome) LocalCustomColorsPalette.current.iconIncomeInit
        else LocalCustomColorsPalette.current.iconExpenseInit


    val targetColor= if(category.isIncome) LocalCustomColorsPalette.current.iconIncomeTarget


    else LocalCustomColorsPalette.current.iconExpenseTarget

        // Asegúrate de que la columna ocupe todo el espacio de la tarjeta
        Column(
            modifier
                .size(120.dp)
                .clip(shape = CircleShape)
                .clickable { onClickItem() }
                , // Ocupa todo el espacio de la Card
            horizontalAlignment = Alignment.CenterHorizontally, // Alinea todo al centro horizontalmente
            verticalArrangement = Arrangement.Center // Alinea todo al centro verticalmente
        ) {

            IconAnimated(category.iconResource, 60,initColor,targetColor )
            Text(
                text = stringResource(id = category.name), // Corrige 'Dividens' a 'Dividends'
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = LocalCustomColorsPalette.current.textColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

    }

