package dev.rajesh.mobile_banking.qrscanner.navigation

import kotlinx.serialization.Serializable

object QrScannerRoute {
    const val root = "qr_scanner"
}

@Serializable
sealed interface QrScannerRoutes {
    @Serializable
    data object QrScannerRoot : QrScannerRoutes

    @Serializable
    data class SameBankTransferRoute(val json: String) : QrScannerRoutes

    @Serializable
    data class InterBankTransferRoute(val json: String) : QrScannerRoutes

    @Serializable
    data class WalletTransferRoute(
        val walletId: String,
        val walletType: String,
        val name: String
    ) : QrScannerRoutes

    @Serializable
    data object FonePayRoute : QrScannerRoutes
}
