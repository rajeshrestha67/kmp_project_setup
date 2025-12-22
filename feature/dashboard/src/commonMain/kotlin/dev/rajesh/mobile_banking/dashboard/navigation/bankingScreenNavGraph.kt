package dev.rajesh.mobile_banking.dashboard.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.rajesh.mobile_banking.banking.BankingScreen
import dev.rajesh.mobile_banking.dashboard.presentation.route.DashboardRoute


fun NavGraphBuilder.bankingScreenNavGraph(
    navController: NavController
) {
    composable<DashboardRoute.BankingRoute> {
        BankingScreen()
    }
}