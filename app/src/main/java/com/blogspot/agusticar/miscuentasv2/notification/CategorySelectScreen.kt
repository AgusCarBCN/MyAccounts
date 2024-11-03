package com.blogspot.agusticar.miscuentasv2.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blogspot.agusticar.miscuentasv2.newamount.view.EntriesViewModel
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette



@Composable
fun EntryCategoryList(
    entriesViewModel: EntriesViewModel
) {
    val listOfCategories by entriesViewModel.listOfCategories.observeAsState()
    LaunchedEffect(Unit) {
        entriesViewModel.getCategories(false)
    }
    Column( modifier = Modifier
        .fillMaxSize()
        .padding(bottom = 25.dp)
        .verticalScroll(
            rememberScrollState()
        )) {
        listOfCategories?.forEach {
            ItemCategoryCheck(
                categoryName = it.name,
                categoryIcon = it.iconResource,
                checked = false,
                onCheckBoxChange = { checked ->
                    !checked
                }
            )
        }
    }
}




@Composable
fun ItemCategoryCheck(
    categoryName: Int?,
    categoryIcon: Int?,
    checked: Boolean,
    onCheckBoxChange: (Boolean) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.padding(start = 15.dp, end = 20.dp, top = 5.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = categoryIcon ?: 0),
                contentDescription = "icon",
                tint = LocalCustomColorsPalette.current.textColor
            )
            Spacer(modifier = Modifier.height(20.dp)) // Espacio entre el texto y el botón
            Text(
                text = stringResource(id = categoryName ?: 0),
                modifier = Modifier
                    .padding(10.dp)
                    .weight(0.4f),
                color = LocalCustomColorsPalette.current.textColor,
                textAlign = TextAlign.Start,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Checkbox(modifier = Modifier
                .weight(0.4f),
                checked = false,
                onCheckedChange = { onCheckBoxChange(!checked) },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Green,
                    uncheckedColor = Color.LightGray,
                    checkmarkColor = Color.Blue
                )
            )

        }

    }

}
