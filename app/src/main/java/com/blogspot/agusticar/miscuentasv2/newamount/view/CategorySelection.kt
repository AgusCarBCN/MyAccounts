package com.blogspot.agusticar.miscuentasv2.newamount.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.components.CategoryEntries
import com.blogspot.agusticar.miscuentasv2.components.HeadSetting
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Category
import com.blogspot.agusticar.miscuentasv2.main.model.IconOptions
import com.blogspot.agusticar.miscuentasv2.main.view.MainViewModel


@Composable

fun CategorySelector(mainViewModel: MainViewModel, categories:List<Category>, status:Boolean) {

    //val categoryItems=if(isIncome)incomeItems else expenseItems
    HeadSetting(title = (if(status) stringResource(id = R.string.chooseincome) else stringResource(
        id = R.string.chooseexpense)), size = 24)
        LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        items(categories.size) { index ->
            CategoryEntries(status,
                title = stringResource(categories[index].name),
                iconResource = categories[index].iconResource,
                modifier = Modifier
                    .padding(10.dp),
                onClickItem = {mainViewModel.selectScreen(IconOptions.NEW_AMOUNT)
                    mainViewModel.selectResources(categories[index].iconResource,
                        categories[index].name,status)
                })
        }
    }
}


