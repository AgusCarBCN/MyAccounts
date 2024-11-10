package com.blogspot.agusticar.miscuentasv2.notification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.SnackBarController
import com.blogspot.agusticar.miscuentasv2.SnackBarEvent
import com.blogspot.agusticar.miscuentasv2.components.AccountCardWithCheckbox
import com.blogspot.agusticar.miscuentasv2.components.HeadSetting
import com.blogspot.agusticar.miscuentasv2.components.ModelDialogWithTextField
import com.blogspot.agusticar.miscuentasv2.createaccounts.view.AccountsViewModel
import com.blogspot.agusticar.miscuentasv2.createaccounts.view.CategoriesViewModel
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Account
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Category
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.CategoryType
import com.blogspot.agusticar.miscuentasv2.search.SearchViewModel
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette
import com.blogspot.agusticar.miscuentasv2.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun EntryAccountList(
    accountsViewModel: AccountsViewModel,
    searchViewModel: SearchViewModel
) {
    // Observa la lista de categorías desde el ViewModel
    val listOfAccounts by accountsViewModel.listOfAccounts.observeAsState(emptyList())
    val currencyCode by accountsViewModel.currencyCodeShowed.observeAsState("USD")
    LaunchedEffect(Unit) {
        accountsViewModel.getAllAccounts()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeadSetting(title = stringResource(id = R.string.selectaccounts),
            size = 22)
        // Asegúrate de que la LazyColumn ocupa solo el espacio necesario
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // Permite que la columna ocupe el espacio disponible
                .padding(bottom = 16.dp), // Espacio en la parte inferior
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(listOfAccounts) { account ->
                AccountCardWithCheckbox(
                    account,
                    currencyCode,
                    accountsViewModel,
                    searchViewModel,
                    onCheckBoxChange = { checked ->
                        accountsViewModel.updateCheckedAccount(
                            account.id,
                            checked
                        )
                        if (!account.isChecked) {
                            accountsViewModel.onEnableDialogChange(true)
                        }
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))  // Espacio entre cada card (separación)
            }
        }
    }
}

