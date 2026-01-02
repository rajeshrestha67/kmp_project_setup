package dev.rajesh.mobile_banking.banktransfer.differentBankTransfer.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.rajesh.mobile_banking.components.appColors
import dev.rajesh.mobile_banking.components.dimens

@Composable
fun ItemSelector(
    label: String? = null,
    value: String? = null,
    error: String? = null,
    onClicked: () -> Unit
) {
    Column {
        Text(
            modifier = Modifier.padding(bottom = MaterialTheme.dimens.small1),
            text = label.orEmpty(),
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.appColors.primaryTextColor
            )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .clickable { onClicked() }
                .border(
                    width = 1.dp,
                    color = MaterialTheme.appColors.primaryTextColor,
                    shape = MaterialTheme.shapes.medium
                )
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                .padding(horizontal = MaterialTheme.dimens.small3),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = value.orEmpty(),
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.appColors.primaryTextColor
                )
            )
        }
        if(error!= null && error.isNotEmpty()){
            Text(
                modifier = Modifier.padding(bottom = MaterialTheme.dimens.small1),
                text = "Required",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.appColors.lightRedColor
                )
            )
        }

    }

}