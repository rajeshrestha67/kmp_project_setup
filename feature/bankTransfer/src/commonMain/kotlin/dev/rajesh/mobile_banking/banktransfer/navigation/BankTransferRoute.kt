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
    data class SameBankTransfer(
        val accountNumber: String? = null,
        val accountName: String? = null,
        val coopBranchJson: String? = null,
    ) : BankTransferRoutes

    @Serializable
    data class OtherBankTransfer(
        val accountNumber: String? = null,
        val accountName: String? = null,
        val bank: String? = null,
    ) : BankTransferRoutes

    @Serializable
    data object FavouriteAccounts : BankTransferRoutes

    @Serializable
    data object SelectCoopBranch : BankTransferRoutes

    @Serializable
    data class Confirmation(val json: String) : BankTransferRoutes


    @Serializable
    data object PaymentAuthentication : BankTransferRoutes

    @Serializable
    data class TransactionSuccessful(val json: String) : BankTransferRoutes


}


@Serializable
sealed interface InterBankTransferRoutes {

    @Serializable
    data object SelectBankRoute : InterBankTransferRoutes

    @Serializable
    data class ConfirmationRoute(val json: String) : InterBankTransferRoutes

    @Serializable
    data object PaymentAuthenticationRoute : InterBankTransferRoutes

    @Serializable
    data class TransactionSuccessfulRoute(val json: String) : InterBankTransferRoutes
}

