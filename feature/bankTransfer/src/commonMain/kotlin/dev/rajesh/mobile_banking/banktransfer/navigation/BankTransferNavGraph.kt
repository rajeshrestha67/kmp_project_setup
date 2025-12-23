package dev.rajesh.mobile_banking.banktransfer.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.rajesh.mobile_banking.banktransfer.presentation.ui.BankTransferScreen
import dev.rajesh.mobile_banking.banktransfer.presentation.ui.OtherBankTransferScreen
import dev.rajesh.mobile_banking.banktransfer.presentation.ui.FavouriteAccountsScreen
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.ui.SameBankTransferScreen

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
        SameBankTransferScreen(onBackClicked = {
            navController.popBackStack()
        })
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

}

