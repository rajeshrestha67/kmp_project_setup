package dev.rajesh.mobile_banking.qrscanner.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import dev.rajesh.mobile_banking.components.permissions.CAMERA_PERMISSION
import dev.rajesh.mobile_banking.components.permissions.rememberRequestPermission
import dev.rajesh.mobile_banking.logger.AppLogger
import dev.rajesh.mobile_banking.qrscanner.presentation.components.QrScannerView

private const val TAG = "QrScannerScreen"

@Composable
fun QrScannerScreen(onBackClicked: () -> Boolean) {

    var isPaused by remember { mutableStateOf(false) }

    val cameraPermission = rememberRequestPermission(
        permissions = listOf(
            CAMERA_PERMISSION
        ),
        onGranted = { permission ->
            AppLogger.i(tag = TAG, message = "Permission denied: $permission")
        },

        onDenied = { permission ->
            AppLogger.i(tag = TAG, message = "Permission denied: $permission")
        },
        onPermanentlyDenied = { permission ->
            AppLogger.i(tag = TAG, message = "Permission denied: $permission")
        },
        onAllGranted = {
            AppLogger.i(tag = TAG, message = "All Permission granted")
        }

    )

    LaunchedEffect(Unit) {
        cameraPermission()
    }


    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
        ) {
            QrScannerView(
                isPaused = isPaused,
                onResult = {
                    AppLogger.e(TAG, "Qr Result: " + it)
                    isPaused = true
                },
                onError = { println(it.message) }
            )
        }

    }
}