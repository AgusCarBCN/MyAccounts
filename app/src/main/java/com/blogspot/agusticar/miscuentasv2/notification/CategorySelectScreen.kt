package com.blogspot.agusticar.miscuentasv2.notification



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.components.HeadSetting
import com.blogspot.agusticar.miscuentasv2.components.ModelButton
import com.blogspot.agusticar.miscuentasv2.createaccounts.view.CategoriesViewModel
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.CategoryType
import com.blogspot.agusticar.miscuentasv2.main.model.IconOptions
import com.blogspot.agusticar.miscuentasv2.main.view.MainViewModel
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette


@Composable
fun EntryCategoryList(
    mainViewModel: MainViewModel,
    categoriesViewModel: CategoriesViewModel
) {
    // Observa la lista de categorías desde el ViewModel
    val listOfCategories by categoriesViewModel.listOfCategories.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        categoriesViewModel.getAllCategoriesByType(CategoryType.EXPENSE)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
       // Asegúrate de que la LazyColumn ocupa solo el espacio necesario
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // Permite que la columna ocupe el espacio disponible
                .padding(bottom = 16.dp) // Espacio en la parte inferior
        ) {
            items(listOfCategories) { category ->
                ItemCategoryCheck(
                    categoryName = category.nameResource,
                    categoryIcon = category.iconResource,
                    checked = category.isChecked,
                    onCheckBoxChange = { checked ->
                       categoriesViewModel.updateCategoryCheckedState(category.id, checked)

                    }
                )
            }
        }


    }
}


@Composable
fun ItemCategoryCheck(
    categoryName: Int?,
    categoryIcon: Int?,
    checked: Boolean,
    onCheckBoxChange: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icono de la categoría
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .weight(0.1f), // Espacio proporcional para el ícono
                painter = painterResource(id = categoryIcon ?: R.drawable.ic_categorycontrol), // Reemplaza con un ícono predeterminado
                contentDescription = "Category icon",
                tint = LocalCustomColorsPalette.current.textColor
            )

            Spacer(modifier = Modifier.width(8.dp)) // Espacio entre el ícono y el texto

            // Nombre de la categoría
            Text(
                text = categoryName?.let { stringResource(id = it) } ?: "Unknown Category",
                modifier = Modifier
                    .weight(0.7f)
                    .padding(horizontal = 8.dp),
                color = LocalCustomColorsPalette.current.textColor,
                textAlign = TextAlign.Start,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            // Checkbox
            Checkbox(
                modifier = Modifier.weight(0.2f), // Ajuste proporcional para el checkbox
                checked = checked,
                onCheckedChange = onCheckBoxChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = LocalCustomColorsPalette.current.backgroundPrimary,
                    uncheckedColor = LocalCustomColorsPalette.current.textColor,
                    checkmarkColor = LocalCustomColorsPalette.current.incomeColor
                )
            )
        }
    }
}



