package dev.rajesh.mobile_banking.banktransfer.differentBankTransfer.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import dev.rajesh.mobile_banking.banktransfer.differentBankTransfer.domain.model.BankDetail
import dev.rajesh.mobile_banking.components.appColors
import dev.rajesh.mobile_banking.components.dimens

@Composable
fun BankItemRow(
    bank: BankDetail,
    onBankSelected: (BankDetail) -> Unit
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable {
            onBankSelected(bank)
        }
    ) {
        GetLetterInitials(bank.bankName)
        Text(
            modifier = Modifier.padding(MaterialTheme.dimens.small3),
            text = bank.bankName,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.appColors.primaryTextColor
            )
        )
    }


    HorizontalDivider(
        modifier = Modifier.padding(horizontal = MaterialTheme.dimens.small2),
        thickness = 1.dp,
        color = MaterialTheme.appColors.primaryTextColor //black on light, white on dark
    )

}


@Composable
fun GetLetterInitials(name: String) {
    Box(
        modifier = Modifier.size(size = 30.dp)
            .clip(shape = CircleShape)
            .background(color = MaterialTheme.appColors.primaryColor)
        /*.border(
            width = 1.dp,
            color = MaterialTheme.appColors.primaryTextColor,
            shape = CircleShape
        )*/
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = name.first().uppercase(),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.appColors.onPrimary
            )
        )
    }
}