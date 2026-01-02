package dev.rajesh.mobile_banking.transactionsuccess

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.rajesh.mobile_banking.components.appColors
import dev.rajesh.mobile_banking.components.button.AppButton
import dev.rajesh.mobile_banking.components.dimens
import dev.rajesh.mobile_banking.transactionsuccess.component.SuccessLottie
import dev.rajesh.mobile_banking.transactionsuccess.component.TransactionDataRow
import dev.rajesh.mobile_banking.transactionsuccess.model.TransactionData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionSuccessFulScreen(
    data: TransactionData,
    goToDashboardClicked: () -> Unit
) {

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.appColors.backgroundColor,
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                windowInsets = WindowInsets(),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.appColors.backgroundColor
                ),
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        //text = if (transactionStatus) "Transaction Successful" else "Transaction Failed",
                        text = "Transaction Successful",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = MaterialTheme.appColors.primaryTextColor
                        )
                    )
                }
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(MaterialTheme.dimens.small3)
        ) {
            SuccessLottie()

            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = data.message,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.appColors.primaryTextColor
                )
            )

            Spacer(
                modifier = Modifier
                    .height(32.dp)
            )

            data.items.forEach {
                TransactionDataRow(it)
            }

            Spacer(
                modifier = Modifier
                    .height(32.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    modifier = Modifier.padding(horizontal = MaterialTheme.dimens.small3),
                    imageVector = Icons.Default.PictureAsPdf,
                    contentDescription = "pdf download image",
                    tint = Color.Red
                )
                Text(
                    "Download Pdf",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = MaterialTheme.appColors.primaryTextColor
                    )
                )

            }
            Spacer(
                modifier = Modifier
                    .height(32.dp)
            )

            AppButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = goToDashboardClicked,
                text = "Go to Dashboard"
            )

        }
    }


}