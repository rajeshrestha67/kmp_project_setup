package dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.state.SameBankTransferState
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.viewmodel.SameBankTransferViewModel

@Composable
fun TransferWithAccountForm(state: SameBankTransferState, viewModel: SameBankTransferViewModel) {
    Column()
    {
        OutlinedTextField(
            value = state.fullName,
            onValueChange = {},
            label = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = state.accountNumber,
            onValueChange = {},
            label = { Text("Account Number") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = state.amount,
            onValueChange = {},
            label = { Text("Amount") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = state.remarks,
            onValueChange = {},
            label = { Text("Remarks") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
