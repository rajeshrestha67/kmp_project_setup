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
    // Home screen is already Dashboard.HOME = "home"

    // Feature entry points from home
    const val BANK_TRANSFER = "home/bank_transfer"
    const val LOAD_WALLET = "home/load_wallet"
    const val PAY_BILLS = "home/pay_bills"

    // Quick services
    const val QR_SCAN = "home/qr_scan"
    const val LOCATE_ATM = "home/locate_atm"

    // Helper to build feature routes
    fun getBankTransferRoute(internalRoute: String): String {
        return "home/bank_transfer/$internalRoute"
    }

    fun getLoadWalletRoute(internalRoute: String): String {
        return "home/load_wallet/$internalRoute"
    }
}