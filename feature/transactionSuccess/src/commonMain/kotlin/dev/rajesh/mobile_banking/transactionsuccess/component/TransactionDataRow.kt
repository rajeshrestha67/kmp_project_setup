package dev.rajesh.mobile_banking.transactionsuccess.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.rajesh.mobile_banking.components.appColors
import dev.rajesh.mobile_banking.components.dimens
import dev.rajesh.mobile_banking.transactionsuccess.model.TransactionDataItem


@Composable
fun TransactionDataRow(it: TransactionDataItem) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier
                .padding(horizontal = MaterialTheme.dimens.small3)
                .fillMaxWidth()
                .weight(1f),
            text = it.label,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.appColors.secondaryTextColor
            )
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.dimens.small3)
                .weight(1f),
            text = it.value,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.appColors.primaryTextColor
            )
        )
    }
}