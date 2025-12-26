package dev.rajesh.mobile_banking.banktransfer.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.rajesh.mobile_banking.banktransfer.presentation.ui.BankTransferScreen
import dev.rajesh.mobile_banking.banktransfer.presentation.ui.FavouriteAccountsScreen
import dev.rajesh.mobile_banking.banktransfer.presentation.ui.OtherBankTransferScreen
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model.CoopBranchDetail
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.ui.SameBankTransferScreen
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.ui.SelectBranchScreen
import dev.rajesh.mobile_banking.confirmation.model.ConfirmationData
import dev.rajesh.mobile_banking.utils.serialization.AppJson


fun NavGraphBuilder.bankTransferNavGraph(navController: NavController) {
    composable(BankTransferRoute.root) {
        BankTransferScreen(
            onOptionSelected = { option ->
                when (option.id) {
                    1 -> navController.navigate(BankTransferRoutes.SameBankTransfer)
                    2 -> navController.navigate(BankTransferRoutes.OtherBankTransfer)
                    3 -> navController.navigate(
                        BankTransferRoutes.FavouriteAccounts
                    )
                }
            },
            onBackClicked = {
                navController.popBackStack()
            }
        )
    }

    composable<BankTransferRoutes.SameBankTransfer> {
        SameBankTransferScreen(
            onBackClicked = {
                navController.popBackStack()
            },
            onSelectCoopBranchClicked = {
                navController.navigate(BankTransferRoutes.SelectCoopBranch)
            },
            showConfirmation = { confirmationData ->
                val confirmationDataJson =
                    AppJson.encodeToString(
                        ConfirmationData.serializer(), confirmationData
                    )

                //navController.navigate(BankTransferRoute.confirmationRoute(confirmationDataJson))
                navController.navigate(confirmationData)
            },
            navController = navController
        )
    }

    composable<BankTransferRoutes.OtherBankTransfer> {
        OtherBankTransferScreen(onBackClicked = {
            navController.popBackStack()
        })
    }

    composable<BankTransferRoutes.FavouriteAccounts> {
        FavouriteAccountsScreen(onBackClicked = {
            navController.popBackStack()
        })
    }

    composable<BankTransferRoutes.SelectCoopBranch> {
        SelectBranchScreen(
            onBackClicked = {
                navController.popBackStack()
            },
            onCoopBranchSelected = { coopBranch ->
                val branchJson = AppJson.encodeToString(CoopBranchDetail.serializer(), coopBranch)
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set(
                        BankTransferResult.SELECTED_COOP_BRANCH,
                        branchJson
                    )
                navController.popBackStack()
            }
        )
    }

}

