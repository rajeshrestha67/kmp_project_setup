package dev.rajesh.mobile_banking.banking

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text

@Composable
fun BankingScreen() {
    BankingScreenContent()
}

@Composable
fun BankingScreenContent() {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { contentPadding ->
        Box(
            modifier = Modifier.fillMaxSize()
                .padding(contentPadding),
        ) {
            Text("Banking Screen")
        }

    }
}