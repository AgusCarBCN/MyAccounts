package com.blogspot.agusticar.miscuentasv2.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.components.BoardType
import com.blogspot.agusticar.miscuentasv2.components.DatePickerSearch
import com.blogspot.agusticar.miscuentasv2.components.HeadSetting
import com.blogspot.agusticar.miscuentasv2.components.ModelButton
import com.blogspot.agusticar.miscuentasv2.components.TextFieldComponent
import com.blogspot.agusticar.miscuentasv2.createaccounts.view.CategoriesViewModel
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Category
import com.blogspot.agusticar.miscuentasv2.main.model.IconOptions
import com.blogspot.agusticar.miscuentasv2.main.view.MainViewModel
import com.blogspot.agusticar.miscuentasv2.search.SearchViewModel
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette

@Composable

fun CategoryScreen(
    category: Category,
    categoriesViewModel: CategoriesViewModel,
    searchViewModel: SearchViewModel,
    mainViewModel: MainViewModel
) {
    val limitMax by categoriesViewModel.limitMax.observeAsState(category.limitMax.toString())
    val toDate by searchViewModel.selectedToDate.observeAsState(category.fromDate)
    val fromDate by searchViewModel.selectedFromDate.observeAsState(category.toDate)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp)
            .background(LocalCustomColorsPalette.current.backgroundPrimary)
            .verticalScroll(
                rememberScrollState()
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextFieldComponent(
            modifier = Modifier.width(360.dp),
            stringResource(id = R.string.limitMax),
            limitMax,
            onTextChange = { categoriesViewModel.onChangeLimitMax(it) },
            BoardType.DECIMAL,
            false
        )
        HeadSetting(title = stringResource(id = R.string.daterange), 20)
        Row(
            modifier = Modifier
                .width(360.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            DatePickerSearch(
                modifier = Modifier
                    .weight(0.5f)
                    .padding(10.dp),
                R.string.fromdate,
                searchViewModel,
                true
            )
            DatePickerSearch(
                modifier = Modifier
                    .weight(0.5f)
                    .padding(10.dp),
                R.string.todate,
                searchViewModel,
                false
            )
        }
        ModelButton(text = stringResource(id = R.string.search),
            R.dimen.text_title_medium,
            modifier = Modifier.width(360.dp),
            true,
            onClickButton = {
            categoriesViewModel.upDateCategoryDates(category.id,fromDate,toDate)
            categoriesViewModel.upDateLimitMaxCategory(category.id,limitMax.toFloat())
            mainViewModel.selectScreen(IconOptions.SELECT_CATEGORIES)
            }
        )
        ModelButton(text = stringResource(id = R.string.backButton),
            R.dimen.text_title_medium,
            modifier = Modifier.width(360.dp),
            true,
            onClickButton = {
                mainViewModel.selectScreen(IconOptions.HOME)
            })
    }
}

