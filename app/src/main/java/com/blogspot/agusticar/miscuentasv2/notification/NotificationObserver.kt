package com.blogspot.agusticar.miscuentasv2.notification

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.createaccounts.view.CategoriesViewModel
@Composable
fun NotificationObserver(categoriesViewModel: CategoriesViewModel, notificationService: NotificationService) {
    categoriesViewModel.UpdateExpensePercentage()
    val expensePercentageMap by categoriesViewModel.expensePercentageFlow.collectAsState()

    // Un conjunto para hacer un seguimiento de las categorías a las que ya se les ha enviado notificación
    val notifiedCategories = remember { mutableSetOf<Int>() }

    expensePercentageMap.forEach { (category, percentage) ->
        if (percentage >= 0.8f) {
            // Si la categoría ya ha recibido una notificación, no enviar otra
            if (!notifiedCategories.contains(category.id)) {
                Log.d("noti", percentage.toString())

                val message =
                    "${stringResource(id = R.string.expenseslimit)} ${stringResource(id = category.nameResource)}"
                val categoryControl = stringResource(id = R.string.categoryespendingcontrol)

                // Enviar la notificación
                notificationService.showBasicNotification(
                    categoryControl,
                    message,
                    category.iconResource
                )

                // Marcar la categoría como notificada
                notifiedCategories.add(category.id)
            }
        }
    }
}

