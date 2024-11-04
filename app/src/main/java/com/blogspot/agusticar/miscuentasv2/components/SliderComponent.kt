package com.blogspot.agusticar.miscuentasv2.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.main.model.Category
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette

@Preview
@Composable
fun CategoryBudgetItemControl() {
    // Variables de estado para el gasto actual, límite y porcentaje
    var currentSpending by remember { mutableStateOf(120f) }  // Gasto actual en la categoría
    var spendingLimit by remember { mutableStateOf(200f) }  // Límite de gasto inicial
    val maxLimit = 500f  // Límite máximo ajustable
    val spendingPercentage = (currentSpending / spendingLimit).coerceIn(0f, 1f) // Porcentaje de gasto

    // Color de la barra de progreso según el porcentaje
    val progressColor = when {
        spendingPercentage < 0.8f -> LocalCustomColorsPalette.current.incomeColor
        spendingPercentage < 1f -> LocalCustomColorsPalette.current.expenseColor
        else -> Color.Red
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(start = 15.dp, end = 20.dp, top = 5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(36.dp),
                painter = painterResource(id = R.drawable.ic_category_coffe),
                contentDescription = "icon",
                tint = LocalCustomColorsPalette.current.textColor
            )
            Spacer(modifier = Modifier.height(20.dp)) // Espacio entre el texto y el botón
            Text(
                text = "Coffe",
                modifier = Modifier
                    .padding(10.dp)
                    .weight(0.4f),
                color = LocalCustomColorsPalette.current.textColor,
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

            // Barra de progreso de gasto actual
            Text(
                text = "Gasto Actual: $${currentSpending.toInt()} / $${spendingLimit.toInt()}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Barra de progreso con color dinámico
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .background(Color.LightGray)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(spendingPercentage)
                        .fillMaxHeight()
                        .background(progressColor)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Slider para ajustar el límite de gasto
            Text(
                text = "Límite de Gasto: $${spendingLimit.toInt()}",
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Slider(
                value = spendingLimit,
                onValueChange = { spendingLimit = it },
                valueRange = 50f..maxLimit,  // Rango ajustable
                steps = (maxLimit / 50).toInt() - 1, // Incremento en pasos de 50
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

