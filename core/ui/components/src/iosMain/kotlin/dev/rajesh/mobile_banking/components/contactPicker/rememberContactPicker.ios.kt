package dev.rajesh.mobile_banking.components.contactPicker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
actual fun rememberContactPicker(
    onContactPicked: (String) -> Unit,
    onError: (Throwable) -> Unit
): () -> Unit {
    return remember {
        {

        }
    }
}