package com.blogspot.agusticar.miscuentasv2.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.blogspot.agusticar.miscuentasv2.search.SearchViewModel
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette
import com.blogspot.agusticar.miscuentasv2.utils.Utils


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerSearch(
    modifier: Modifier,
    label: Int,
    searchViewModel:SearchViewModel,
    isDateFrom:Boolean
) {
    //var showDatePicker by remember { mutableStateOf(false) }
    val showDatePicker by searchViewModel.showDatePicker.observeAsState(false)
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis?.let {
        Utils.convertMillisToDate(it)
    } ?: ""

    Box(modifier = Modifier.width(160.dp)) {
        TextField(
            value = selectedDate,
            onValueChange = { if(isDateFrom)searchViewModel.onToDateSelected(selectedDate)
                            else searchViewModel.onFromDateSelected(selectedDate)},
            label = { Text(stringResource(label)) },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { searchViewModel.onShowDatePicker(true)}) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select date"
                    )
                }
            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            colors = TextFieldDefaults.colors(
                focusedTextColor = LocalCustomColorsPalette.current.textColor,
                focusedIndicatorColor = Color.Transparent,  // Sin borde cuando está enfocado
                unfocusedIndicatorColor = Color.Transparent,  // Sin borde cuando no está enfocado
                focusedContainerColor = LocalCustomColorsPalette.current.focusedContainerTextField,
                unfocusedContainerColor = LocalCustomColorsPalette.current.unfocusedContainerTextField,
                unfocusedTextColor = LocalCustomColorsPalette.current.textColor,
                focusedLabelColor = LocalCustomColorsPalette.current.textColor,
                unfocusedLabelColor = LocalCustomColorsPalette.current.textColor,
                disabledLabelColor = LocalCustomColorsPalette.current.textColor,
                disabledTextColor = LocalCustomColorsPalette.current.textColor,
                unfocusedTrailingIconColor = LocalCustomColorsPalette.current.textColor,
                focusedTrailingIconColor = LocalCustomColorsPalette.current.textColor
            )
        )

        if (showDatePicker) {
            Popup(
                onDismissRequest = {searchViewModel.onShowDatePicker(false) },
                alignment = Alignment.TopStart
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = 64.dp)
                        .shadow(elevation = 4.dp)
                        .background(
                            LocalCustomColorsPalette.current.drawerColor
                        )
                        .padding(16.dp)
                ) {
                    DatePicker(
                        state = datePickerState,
                        showModeToggle = false,
                        colors = DatePickerColors(
                            containerColor = LocalCustomColorsPalette.current.drawerColor,
                            titleContentColor = LocalCustomColorsPalette.current.textHeadColor,
                            headlineContentColor = LocalCustomColorsPalette.current.textHeadColor,
                            weekdayContentColor = LocalCustomColorsPalette.current.textHeadColor,
                            subheadContentColor = LocalCustomColorsPalette.current.textColor,
                            navigationContentColor = LocalCustomColorsPalette.current.textHeadColor,
                            yearContentColor = LocalCustomColorsPalette.current.textHeadColor,
                            disabledYearContentColor = LocalCustomColorsPalette.current.disableButton,
                            dayContentColor = LocalCustomColorsPalette.current.textColor,
                            currentYearContentColor = LocalCustomColorsPalette.current.textHeadColor,
                            selectedYearContentColor = LocalCustomColorsPalette.current.textHeadColor,
                            disabledSelectedYearContentColor = LocalCustomColorsPalette.current.disableButton,
                            disabledSelectedYearContainerColor = LocalCustomColorsPalette.current.disableButton,
                            selectedYearContainerColor = LocalCustomColorsPalette.current.focusedContainerTextField,
                            selectedDayContainerColor = LocalCustomColorsPalette.current.textColor,
                            disabledSelectedDayContainerColor = LocalCustomColorsPalette.current.disableButton,
                            selectedDayContentColor = LocalCustomColorsPalette.current.textButtonColorPressed,
                            disabledSelectedDayContentColor = LocalCustomColorsPalette.current.disableButton,
                            disabledDayContentColor = LocalCustomColorsPalette.current.disableButton,
                            todayContentColor = LocalCustomColorsPalette.current.textHeadColor,
                            todayDateBorderColor = LocalCustomColorsPalette.current.disableButton,
                            dayInSelectionRangeContainerColor = LocalCustomColorsPalette.current.buttonColorPressed,
                            dayInSelectionRangeContentColor = LocalCustomColorsPalette.current.textButtonColorPressed,
                            dividerColor = LocalCustomColorsPalette.current.disableButton,
                            dateTextFieldColors = TextFieldDefaults.colors(
                                focusedTextColor = LocalCustomColorsPalette.current.textColor,
                                focusedIndicatorColor = Color.Transparent,  // Sin borde cuando está enfocado
                                unfocusedIndicatorColor = Color.Transparent,  // Sin borde cuando no está enfocado
                                focusedContainerColor = LocalCustomColorsPalette.current.focusedContainerTextField,
                                unfocusedContainerColor = LocalCustomColorsPalette.current.unfocusedContainerTextField,
                                unfocusedTextColor = LocalCustomColorsPalette.current.textColor,
                                focusedLabelColor = LocalCustomColorsPalette.current.textColor,
                                unfocusedLabelColor = LocalCustomColorsPalette.current.textColor,
                                disabledLabelColor = LocalCustomColorsPalette.current.textColor,
                                disabledTextColor = LocalCustomColorsPalette.current.textColor,
                                unfocusedTrailingIconColor = LocalCustomColorsPalette.current.textColor,
                                focusedTrailingIconColor = LocalCustomColorsPalette.current.textColor
                            )
                        )
                    )


                }
            }
        }
    }
}





