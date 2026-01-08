package dev.rajesh.mobile_banking.useraccounts.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import dev.rajesh.mobile_banking.components.appColors
import dev.rajesh.mobile_banking.components.dimens
import dev.rajesh.mobile_banking.res.SharedRes
import dev.rajesh.mobile_banking.user.domain.model.AccountDetail
import dev.rajesh.mobile_banking.useraccounts.presentation.components.AccountSelectionBottomSheet
import dev.rajesh.mobile_banking.useraccounts.presentation.state.AccountSelectionState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun AccountDetailView(
    state: AccountSelectionState,
    onSelectAccount: (AccountDetail) -> Unit
) {
    var showSheet by remember { mutableStateOf(false) }

    val selectedAccount = state.selectedAccount ?: return

    OutlinedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, MaterialTheme.appColors.primaryColor)
    ) {
        Row {
            Column(
                modifier = Modifier
                    .padding(MaterialTheme.dimens.small3)
                    .weight(1.0f)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = buildAnnotatedString {
                            append(stringResource(SharedRes.Strings.currencyType))
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.W900,
                                    fontSize = MaterialTheme.dimens.largeFontSize
                                )
                            ) {
                                append(selectedAccount.availableBalance)
                            }
                        },
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = MaterialTheme.appColors.primaryTextColor,
                            fontSize = MaterialTheme.dimens.regularFontSize
                        )
                    )

                    IconButton(
                        onClick = {},
                        content = {
                            Icon(
                                modifier = Modifier.size(20.dp),
                                imageVector = Icons.Default.VisibilityOff,
                                contentDescription = "visibility"
                            )
                        }
                    )
                }
                Text(
                    selectedAccount.accountType,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.appColors.secondaryTextColor
                    )
                )

                Text(
                    selectedAccount.accountNumber,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.appColors.primaryTextColor
                    )
                )

            }

            Column(
                modifier = Modifier.padding(MaterialTheme.dimens.small2),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    stringResource(SharedRes.Strings.primary),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White
                    ),
                    modifier = Modifier
                        .background(color = Color.Red)
                        .padding(
                            horizontal = MaterialTheme.dimens.small2,
                            vertical = MaterialTheme.dimens.small1
                        )
                )

                IconButton(
                    onClick = { showSheet = true },
                    content = {
                        Icon(
                            modifier = Modifier.size(16.dp),
                            painter = painterResource(SharedRes.Icons.forwardArrow),
                            contentDescription = "arrow"
                        )
                    }
                )
            }

        }

    }

    if (showSheet) {
        AccountSelectionBottomSheet(
            state = state,
            onDismiss = { showSheet = false },
            onAccountSelected = { selectedAccount ->
                onSelectAccount(selectedAccount)
                showSheet = false
            }
        )
    }
}
