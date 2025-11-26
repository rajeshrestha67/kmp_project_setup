package dev.rajesh.mobile_banking.dashboard.presentation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.rajesh.mobile_banking.banking.BankingScreen
import dev.rajesh.mobile_banking.dashboard.presentation.route.DashboardRoute


fun NavGraphBuilder.bankingScreenBuilder(
    navController: NavController
) {
    composable<DashboardRoute.BankingRoute> {
        BankingScreen()
    }
}