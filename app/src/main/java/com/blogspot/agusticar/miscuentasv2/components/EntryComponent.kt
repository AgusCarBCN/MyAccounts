package com.blogspot.agusticar.miscuentasv2.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blogspot.agusticar.miscuentasv2.createaccounts.view.AccountsViewModel
import com.blogspot.agusticar.miscuentasv2.main.data.database.dto.EntryDTO
import com.blogspot.agusticar.miscuentasv2.newamount.view.EntriesViewModel
import com.blogspot.agusticar.miscuentasv2.setting.SpacerApp
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette
import com.blogspot.agusticar.miscuentasv2.utils.Utils




@Composable
fun EntryList(listOfEntries: List<EntryDTO>, currencyCode: String) {
    // Agrupar las entradas por fecha
    val groupedEntries = listOfEntries.groupBy { it.date }  // Asumiendo que it.date es un String o LocalDate

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .background(LocalCustomColorsPalette.current.backgroundPrimary)

    ) {
        groupedEntries.forEach { (date, entries) ->
            // Mostrar una cabecera sticky para la fecha
            stickyHeader {
                Column(modifier = Modifier.fillMaxWidth()
                    .height(70.dp)
                    .background(LocalCustomColorsPalette.current.backgroundPrimary),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                    Text(
                        modifier = Modifier
                            .background(LocalCustomColorsPalette.current.backgroundPrimary)
                            .fillParentMaxWidth()
                            .padding(start=15.dp),
                        text = Utils.toDateEntry(date),
                        textAlign = TextAlign.Start,
                        fontSize = 18.sp,
                        color = LocalCustomColorsPalette.current.textHeadColor,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Mostrar los elementos de ese grupo
            items(entries) { entry ->
                ItemEntry(
                    entry,
                    currencyCode = currencyCode
                )
            }
        }
    }
}


@Composable

fun ItemEntry(entry:EntryDTO,
              currencyCode:String){
    
    Column {

        Row(modifier = Modifier.padding(start=15.dp, end=15.dp, top=5.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text =  entry.description,
                modifier = Modifier
                    .weight(0.6f),
                textAlign = TextAlign.Start,
                fontSize = 20.sp,
                color= LocalCustomColorsPalette.current.textHeadColor
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text =Utils.numberFormat(entry.amount,currencyCode),
                modifier = Modifier
                    .weight(0.4f),
                color= if(entry.amount>=0)LocalCustomColorsPalette.current.incomeColor
                else LocalCustomColorsPalette.current.expenseColor,
                textAlign = TextAlign.End,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

        }
        Row(modifier = Modifier.padding(start=10.dp, end=10.dp, top=5.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
           Icon(modifier = Modifier.size(24.dp),
               painter = painterResource(id = entry.categoryId),
               contentDescription ="icon" ,
               tint= LocalCustomColorsPalette.current.textColor)
            Spacer(modifier = Modifier.height(20.dp)) // Espacio entre el texto y el botón
            Text(
                text = stringResource(id = entry.categoryName),
                modifier = Modifier
                    .padding(10.dp)
                    .weight(0.4f),
                color= LocalCustomColorsPalette.current.textColor,
                textAlign = TextAlign.Start,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text =entry.name,
                modifier = Modifier
                    .padding(10.dp)
                    .weight(0.4f),
                color= LocalCustomColorsPalette.current.textColor,
                textAlign = TextAlign.End,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

        }
        SpacerApp()
    }
    
}