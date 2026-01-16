package dev.rajesh.mobile_banking.components.media

import androidx.compose.runtime.Composable

@Composable
expect fun rememberGalleryLoader(
    onLoaded: (List<String>) -> Unit,
    onError: (Throwable) -> Unit
): () -> Unit
