package dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model.CoopBranchDetail
import dev.rajesh.mobile_banking.components.appColors
import dev.rajesh.mobile_banking.components.dimens

@Composable
fun CoopBranchItem(
    coopBranch: CoopBranchDetail,
    onCoopBranchSelected: (CoopBranchDetail) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onCoopBranchSelected(coopBranch)
            }.padding(MaterialTheme.dimens.small2)
    ) {
        Text(text = coopBranch.name, style = MaterialTheme.typography.titleMedium)
        Text(text = coopBranch.address, style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
        HorizontalDivider(thickness = 1.dp, color = MaterialTheme.appColors.primaryContainerColor)
    }
}