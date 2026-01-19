package dev.rajesh.mobile_banking.loadWallet.presentation.navigation

import kotlinx.serialization.Serializable

object LoadWalletRoute {
    const val root = "load_wallet"
}


@Serializable
sealed interface LoadWalletRoutes {
    @Serializable
    data object LoadWalletRoot : LoadWalletRoutes

    @Serializable
    data class LoadWalletDetail(
        val json: String? = null,
        val walletUserId: String? = null,
        val walletHolderName: String? = null,
    ) : LoadWalletRoutes

    @Serializable
    data class ConfirmationRoute(val json: String) : LoadWalletRoutes

    @Serializable
    data object PaymentAuthenticationRoute : LoadWalletRoutes

    @Serializable
    data class TransactionSuccessfulRoute(val json: String) : LoadWalletRoutes
}


