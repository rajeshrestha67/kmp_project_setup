package dev.rajesh.mobile_banking.useraccounts.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.rajesh.mobile_banking.components.appColors
import dev.rajesh.mobile_banking.components.dimens
import dev.rajesh.mobile_banking.user.domain.model.AccountDetail

@Composable
fun AccountBottomSheetItem(
    accountDetail: AccountDetail,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.dimens.small2)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.appColors.primaryContainerColor
        ),
        border = if (isSelected)
            BorderStroke(2.dp, MaterialTheme.appColors.primaryColor)
        else null
    ) {
        Column(
            modifier = Modifier
                .padding(MaterialTheme.dimens.small3)
        ) {
            Text(
                accountDetail.accountNumber,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                accountDetail.accountType,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.appColors.secondaryTextColor
                )
            )
        }

    }

}