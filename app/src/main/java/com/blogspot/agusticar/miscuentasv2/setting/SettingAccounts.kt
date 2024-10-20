package com.blogspot.agusticar.miscuentasv2.setting

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.SnackBarController
import com.blogspot.agusticar.miscuentasv2.SnackBarEvent
import com.blogspot.agusticar.miscuentasv2.components.AccountCard
import com.blogspot.agusticar.miscuentasv2.components.BoardType
import com.blogspot.agusticar.miscuentasv2.components.CurrencySelector
import com.blogspot.agusticar.miscuentasv2.components.HeadSetting
import com.blogspot.agusticar.miscuentasv2.components.IconAnimated
import com.blogspot.agusticar.miscuentasv2.components.ModelButton
import com.blogspot.agusticar.miscuentasv2.components.TextFieldComponent
import com.blogspot.agusticar.miscuentasv2.components.message
import com.blogspot.agusticar.miscuentasv2.createaccounts.view.AccountsViewModel
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Account
import com.blogspot.agusticar.miscuentasv2.main.model.IconOptions
import com.blogspot.agusticar.miscuentasv2.main.view.MainViewModel
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette
import com.blogspot.agusticar.miscuentasv2.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun AccountList(
    mainViewModel: MainViewModel,
    accountsViewModel: AccountsViewModel,
    option: Boolean
) {
    val currencyCode by accountsViewModel.currencyCode.observeAsState("USD")
    accountsViewModel.getAllAccounts()
    // Observa el estado de la lista de cuentas
    val accounts by accountsViewModel.listOfAccounts.observeAsState(null)   // Observa el estado de la lista de cuentas


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalCustomColorsPalette.current.backgroundPrimary),
        verticalArrangement = Arrangement.Center,  // Centra los elementos verticalmente
        horizontalAlignment = Alignment.CenterHorizontally  // Centra los elementos horizontalmente
    ) {
        if (accounts.isNullOrEmpty()) {
            Text(
                text = stringResource(id = R.string.noaccounts),
                color = LocalCustomColorsPalette.current.textColor,
                fontSize = 18.sp
            )
        } else {

            HeadSetting(
                title = stringResource(id = R.string.youraccounts),
                size = 22
            )
            // Mostrar las cuentas si están disponibles
            LazyColumn(
                modifier = Modifier.fillMaxSize(),

                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                items(accounts!!) { account -> // Solo utiliza accounts
                    AccountCard(
                        Utils.numberFormat(account.balance, currencyCode),
                        account.name,
                        if (option) R.string.deleteaccount else R.string.modify,
                        onClickCard = {
                            mainViewModel.selectScreen(if (option) IconOptions.DELETE_ACCOUNT else IconOptions.EDIT_ACCOUNTS)
                            accountsViewModel.onAccountSelected(account)
                            if (option) {
                                mainViewModel.showDeleteAccountDialog(true)
                            }
                        }
                    )  // Crea un card para cada cuenta en la lista
                    Spacer(modifier = Modifier.height(20.dp))  // Espacio entre cada card (separación)
                }
            }
        }
    }
}

@Composable
fun ModifyAccountsComponent(
    mainViewModel: MainViewModel,
    accountsViewModel: AccountsViewModel,

    ) {
    val scope = rememberCoroutineScope()

    val nameButtonChange by accountsViewModel.isEnableChangeNameButton.observeAsState(false)
    val balanceButtonChange by accountsViewModel.isEnableChangeBalanceButton.observeAsState(false)
    val accountSelected by accountsViewModel.accountSelected.observeAsState()
    val accountNameInit = accountSelected?.name ?: ""
    val accountBalanceInit = accountSelected?.balance ?: 0.0
    val accountId=accountSelected?.id?:0

    val name by accountsViewModel.newName.observeAsState(accountNameInit)
    val balance by accountsViewModel.newAmount.observeAsState(accountBalanceInit.toString())


    //SnackBarMessage
    val nameChanged = message(resource = R.string.namechanged)
    val balanceChanged = message(resource = R.string.amountchanged)
    Column(

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        HeadSetting(title = stringResource(id = R.string.edit_account), 20)

        IconAnimated(
            iconResource = R.drawable.configaccountoption, sizeIcon = 120,
            LocalCustomColorsPalette.current.imageTutorialInit,
            LocalCustomColorsPalette.current.imageTutorialTarget
        )


        TextFieldComponent(
            modifier = Modifier.width(360.dp),
            stringResource(id = R.string.amountName),
            name,
            onTextChange = { accountsViewModel.onTextNameChanged(it) },
            BoardType.TEXT,
            false
        )
        ModelButton(text = stringResource(id = R.string.change),
            R.dimen.text_title_medium,
            modifier = Modifier.width(360.dp),
            nameButtonChange,
            onClickButton = {
                try {
                    scope.launch(Dispatchers.IO) {
                        accountsViewModel.upDateAccountName(accountId,name)
                        SnackBarController.sendEvent(event = SnackBarEvent(nameChanged))
                    }
                } catch (e: Exception) {
                    Log.d("Cuenta", "Error: ${e.message}")
                    println("Error al cargar ${e.message}")
                }
            }
        )


        TextFieldComponent(
            modifier = Modifier.width(360.dp),
            stringResource(id = R.string.enteramount),
           balance,
            onTextChange = {
                accountsViewModel.onTextBalanceChanged(it)
            },
            BoardType.DECIMAL,
            false
        )



        ModelButton(text = stringResource(id = R.string.change),
            R.dimen.text_title_medium,
            modifier = Modifier.width(360.dp),
            balanceButtonChange,
            onClickButton = {
                try {
                    scope.launch(Dispatchers.IO) {
                        val newBalance=balance.toDoubleOrNull()?:0.0
                        accountsViewModel.upDateAccountBalance(accountId,newBalance)
                        SnackBarController.sendEvent(event = SnackBarEvent(balanceChanged))

                    }
                } catch (e: Exception) {
                    Log.d("Cuenta", "Error: ${e.message}")
                    println("Error al cargar ${e.message}")
                }

            }
        )

        ModelButton(text = stringResource(id = R.string.backButton),
            R.dimen.text_title_medium,
            modifier = Modifier.width(360.dp),
            true,
            onClickButton = {
                mainViewModel.selectScreen(IconOptions.HOME)



            }
        )

    }
    }


