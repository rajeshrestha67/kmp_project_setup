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
    data object SameBankTransferRoute: QrScannerRoutes

    @Serializable
    data object InterBankTransferRoute: QrScannerRoutes

    @Serializable
    data object WalletTransferRoute: QrScannerRoutes

    @Serializable
    data object FonePayRoute: QrScannerRoutes
}
