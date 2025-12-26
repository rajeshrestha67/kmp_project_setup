package dev.rajesh.mobile_banking.banktransfer.navigation

import kotlinx.serialization.Serializable

object BankTransferRoute {
    const val root = "bank_transfer"
}

@Serializable
sealed interface BankTransferRoutes {
    @Serializable
    data object BankTransferRoot : BankTransferRoutes

    @Serializable
    data object SameBankTransfer : BankTransferRoutes

    @Serializable
    data object OtherBankTransfer : BankTransferRoutes

    @Serializable
    data object FavouriteAccounts : BankTransferRoutes

    @Serializable
    data object SelectCoopBranch : BankTransferRoutes

    @Serializable
    data object Confirmation : BankTransferRoutes


}

