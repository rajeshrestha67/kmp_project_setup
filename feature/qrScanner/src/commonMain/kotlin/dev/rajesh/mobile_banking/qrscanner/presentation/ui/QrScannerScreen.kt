package dev.rajesh.mobile_banking.qrscanner.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun QrScannerScreen(onBackClicked: () -> Boolean) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
        ) {
            Text("QrScanner")
        }

    }
}