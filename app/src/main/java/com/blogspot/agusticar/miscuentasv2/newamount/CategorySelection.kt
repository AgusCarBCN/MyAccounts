package com.blogspot.agusticar.miscuentasv2.newamount

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.components.CategoryEntries
import com.blogspot.agusticar.miscuentasv2.main.model.Category
import com.blogspot.agusticar.miscuentasv2.main.model.IconOptions
import com.blogspot.agusticar.miscuentasv2.main.view.MainViewModel

/*
@Composable

fun CategorySelectorIncome(mainViewModel: MainViewModel) {


    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        items(incomeItems.size) { index ->
            CategoryEntries(
                title = stringResource(incomeItems[index].categoryDescription),
                iconResource = incomeItems[index].categoryIcon,
                modifier = Modifier
                    .padding(10.dp),
                onClickItem = {mainViewModel.selectScreen(IconOptions.NEW_INCOME)})
        }
      }
    }

@Composable
fun CategorySelectorExpenses(mainViewModel:MainViewModel) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)

        // Espaciado alrededor de la cuadrícula


    ) {
        items(expenseItems.size) { index ->
            CategoryEntries(
                title = stringResource(expenseItems[index].categoryDescription),
                iconResource = expenseItems[index].categoryIcon,
                modifier = Modifier
                    .padding(10.dp),
                onClickItem = {mainViewModel.selectScreen(IconOptions.NEW_EXPENSE)
                    mainViewModel.selectResources(expenseItems[index].categoryIcon,
                        expenseItems[index].categoryDescription)
                })
        }
    }
}*/

@Composable

fun CategorySelector(mainViewModel: MainViewModel,isIncome:Boolean) {

    val categoryItems=if(isIncome)incomeItems else expenseItems

        LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        items(categoryItems.size) { index ->
            CategoryEntries(
                title = stringResource(categoryItems[index].categoryDescription),
                iconResource = categoryItems[index].categoryIcon,
                modifier = Modifier
                    .padding(10.dp),
                onClickItem = {mainViewModel.selectScreen(IconOptions.NEW_AMOUNT)
                    mainViewModel.selectResources(categoryItems[index].categoryIcon,
                        categoryItems[index].categoryDescription,isIncome)
                })
        }
    }
}



val incomeItems = listOf(
    Category(
        R.string.salary,
        R.drawable.ic_category_salary
    ),
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
    Category(
        R.string.lotery,
        R.drawable.ic_category_lotery
    ),
    Category(
        R.string.otherincomes,
        R.drawable.ic_category_otherincomes
    )


)


val expenseItems = listOf(
    Category(
        R.string.food,
        R.drawable.ic_category_grocery
    ),
    Category(
        R.string.morgage,
        R.drawable.ic_category_mortgage
    ),
    Category(
        R.string.electricitybill,
        R.drawable.ic_category_electricity
    ),
    Category(
        R.string.waterbill,
        R.drawable.ic_category_water
    ),

    Category(
        R.string.gasbill,
        R.drawable.ic_category_gasbill
    ),
    Category(
        R.string.publictransport,
        R.drawable.ic_category_publictansport
    ),
    Category(
        R.string.fuel_title,
        R.drawable.ic_category_fuelcar
    ),
    Category(
        R.string.insurances,
        R.drawable.ic_category_otherinsurance
    ),

    Category(
        R.string.health,
        R.drawable.ic_category_healthbill
    ),

    Category(
        R.string.entertainment,
        R.drawable.ic_category_leiure
    ),

    Category(
        R.string.subscriptions,
        R.drawable.ic_category_subscriptions
    ),

    Category(
        R.string.vacations_travel_title,
        R.drawable.ic_category_vacation
    ),

    Category(
        R.string.clothing,
        R.drawable.ic_category_clothing
    ),

    Category(
        R.string.courses_books_materials_title,
        R.drawable.ic_category_education
    ),

    Category(
        R.string.house,
        R.drawable.ic_category_house
    ),

    Category(
        R.string.car,
        R.drawable.ic_category_repaircar
    ),

    Category(
        R.string.gym,
        R.drawable.ic_category_sport
    ),

    Category(
        R.string.pets,
        R.drawable.ic_category_pet
    ),

    Category(
        R.string.personal_care_title,
        R.drawable.ic_category_personalcare
    ),

    Category(
        R.string.gifts_title,
        R.drawable.ic_category_gif
    ),

    Category(
        R.string.donations_title,
        R.drawable.ic_category_donation
    ),

    Category(
        R.string.other_expenses,
        R.drawable.ic_category_otherincomes
    )


)


