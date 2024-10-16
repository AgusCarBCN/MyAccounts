package com.blogspot.agusticar.miscuentasv2.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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

fun EntryList(listOfEntries:List<EntryDTO>,currencyCode:String)
{


    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .background(LocalCustomColorsPalette.current.backgroundPrimary)
    ) {
        items(listOfEntries) { entry ->
            ItemEntry(
                description =entry.description ,
                categoryDescription =entry.categoryName ,
                categoryIcon =entry.categoryId ,
                amount =entry.amount ,
                currencyCode = currencyCode,
                date =entry.date
            )
            Log.d("Entry", entry.description)
        }
    }

}

@Composable

fun ItemEntry(description:String,
              categoryDescription:Int,
              categoryIcon:Int,
              amount:Double,
              currencyCode:String,date:String){
    
    Column {
        Text(text = Utils.toDateEntry(date),
            textAlign = TextAlign.Start,
            fontSize = 18.sp,
            color = LocalCustomColorsPalette.current.textColor,
            fontWeight = FontWeight.Bold
        )
        Row(modifier = Modifier.padding(5.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text =  description,
                modifier = Modifier
                    .padding(10.dp)
                    .weight(0.6f),
                textAlign = TextAlign.Start,
                fontSize = 18.sp,
                color= LocalCustomColorsPalette.current.textHeadColor
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text =Utils.numberFormat(amount,currencyCode),
                modifier = Modifier
                    .padding(10.dp)
                    .weight(0.4f),
                color= if(amount>=0)LocalCustomColorsPalette.current.incomeColor
                else LocalCustomColorsPalette.current.expenseColor,
                textAlign = TextAlign.End,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

        }
        Row(modifier = Modifier.padding(5.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
           Icon(modifier = Modifier.size(24.dp),
               painter = painterResource(id = categoryIcon),
               contentDescription ="icon" ,
               tint= LocalCustomColorsPalette.current.textColor)
            Spacer(modifier = Modifier.height(12.dp)) // Espacio entre el texto y el botón
            Text(
                text = stringResource(id = categoryDescription),
                modifier = Modifier
                    .padding(10.dp)
                    .weight(0.4f),
                color= LocalCustomColorsPalette.current.textColor,
                textAlign = TextAlign.Start,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

        }
        SpacerApp()
    }
    
}