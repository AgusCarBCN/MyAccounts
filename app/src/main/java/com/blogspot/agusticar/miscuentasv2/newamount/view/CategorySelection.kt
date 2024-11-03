package com.blogspot.agusticar.miscuentasv2.newamount.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.components.CategoryEntries

import com.blogspot.agusticar.miscuentasv2.components.HeadSetting
import com.blogspot.agusticar.miscuentasv2.components.ModelButton
import com.blogspot.agusticar.miscuentasv2.createaccounts.view.AccountsViewModel
import com.blogspot.agusticar.miscuentasv2.main.model.IconOptions
import com.blogspot.agusticar.miscuentasv2.main.view.MainViewModel


@Composable

fun CategorySelector(mainViewModel: MainViewModel, entriesViewModel:EntriesViewModel, status:Boolean) {


    val listOfCategories by entriesViewModel.listOfCategories.observeAsState(listOf())

    entriesViewModel.getCategories(status)


    HeadSetting(title = (if(status) stringResource(id = R.string.chooseincome) else stringResource(
        id = R.string.chooseexpense)), size = 24)
        LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        items(listOfCategories.size) { index ->
            CategoryEntries(listOfCategories[index],
                modifier = Modifier
                    .padding(10.dp),
                onClickItem = {mainViewModel.selectScreen(IconOptions.NEW_AMOUNT)
                   entriesViewModel.onCategorySelected(listOfCategories[index])
                })
        }
    }
}

