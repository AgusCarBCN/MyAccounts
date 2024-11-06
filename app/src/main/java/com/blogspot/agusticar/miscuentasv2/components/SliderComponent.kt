package com.blogspot.agusticar.miscuentasv2.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
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
import com.blogspot.agusticar.miscuentasv2.createaccounts.view.AccountsViewModel
import com.blogspot.agusticar.miscuentasv2.createaccounts.view.CategoriesViewModel
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Category
import com.blogspot.agusticar.miscuentasv2.newamount.view.EntriesViewModel
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette
import com.blogspot.agusticar.miscuentasv2.utils.Utils
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


@Composable
fun CategoryBudgetItemControl(
    category: Category,
    categoriesViewModel: CategoriesViewModel,
    entriesViewModel: EntriesViewModel,
    accountsViewModel: AccountsViewModel
) {
    val currencyCode by accountsViewModel.currencyCodeSelected.observeAsState("USD")

    // Estado para almacenar el total de gastos por categoría
    var expensesByCategory by remember { mutableStateOf(0.0) }

    // Cargar el total de gastos de la categoría cuando cambie el composable o la categoría
    LaunchedEffect(category.id) {
        expensesByCategory = entriesViewModel.sumOfExpensesByCategory(category.id) ?: 0.0
    }

    // Variables de estado para el límite de gasto y porcentaje
    var spendingLimit by remember { mutableStateOf(200.0) }  // Límite de gasto inicial
    val maxLimit = 500f  // Límite máximo ajustable
    val spendingPercentage = (expensesByCategory / spendingLimit).coerceIn(0.0, 1.0) // Porcentaje de gasto

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
            modifier = Modifier
                .padding(start = 15.dp, end = 20.dp, top = 5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(36.dp),
                painter = painterResource(category.iconResource),
                contentDescription = "icon",
                tint = LocalCustomColorsPalette.current.textColor
            )
            Spacer(modifier = Modifier.height(20.dp)) // Espacio entre el texto y el botón
            Text(
                text = stringResource(category.nameResource),
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
            text = "Gasto Actual: ${Utils.numberFormat(expensesByCategory, currencyCode)} / $${spendingLimit.toInt()}",
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
                    .fillMaxWidth(spendingPercentage.toFloat())
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
            value = spendingLimit.toFloat(),
            onValueChange = { spendingLimit = it.toDouble() },
            valueRange = 50f..maxLimit,  // Rango ajustable
            steps = (maxLimit / 50).toInt() - 1, // Incremento en pasos de 50
            modifier = Modifier.fillMaxWidth()
        )
    }
}
