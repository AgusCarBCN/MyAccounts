package com.blogspot.agusticar.miscuentasv2.notification

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.createaccounts.view.CategoriesViewModel

@Composable
fun NotificationObserver(categoriesViewModel: CategoriesViewModel, notificationService: NotificationService) {
    categoriesViewModel.UpdateExpensePercentage()
    val expensePercentageMap by categoriesViewModel.expensePercentageFlow.collectAsState()

    Log.d("noti", expensePercentageMap.toString())

    expensePercentageMap.forEach { (category, percentage) ->
        if (percentage >= 0.8f) {
            Log.d("noti",percentage.toString())
            val message="${stringResource(id = R.string.expenseslimit)} ${stringResource(id = category.nameResource)}"
            val categoryControl= stringResource(id = R.string.categoryespendingcontrol)
            notificationService.showBasicNotification(
                categoryControl,
                message ,
                category.iconResource
            )
        }
    }
}
