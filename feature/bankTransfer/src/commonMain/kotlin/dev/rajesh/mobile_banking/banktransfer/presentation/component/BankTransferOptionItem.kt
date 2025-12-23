package dev.rajesh.mobile_banking.banktransfer.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.rajesh.mobile_banking.banktransfer.presentation.model.BankTransferOption
import dev.rajesh.mobile_banking.components.appColors
import dev.rajesh.mobile_banking.components.dimens

@Composable
fun BankTransferOptionItem(
    bankTransferOption: BankTransferOption,
    onOptionClicked: (BankTransferOption) -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.dimens.small2),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.appColors.primaryContainerColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        onClick = {
            onOptionClicked(bankTransferOption)
        }
    ) {
        Column(
            modifier = Modifier.padding(MaterialTheme.dimens.small3)
        ) {
            Text(
                text = bankTransferOption.title, style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.appColors.primaryTextColor
                )
            )
            Text(
                text = bankTransferOption.description,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.appColors.primaryTextColor
                )
            )
        }
    }

}