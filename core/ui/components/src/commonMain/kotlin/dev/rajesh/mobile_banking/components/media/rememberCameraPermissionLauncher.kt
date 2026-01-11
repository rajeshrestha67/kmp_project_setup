package dev.rajesh.mobile_banking.components.media

import androidx.compose.runtime.Composable

@Composable
expect fun rememberCameraPermissionLauncher(
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit
)