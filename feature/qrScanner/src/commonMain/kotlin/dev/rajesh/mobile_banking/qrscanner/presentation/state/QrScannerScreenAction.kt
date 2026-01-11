package dev.rajesh.mobile_banking.qrscanner.presentation.state

sealed interface QrScannerScreenAction {
    data class OnQrScanned(val scannedData: String): QrScannerScreenAction

    data class OnQrScanError(val errorMsg: String): QrScannerScreenAction
}