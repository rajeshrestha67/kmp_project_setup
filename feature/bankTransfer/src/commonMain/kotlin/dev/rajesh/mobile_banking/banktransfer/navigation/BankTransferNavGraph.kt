package dev.rajesh.mobile_banking.banktransfer.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.rajesh.mobile_banking.banktransfer.BankTransferScreen
import dev.rajesh.mobile_banking.banktransfer.ExternalBankTransferScreen
import dev.rajesh.mobile_banking.banktransfer.FavouriteAccountsScreen
import dev.rajesh.mobile_banking.banktransfer.InternalBankTransferScreen

fun NavGraphBuilder.bankTransferNavGraph(navController: NavController) {
    composable(BankTransferRoute.root) {
        BankTransferScreen(
            onOptionSelected = { option ->
                when (option) {
                    BankTransferOption.SAME_BANK -> navController.navigate(BankTransferRoute.sameBank)
                    BankTransferOption.OTHER_BANK -> navController.navigate(BankTransferRoute.otherBank)
                    BankTransferOption.FAVOURITE_ACCOUNTS -> navController.navigate(
                        BankTransferRoute.favouriteAccounts
                    )
                }
            }
        )
    }

    composable(BankTransferRoute.sameBank) {
        InternalBankTransferScreen()
    }

    composable(BankTransferRoute.otherBank) {
        ExternalBankTransferScreen()
    }

    composable(BankTransferRoute.favouriteAccounts) {
        FavouriteAccountsScreen()
    }

}

