package dev.rajesh.mobile_banking.components.contactPicker

import androidx.compose.runtime.Composable

@Composable
expect fun rememberContactPicker(
    onContactPicked: (String) -> Unit,
    onError: (Throwable) -> Unit = {}
): () -> Unit