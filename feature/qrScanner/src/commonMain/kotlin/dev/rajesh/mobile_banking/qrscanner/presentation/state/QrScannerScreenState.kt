package dev.rajesh.mobile_banking.qrscanner.presentation.state

data class QrScannerScreenState(
    val qrScannedData: String? = null,
    val qrScanError: String? = null,
)