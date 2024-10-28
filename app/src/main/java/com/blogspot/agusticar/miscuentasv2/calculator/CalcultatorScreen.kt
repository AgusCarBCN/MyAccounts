package com.blogspot.agusticar.miscuentasv2.calculator

import androidx.compose.material3.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.components.CalculatorButton
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette

@Composable
@Preview
fun CalculatorUI(
    //viewModel: CalculatorViewModel,
) {
    //val expression = viewModel.expression
    val buttonSpacing = 8.dp

    // Log.d("MainActivity", "onCreate: ${viewModel.expression.value}")
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalCustomColorsPalette.current.backgroundPrimary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.spacedBy(buttonSpacing),
        ) {
            LazyRow(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth(),
                reverseLayout = true
            ) {
                item {
                    Text(
                        text = "0",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp, horizontal = 16.dp),
                        fontSize = 64.sp,
                        fontWeight = FontWeight.Normal, // Estilo de texto en negrita
                        textAlign = androidx.compose.ui.text.style.TextAlign.End,
                        color = LocalCustomColorsPalette.current.textColor
                    )

                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    ,
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                CalculatorButton(
                    symbol = "AC",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .clickable {
                            //viewModel.clear()
                        },
                    LocalCustomColorsPalette.current.initDelCalc,
                    LocalCustomColorsPalette.current.targetDelCalc,
                    LocalCustomColorsPalette.current.textInitOperatorCalc,
                    LocalCustomColorsPalette.current.textTargetOperatorCalc
                )
                CalculatorButton(
                    symbol = "(",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .clickable {
                            //viewModel.clear()
                        },
                    LocalCustomColorsPalette.current.initOperatorCalc,
                    LocalCustomColorsPalette.current.targetOperatorCalc,
                    LocalCustomColorsPalette.current.textInitOperatorCalc,
                    LocalCustomColorsPalette.current.textTargetOperatorCalc
                )
                CalculatorButton(
                    symbol = ")",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .clickable {
                            //viewModel.clear()
                        },
                    LocalCustomColorsPalette.current.initOperatorCalc,
                    LocalCustomColorsPalette.current.targetOperatorCalc,
                    LocalCustomColorsPalette.current.textInitOperatorCalc,
                    LocalCustomColorsPalette.current.textTargetOperatorCalc
                )
                CalculatorButton(
                    symbol = "÷",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .clickable {
                            //viewModel.clear()
                        },
                    LocalCustomColorsPalette.current.initOperatorCalc,
                    LocalCustomColorsPalette.current.targetOperatorCalc,
                    LocalCustomColorsPalette.current.textInitOperatorCalc,
                    LocalCustomColorsPalette.current.textTargetOperatorCalc
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    ,
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                CalculatorButton(
                    symbol = "7",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .clickable {
                            //viewModel.clear()
                        },
                    LocalCustomColorsPalette.current.buttonColorDefault,
                    LocalCustomColorsPalette.current.buttonTransition,
                    LocalCustomColorsPalette.current.textButtonColorDefault,
                    LocalCustomColorsPalette.current.textTransition
                )
                CalculatorButton(
                    symbol = "8",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .clickable {
                            //viewModel.clear()
                        },
                    LocalCustomColorsPalette.current.buttonColorDefault,
                    LocalCustomColorsPalette.current.buttonTransition,
                    LocalCustomColorsPalette.current.textButtonColorDefault,
                    LocalCustomColorsPalette.current.textTransition
                )
                CalculatorButton(
                    symbol = "9",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .clickable {
                            //viewModel.clear()
                        },
                    LocalCustomColorsPalette.current.buttonColorDefault,
                    LocalCustomColorsPalette.current.buttonTransition,
                    LocalCustomColorsPalette.current.textButtonColorDefault,
                    LocalCustomColorsPalette.current.textTransition
                )
                CalculatorButton(
                    symbol = "×",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .clickable {
                            //viewModel.clear()
                        },
                    LocalCustomColorsPalette.current.initOperatorCalc,
                    LocalCustomColorsPalette.current.targetOperatorCalc,
                    LocalCustomColorsPalette.current.textInitOperatorCalc,
                    LocalCustomColorsPalette.current.textTargetOperatorCalc
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                   ,
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                CalculatorButton(
                    symbol = "4",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .clickable {
                            //viewModel.clear()
                        },
                    LocalCustomColorsPalette.current.buttonColorDefault,
                    LocalCustomColorsPalette.current.buttonTransition,
                    LocalCustomColorsPalette.current.textButtonColorDefault,
                    LocalCustomColorsPalette.current.textTransition
                )
                CalculatorButton(
                    symbol = "5",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .clickable {
                            //viewModel.clear()
                        },
                    LocalCustomColorsPalette.current.buttonColorDefault,
                    LocalCustomColorsPalette.current.buttonTransition,
                    LocalCustomColorsPalette.current.textButtonColorDefault,
                    LocalCustomColorsPalette.current.textTransition
                )
                CalculatorButton(
                    symbol = "6",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .clickable {
                            //viewModel.clear()
                        },
                    LocalCustomColorsPalette.current.buttonColorDefault,
                    LocalCustomColorsPalette.current.buttonTransition,
                    LocalCustomColorsPalette.current.textButtonColorDefault,
                    LocalCustomColorsPalette.current.textTransition
                )
                CalculatorButton(
                    symbol = "-",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .clickable {
                            //viewModel.clear()
                        },
                    LocalCustomColorsPalette.current.initOperatorCalc,
                    LocalCustomColorsPalette.current.targetOperatorCalc,
                    LocalCustomColorsPalette.current.textInitOperatorCalc,
                    LocalCustomColorsPalette.current.textTargetOperatorCalc
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                     ,
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                CalculatorButton(
                    symbol = "1",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .clickable {
                            //viewModel.clear()
                        },
                    LocalCustomColorsPalette.current.buttonColorDefault,
                    LocalCustomColorsPalette.current.buttonTransition,
                    LocalCustomColorsPalette.current.textButtonColorDefault,
                    LocalCustomColorsPalette.current.textTransition
                )
                CalculatorButton(
                    symbol = "2",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .clickable {
                            //viewModel.clear()
                        },
                    LocalCustomColorsPalette.current.buttonColorDefault,
                    LocalCustomColorsPalette.current.buttonTransition,
                    LocalCustomColorsPalette.current.textButtonColorDefault,
                    LocalCustomColorsPalette.current.textTransition
                )
                CalculatorButton(
                    symbol = "3",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .clickable {
                            //viewModel.clear()
                        },
                    LocalCustomColorsPalette.current.buttonColorDefault,
                    LocalCustomColorsPalette.current.buttonTransition,
                    LocalCustomColorsPalette.current.textButtonColorDefault,
                    LocalCustomColorsPalette.current.textTransition
                )
                CalculatorButton(
                    symbol = "+",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .clickable {
                            //viewModel.clear()
                        },
                    LocalCustomColorsPalette.current.initOperatorCalc,
                    LocalCustomColorsPalette.current.targetOperatorCalc,
                    LocalCustomColorsPalette.current.textInitOperatorCalc,
                    LocalCustomColorsPalette.current.textTargetOperatorCalc
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                    ,
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                CalculatorButton(
                    symbol = "0",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .clickable {
                            //viewModel.clear()
                        },
                    LocalCustomColorsPalette.current.buttonColorDefault,
                    LocalCustomColorsPalette.current.buttonTransition,
                    LocalCustomColorsPalette.current.textButtonColorDefault,
                    LocalCustomColorsPalette.current.textTransition
                )
                CalculatorButton(
                    symbol = ".",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .clickable {
                            //viewModel.clear()
                        },
                    LocalCustomColorsPalette.current.buttonColorDefault,
                    LocalCustomColorsPalette.current.buttonTransition,
                    LocalCustomColorsPalette.current.textButtonColorDefault,
                    LocalCustomColorsPalette.current.textTransition
                )
                CalculatorButton(
                    symbol = stringResource(id = R.string.calcback),
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .clickable {
                            //viewModel.clear()
                        },
                    LocalCustomColorsPalette.current.initDelCalc,
                    LocalCustomColorsPalette.current.targetDelCalc,
                    LocalCustomColorsPalette.current.textInitOperatorCalc,
                    LocalCustomColorsPalette.current.textTargetOperatorCalc
                )
                CalculatorButton(
                    symbol = "=",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .clickable {
                            //viewModel.clear()
                        },
                    LocalCustomColorsPalette.current.initOperatorCalc,
                    LocalCustomColorsPalette.current.targetOperatorCalc,
                    LocalCustomColorsPalette.current.textInitOperatorCalc,
                    LocalCustomColorsPalette.current.textTargetOperatorCalc
                )
            }
        }
    }
}
