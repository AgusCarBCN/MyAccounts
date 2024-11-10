package com.blogspot.agusticar.miscuentasv2.notification


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.createaccounts.view.AccountsViewModel
import com.blogspot.agusticar.miscuentasv2.createaccounts.view.CategoriesViewModel
import com.blogspot.agusticar.miscuentasv2.utils.Utils


@Composable
fun NotificationObserver(
    categoriesViewModel: CategoriesViewModel,
    accountsViewModel: AccountsViewModel,
    notificationService: NotificationService
) {
    // Update expense percentage
    categoriesViewModel.UpdateExpensePercentage()
    val expensePercentageMap by categoriesViewModel.expensePercentageFlow.collectAsState()
    val codeCurrency by accountsViewModel.currencyCodeSelected.observeAsState("USD")

    // Track categories that have already received a notification
    val notifiedCategories = remember { mutableSetOf<Int>() }

    expensePercentageMap.forEach { (category, percentage) ->
        // State to track the expenses by category and whether it has been loaded
        var expensesByCategory by remember { mutableStateOf<Double?>(null) }

        // Load expenses for each category when the category ID changes
        LaunchedEffect(category.id) {
            expensesByCategory = categoriesViewModel.sumOfExpensesByCategory(category.id,
                category.fromDate,
                category.toDate) ?: 0.0

        }

        // Only proceed with notifications if expensesByCategory has been loaded
        expensesByCategory?.let { expenses ->
            // Notification logic remains outside LaunchedEffect to allow stringResource usage

                if (!notifiedCategories.contains(category.id)) {
                    val message = when {
                        percentage > 0.8f && percentage < 1.0f -> {
                            "${stringResource(id = R.string.expenseslimit80)}\n" +
                                    "${stringResource(id = R.string.expensebycategory)} ${Utils.numberFormat(expenses, codeCurrency)}."
                        }
                        percentage >= 1.0f -> {
                            "${stringResource(id = R.string.expenseslimit)}\n" +
                                    "${stringResource(id = R.string.expensebycategory)} ${Utils.numberFormat(expenses, codeCurrency)}."
                        }
                        else -> {
                            "${stringResource(id = R.string.expenselimitOk)} \n" +
                                    "${stringResource(id = R.string.expensebycategory)} ${Utils.numberFormat(expenses, codeCurrency)}."
                        }
                    }

                    val categoryControl = stringResource(id = R.string.categoryespendingcontrol) +" "+
                            stringResource(id = category.nameResource)


                    // Send the notification
                    notificationService.showBasicNotification(
                        title = categoryControl,
                        message = message,
                         category.iconResource
                    )

                    // Mark category as notified
                    notifiedCategories.add(category.id)
                }
            }
        }
    }

