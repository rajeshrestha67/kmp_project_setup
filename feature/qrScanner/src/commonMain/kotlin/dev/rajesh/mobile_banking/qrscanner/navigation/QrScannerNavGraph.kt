package dev.rajesh.mobile_banking.qrscanner.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.rajesh.mobile_banking.qrscanner.presentation.ui.QrScannerScreen


fun NavGraphBuilder.QrScannerNavGraph(
    navController: NavController,
    onExitToDashboard:()-> Unit
){
    composable(QrScannerRoute.root){
        QrScannerScreen(
            onBackClicked = {
                navController.popBackStack()
            }
        )

    }
}