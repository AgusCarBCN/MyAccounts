package com.blogspot.agusticar.miscuentasv2.piechart

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.components.AccountSelector
import com.blogspot.agusticar.miscuentasv2.components.HeadSetting
import com.blogspot.agusticar.miscuentasv2.components.IconAnimated
import com.blogspot.agusticar.miscuentasv2.createaccounts.view.AccountsViewModel
import com.blogspot.agusticar.miscuentasv2.newamount.view.EntriesViewModel
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette
import com.blogspot.agusticar.miscuentasv2.utils.Utils
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun PieChartScreen(
    entriesViewModel: EntriesViewModel,
    accountViewModel: AccountsViewModel
) {

    val getTotalIncomes by entriesViewModel.totalIncomes.observeAsState(0.0)
    val getTotalExpenses by entriesViewModel.totalExpenses.observeAsState(0.0)
    val listOfEntries by entriesViewModel.listOfEntries.collectAsState()
    val accountSelected by accountViewModel.accountSelected.observeAsState()
    accountViewModel.getAllAccounts()
    val idAccount = accountSelected?.id ?: 1


    //Array de porcentajes para dibujar los gráficos circulares
    val percentIncomesList = mutableListOf<Float>()
    val percentExpensesList = mutableListOf<Float>()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp)
            .verticalScroll(
                rememberScrollState()
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        //Getting entries from selected account
        entriesViewModel.getAllEntriesByAccount(idAccount)

        //Calculate percent of entries and adding to percentsList
        listOfEntries.forEach { entry->
            if(entry.amount >=0){
                percentIncomesList.add((entry.amount / getTotalIncomes).toFloat())
            }else{
                percentExpensesList.add((entry.amount / getTotalIncomes).toFloat())
            }
        }
        HeadSetting(title = stringResource(id = R.string.incomechart), 24)
        ChartPie(percentIncomesList)

        HeadSetting(title = stringResource(id = R.string.expensechart), 24)
      ChartPie(percentExpensesList)
        AccountSelector(stringResource(id = R.string.selectanaccount), accountViewModel)
    }

}
@Composable
fun ChartPie(percentList: MutableList<Float>) {
    val initAngle = -90f
    var currentAngle = initAngle
    var endAngle = 0f
    val total = percentList.sum()
    val textColorPieChart = LocalCustomColorsPalette.current.iconColor
    val isDarkTheme = isSystemInDarkTheme()

    // Lista para almacenar los colores generados
    val colors =  mutableListOf<Color>()

    // Generar colores aleatorios para cada segmento
    percentList.forEach { _ ->
        val color = colorGenerator(isDarkTheme) // Cambia esto si deseas un método diferente
        colors.add(color)
    }

    Row(modifier = Modifier.padding(10.dp)) {
        // Gráfico circular
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .weight(0.8f),
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier
                    .size(220.dp)
                    .align(Alignment.Center)
            ) {
                val centerX = size.width / 2
                val centerY = size.height / 2

                // Dibuja cada segmento
                currentAngle = initAngle // Reinicia el ángulo actual para el nuevo dibujo
                percentList.forEachIndexed { index, element ->
                    endAngle = (element / total) * 360
                    val midAngle = currentAngle + endAngle / 2

                    // Dibuja el arco
                    drawArc(
                        color = colors[index],
                        startAngle = currentAngle,
                        sweepAngle = endAngle,
                        useCenter = true,
                        style = Fill
                    )

                    // Actualiza el ángulo actual
                    currentAngle += endAngle

                    // Calcula las coordenadas para el texto
                    val textX = centerX + (size.width / 3) * cos(
                        Math.toRadians(midAngle.toDouble()).toFloat()
                    )
                    val textY = centerY + (size.height / 3) * sin(
                        Math.toRadians(midAngle.toDouble()).toFloat()
                    )

                    // Dibuja el texto con el porcentaje
                    val percent = (element / total * 100).toInt()
                    val text = "$percent%"
                    val textPaint = Paint().asFrameworkPaint()
                    textPaint.color = textColorPieChart.toArgb()
                    textPaint.textSize = 55f
                    textPaint.isFakeBoldText = true
                    drawContext.canvas.nativeCanvas.drawText(text, textX, textY, textPaint)
                }
            }
        }
        Column(modifier = Modifier.weight(0.2f)) {
            colors.forEach { element ->
                LegendItem(element,"label")
            }
        }

    }
}


fun colorGenerator(isDarkTheme: Boolean): Color {
    val random = Random.Default

    return if (isDarkTheme) {
        // Genera colores intensos y oscuros para un fondo claro
        val red = random.nextInt(100, 180) // Colores oscuros pero más intensos
        val green = random.nextInt(100, 180)
        val blue = random.nextInt(100, 180)
        Color(red, green, blue)
    } else {
        // Genera colores intensos y claros para un fondo oscuro
        val red = random.nextInt(180, 256) // Colores más vivos
        val green = random.nextInt(180, 256)
        val blue = random.nextInt(180, 256)
        Color(red, green, blue)
    }
}

@Composable
fun LegendItem( color:Color,label: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(4.dp)
    ) {
        // Cuadro de color de la leyenda
        Box(
            modifier = Modifier
                .size(16.dp)
                .clip(CircleShape)
                .background(color)
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Texto de la leyenda
        Text(
            text = label,
            color = LocalCustomColorsPalette.current.textColor, // Puedes ajustar el color del texto según tu tema
            fontSize = 16.sp
        )
    }
}



