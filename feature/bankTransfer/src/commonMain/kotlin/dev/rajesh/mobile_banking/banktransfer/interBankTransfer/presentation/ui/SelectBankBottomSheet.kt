package dev.rajesh.mobile_banking.banktransfer.interBankTransfer.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.BankDetail
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.presentation.components.BankItemRow
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.presentation.state.SelectBankAction
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.presentation.state.SelectBankState
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.presentation.viewmodel.SelectBankViewModel
import dev.rajesh.mobile_banking.components.appColors
import dev.rajesh.mobile_banking.components.dimens
import dev.rajesh.mobile_banking.components.textField.AppTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectBankBottomSheet(
    viewModel: SelectBankViewModel,
    state: SelectBankState,
    onDismiss: () -> Unit,
    onBankSelected: (BankDetail) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        containerColor = MaterialTheme.appColors.backgroundColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f)
                .padding(MaterialTheme.dimens.small3)
        ) {
            Text(
                text = "Select Bank",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.appColors.primaryTextColor
                )
            )

            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))

            AppTextField(
                modifier = Modifier.fillMaxWidth(),
                hint = "Search",
                text = state.searchText,
                onValueChange = {
                    viewModel.onAction(SelectBankAction.OnSearchTextChanged(it))
                },
                error = null,
                onErrorStateChange = {},
            )
            LazyColumn {
                items(state.banks) { bank ->
                    BankItemRow(
                        bank = bank,
                        onBankSelected = { selectedBank ->
                            onBankSelected(selectedBank)
                            viewModel.clearTextFields()
                            onDismiss()
                        }
                    )
                }
            }
        }
    }
}
