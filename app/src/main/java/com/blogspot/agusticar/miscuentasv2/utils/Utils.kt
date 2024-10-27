package com.blogspot.agusticar.miscuentasv2.utils

import android.content.Context
import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Entry
import com.blogspot.agusticar.miscuentasv2.main.model.currencyLocales
import com.blogspot.agusticar.miscuentasv2.setting.model.EntryCSV
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import kotlin.math.abs

class Utils {

    companion object {

        fun isValidDecimal(text: String): Boolean {

            return text.isEmpty() || text.matches(Regex("^([1-9]\\d*|0)?(\\.\\d*)?\$"))

        }
        fun convertMillisToDate(millis: Long): String {
            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            return formatter.format(Date(millis))
        }

        fun numberFormat(amount: Double, currencyCode: String): String {

            val locale = currencyLocales[currencyCode] ?: Locale.GERMAN
            // Formatear la cantidad en la moneda especificada
            val numberFormat = NumberFormat.getCurrencyInstance(locale)
            // Iniciar la carga de cuentas solo cuando el Composable se inicia
            return numberFormat.format(
                abs(amount)
            )
        }

        fun toDateEntry(date: String): String {
            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

            val dateEntry = formatter.parse(date)
            date.let { formatter.parse(it) }
            return dateEntry?.dateFormatDayMonth() ?: ""


        }
        fun convertStringToLocalDate(date: String): LocalDate {
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault())
            return LocalDate.parse(date, formatter)
        }

        fun writeCsvFile(
            entries: MutableList<EntryCSV>,
            context: Context,
            fileName: String,
            directory: DocumentFile
        ) {
            val file = directory.createFile("text/csv", fileName)
            val outputStream = context.contentResolver.openOutputStream(file?.uri!!)

            BufferedWriter(OutputStreamWriter(outputStream)).use { writer ->
                // Escribir el encabezado del CSV
                writer.write("Id,Description,Category,Amount,Date,CategoryId,CategoryName,accountId\n")

                //Escribir las rutas en fichero
                for (entry in entries) {
                    // Asegúrate de que los datos sean válidos antes de escribirlos
                    val csvLine = "${entry.id}," +
                        "${entry.description}," +
                                "${entry.category}," +
                                "${entry.amount}," +
                                "${entry.date}," +
                                "${entry.categoryId}," +
                                "${entry.categoryName}," +
                                "${entry.accountId}\n"
                    writer.write(csvLine)
                }
            }

        }

        fun readCsvFile(
            context: Context,
            fileUri: Uri
        ): MutableList<Entry> {
            val entries = mutableListOf<Entry>()


            context.contentResolver.openInputStream(fileUri)?.use { inputStream ->
                val bufferedReader = BufferedReader(InputStreamReader(inputStream))
                val csvParser = CSVParser.parse(bufferedReader, CSVFormat.DEFAULT)

                for (record in csvParser) {
                    try {val id=record.get(0).toLong()
                        val description = record.get(1)
                        val amount = record.get(3).toDoubleOrNull() ?: 0.0
                        val date = record.get(4)
                        val categoryId = record.get(5).toInt()
                        val categoryName = record.get(6).toInt()
                        val accountId = record.get(7).toInt()


                        // Crear objeto route y agregarlo a lista

                        val entry = Entry(
                            id=id,
                            description = description,
                            amount = amount,
                            date = date,
                            categoryId = categoryId,
                            categoryName = categoryName,
                            accountId = accountId
                        )
                        entries.add(entry)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
            return entries
        }


    }
}