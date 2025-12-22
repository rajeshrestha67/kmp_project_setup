package dev.rajesh.mobile_banking.dashboard.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import dev.rajesh.mobile_banking.banktransfer.navigation.BankTransferRoute
import dev.rajesh.mobile_banking.banktransfer.navigation.bankTransferNavGraph
import dev.rajesh.mobile_banking.dashboard.presentation.route.DashboardRoute
import dev.rajesh.mobile_banking.home.HomeScreen

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

}


//package dev.rajesh.mobile_banking.dashboard.navigation
//
//import androidx.navigation.NavController
//import androidx.navigation.NavGraphBuilder
//import androidx.navigation.navigation
//import dev.rajesh.mobile_banking.banking.navigation.BankingRoutes
//import dev.rajesh.mobile_banking.banktransfer.navigation.bankTransferNavGraph
//import dev.rajesh.mobile_banking.dashboard.presentation.route.DashboardRoutes
//import dev.rajesh.mobile_banking.home.navigation.HomeRoutes
//import dev.rajesh.mobile_banking.menu.navigation.MenuRoutes
//import dev.rajesh.mobile_banking.transactionhistory.navigation.TransactionHistoryRoutes
//
//fun NavGraphBuilder.dashboardNavGraph(navController: NavController) {
//
//    // BottomBar Screens - Each with their own sub-navigation
//
//    //HomeScreen
//    navigation(
//        startDestination = HomeRoutes.ROOT,
//        route = DashboardRoutes.HOME_TAB
//    ) {
//        homeNavGraph(navController = navController)
//    }
//
//    //BankingScreen
//    navigation(
//        startDestination = BankingRoutes.ROOT,
//        route = DashboardRoutes.BANKING_TAB
//    ) {
//        bankTransferNavGraph(navController = navController)
//    }
//
//    //Transaction History Screen
//    navigation(
//        startDestination = TransactionHistoryRoutes.ROOT,
//        route = DashboardRoutes.TRANSACTION_HISTORY_TAB
//    ) {
//        transactionHistoryNavGraph(navController = navController)
//    }
//
//    //MenuScreen
//    navigation(
//        startDestination = MenuRoutes.ROOT,
//        route = DashboardRoutes.MENU_TAB
//    ) {
//        menuScreennavGraph(navController = navController)
//    }
//
//
//}