package dev.rajesh.mobile_banking.dashboard.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import dev.rajesh.mobile_banking.banktransfer.navigation.BankTransferRoute
import dev.rajesh.mobile_banking.banktransfer.navigation.bankTransferNavGraph
import dev.rajesh.mobile_banking.dashboard.presentation.route.DashboardRoute
import dev.rajesh.mobile_banking.home.HomeScreen
import dev.rajesh.mobile_banking.home.navigation.HomeRoutes
import dev.rajesh.mobile_banking.loadWallet.presentation.navigation.LoadWalletRoute
import dev.rajesh.mobile_banking.loadWallet.presentation.navigation.loadWalletNavGraph
import dev.rajesh.mobile_banking.qrscanner.navigation.QrScannerNavGraph
import dev.rajesh.mobile_banking.qrscanner.navigation.QrScannerRoute
import dev.rajesh.mobile_banking.qrscanner.navigation.QrScannerRoutes

fun NavGraphBuilder.homeScreenNavGraph(
    navController: NavController,
    onExitToDashboard: () -> Unit
) {

    composable<DashboardRoute.HomeRoute> {
        HomeScreen(navController = navController)
    }

    //bankTransfer Feature (nested navigation)
    navigation(
        startDestination = BankTransferRoute.root,
        route = HomeRoutes.BANK_TRANSFER
    ) {
        bankTransferNavGraph(
            navController,
            onExitToDashboard = onExitToDashboard
        )
    }

    //LoadWallet navGraph
    navigation(
        startDestination = LoadWalletRoute.root,
        route = HomeRoutes.LOAD_WALLET
    ) {
        loadWalletNavGraph(
            navController,
            onExitToDashboard = onExitToDashboard
        )
    }

    //Qr Scanner
    navigation(
        startDestination = QrScannerRoute.root,
        route = HomeRoutes.QR_SCANNER
    ) {
        QrScannerNavGraph(
            navController,
            onExitToDashboard = onExitToDashboard
        )
    }
}