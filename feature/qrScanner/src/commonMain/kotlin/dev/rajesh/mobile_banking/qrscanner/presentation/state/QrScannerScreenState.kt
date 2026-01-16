package dev.rajesh.mobile_banking.qrscanner.presentation.state

import dev.rajesh.mobile_banking.model.ErrorData

data class QrScannerScreenState(
    val qrScannedData: String? = null,
    val qrScanError: String? = null,
    val errorData: ErrorData? = null
)