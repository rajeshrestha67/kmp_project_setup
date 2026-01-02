package dev.rajesh.mobile_banking.banktransfer.differentBankTransfer.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.rajesh.mobile_banking.banktransfer.differentBankTransfer.presentation.components.ItemSelector
import dev.rajesh.mobile_banking.banktransfer.differentBankTransfer.presentation.state.OtherBankTransferScreenAction
import dev.rajesh.mobile_banking.banktransfer.differentBankTransfer.presentation.state.SelectBankAction
import dev.rajesh.mobile_banking.banktransfer.differentBankTransfer.presentation.viewmodel.OtherBankTransferViewModel
import dev.rajesh.mobile_banking.banktransfer.differentBankTransfer.presentation.viewmodel.SelectBankViewModel
import dev.rajesh.mobile_banking.components.appColors
import dev.rajesh.mobile_banking.components.button.AppButton
import dev.rajesh.mobile_banking.components.dimens
import dev.rajesh.mobile_banking.components.textField.AppTextField
import dev.rajesh.mobile_banking.components.textField.FormValidate
import dev.rajesh.mobile_banking.logger.AppLogger
import dev.rajesh.mobile_banking.res.SharedRes
import dev.rajesh.mobile_banking.useraccounts.presentation.AccountDetailView
import dev.rajesh.mobile_banking.useraccounts.presentation.state.AccountSelectionAction
import dev.rajesh.mobile_banking.useraccounts.presentation.viewmodel.AccountSelectionViewModel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtherBankTransferScreen(
    onBackClicked: () -> Unit,
) {

    val otherBankTransferViewModel: OtherBankTransferViewModel = koinViewModel()
    val state by otherBankTransferViewModel.state.collectAsStateWithLifecycle()

    val accountSelectionViewModel: AccountSelectionViewModel = koinViewModel()
    val accountState by accountSelectionViewModel.state.collectAsStateWithLifecycle()

    val bankSelectionViewModel: SelectBankViewModel = koinViewModel()
    val bankState by bankSelectionViewModel.state.collectAsStateWithLifecycle()

    var shouldShowBankList by remember { mutableStateOf(false) }

    val focusRequester: FocusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

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
                        text = "Other Bank Transfer",
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
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(horizontal = MaterialTheme.dimens.small3)
        ) {
            Text(
                text = "Transfer From",
                style = MaterialTheme.typography.labelMedium.copy(
                    color = MaterialTheme.appColors.secondaryTextColor
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            AccountDetailView(
                state = accountState,
                onSelectAccount = {
                    accountSelectionViewModel.onAction(AccountSelectionAction.SelectAccount(it))
                }
            )

            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))

            ItemSelector(
                label = "Receiver's Bank",
                value = if (state.selectedBank != null) {
                    state.selectedBank?.bankName
                } else {
                    "-- Select Bank --"
                },
                onClicked = {
                    shouldShowBankList = true
                }
            )

            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))

            AppTextField(
                modifier = Modifier.fillMaxWidth(),
                label = "Receiver's Account Number",
                text = state.receiversAccountNumber,
                hint = "",
                onValueChange = {
                    otherBankTransferViewModel.onAction(
                        OtherBankTransferScreenAction.OnReceiversAccountNumberChanged(
                            it
                        )
                    )
                },
                imeAction = ImeAction.Next,
                error = state.receiversAccountNumberError,
                onErrorStateChange = {
                    otherBankTransferViewModel.onAction(
                        OtherBankTransferScreenAction.OnReceiversAccountNumberError(
                            it
                        )
                    )
                },
                rules = FormValidate.requiredValidationRules,
                showErrorMessage = true,
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                )
            )

            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))

            AppTextField(
                modifier = Modifier.fillMaxWidth(),
                label = "Receiver's FullName",
                hint = "",
                text = state.receiversFullName,
                onValueChange = {
                    otherBankTransferViewModel.onAction(
                        OtherBankTransferScreenAction.OnReceiversFullNameChanged(
                            it
                        )
                    )
                },
                error = state.receiversFullNameError,
                onErrorStateChange = {
                    otherBankTransferViewModel.onAction(
                        OtherBankTransferScreenAction.OnReceiversFullNameError(
                            it
                        )
                    )
                },
                rules = FormValidate.requiredValidationRules,
                imeAction = ImeAction.Next,
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                )

            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))

            AppTextField(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(SharedRes.Strings.amount),
                hint = "",
                text = state.amount,
                onValueChange = {
                    otherBankTransferViewModel.onAction(
                        OtherBankTransferScreenAction.OnAmountChanged(
                            it
                        )
                    )
                },
                error = state.amountError,
                onErrorStateChange = {
                    otherBankTransferViewModel.onAction(
                        OtherBankTransferScreenAction.OnAmountError(
                            it
                        )
                    )
                },
                rules = FormValidate.requiredValidationRules,
                imeAction = ImeAction.Next,
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                )


            )

            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))

            AppTextField(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(SharedRes.Strings.remarks),
                hint = "",
                text = state.remarks,
                onValueChange = {
                    otherBankTransferViewModel.onAction(
                        OtherBankTransferScreenAction.OnRemarksChanged(
                            it
                        )
                    )
                },
                error = state.remarksError,
                onErrorStateChange = {
                    otherBankTransferViewModel.onAction(
                        OtherBankTransferScreenAction.OnRemarksError(
                            it
                        )
                    )
                },
                rules = FormValidate.requiredValidationRules,
                imeAction = ImeAction.Next,
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                )
            )

            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small3))

            AppButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    otherBankTransferViewModel.onAction(OtherBankTransferScreenAction.OnProceedClicked)
                },
                text = stringResource(SharedRes.Strings.proceed)
            )


        }
    }

    if (shouldShowBankList) {
        SelectBankBottomSheet(
            state = bankState,
            viewModel = bankSelectionViewModel,
            onDismiss = {
                shouldShowBankList = false
            },
            onBankSelected = {
                otherBankTransferViewModel.onAction(OtherBankTransferScreenAction.OnBankSelected(it))
                shouldShowBankList = false
            }
        )
    }
}