package dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.state.SameBankTransferAction
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.state.SameBankTransferState
import dev.rajesh.mobile_banking.components.textField.AmountTextField
import dev.rajesh.mobile_banking.components.textField.AppTextField
import dev.rajesh.mobile_banking.components.textField.FormValidate
import dev.rajesh.mobile_banking.res.SharedRes
import mobilebanking.feature.banktransfer.generated.resources.Res
import org.jetbrains.compose.resources.stringResource

@Composable
fun TransferWithAccountForm(
    state: SameBankTransferState,
    onAction: (SameBankTransferAction) -> Unit,
    selectCoopBranchClicked: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    Column()
    {
        AppTextField(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(SharedRes.Strings.fullName),
            hint = "",
            text = state.fullName,
            onValueChange = {
                onAction(SameBankTransferAction.OnFullNameChanged(it))
            },
            error = state.fullNameError,
            onErrorStateChange = {
                onAction(SameBankTransferAction.OnFullNameError(it))
            },
            rules = FormValidate.requiredValidationRules,
            imeAction = ImeAction.Next,
            enabled = true,
            showErrorMessage = true,
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        AppTextField(
            text = state.accountNumber,
            label = stringResource(SharedRes.Strings.accountNumber),
            hint = "",
            onValueChange = {
                onAction(SameBankTransferAction.OnAccountNumberChanged(it))
            },
            error = state.accountNumberError,
            onErrorStateChange = {
                onAction(SameBankTransferAction.OnAccountNumberError(it))
            },
            rules = FormValidate.requiredValidationRules,
            enabled = true,
            showErrorMessage = true,
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        AppTextField(
            text = state.branch?.name.orEmpty(),
            label = stringResource(SharedRes.Strings.branch),
            hint = "",
            onValueChange = {
                //onAction(SameBankTransferAction.OnBranchChanged(it))
            },
            error = state.branchError,
            onErrorStateChange = {
                onAction(SameBankTransferAction.OnBranchError(it))
            },
            rules = FormValidate.requiredValidationRules,
            enabled = false,
            showErrorMessage = true,
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),
            modifier = Modifier.fillMaxWidth().clickable {
                selectCoopBranchClicked()
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        AmountTextField(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(SharedRes.Strings.amount),
            hint = "",
            value = state.amount,
            onValueChange = {
                onAction(SameBankTransferAction.OnAmountChanged(it))
            },
            error = state.amountError,
            onErrorStateChange = {
                onAction(SameBankTransferAction.OnAmountError(it))
            },
            rules = FormValidate.requiredValidationRules,
            imeAction = ImeAction.Next,
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        AppTextField(
            text = state.remarks,
            label = stringResource(SharedRes.Strings.remarks),
            hint = "",
            onValueChange = {
                onAction(SameBankTransferAction.OnRemarksChanged(it))

            },
            error = state.remarksError,
            rules = FormValidate.requiredValidationRules,
            onErrorStateChange = {
                onAction(SameBankTransferAction.OnRemarksError(it))

            },
            enabled = true,
            showErrorMessage = true,
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            )
        )
    }
}
