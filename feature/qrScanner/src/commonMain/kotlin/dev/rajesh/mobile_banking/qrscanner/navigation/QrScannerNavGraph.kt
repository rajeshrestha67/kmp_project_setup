package dev.rajesh.mobile_banking.qrscanner.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.rajesh.mobile_banking.banktransfer.navigation.BankTransferRoutes
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
            toWallet = { walletDetail, walletUserId, walletHolderName ->
                //navigate to wallet
                val json = AppJson.encodeToString(
                    WalletDetail.serializer(),
                    walletDetail
                )

                navController.navigate(
                    LoadWalletRoutes.LoadWalletDetail(
                        json,
                        walletUserId,
                        walletHolderName
                    )
                )
            },
            onBackClicked = {
                navController.popBackStack()
            },
        )

    }
}