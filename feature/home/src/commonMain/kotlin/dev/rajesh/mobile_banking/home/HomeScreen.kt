package dev.rajesh.mobile_banking.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen() {
    HomeScreenContent()
}

@Composable
fun HomeScreenContent() {
    Scaffold(modifier = Modifier.fillMaxSize()) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            Text("Home Screen")
        }

    }
}