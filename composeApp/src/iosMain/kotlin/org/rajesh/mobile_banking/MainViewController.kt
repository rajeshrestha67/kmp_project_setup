package org.rajesh.mobile_banking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.ComposeUIViewController

fun MainViewController() = ComposeUIViewController { App() }


@Composable
fun SplashScreenView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0XFFF8F9CC)),
    ) {
        Text(text = "Mobile banking")


    }
}