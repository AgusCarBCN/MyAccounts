package com.blogspot.agusticar.miscuentasv2.barchart

import android.content.Context
import android.util.Log
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.barchart.model.BarChartData
import com.blogspot.agusticar.miscuentasv2.components.AccountSelector
import com.blogspot.agusticar.miscuentasv2.components.HeadSetting
import com.blogspot.agusticar.miscuentasv2.components.YearSelector
import com.blogspot.agusticar.miscuentasv2.createaccounts.view.AccountsViewModel
import com.blogspot.agusticar.miscuentasv2.newamount.view.EntriesViewModel
import com.blogspot.agusticar.miscuentasv2.setting.SettingViewModel
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.util.Calendar
import kotlin.math.abs


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

    val data by barChartViewModel.barChartData.observeAsState(mutableListOf())
    val isDarkTheme by settingViewModel.switchDarkTheme.observeAsState(false)
    val colorIncomeBar = LocalCustomColorsPalette.current.incomeColor
    val colorExpenseBar = LocalCustomColorsPalette.current.expenseColor
    val context= LocalContext.current

    val idAccount = accountSelected?.id ?: 1

        barChartViewModel.barChartDataByMonth(idAccount)



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

        HeadSetting(title = stringResource(id = R.string.alloption), 20)
        BarChart(context, data,isDarkTheme)

    }

}


@Composable
fun BarChart(
    context: Context,
    data: MutableList<BarChartData>,
    isDarkTheme: Boolean
) {
    val months= mutableListOf<String>()
    data.forEach {element->
        months.add(stringResource(id =element.month ))
    }
    Log.d("data",data.toString())
    val incomeLabel= stringResource(id = R.string.incomechart)
    val expensesLabel= stringResource(id = R.string.expensechart)
    AndroidView(
        factory = { ctx ->
            BarChart(ctx).apply {
                // Set up chart properties
                setBackgroundColor(if(isDarkTheme) ContextCompat.getColor(context, R.color.darkBrown)
                else ContextCompat.getColor(context, R.color.lightYellow))
                setGridBackgroundColor(if(isDarkTheme) ContextCompat.getColor(context, R.color.darkBrown)
                else ContextCompat.getColor(context, R.color.lightYellow))
                description.isEnabled = false
                setTouchEnabled(true) // Correct usage to enable touch
                setDrawGridBackground(false)
                axisRight.isEnabled = false// Disable right Y axis
                // Setup the X-axis
                xAxis.apply {
                    valueFormatter = IndexAxisValueFormatter(months)
                    granularity = 1f
                    position = com.github.mikephil.charting.components.XAxis.XAxisPosition.BOTTOM
                    setDrawGridLines(false)
                    textColor = if(isDarkTheme) ContextCompat.getColor(context, R.color.lightYellow)
                    else ContextCompat.getColor(context, R.color.darkBrown)
                    axisLineColor =  if(isDarkTheme) ContextCompat.getColor(context, R.color.lightYellow)
                    else ContextCompat.getColor(context, R.color.darkBrown)
                    textSize = 12f
                }


                // Configuración del eje Y izquierdo
                axisLeft.apply {
                    textColor =  if(isDarkTheme) ContextCompat.getColor(context, R.color.lightYellow)
                    else ContextCompat.getColor(context, R.color.darkBrown)
                    axisLineColor = if(isDarkTheme) ContextCompat.getColor(context, R.color.lightYellow)
                    else ContextCompat.getColor(context, R.color.darkBrown)
                    setDrawGridLines(false) // Habilitar líneas de cuadrícula en el eje Y
                    gridColor =  if(isDarkTheme) ContextCompat.getColor(context, R.color.lightYellow)
                    else ContextCompat.getColor(context, R.color.darkBrown)
                    textSize = 12f
                    axisMinimum = 0f
                }
                // Configuración de la leyenda
                legend.apply {
                    isEnabled = true // Habilitar la leyenda
                    textColor =  if(isDarkTheme) ContextCompat.getColor(context, R.color.lightYellow)
                    else ContextCompat.getColor(context, R.color.darkBrown)
                    textSize = 14f // Establecer el tamaño del texto de la leyenda
                    form = Legend.LegendForm.CIRCLE // Forma de la leyenda (puede ser SQUARE, CIRCLE, LINE)
                    formSize = 10f // Tamaño de la forma en la leyenda
                    horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT // Alineación horizontal
                    verticalAlignment = Legend.LegendVerticalAlignment.TOP // Alineación vertical
                    orientation = Legend.LegendOrientation.HORIZONTAL // Orientación de la leyenda
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp),
        update = { chart ->
            // Prepare bar entries for each category
            val barEntriesIncomes = data.mapIndexed { index, value ->
                BarEntry(index.toFloat(), value.incomes)
            }
            val barEntriesExpenses = data.mapIndexed { index, value ->
                BarEntry(index.toFloat(), abs(value.expenses))
            }


            // Create datasets
            val incomesDataSet = BarDataSet(barEntriesIncomes,incomeLabel ).apply {
                color = if(isDarkTheme)ContextCompat.getColor(context, R.color.darkGreenPistacho)
                else ContextCompat.getColor(context, R.color.darkgreen)
            }
            val expensesDataSet = BarDataSet(barEntriesExpenses, expensesLabel).apply {
                color = if(isDarkTheme)ContextCompat.getColor(context, R.color.coralred)
                else ContextCompat.getColor(context, R.color.red)
            }


            // Create bar data
            val dataBarChart = BarData(incomesDataSet, expensesDataSet)
            chart.data = dataBarChart

            // Refresh chart
            chart.invalidate()
        }
    )
}

