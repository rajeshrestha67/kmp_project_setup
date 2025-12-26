package dev.rajesh.mobile_banking.loadwallet.presentation.navigation

import kotlinx.serialization.Serializable

object LoadWalletRoute {
    const val root = "load_wallet"
    const val LOAD_WALLET = "LOAD_WALLET"
}


@Serializable
sealed interface LoadWalletRoutes{
    @Serializable
    data object LoadWalletRoot: LoadWalletRoutes
}


