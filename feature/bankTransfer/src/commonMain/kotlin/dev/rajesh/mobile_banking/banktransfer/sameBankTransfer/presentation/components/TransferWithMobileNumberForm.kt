package dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
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
import dev.rajesh.mobile_banking.components.contactPicker.rememberContactPicker
import dev.rajesh.mobile_banking.components.permissions.READ_CONTACT_PERMISSION
import dev.rajesh.mobile_banking.components.permissions.rememberRequestPermission
import dev.rajesh.mobile_banking.components.textField.AmountTextField
import dev.rajesh.mobile_banking.components.textField.AppTextField
import dev.rajesh.mobile_banking.components.textField.FormValidate
import dev.rajesh.mobile_banking.logger.AppLogger


private const val TAG = "TransferWithMobileNumberForm"

@Composable
fun TransferWithMobileNumberForm(
    state: SameBankTransferState
) {
    val focusRequester: FocusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val pickContact = rememberContactPicker(
        onContactPicked = { phoneNumber ->
            //viewModel.onMobileNumberChanged(phoneNumber)
            AppLogger.i(TAG, "Contact picked: $phoneNumber")
        },
        onError = { error ->
            AppLogger.e(TAG, "Error picking contact: ${error.message}")
        }
    )


    val onPermission = rememberRequestPermission(
        permissions = listOf(
            READ_CONTACT_PERMISSION
        ), onGranted = { permission ->
            AppLogger.i(
                tag = TAG,
                message = "Permission granted: $permission"
            )
            pickContact()
        },
        onDenied = { permission ->
            AppLogger.i(
                tag = TAG,
                message = "Permission denied: $permission"
            )
        },
        onPermanentlyDenied = { permission ->
            AppLogger.i(
                tag = TAG,
                message = "Permission denied permanent: $permission"
            )
        },
        onAllGranted = {
            AppLogger.i(
                tag = TAG,
                message = "All Permission granted"
            )
        }
    )
//
//    LaunchedEffect(Unit) {
//        onPermission()
//    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column {
        MobileNumberField(
            value = state.mobileNumber,
            onValueChanged = {
                //viewModel.onMobileNumberChanged(it)
            },
            onPickContactClicked = {
                onPermission()
            }
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