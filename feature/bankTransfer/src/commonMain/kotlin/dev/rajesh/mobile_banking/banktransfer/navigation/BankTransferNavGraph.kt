package dev.rajesh.mobile_banking.banktransfer.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.rajesh.mobile_banking.banktransfer.presentation.ui.BankTransferScreen
import dev.rajesh.mobile_banking.banktransfer.presentation.ui.OtherBankTransferScreen
import dev.rajesh.mobile_banking.banktransfer.presentation.ui.FavouriteAccountsScreen
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model.CoopBranchDetail
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.ui.SameBankTransferScreen
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.ui.SelectBranchScreen
import dev.rajesh.mobile_banking.logger.AppLogger
import dev.rajesh.mobile_banking.utils.serialization.AppJson

fun NavGraphBuilder.bankTransferNavGraph(navController: NavController) {
    composable(BankTransferRoute.root) {
        BankTransferScreen(
            onOptionSelected = { option ->
                when (option.id) {
                    1 -> navController.navigate(BankTransferRoute.sameBank)
                    2 -> navController.navigate(BankTransferRoute.otherBank)
                    3 -> navController.navigate(
                        BankTransferRoute.favouriteAccounts
                    )
                }
            },
            onBackClicked = {
                navController.popBackStack()
            }
        )
    }

    composable(BankTransferRoute.sameBank) {
        SameBankTransferScreen(
            onBackClicked = {
                navController.popBackStack()
            },
            onSelectCoopBranchClicked = {
                navController.navigate(BankTransferRoute.coopBranch)
            },
            navController = navController
        )
    }

    composable(BankTransferRoute.otherBank) {
        OtherBankTransferScreen(onBackClicked = {
            navController.popBackStack()
        })
    }

    composable(BankTransferRoute.favouriteAccounts) {
        FavouriteAccountsScreen(onBackClicked = {
            navController.popBackStack()
        })
    }

    composable(BankTransferRoute.coopBranch) {
        SelectBranchScreen(
            onBackClicked = {
                navController.popBackStack()
            },
            onCoopBranchSelected = { coopBranch ->
                AppLogger.i("BankTransferNavGraph", "selected branch: ${coopBranch.name}")
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

