package com.blogspot.agusticar.miscuentasv2.barchart

import android.graphics.Paint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.blogspot.agusticar.miscuentasv2.createaccounts.view.AccountsViewModel
import com.blogspot.agusticar.miscuentasv2.newamount.view.EntriesViewModel
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.barchart.model.BarChartData
import com.blogspot.agusticar.miscuentasv2.components.AccountSelector
import com.blogspot.agusticar.miscuentasv2.components.YearSelector
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette
import java.util.Calendar
import kotlin.math.round


@Composable

fun BarChartScreen(entriesViewModel: EntriesViewModel,
                   accountViewModel: AccountsViewModel,
                   barChartViewModel: BarChartViewModel

){
    val year = Calendar.getInstance().get(Calendar.YEAR)
    val accountSelected by accountViewModel.accountSelected.observeAsState()
    val yearSelected by barChartViewModel.selectedYear.observeAsState(year)
    val barChartData by barChartViewModel.barChartData.observeAsState(mutableListOf())


    val idAccount = accountSelected?.id ?: 1
    LaunchedEffect(idAccount) {
        barChartViewModel.barChartDataByMonth(idAccount)
    }
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Log.d("data",barChartData.toString())
        Log.d("year",yearSelected.toString())
        BarChart()
        Row(
            modifier = Modifier
                .width(300.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AccountSelector(200, 10, stringResource(id = R.string.account), accountViewModel)
            YearSelector(barChartViewModel)
        }

    }

}
@Composable
fun BarChart() {

    val chartData = listOf(
        Pair("1", 0),
        Pair("2", 0),
        Pair("2", 0),
        Pair("4", 205),
        Pair("5", 150),
        Pair("6", 175),
        Pair("7", 70),
        Pair("8", 205),
        Pair("9", 150),
        Pair("10", 175),
        Pair("11", 150),
        Pair("12", 175),
    )

    val spacingFromLeft = 100f
    val spacingFromBottom = 40f

    val upperValue = remember { (chartData.maxOfOrNull { it.second }?.plus(1)) ?: 0 }
    val lowerValue = remember { (chartData.minOfOrNull { it.second }?.toInt() ?: 0) }

    val density = LocalDensity.current

    //paint for the text shown in data values
    val textPaint = remember(density) {
        Paint().apply {
            color = android.graphics.Color.BLACK
            textAlign = Paint.Align.CENTER
            textSize = density.run { 12.sp.toPx() }
        }
    }
    Column( modifier = Modifier
        .fillMaxWidth()
        )   {



        androidx.compose.foundation.Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(LocalCustomColorsPalette.current.backgroundSecondary)
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
                    color = Color.Black,
                    strokeWidth = 3f
                )
            }

            //Vertical line
            drawLine(
                start = Offset(spacingFromLeft, canvasHeight - spacingFromBottom),
                end = Offset(spacingFromLeft, 0f),
                color = Color.Black,
                strokeWidth = 3f
            )

            //Horizontal line
            drawLine(
                start = Offset(spacingFromLeft, canvasHeight - spacingFromBottom),
                end = Offset(canvasWidth - 40f, canvasHeight - spacingFromBottom),
                color = Color.Black,
                strokeWidth = 3f
            )

            //draw bars
            chartData.forEachIndexed { index, chartPair ->

                //draw text at top of each bar
               if(chartPair.second>0) {
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
                    color = if(chartPair.second>0)Color.Magenta else Color.Transparent,
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
        }

