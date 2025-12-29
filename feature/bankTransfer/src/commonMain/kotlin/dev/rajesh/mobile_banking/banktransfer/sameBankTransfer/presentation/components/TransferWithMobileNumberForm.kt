package dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.components
//
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.text.KeyboardActions
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Contacts
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.focus.FocusDirection
//import androidx.compose.ui.focus.FocusRequester
//import androidx.compose.ui.platform.LocalFocusManager
//import androidx.compose.ui.platform.LocalSoftwareKeyboardController
//import androidx.compose.ui.text.input.ImeAction
//import androidx.compose.ui.unit.dp
//import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.state.SameBankTransferAction
//import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.state.SameBankTransferState
//import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.viewmodel.SameBankTransferViewModel
//import dev.rajesh.mobile_banking.components.contactPicker.rememberContactPicker
//import dev.rajesh.mobile_banking.components.permissions.READ_CONTACT_PERMISSION
//import dev.rajesh.mobile_banking.components.permissions.rememberRequestPermission
//import dev.rajesh.mobile_banking.components.textField.AmountTextField
//import dev.rajesh.mobile_banking.components.textField.AppTextField
//import dev.rajesh.mobile_banking.components.textField.FormValidate
//import dev.rajesh.mobile_banking.components.textField.MobileTextField
//import dev.rajesh.mobile_banking.logger.AppLogger
//import dev.rajesh.mobile_banking.res.SharedRes
//import org.jetbrains.compose.resources.stringResource
//
//
//private const val TAG = "TransferWithMobileNumberForm"
//
//@Composable
//fun TransferWithMobileNumberForm(
//    state: SameBankTransferState,
//    onAction: (SameBankTransferAction) -> Unit
//) {
//    val focusRequester: FocusRequester = remember { FocusRequester() }
//    val focusManager = LocalFocusManager.current
//    val keyboardController = LocalSoftwareKeyboardController.current
//
//    val pickContact = rememberContactPicker(
//        onContactPicked = { phoneNumber ->
//            //viewModel.onMobileNumberChanged(phoneNumber)
//            AppLogger.i(TAG, "Contact picked: $phoneNumber")
//        },
//        onError = { error ->
//            AppLogger.e(TAG, "Error picking contact: ${error.message}")
//        }
//    )
//
//
//    val onPermission = rememberRequestPermission(
//        permissions = listOf(
//            READ_CONTACT_PERMISSION
//        ), onGranted = { permission ->
//            AppLogger.i(
//                tag = TAG,
//                message = "Permission granted: $permission"
//            )
//            pickContact()
//        },
//        onDenied = { permission ->
//            AppLogger.i(
//                tag = TAG,
//                message = "Permission denied: $permission"
//            )
//        },
//        onPermanentlyDenied = { permission ->
//            AppLogger.i(
//                tag = TAG,
//                message = "Permission denied permanent: $permission"
//            )
//        },
//        onAllGranted = {
//            AppLogger.i(
//                tag = TAG,
//                message = "All Permission granted"
//            )
//        }
//    )
//
//    LaunchedEffect(Unit) {
//        focusRequester.requestFocus()
//    }
//
//    Column {
//
//        MobileTextField(
//            modifier = Modifier.fillMaxWidth(),
//            label = stringResource(SharedRes.Strings.mobileNumber),
//            hint = "",
//            value = state.mobileNumber,
//            onValueChange = {
//                onAction(SameBankTransferAction.OnMobileNumberChanged(it))
//            },
//            error = state.mobileNumberError,
//            onErrorStateChange = {
//                onAction(SameBankTransferAction.OnMobileNumberError(it))
//            },
//            rules = FormValidate.mobileNumberValidationRules,
//            imeAction = ImeAction.Next,
//            keyboardActions = KeyboardActions(
//                onNext = {
//                    focusManager.moveFocus(FocusDirection.Down)
//                }
//            ),
//            trailingIcon = {
//                IconButton(onClick = { onPermission() }) {
//                    Icon(
//                        imageVector = Icons.Default.Contacts,
//                        contentDescription = "Pick Contact"
//                    )
//                }
//            }
//        )
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        AmountTextField(
//            modifier = Modifier.fillMaxWidth(),
//            label = stringResource(SharedRes.Strings.amount),
//            hint = "",
//            value = state.amount,
//            onValueChange = {
//                onAction(SameBankTransferAction.OnAmountChanged(it))
//            },
//            error = state.amountError,
//            onErrorStateChange = {
//                onAction(SameBankTransferAction.OnAmountError(it))
//            },
//            rules = FormValidate.requiredValidationRules,
//            imeAction = ImeAction.Next,
//            keyboardActions = KeyboardActions(
//                onNext = {
//                    focusManager.moveFocus(FocusDirection.Down)
//                }
//            )
//        )
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        AppTextField(
//            text = state.remarks,
//            label = stringResource(SharedRes.Strings.remarks),
//            hint = "",
//            onValueChange = {
//                onAction(SameBankTransferAction.OnRemarksChanged(it))
//            },
//            rules = FormValidate.requiredValidationRules,
//            error = state.remarksError,
//            onErrorStateChange = {
//                onAction(SameBankTransferAction.OnRemarksError(it))
//            },
//            enabled = true,
//            showErrorMessage = true,
//            keyboardActions = KeyboardActions(
//                onNext = {
//                    focusManager.moveFocus(FocusDirection.Enter)
//                }
//            )
//        )
//
//
//    }
//}