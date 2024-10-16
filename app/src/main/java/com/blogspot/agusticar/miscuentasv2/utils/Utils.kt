package com.blogspot.agusticar.miscuentasv2.utils

import com.blogspot.agusticar.miscuentasv2.main.model.currencyLocales
import java.sql.Date
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date.parse
import java.util.Locale
import kotlin.math.abs

class Utils {

    companion object {

        fun isValidDecimal(text: String): Boolean {

            return text.isEmpty() || text.matches(Regex("^([1-9]\\d*|0)?(\\.\\d*)?\$"))

        }
        fun numberFormat(amount: Double,currencyCode:String): String {

            val locale = currencyLocales[currencyCode] ?: Locale.GERMAN
            // Formatear la cantidad en la moneda especificada
            val numberFormat = NumberFormat.getCurrencyInstance(locale)
            // Iniciar la carga de cuentas solo cuando el Composable se inicia
            return numberFormat.format(
                abs(amount)
            )
        }
        fun toDateEntry(date:String):String{
            val formatter= SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

            val dateEntry=formatter.parse(date)
            date.let { formatter.parse(it) }
            return dateEntry?.dateFormatDayMonth() ?: ""


        }
    }
}