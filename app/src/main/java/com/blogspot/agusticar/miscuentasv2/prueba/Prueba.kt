package com.blogspot.agusticar.miscuentasv2.prueba

import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import com.blogspot.agusticar.miscuentasv2.createaccounts.view.CreateAccountsViewModel
import com.blogspot.agusticar.miscuentasv2.login.LoginViewModel
import com.blogspot.agusticar.miscuentasv2.main.model.currencyLocales
import java.text.NumberFormat
import java.util.Locale


@Composable
fun Test(viewModel: CreateAccountsViewModel){


    val currencyCode by viewModel.currencyCode.observeAsState("")

    val symbol by viewModel.currencySymbol.observeAsState("")

    val locale = currencyLocales[currencyCode] ?: Locale.GERMAN
    // Formatear la cantidad en la moneda especificada
    val numberFormat = NumberFormat.getCurrencyInstance(locale)
        val amount=3000.5
     numberFormat.format(amount)
     Text(text = "Currency Code: $currencyCode  Amount: ${numberFormat.format(amount)}")


}