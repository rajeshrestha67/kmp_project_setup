package dev.rajesh.mobile_banking.loadwallet.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.rajesh.mobile_banking.components.dimens

@Composable
fun LoadWalletScreen() {
    Column {
        Text("Load Wallet")
        Spacer(modifier = Modifier.height(height = MaterialTheme.dimens.small3))
    }
}