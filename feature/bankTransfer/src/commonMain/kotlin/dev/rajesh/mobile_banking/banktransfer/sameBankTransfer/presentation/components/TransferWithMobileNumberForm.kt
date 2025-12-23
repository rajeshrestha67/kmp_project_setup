package dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.state.SameBankTransferState
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.viewmodel.SameBankTransferViewModel
import dev.rajesh.mobile_banking.components.textField.AmountTextField
import dev.rajesh.mobile_banking.components.textField.AppTextField
import dev.rajesh.mobile_banking.components.textField.FormValidate
import dev.rajesh.mobile_banking.components.textField.MobileTextField
import dev.rajesh.mobile_banking.res.SharedRes
import org.jetbrains.compose.resources.stringResource

@Composable
fun TransferWithMobileNumberForm(
    state: SameBankTransferState,
    viewModel: SameBankTransferViewModel
) {
    val focusRequester: FocusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column {

        MobileTextField(
            modifier = Modifier.fillMaxWidth(), //.focusRequester(focusRequester),
            label = stringResource(SharedRes.Strings.mobileNumber),
            hint = "",
            value = state.mobileNumber,
            onValueChange = {},
            onErrorStateChange = {},
            enabled = !state.isLoading,
            rules = FormValidate.mobileNumberValidationRules,
            imeAction = ImeAction.Next,
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        AmountTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "Amount",
            hint = "",
            value = "",
            onValueChange = {},
            onErrorStateChange = {},
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
            text = "",
            label = "Remarks",
            hint = "",
            onValueChange = {
            },
            rules = FormValidate.requiredValidationRules,
            onErrorStateChange = {},
            enabled = true,
            showErrorMessage = true
        )


    }
}