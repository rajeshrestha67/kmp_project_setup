package dev.rajesh.mobile_banking.loadWallet.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import dev.rajesh.mobile_banking.components.appColors
import dev.rajesh.mobile_banking.components.dimens
import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletDetail

@Composable
fun WalletItem(
    walletDetail: WalletDetail,
    onClick: (WalletDetail) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(walletDetail) }
            .padding(MaterialTheme.dimens.small1),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = walletDetail.icon,
            modifier = Modifier.size(50.dp),
            contentDescription = "wallet icon",
        )
        Spacer(modifier = Modifier.padding(MaterialTheme.dimens.small1))
        Text(
            text = walletDetail.name,
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.appColors.primaryTextColor
            )
        )
    }
}