package dev.rajesh.mobile_banking.banktransfer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.rajesh.mobile_banking.banktransfer.navigation.BankTransferOption
import dev.rajesh.mobile_banking.components.dimens

@Composable
fun BankTransferScreen(
    onOptionSelected: (BankTransferOption) -> Unit
) {
    Column {
        Text("Bank Transfer")
        Spacer(modifier = Modifier.height(height = MaterialTheme.dimens.small3))
    }
}