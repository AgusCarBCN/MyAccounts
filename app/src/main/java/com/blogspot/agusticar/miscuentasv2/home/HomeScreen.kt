package com.blogspot.agusticar.miscuentasv2.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.blogspot.agusticar.miscuentasv2.components.ElevatedCardExample

import com.blogspot.agusticar.miscuentasv2.createaccounts.view.CreateAccountsViewModel
import com.blogspot.agusticar.miscuentasv2.main.model.currencyLocales
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette
import java.text.NumberFormat
import java.util.Locale

@Composable

fun HomeScreen(viewModel: CreateAccountsViewModel)
{
    val currencyCode by viewModel.currencyCode.observeAsState("")
    val locale = currencyLocales[currencyCode] ?: Locale.GERMAN
    // Formatear la cantidad en la moneda especificada
    val numberFormat = NumberFormat.getCurrencyInstance(locale)
    val income=3000.43
    val expenses=1200.78
    Column(modifier = Modifier
        .fillMaxSize()
        .background(LocalCustomColorsPalette.current.backgroundPrimary)){

        ElevatedCardExample(numberFormat.format(income))

        }

    }

