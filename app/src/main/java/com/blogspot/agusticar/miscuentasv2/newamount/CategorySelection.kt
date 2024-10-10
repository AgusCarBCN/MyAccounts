package com.blogspot.agusticar.miscuentasv2.newamount

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.components.CategoryEntries
import com.blogspot.agusticar.miscuentasv2.main.model.Category



@Composable
@Preview
fun CategorySelector(){

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize()
            .padding(10.dp)

        // Espaciado alrededor de la cuadrícula


    ) {
        items(items.size) { index ->
            CategoryEntries(
                title = stringResource(items[index].categoryDescription),
                iconResource = items[index].categoryIcon,
                modifier = Modifier
                    .padding(10.dp)

                )
        }
    }
}



    val items = listOf(
        Category( R.string.salary,
            R.drawable.ic_category_salary),
        Category(
            R.string.dividens,
            R.drawable.ic_category_dividens
        ),
        Category(
            R.string.rental,
            R.drawable.ic_category_rent
        ),
        Category(
            R.string.freelance,
            R.drawable.ic_category_freelances
        ),

        Category(
            R.string.sales,
            R.drawable.ic_category_sales
        ),
        Category(
            R.string.subsidies,
            R.drawable.ic_category_donation
        ),
        Category( R.string.lotery,
            R.drawable.ic_category_lotery
        ),
        Category( R.string.otherincomes,
            R.drawable.ic_category_otherincomes)


    )




