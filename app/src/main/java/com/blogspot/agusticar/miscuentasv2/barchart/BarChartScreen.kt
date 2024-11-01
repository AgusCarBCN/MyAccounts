package com.blogspot.agusticar.miscuentasv2.barchart

import android.graphics.Paint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.components.AccountSelector
import com.blogspot.agusticar.miscuentasv2.components.HeadSetting
import com.blogspot.agusticar.miscuentasv2.components.YearSelector
import com.blogspot.agusticar.miscuentasv2.createaccounts.view.AccountsViewModel
import com.blogspot.agusticar.miscuentasv2.newamount.view.EntriesViewModel
import com.blogspot.agusticar.miscuentasv2.setting.SettingViewModel
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette
import java.util.Calendar
import kotlin.math.abs
import kotlin.math.round


@Composable

fun BarChartScreen(
    entriesViewModel: EntriesViewModel,
    accountViewModel: AccountsViewModel,
    barChartViewModel: BarChartViewModel,
    settingViewModel: SettingViewModel

) {
    val year = Calendar.getInstance().get(Calendar.YEAR)
    val accountSelected by accountViewModel.accountSelected.observeAsState()
    val yearSelected by barChartViewModel.selectedYear.observeAsState(year)
    //val barChartData by barChartViewModel.barChartData.observeAsState(mutableListOf())
    val incomes by barChartViewModel.barChartDataIncome.observeAsState(mutableListOf())
    val expenses by barChartViewModel.barChartDataExpense.observeAsState(mutableListOf())


    val isDarkTheme by settingViewModel.switchDarkTheme.observeAsState(false)
    val colorIncomeBar= LocalCustomColorsPalette.current.incomeColor
    val colorExpenseBar = LocalCustomColorsPalette.current.expenseColor

    val idAccount = accountSelected?.id ?: 1
    LaunchedEffect(idAccount) {
        barChartViewModel.barChartDataByMonth(idAccount)

    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {

        Row(
            modifier = Modifier
                .width(300.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AccountSelector(200, 10, stringResource(id = R.string.account), accountViewModel)
            YearSelector(barChartViewModel)
        }

            HeadSetting(title = stringResource(id = R.string.incomechart), 20)
            BarChart(incomes, colorIncomeBar,3000,1000, isDarkTheme)


            HeadSetting(title = stringResource(id = R.string.expensechart), 20)
            BarChart(expenses, colorExpenseBar,300,1, isDarkTheme)

    }

}

@Composable
fun BarChart(
    data: MutableList<Pair<Int, Int>>,
    colorBar: Color,
    maxDefault:Int,
    plus:Int,
    isDarkTheme: Boolean

) {

    // Convierte chartData en List<Pair<String, Int>> usando stringResource para el primer valor
    val chartData: List<Pair<String, Int>> = data.map { pair ->
        Pair(stringResource(id = pair.first), abs(pair.second))
    }
    val colorChart= LocalCustomColorsPalette.current.backgroundSecondary



    val spacingFromLeft = 100f
    val spacingFromBottom = 40f

    val upperValue = remember { (chartData.maxOfOrNull { it.second }?.plus(1)) ?: 300 }
    val lowerValue = remember { (chartData.minOfOrNull { it.second }?.toInt() ?: 1) }

    val density = LocalDensity.current

    //paint for the text shown in data values
    val textPaint = remember(density) {
        Paint().apply {
            color = if(isDarkTheme)android.graphics.Color.WHITE
            else android.graphics.Color.BLACK
            textAlign = Paint.Align.CENTER
            textSize = density.run { 12.sp.toPx() }
        }
    }

    androidx.compose.foundation.Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(colorChart)
            .padding(16.dp)
    ) {

        val canvasHeight = size.height
        val canvasWidth = size.width

        val spacePerData = (canvasWidth - spacingFromLeft) / chartData.size

        //loop through each index by step of 1
        //data shown horizontally
        (chartData.indices step 1).forEach { i ->
            val text = chartData[i].first
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    text,
                    spacingFromLeft + 30f + i * spacePerData,
                    canvasHeight,
                    textPaint
                )
            }
        }

        val valuesToShow = 5f  //we will show 5 data values on vertical line

        val eachStep = (upperValue - lowerValue) / valuesToShow
        //data shown vertically
        (0..4).forEach { i ->
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    round(lowerValue + eachStep * i).toString(),
                    20f,
                    canvasHeight - 30f - i * canvasHeight / 5f,
                    textPaint
                )
            }

            //draw horizontal line at each value
            drawLine(
                start = Offset(
                    spacingFromLeft - 20f,
                    canvasHeight - spacingFromBottom - i * canvasHeight / 5f
                ),
                end = Offset(
                    spacingFromLeft,
                    canvasHeight - spacingFromBottom - i * canvasHeight / 5f
                ),
                color = if(isDarkTheme) Color.White
                else Color.Black ,
                strokeWidth = 3f
            )
        }

        //Vertical line
        drawLine(
            start = Offset(spacingFromLeft, canvasHeight - spacingFromBottom),
            end = Offset(spacingFromLeft, 0f),
            color = if(isDarkTheme) Color.White
            else Color.Black,
            strokeWidth = 3f
        )

        //Horizontal line
        drawLine(
            start = Offset(spacingFromLeft, canvasHeight - spacingFromBottom),
            end = Offset(canvasWidth - 40f, canvasHeight - spacingFromBottom),
            color = if(isDarkTheme) Color.White
            else Color.Black,
            strokeWidth = 3f
        )

        //draw bars
        chartData.forEachIndexed { index, chartPair ->

            //draw text at top of each bar
           if(chartPair.second!=0) {
               drawContext.canvas.nativeCanvas.apply {
                   drawText(
                       chartPair.second.toString(),
                       spacingFromLeft + 40f + index * spacePerData,
                       (upperValue - chartPair.second.toFloat()) / upperValue * canvasHeight - 10f,
                       textPaint
                   )
               }
           }
            //draw Bar for each value
            drawRoundRect(
                color = if(chartPair.second==0) Color.Transparent
                else colorBar,
                topLeft = Offset(
                    spacingFromLeft + 10f + index * spacePerData,
                    (upperValue - chartPair.second.toFloat()) / upperValue * canvasHeight
                ),
                size = Size(
                    55f,
                    (chartPair.second.toFloat() / upperValue) * canvasHeight - spacingFromBottom
                ),
                cornerRadius = CornerRadius(10f, 10f)
            )


        }
    }
}
