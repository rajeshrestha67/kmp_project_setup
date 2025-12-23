package dev.rajesh.mobile_banking.banktransfer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.rajesh.mobile_banking.banktransfer.navigation.BankTransferOption
import dev.rajesh.mobile_banking.components.appColors
import dev.rajesh.mobile_banking.components.dimens
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun BankTransferScreen(
    onOptionSelected: (BankTransferOption) -> Unit,
    onBackClicked: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
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
                        "Bank Transfer",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = MaterialTheme.appColors.primaryTextColor
                        )
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClicked,
                        content = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "back arrow"
                            )
                        }
                    )
                }
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(contentPadding)
                .fillMaxSize()
                .padding(horizontal = MaterialTheme.dimens.small3)
        ) {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.dimens.small2),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.appColors.primaryContainerColor
                )
            ) {
                Column (
                    modifier = Modifier.padding(MaterialTheme.dimens.small3)
                ){
                    Text("Same Bank")
                    Text("Transfer fund to other accounts within same bank")
                }
            }

            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.dimens.small2),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.appColors.primaryContainerColor
                )
            ) {
                Column (
                    modifier = Modifier.padding(MaterialTheme.dimens.small3)
                ){
                    Text("Other Bank")
                    Text("Transfer fund to other accounts in other bank")
                }
            }

            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.dimens.small2),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.appColors.primaryContainerColor
                )
            ) {
                Column (
                    modifier = Modifier.padding(MaterialTheme.dimens.small3)
                ){
                    Text("Favourite Accounts")
                    Text("Transfer fund to your favourite accounts")
                }
            }
        }

    }
}

