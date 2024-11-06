package com.blogspot.agusticar.miscuentasv2.notification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.blogspot.agusticar.miscuentasv2.components.CategoryBudgetItemControl
import com.blogspot.agusticar.miscuentasv2.createaccounts.view.AccountsViewModel
import com.blogspot.agusticar.miscuentasv2.createaccounts.view.CategoriesViewModel
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.CategoryType
import com.blogspot.agusticar.miscuentasv2.newamount.view.EntriesViewModel

@Composable

fun ExpenseControlScreen(categoriesViewModel: CategoriesViewModel,
                         entriesViewModel: EntriesViewModel,
                         accountsViewModel: AccountsViewModel
){

    // Observa la lista de categorías desde el ViewModel
    val listOfCategories by categoriesViewModel.listOfCategories.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        categoriesViewModel.getAllCategoriesChecked(CategoryType.EXPENSE)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Asegúrate de que la LazyColumn ocupa solo el espacio necesario
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // Permite que la columna ocupe el espacio disponible
                .padding(bottom = 16.dp) // Espacio en la parte inferior
        ) {
            items(listOfCategories) { category ->
               CategoryBudgetItemControl(category,
                   categoriesViewModel,
                   entriesViewModel,
                   accountsViewModel)
            }
        }


    }

}