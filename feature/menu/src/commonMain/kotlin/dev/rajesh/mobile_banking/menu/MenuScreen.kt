package dev.rajesh.mobile_banking.menu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MenuScreen() {
    MenuScreenContent()
}

@Composable
fun MenuScreenContent() {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { contentPadding ->
        Box(
            modifier = Modifier.fillMaxSize()
                .padding(contentPadding)
        ) {
            Text("Menu Screen")
        }

    }
}