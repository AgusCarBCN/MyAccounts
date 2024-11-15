package com.blogspot.agusticar.miscuentasv2.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.SnackBarController
import com.blogspot.agusticar.miscuentasv2.SnackBarEvent
import com.blogspot.agusticar.miscuentasv2.createaccounts.view.AccountsViewModel
import com.blogspot.agusticar.miscuentasv2.createaccounts.view.CategoriesViewModel
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Account
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Category
import com.blogspot.agusticar.miscuentasv2.newamount.view.EntriesViewModel
import com.blogspot.agusticar.miscuentasv2.search.SearchViewModel
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette
import com.blogspot.agusticar.miscuentasv2.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.abs


@Composable
fun CategoryBudgetItemControl(
    category: Category,
    categoriesViewModel: CategoriesViewModel,
    accountsViewModel: AccountsViewModel
) {

    val currencyCode by accountsViewModel.currencyCodeSelected.observeAsState("USD")

    val limitExpenseText=stringResource(id = R.string.limitexpense)
    val currentExpense=stringResource(id = R.string.currentexpense)
    val scope = rememberCoroutineScope()

    // Estado para almacenar el total de gastos por categoría
    var expensesByCategory by remember { mutableDoubleStateOf(0.0) }

    // Cargar el total de gastos de la categoría cuando cambie el composable o la categoría
    LaunchedEffect(category.id) {
        expensesByCategory = categoriesViewModel.sumOfExpensesByCategory(category.id,
            category.fromDate,
            category.toDate) ?: 0.0
    }
    val messageUpdateSpendingLimit = "${stringResource(id = R.string.espendingupdate)} ${stringResource(id = category.nameResource)}"

    // Variables de estado para el límite de gasto y porcentaje
    var spendingLimit by remember { mutableDoubleStateOf(category.spendingLimit) }  // Límite de gasto inicial
    val maxLimit = category.limitMax
    val spendingPercentage = (abs(expensesByCategory.toFloat()) / spendingLimit.toFloat()).coerceIn(0.0f, 1.0f) // Porcentaje de gasto

    // Color de la barra de progreso según el porcentaje
    val progressColor = when {
        spendingPercentage < 0.5f -> LocalCustomColorsPalette.current.progressBar50
        spendingPercentage < 0.8f -> LocalCustomColorsPalette.current.progressBar80
        else -> LocalCustomColorsPalette.current.progressBar100
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .padding(start = 15.dp, end = 20.dp, top = 5.dp, bottom = 15.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center, // Centrar los elementos en el Row
            verticalAlignment = Alignment.CenterVertically // Alinear verticalmente al centro
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(category.iconResource),
                contentDescription = "icon",
                tint = LocalCustomColorsPalette.current.expenseColor
            )
            Text(
                text = stringResource(category.nameResource),
                modifier = Modifier
                    .padding(start = 5.dp) // Ajusta el espacio entre el icono y el texto
                    , // Hace que el texto ocupe espacio disponible
                color = LocalCustomColorsPalette.current.textHeadColor,
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Row(
            modifier = Modifier
                .padding(start = 15.dp, end = 20.dp, top = 5.dp, bottom = 15.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center, // Centrar los elementos en el Row
            verticalAlignment = Alignment.CenterVertically // Alinear verticalmente al centro
        ) {
            Text(
                text = "${stringResource(id = R.string.fromdate)}: ${category.fromDate}",
                modifier = Modifier
                    .padding(start = 5.dp) // Ajusta el espacio entre el icono y el texto
                , // Hace que el texto ocupe espacio disponible
                color = LocalCustomColorsPalette.current.textColor,
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${stringResource(id = R.string.todate)}: ${category.toDate}",
                modifier = Modifier
                    .padding(start = 5.dp) // Ajusta el espacio entre el icono y el texto
                , // Hace que el texto ocupe espacio disponible
                color = LocalCustomColorsPalette.current.textColor,
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }




        // Barra de progreso de gasto actual
        Text(
            text = "$currentExpense: ${Utils.numberFormat(expensesByCategory, currencyCode)} / ${Utils.numberFormat(spendingLimit,currencyCode)}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = LocalCustomColorsPalette.current.textColor,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Barra de progreso con color dinámico
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .background(LocalCustomColorsPalette.current.drawerColor)
                .clip(RoundedCornerShape(5.dp))
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
            text = "$limitExpenseText: ${Utils.numberFormat(spendingLimit,currencyCode)}",
            fontSize = 16.sp,
            color = LocalCustomColorsPalette.current.textColor,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Slider(
            value = spendingLimit.toFloat(),
            onValueChange = { spendingLimit = it.toDouble()

                            },
            valueRange = 0f..maxLimit.toFloat(),  // Rango ajustable
            steps = (maxLimit / 1).toInt() - 1, // Incremento en pasos de 50
            modifier = Modifier.fillMaxWidth(),
            colors = SliderColors(
                thumbColor = LocalCustomColorsPalette.current.thumbCheckedColor,
                activeTrackColor = LocalCustomColorsPalette.current.trackCheckedColor,
                activeTickColor = LocalCustomColorsPalette.current.thumbCheckedColor,
                inactiveTrackColor = LocalCustomColorsPalette.current.trackDefaultColor,
                inactiveTickColor = LocalCustomColorsPalette.current.trackDefaultColor,
                disabledThumbColor = LocalCustomColorsPalette.current.trackDefaultColor,
                disabledActiveTrackColor = LocalCustomColorsPalette.current.trackDefaultColor,
                disabledActiveTickColor = LocalCustomColorsPalette.current.trackDefaultColor,
                disabledInactiveTrackColor = LocalCustomColorsPalette.current.trackDefaultColor,
                disabledInactiveTickColor = LocalCustomColorsPalette.current.trackDefaultColor,

            )
        )
        ModelButton(text = stringResource(id = R.string.confirmButton),
            R.dimen.text_title_small,
            modifier = Modifier.width(320.dp),
            true,
            onClickButton = {

                    categoriesViewModel.upDateSpendingLimitCategory(category.id, spendingLimit)
                    scope.launch(Dispatchers.Main) {
                        SnackBarController.sendEvent(
                            event = SnackBarEvent(
                                messageUpdateSpendingLimit
                            )
                        )
                    }
                }

        )
    }
}
@Composable
fun AccountBudgetItemControl(
    account:Account,
    accountsViewModel: AccountsViewModel
) {

    val currencyCode by accountsViewModel.currencyCodeSelected.observeAsState("USD")

    val limitExpenseText=stringResource(id = R.string.limitexpense)
    val currentExpense=stringResource(id = R.string.currentexpense)
    val scope = rememberCoroutineScope()

    // Estado para almacenar el total de gastos por categoría
    var expensesByAccount by remember { mutableDoubleStateOf(0.0) }

    // Cargar el total de gastos de la categoría cuando cambie el composable o la categoría
    LaunchedEffect(account.id) {
        expensesByAccount = accountsViewModel.sumOfExpensesByAccount(account.id,
            account.fromDate,
            account.toDate) ?: 0.0
    }
    val messageUpdateSpendingLimit = "${stringResource(id = R.string.espendingupdate)} ${account.name}"

    // Variables de estado para el límite de gasto y porcentaje
    var spendingLimit by remember { mutableDoubleStateOf(account.spendingLimit) }  // Límite de gasto inicial
    val maxLimit = account.limitMax
    val spendingPercentage = (abs(expensesByAccount.toFloat()) / spendingLimit.toFloat()).coerceIn(0.0f, 1.0f) // Porcentaje de gasto

    // Color de la barra de progreso según el porcentaje
    val progressColor = when {
        spendingPercentage < 0.5f -> LocalCustomColorsPalette.current.progressBar50
        spendingPercentage < 0.8f -> LocalCustomColorsPalette.current.progressBar80
        else -> LocalCustomColorsPalette.current.progressBar100
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

            Text(
                text = account.name,
                modifier = Modifier
                    .padding(start = 5.dp) // Ajusta el espacio entre el icono y el texto
                , // Hace que el texto ocupe espacio disponible
                color = LocalCustomColorsPalette.current.textHeadColor,
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

        Row(
            modifier = Modifier
                .padding(start = 15.dp, end = 20.dp, top = 5.dp, bottom = 15.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center, // Centrar los elementos en el Row
            verticalAlignment = Alignment.CenterVertically // Alinear verticalmente al centro
        ) {
            Text(
                text = "${stringResource(id = R.string.fromdate)}: ${account.fromDate}",
                modifier = Modifier
                    .padding(start = 5.dp) // Ajusta el espacio entre el icono y el texto
                , // Hace que el texto ocupe espacio disponible
                color = LocalCustomColorsPalette.current.textColor,
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${stringResource(id = R.string.todate)}: ${account.toDate}",
                modifier = Modifier
                    .padding(start = 5.dp) // Ajusta el espacio entre el icono y el texto
                , // Hace que el texto ocupe espacio disponible
                color = LocalCustomColorsPalette.current.textColor,
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }




        // Barra de progreso de gasto actual
        Text(
            text = "$currentExpense: ${Utils.numberFormat(expensesByAccount, currencyCode)} / ${Utils.numberFormat(spendingLimit,currencyCode)}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = LocalCustomColorsPalette.current.textColor,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Barra de progreso con color dinámico
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .background(LocalCustomColorsPalette.current.drawerColor)
                .clip(RoundedCornerShape(5.dp))
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
            text = "$limitExpenseText: ${Utils.numberFormat(spendingLimit,currencyCode)}",
            fontSize = 16.sp,
            color = LocalCustomColorsPalette.current.textColor,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Slider(
            value = spendingLimit.toFloat(),
            onValueChange = { spendingLimit = it.toDouble()

            },
            valueRange = 0f..maxLimit.toFloat(),  // Rango ajustable
            steps = (maxLimit / 1).toInt() - 1, // Incremento en pasos de 50
            modifier = Modifier.fillMaxWidth(),
            colors = SliderColors(
                thumbColor = LocalCustomColorsPalette.current.thumbCheckedColor,
                activeTrackColor = LocalCustomColorsPalette.current.trackCheckedColor,
                activeTickColor = LocalCustomColorsPalette.current.thumbCheckedColor,
                inactiveTrackColor = LocalCustomColorsPalette.current.trackDefaultColor,
                inactiveTickColor = LocalCustomColorsPalette.current.trackDefaultColor,
                disabledThumbColor = LocalCustomColorsPalette.current.trackDefaultColor,
                disabledActiveTrackColor = LocalCustomColorsPalette.current.trackDefaultColor,
                disabledActiveTickColor = LocalCustomColorsPalette.current.trackDefaultColor,
                disabledInactiveTrackColor = LocalCustomColorsPalette.current.trackDefaultColor,
                disabledInactiveTickColor = LocalCustomColorsPalette.current.trackDefaultColor,

                )
        )
        ModelButton(text = stringResource(id = R.string.confirmButton),
            R.dimen.text_title_small,
            modifier = Modifier.width(320.dp),
            true,
            onClickButton = {

                accountsViewModel.upDateSpendingLimitAccount(account.id, spendingLimit)
                scope.launch(Dispatchers.Main) {
                    SnackBarController.sendEvent(
                        event = SnackBarEvent(
                            messageUpdateSpendingLimit
                        )
                    )
                }
            }

        )
    }
}
