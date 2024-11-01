package com.blogspot.agusticar.miscuentasv2.barchart


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.barchart.model.BarChartData
import com.blogspot.agusticar.miscuentasv2.main.domain.database.entriesusecase.GetSumExpensesByMonthUseCase
import com.blogspot.agusticar.miscuentasv2.main.domain.database.entriesusecase.GetSumIncomesByMonthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BarChartViewModel @Inject constructor(
    private val sumIncomesByMonth: GetSumIncomesByMonthUseCase,
    private val sumExpensesByMonth: GetSumExpensesByMonthUseCase
) : ViewModel() {

    // MutableStateFlow para la lista de entradas
    private val _barChartData = MutableLiveData<MutableList<BarChartData>>(mutableListOf())
    val barChartData: LiveData<MutableList<BarChartData>> = _barChartData

    private val _showYearPicker = MutableLiveData<Boolean>()
    val showYearPicker: LiveData<Boolean> = _showYearPicker

    private val _selectedYear = MutableLiveData<String>("2024")
    val selectedYear: LiveData<String> = _selectedYear

    fun onShowYearPicker(newValue: Boolean) {
        _showYearPicker.value = newValue
    }

    fun onSelectedYear(year: String) {
        _selectedYear.value = year
    }

    fun barChartDataByMonth(accountId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val year = _selectedYear.value?:"2024"
            val data: MutableList<BarChartData> = mutableListOf()

            listOfMonths.forEachIndexed { index, month ->
                // Asegúrate de que el mes esté en el formato correcto
                val monthValue:String = if(index<9){
                    "0${(index+1)}"
                }else{
                    "${index+1}"
                }

                try {
                    // Realiza llamadas asíncronas para ingresos y gastos
                    val incomeAmountDeferred = async { sumIncomesByMonth.invoke(accountId, monthValue,year) }
                    val expenseAmountDeferred = async { sumExpensesByMonth.invoke(accountId, monthValue,year) }
                    Log.d("data",monthValue)
                    Log.d("data",year)
                    // Espera los resultados
                    val incomeAmount = incomeAmountDeferred.await().toFloat()
                    val expenseAmount = expenseAmountDeferred.await().toFloat()
                    val resultAmount = incomeAmount + expenseAmount
                    Log.d("data",incomeAmount.toString())
                    Log.d("data",expenseAmount.toString())
                    Log.d("data",resultAmount.toString())
                    // Agrega los datos a la lista
                    data.add(BarChartData(month, incomeAmount, resultAmount, resultAmount))

                } catch (e: Exception) {
                    Log.e("ViewModel", "Error al obtener entradas para la cuenta $accountId para el mes $month: ${e.message}")
                }
            }

            // Publica los datos al LiveData una vez que todos los meses se hayan procesado
            _barChartData.postValue(data)
        }
    }


    private val listOfMonths = listOf(
        R.string.january,
        R.string.february,
        R.string.march,
        R.string.april,
        R.string.may,
        R.string.june,
        R.string.july,
        R.string.august,
        R.string.september,
        R.string.october,
        R.string.november,
        R.string.december
    )
}