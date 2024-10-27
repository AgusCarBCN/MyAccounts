package com.blogspot.agusticar.miscuentasv2.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.search.SearchViewModel
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette



@Composable
fun RadioButtonSearch(searchViewModel: SearchViewModel) {
    val options = searchViewModel.options
    val selectedOption by searchViewModel.selectedOption.observeAsState(options[0])
    Row() {

        options.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .weight(1f)
                    .selectable(
                        selected = (option == selectedOption),
                        onClick = { searchViewModel.onOptionSelected(option) }
                    )
                    .padding(8.dp)
            ) {
                RadioButton(
                    selected = (option == selectedOption),
                    onClick = { searchViewModel.onOptionSelected(option) },
                    colors = RadioButtonColors(
                        selectedColor = LocalCustomColorsPalette.current.buttonColorPressed,
                        unselectedColor = LocalCustomColorsPalette.current.textColor,
                        disabledSelectedColor = LocalCustomColorsPalette.current.disableButton,
                        disabledUnselectedColor = LocalCustomColorsPalette.current.disableButton
                    )
                )
                Text(text = stringResource(id = option),
                    color = LocalCustomColorsPalette.current.textColor)

            }
        }
    }
}
