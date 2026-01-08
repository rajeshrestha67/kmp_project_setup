package dev.rajesh.mobile_banking.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun NoDataFoundView(message: String) {
    Column {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = message,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.appColors.primaryTextColor
            )
        )
    }
}