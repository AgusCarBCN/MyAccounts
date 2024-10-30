package com.blogspot.agusticar.miscuentasv2.piechart

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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

    val initColor = LocalCustomColorsPalette.current.buttonColorDefault
    val targetColor = LocalCustomColorsPalette.current.buttonColorPressed

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
        chartPie(percentIncomesList)

        HeadSetting(title = stringResource(id = R.string.expensechart), 24)
        chartPie(percentExpensesList)
        AccountSelector(stringResource(id = R.string.selectanaccount), accountViewModel)
    }

}
@Composable
fun chartPie(percentList: MutableList<Float>): MutableList<Color> {
    val initAngle = -90f
    var currentAngle = initAngle
    var endAngle = 0f
    val total = percentList.sum()
    val textColorPieChart = LocalCustomColorsPalette.current.iconColor
    val isDarkTheme = isSystemInDarkTheme()
    // Lista mutable para almacenar los colores generados
    val segmentColors = mutableListOf<Color>()
    var color:Color
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        contentAlignment = Alignment.Center // Centra el contenido dentro del Box
    ) {
        Canvas(
            modifier = Modifier
                .size(250.dp)
                .align(Alignment.Center) // Centra el Canvas en el Box
        ) {
            val centerX = size.width / 2
            val centerY = size.height / 2

            percentList.forEach { element ->
                // Calcula el ángulo de barrido
                endAngle = (element / total) * 360
                val midAngle = currentAngle + endAngle / 2 // Corrige el ángulo intermedio
                color=colorGenerator(isDarkTheme)
                // Dibuja la porción de arco
                drawArc(
                    color = color,
                    startAngle = currentAngle,
                    sweepAngle = endAngle,
                    useCenter = true,
                    style = Fill
                )
                segmentColors.add(color)
                // Actualiza el ángulo actual
                currentAngle += endAngle

                // Calcula las coordenadas para el texto
                val textX = centerX + (size.width / 3) * cos(Math.toRadians(midAngle.toDouble()).toFloat())
                val textY = centerY + (size.height / 3) * sin(Math.toRadians(midAngle.toDouble()).toFloat())

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
    return segmentColors
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
fun LegendItem(color: Color, label: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(4.dp)
    ) {
        // Cuadro de color de la leyenda
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(color) // Color del cuadro
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



