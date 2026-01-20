package dev.rajesh.mobile_banking.qrscanner.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.BankDetail
import dev.rajesh.mobile_banking.banktransfer.navigation.BankTransferRoutes
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model.CoopBranchDetail
import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletDetail
import dev.rajesh.mobile_banking.loadWallet.presentation.navigation.LoadWalletRoutes
import dev.rajesh.mobile_banking.qrscanner.domain.model.AccountDetails
import dev.rajesh.mobile_banking.qrscanner.presentation.ui.QrScannerScreen
import dev.rajesh.mobile_banking.utils.serialization.AppJson


fun NavGraphBuilder.QrScannerNavGraph(
    navController: NavController,
    onExitToDashboard: () -> Unit
) {
    composable(QrScannerRoute.root) {
        QrScannerScreen(
            toInterBankTransfer = { accountDetails, bank ->
                var bankJson: String? = null
                bank?.let {
                    bankJson = AppJson.encodeToString(
                        BankDetail.serializer(),
                        it
                    )
                }

                navController.navigate(
                    BankTransferRoutes.OtherBankTransfer(
                        accountNumber = accountDetails.accountNumber,
                        accountName = accountDetails.accountName,
                        bank = bankJson
                    )
                )
            },
            toSameBankTransfer = { accountDetails, coopBranch ->
                var coopBranchJson: String? = null
                coopBranch?.let {
                    coopBranchJson = AppJson.encodeToString(
                        CoopBranchDetail.serializer(),
                        it
                    )
                }
                navController.navigate(
                    BankTransferRoutes.SameBankTransfer(
                        accountNumber = accountDetails.accountNumber,
                        accountName = accountDetails.accountName,
                        coopBranchJson = coopBranchJson
                    )
                )
            },
            toWallet = { walletDetail, walletUserId, walletHolderName ->
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