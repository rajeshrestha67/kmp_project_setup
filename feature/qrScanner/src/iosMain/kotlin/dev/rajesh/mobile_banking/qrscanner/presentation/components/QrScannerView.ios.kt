package dev.rajesh.mobile_banking.qrscanner.presentation.components

import androidx.compose.runtime.Composable

@Composable
actual fun QrScannerView(
    isPaused: Boolean,
    onResult: (String) -> Unit,
    onError: (Throwable) -> Unit
) {
}