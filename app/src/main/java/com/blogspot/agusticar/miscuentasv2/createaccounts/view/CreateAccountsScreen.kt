package com.blogspot.agusticar.miscuentasv2.createaccounts.view


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
import com.blogspot.agusticar.miscuentasv2.SnackBarController
import com.blogspot.agusticar.miscuentasv2.SnackBarEvent
import com.blogspot.agusticar.miscuentasv2.components.BoardType
import com.blogspot.agusticar.miscuentasv2.components.CurrencySelector
import com.blogspot.agusticar.miscuentasv2.components.IconAnimated
import com.blogspot.agusticar.miscuentasv2.components.ModelButton
import com.blogspot.agusticar.miscuentasv2.components.TextFieldComponent
import com.blogspot.agusticar.miscuentasv2.components.message
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Account
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


//Mapa de divisas y simbolos


@Composable

fun CreateAccountsComponent(
    accountsViewModel: AccountsViewModel,
    categoriesViewModel: CategoriesViewModel,
    navToLogin: () -> Unit,
    navToBack: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalCustomColorsPalette.current.backgroundPrimary) // Reemplaza con tu color de fondo
    ) {

        val scope = rememberCoroutineScope()
        val currencyShowedCode by accountsViewModel.currencyCodeShowed.observeAsState("EUR")
        val isCurrencyExpanded by accountsViewModel.isCurrencyExpanded.observeAsState(false)
        val isEnableButton by accountsViewModel.isEnableButton.observeAsState(false)
        val accountName by accountsViewModel.name.observeAsState("")
        val accountBalance by accountsViewModel.amount.observeAsState("")
        val enableCurrencySelector by accountsViewModel.enableCurrencySelector.observeAsState(true)

        val newAccountCreated = message(resource = R.string.newaccountcreated)
        val errorAccountCreated = message(resource = R.string.erroraccountcreated)
        val errorWritingDataStore=message(resource = R.string.errorwritingdatastore)
        Column(

            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            if (!isCurrencyExpanded) {
                Text(
                    modifier = Modifier
                        .padding(50.dp),
                    text = if (!isCurrencyExpanded) stringResource(id = R.string.createAccount) else "",
                    fontSize = with(LocalDensity.current) { dimensionResource(id = R.dimen.text_title_medium).toSp() },
                    fontWeight = FontWeight.Bold, // Estilo de texto en negrita
                    textAlign = TextAlign.Center,
                    color = LocalCustomColorsPalette.current.textColor
                )
                if (!enableCurrencySelector) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(60.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        IconAnimated(
                            iconResource = R.drawable.configaccountoption, sizeIcon = 120,
                            LocalCustomColorsPalette.current.imageTutorialInit,
                            LocalCustomColorsPalette.current.imageTutorialTarget
                        )
                    }
                }

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
                    onTextChange = { accountsViewModel.onTextFieldsChanged(it, accountBalance) },
                    BoardType.TEXT,
                    false
                )
                TextFieldComponent(
                    modifier = Modifier.width(360.dp),
                    stringResource(id = R.string.enteramount),
                    accountBalance,
                    onTextChange = {
                        accountsViewModel.onTextFieldsChanged(accountName, it)
                    },
                    BoardType.DECIMAL,
                    false
                )
                ModelButton(text = stringResource(id = R.string.addAccount),
                    R.dimen.text_title_medium,
                    modifier = Modifier.width(360.dp),
                    isEnableButton,
                    onClickButton = {
                        scope.launch(Dispatchers.IO) {
                            try {
                                val amountDecimal = accountBalance.toDoubleOrNull() ?: 0.0
                                accountsViewModel.addAccount(
                                    Account(
                                        name = accountName,
                                        balance = amountDecimal
                                    )
                                )
                                withContext(Dispatchers.Main) {
                                    SnackBarController.sendEvent(
                                        event = SnackBarEvent(
                                            newAccountCreated
                                        )
                                    )
                                }

                            } catch (e: Exception) {
                                withContext(Dispatchers.Main) {
                                    SnackBarController.sendEvent(
                                        event = SnackBarEvent(
                                            errorAccountCreated
                                        )
                                    )
                                }
                            }
                        }
                    }
                )
            }
            if (enableCurrencySelector) {
                CurrencySelector(accountsViewModel)
            }
            if (!isCurrencyExpanded) {
                if (enableCurrencySelector) {
                    ModelButton(text = stringResource(id = R.string.confirmButton),
                        R.dimen.text_title_medium,
                        modifier = Modifier.width(360.dp),
                        true,
                        onClickButton = {
                            scope.launch(Dispatchers.IO) {
                                try {
                                    accountsViewModel.setCurrencyCode(currencyShowedCode)
                                    categoriesViewModel.populateCategories()
                                }catch (e: Exception) {
                                    withContext(Dispatchers.Main) {
                                        SnackBarController.sendEvent(
                                            event = SnackBarEvent(
                                                errorWritingDataStore
                                            )
                                        )
                                    }
                                }
                            }
                            navToLogin()
                        }
                    )
                }
                ModelButton(text = stringResource(id = R.string.backButton),
                    R.dimen.text_title_medium,
                    modifier = Modifier.width(360.dp),
                    true,
                    onClickButton = {
                        accountsViewModel.resetFields()
                        navToBack()

                    }
                )
            }
        }
    }
}

