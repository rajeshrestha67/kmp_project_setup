package dev.rajesh.mobile_banking.qrscanner.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.rajesh.mobile_banking.banktransfer.navigation.BankTransferRoutes
import dev.rajesh.mobile_banking.loadWallet.domain.model.QrWallet
import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletDetail
import dev.rajesh.mobile_banking.loadWallet.presentation.navigation.LoadWalletRoutes
import dev.rajesh.mobile_banking.qrscanner.presentation.ui.QrScannerScreen
import dev.rajesh.mobile_banking.utils.serialization.AppJson


fun NavGraphBuilder.QrScannerNavGraph(
    navController: NavController,
    onExitToDashboard: () -> Unit
) {
    composable(QrScannerRoute.root) {
        QrScannerScreen(

            toInterBankTransfer = { accountDetails ->
                //navigate to inter bank transfer
                navController.navigate(BankTransferRoutes.OtherBankTransfer)
            },
            toSameBankTransfer = { accountDetails ->
                //navigate to sameBankTransfer
                navController.navigate(BankTransferRoutes.SameBankTransfer)
            },
            toWallet = { qrWalletData ->
                //navigate to wallet
                val qrWalletJson = AppJson.encodeToString(
                    QrWallet.serializer(),
                    qrWalletData
                )
                navController.navigate(
                    LoadWalletRoutes.LoadWalletDetail(
                        null,
                        qrWalletJson,
                    )
                )
            },
            onBackClicked = {
                navController.popBackStack()
            },
        )

    }
}