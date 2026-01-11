package dev.rajesh.mobile_banking.home.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface HomeRoute {
    @Serializable
    data object Root: HomeRoute
    @Serializable
    data object Home: HomeRoute
}

object HomeRoutes {
    const val ROOT = "HOME"

    // Feature entry points from home
    const val BANK_TRANSFER = "home/bank_transfer"
    const val LOAD_WALLET = "home/load_wallet"
    const val QR_SCANNER = "home/qrScanner"

}