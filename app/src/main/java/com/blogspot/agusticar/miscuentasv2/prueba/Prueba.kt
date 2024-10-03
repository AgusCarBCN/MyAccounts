package com.blogspot.agusticar.miscuentasv2.prueba

import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import com.blogspot.agusticar.miscuentasv2.createaccounts.view.CreateAccountsViewModel
import com.blogspot.agusticar.miscuentasv2.login.LoginViewModel





@Composable
fun Test(viewModel: CreateAccountsViewModel){


    val currencyCode by viewModel.currencyCode.observeAsState("")

    val symbol by viewModel.currencySymbol.observeAsState("")


    androidx.compose.material3.Text(text = "Currency Code: $currencyCode")

    androidx.compose.material3.Text(text = "Symbol: $symbol")
}