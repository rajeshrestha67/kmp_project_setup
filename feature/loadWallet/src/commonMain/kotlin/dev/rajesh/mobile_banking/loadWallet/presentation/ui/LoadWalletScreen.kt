package dev.rajesh.mobile_banking.loadWallet.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.rajesh.mobile_banking.components.appColors
import dev.rajesh.mobile_banking.components.button.AppButton
import dev.rajesh.mobile_banking.components.contactPicker.rememberContactPicker
import dev.rajesh.mobile_banking.components.dimens
import dev.rajesh.mobile_banking.components.permissions.READ_CONTACT_PERMISSION
import dev.rajesh.mobile_banking.components.permissions.rememberRequestPermission
import dev.rajesh.mobile_banking.components.textField.AmountTextField
import dev.rajesh.mobile_banking.components.textField.AppTextField
import dev.rajesh.mobile_banking.components.textField.FormValidate
import dev.rajesh.mobile_banking.components.textField.MobileTextField
import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletDetail
import dev.rajesh.mobile_banking.loadWallet.presentation.state.LoadWalletScreenAction
import dev.rajesh.mobile_banking.loadWallet.presentation.viewmodel.LoadWalletViewModel
import dev.rajesh.mobile_banking.logger.AppLogger
import dev.rajesh.mobile_banking.res.SharedRes
import dev.rajesh.mobile_banking.useraccounts.presentation.AccountDetailView
import dev.rajesh.mobile_banking.useraccounts.presentation.state.AccountSelectionAction
import dev.rajesh.mobile_banking.useraccounts.presentation.viewmodel.AccountSelectionViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoadWalletScreen(
    walletDetail: WalletDetail,
    onBackClicked: () -> Unit
) {

    val TAG = "LoadWalletScreen"

    val viewModel: LoadWalletViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    val accountSelectionViewModel: AccountSelectionViewModel = koinViewModel()
    val accountState by accountSelectionViewModel.state.collectAsStateWithLifecycle()

    val focusRequester: FocusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val scrollState = rememberScrollState()

    val pickContact = rememberContactPicker(
        onContactPicked = { phoneNumber ->
            AppLogger.i(TAG, "Contact picked: $phoneNumber")
            viewModel.onAction(LoadWalletScreenAction.OnWalletIdChanged(phoneNumber))
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

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

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
                        text = "Load ${walletDetail.name}",
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
                                modifier = Modifier.size(MaterialTheme.dimens.backButtonSize),
                                painter = painterResource(SharedRes.Icons.backArrow),
                                contentDescription = "back arrow",
                                tint = MaterialTheme.appColors.iconColor,
                            )
                        }
                    )
                }

            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .padding(horizontal = MaterialTheme.dimens.small3)
                .verticalScroll(scrollState),
        ) {

            AccountDetailView(
                state = accountState,
                onSelectAccount = {
                    accountSelectionViewModel.onAction(AccountSelectionAction.SelectAccount(it))
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            MobileTextField(
                modifier = Modifier.fillMaxWidth(),
                label = "Wallet Id",
                hint = "",
                value = state.walletId,
                onValueChange = {
                    viewModel.onAction(LoadWalletScreenAction.OnWalletIdChanged(it))
                },
                error = state.walletIdError,
                onErrorStateChange = {
                    viewModel.onAction(LoadWalletScreenAction.OnWalletIdError(it))
                },
                rules = FormValidate.mobileNumberValidationRules,
                imeAction = ImeAction.Next,
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                trailingIcon = {
                    IconButton(onClick = { onPermission() }) {
                        Icon(
                            imageVector = Icons.Default.Contacts,
                            contentDescription = "Pick Contact"
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            //amount
            AmountTextField(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(SharedRes.Strings.amount),
                hint = "",
                value = state.amount,
                onValueChange = {
                    viewModel.onAction(LoadWalletScreenAction.OnAmountChanged(it))
                },
                error = state.amountError,
                onErrorStateChange = {
                    viewModel.onAction(LoadWalletScreenAction.OnAmountError(it))
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

            //remarks
            AppTextField(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(SharedRes.Strings.remarks),
                hint = "",
                text = state.remarks,
                onValueChange = {
                    viewModel.onAction(LoadWalletScreenAction.OnRemarksChanged(it))
                },
                error = state.remarksError,
                onErrorStateChange = {
                    viewModel.onAction(LoadWalletScreenAction.OnRemarksError(it))
                },
                rules = FormValidate.requiredValidationRules,
                imeAction = ImeAction.Done,
                enabled = true,
                showErrorMessage = true,
                keyboardActions = KeyboardActions(
                    onDone = {
                        //do something
                    }
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            AppButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(SharedRes.Strings.proceed),
                onClick = {
                    viewModel.onAction(LoadWalletScreenAction.OnProceedClicked)
                }
            )

        }
    }
}