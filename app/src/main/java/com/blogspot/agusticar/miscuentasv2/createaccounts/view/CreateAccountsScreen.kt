package com.blogspot.agusticar.miscuentasv2.createaccounts.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.components.BoardType
import com.blogspot.agusticar.miscuentasv2.components.CurrencySelector
import com.blogspot.agusticar.miscuentasv2.components.ModelButton
import com.blogspot.agusticar.miscuentasv2.components.TextFieldComponent
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Account
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


//Mapa de divisas y simbolos


@Composable

fun CreateAccountsComponent(
    accountsViewModel: AccountsViewModel,
    navToLogin: () -> Unit,
    navToBack: () -> Unit
) {


    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalCustomColorsPalette.current.backgroundPrimary) // Reemplaza con tu color de fondo
    ) {
        val (titleCreateAccount, inputData) = createRefs()
        val scope = rememberCoroutineScope()
        val currencyCode by accountsViewModel.currencyCode.observeAsState("EUR")
        val isCurrencyExpanded by accountsViewModel.isCurrencyExpanded.observeAsState(false)
        val accountName by accountsViewModel.name.observeAsState("")
        val accountBalance by accountsViewModel.amount.observeAsState("")



        Text(
            modifier = Modifier
                .padding(top = 30.dp)
                .constrainAs(titleCreateAccount) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(inputData.top)
                },
            text = if (!isCurrencyExpanded) stringResource(id = R.string.createAccount) else "",
            fontSize = with(LocalDensity.current) { dimensionResource(id = R.dimen.text_title_medium).toSp() },
            fontWeight = FontWeight.Bold, // Estilo de texto en negrita
            textAlign = TextAlign.Center,
            color = LocalCustomColorsPalette.current.textColor
        )


        Column(
            modifier = Modifier
                .constrainAs(inputData) {
                    top.linkTo(titleCreateAccount.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            if (!isCurrencyExpanded) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.createaccountmsg),
                    fontSize = with(LocalDensity.current) { dimensionResource(id = R.dimen.text_body_large).toSp() },
                    fontWeight = FontWeight.Bold, // Estilo de texto en negrita
                    textAlign = TextAlign.Center,
                    color = LocalCustomColorsPalette.current.textColor
                )

                TextFieldComponent(
                    modifier = Modifier.width(360.dp),
                    stringResource(id = R.string.amountName),
                    accountName,
                    onTextChange = { accountsViewModel.onNameChanged(it) },
                    BoardType.TEXT,
                    false
                )
                TextFieldComponent(
                    modifier = Modifier.width(360.dp),
                    stringResource(id = R.string.enteramount),
                    accountBalance,
                    onTextChange = {
                        accountsViewModel.onAmountChanged(it)
                    },
                    BoardType.DECIMAL,
                    false
                )
                ModelButton(text = stringResource(id = R.string.addAccount),
                    R.dimen.text_title_medium,
                    modifier = Modifier.width(360.dp),
                    true,
                    onClickButton = {
                        try {
                            scope.launch(Dispatchers.IO) {
                                val amountDecimal = accountBalance.toDoubleOrNull() ?: 0.0
                                accountsViewModel.addAccount(
                                    Account(
                                        name = accountName,
                                        balance = amountDecimal
                                    )
                                )
                                //createAccountsViewModel.onClearFields()
                                Log.d("Cuenta", "Cuenta creada")
                                println("Cuenta creada")

                            }
                        } catch (e: Exception) {
                            Log.d("Cuenta", "Error: ${e.message}")
                            println("Error al cargar ${e.message}")
                        }


                    }
                )
            }

            CurrencySelector(accountsViewModel)
            if (!isCurrencyExpanded) {
                ModelButton(text = stringResource(id = R.string.confirmButton),
                    R.dimen.text_title_medium,
                    modifier = Modifier.width(360.dp),
                    true,
                    onClickButton = {
                        scope.launch {
                            Log.d("valor a guardar", "Code: $currencyCode")
                            accountsViewModel.setCurrencyCode(currencyCode)
                        }
                        navToLogin()

                    }
                )

                ModelButton(text = stringResource(id = R.string.backButton),
                    R.dimen.text_title_medium,
                    modifier = Modifier.width(360.dp),
                    true,
                    onClickButton = {
                        navToBack()

                    }
                )
            }
        }
    }
}

