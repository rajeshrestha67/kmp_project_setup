package dev.rajesh.mobile_banking.dashboard.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import dev.rajesh.mobile_banking.banktransfer.navigation.BankTransferRoute
import dev.rajesh.mobile_banking.banktransfer.navigation.bankTransferNavGraph
import dev.rajesh.mobile_banking.dashboard.presentation.route.DashboardRoute
import dev.rajesh.mobile_banking.home.HomeScreen
import dev.rajesh.mobile_banking.loadwallet.presentation.navigation.LoadWalletRoute
import dev.rajesh.mobile_banking.loadwallet.presentation.navigation.loadWalletNavGraph

fun NavGraphBuilder.homeScreenNavGraph(navController: NavController) {

    composable<DashboardRoute.HomeRoute> {
        HomeScreen(navController = navController)
    }

    //bankTransfer Feature (nested navigation)
    navigation(
        startDestination = BankTransferRoute.root,
        route = "home/bank_transfer" // Use a unique route path
    ) {
        bankTransferNavGraph(navController)
    }

    //LoadWallet navGraph
    navigation(
        startDestination = LoadWalletRoute.ROOT,
        route = "home/load_wallet"
    ) {
        loadWalletNavGraph(navController)
    }
}