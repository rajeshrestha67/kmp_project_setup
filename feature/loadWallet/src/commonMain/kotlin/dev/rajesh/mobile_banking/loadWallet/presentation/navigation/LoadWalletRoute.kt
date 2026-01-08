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
    data class LoadWalletDetail(val json: String) : LoadWalletRoutes
}


