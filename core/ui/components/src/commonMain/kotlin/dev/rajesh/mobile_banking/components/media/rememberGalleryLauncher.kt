package dev.rajesh.mobile_banking.components.media

import androidx.compose.runtime.Composable


@Composable
expect fun rememberGalleryLauncher(
    onImageSelected: (String) -> Unit,
    onError: (Throwable) -> Unit = {}
): () -> Unit