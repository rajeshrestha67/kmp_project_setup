package dev.rajesh.mobile_banking.dashboard.presentation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.rajesh.mobile_banking.dashboard.presentation.route.DashboardRoute
import dev.rajesh.mobile_banking.transactionhistory.TransactionHistoryScreen


fun NavGraphBuilder.transactionHistoryScreenBuilder(
    navController: NavController
) {
    composable<DashboardRoute.TransactionHistoryRoute> {
        TransactionHistoryScreen()
    }
}