package dev.rajesh.mobile_banking.useraccounts.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.rajesh.mobile_banking.components.appColors
import dev.rajesh.mobile_banking.components.dimens
import dev.rajesh.mobile_banking.res.SharedRes
import dev.rajesh.mobile_banking.user.domain.model.AccountDetail
import dev.rajesh.mobile_banking.useraccounts.presentation.state.AccountSelectionState
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountSelectionBottomSheet(
    state: AccountSelectionState,
    onDismiss: () -> Unit,
    onAccountSelected: (AccountDetail) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.dimens.small3)
        ) {
            Text(
                stringResource(SharedRes.Strings.chooseAccount),
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.appColors.primaryTextColor
                )
            )

            LazyColumn() {
                items(state.accounts) { item ->
                    AccountBottomSheetItem(
                        accountDetail = item,
                        isSelected = item.id == state.selectedAccount?.id,
                        onClick = {
                            onAccountSelected(item)
                        }
                    )
                }
            }
        }
    }

}