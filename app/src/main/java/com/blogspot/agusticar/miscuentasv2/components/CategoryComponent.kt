package com.blogspot.agusticar.miscuentasv2.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Category
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.CategoryType
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette
@Composable
fun CategoryEntries(category: Category, modifier: Modifier, onClickItem: () -> Unit) {
    // Determinamos el color inicial y objetivo sin animación
    val initColor = if (category.type == CategoryType.INCOME) {
        LocalCustomColorsPalette.current.iconIncomeInit
    } else {
        LocalCustomColorsPalette.current.iconExpenseInit
    }


    // Asegúrate de que la columna ocupe todo el espacio de la tarjeta
    Column(
        modifier
            .size(120.dp)
            .clip(shape = CircleShape)
            .clickable { onClickItem() }, // Clickable para la acción
        horizontalAlignment = Alignment.CenterHorizontally, // Alinea todo al centro horizontalmente
        verticalArrangement = Arrangement.Center // Alinea todo al centro verticalmente
    ) {
        // Usamos Icon sin animación
        Icon(
            painter = painterResource(id = category.iconResource),
            contentDescription = null,
            modifier = Modifier.size(60.dp),
            tint = initColor // Aplicamos el color inicial directamente
        )
        Text(
            text = stringResource(id = category.nameResource), // Nombre de la categoría
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = LocalCustomColorsPalette.current.textColor, // Color del texto
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}
