package dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.ui

import ErrorDialog
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import dev.rajesh.mobile_banking.banktransfer.navigation.BankTransferResult
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.state.SameBankTransferAction
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.state.SameBankTransferEffect
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.state.TransferTab
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.viewmodel.SameBankTransferViewModel
import dev.rajesh.mobile_banking.components.appColors
import dev.rajesh.mobile_banking.components.button.AppButton
import dev.rajesh.mobile_banking.components.contactPicker.rememberContactPicker
import dev.rajesh.mobile_banking.components.dimens
import dev.rajesh.mobile_banking.components.loadingScreen.LoadingScreen
import dev.rajesh.mobile_banking.components.permissions.READ_CONTACT_PERMISSION
import dev.rajesh.mobile_banking.components.permissions.rememberRequestPermission
import dev.rajesh.mobile_banking.components.textField.AmountTextField
import dev.rajesh.mobile_banking.components.textField.AppTextField
import dev.rajesh.mobile_banking.components.textField.FormValidate
import dev.rajesh.mobile_banking.components.textField.MobileTextField
import dev.rajesh.mobile_banking.confirmation.model.ConfirmationData
import dev.rajesh.mobile_banking.logger.AppLogger
import dev.rajesh.mobile_banking.paymentAuthentication.PaymentAuthResult
import dev.rajesh.mobile_banking.res.SharedRes
import dev.rajesh.mobile_banking.useraccounts.presentation.AccountDetailView
import dev.rajesh.mobile_banking.useraccounts.presentation.state.AccountSelectionAction
import dev.rajesh.mobile_banking.useraccounts.presentation.viewmodel.AccountSelectionViewModel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

private const val TAG = "SameBankTransferScreen"


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SameBankTransferScreen(
    navController: NavController,
    onBackClicked: () -> Unit,
    onSelectCoopBranchClicked: () -> Unit,
    showConfirmation: (ConfirmationData) -> Unit
) {
    val viewModel: SameBankTransferViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    val accountSelectionViewModel: AccountSelectionViewModel = koinViewModel()
    val accountState by accountSelectionViewModel.state.collectAsStateWithLifecycle()

    val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle

    val selectedBranchFlow = savedStateHandle?.getStateFlow<String?>(
        BankTransferResult.SELECTED_COOP_BRANCH,
        null
    )

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

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    val selectedBranch by selectedBranchFlow?.collectAsStateWithLifecycle()
        ?: remember { mutableStateOf(null) }

    LaunchedEffect(selectedBranch) {
        selectedBranch?.let {
            viewModel.onAction(
                SameBankTransferAction.OnBranchSelected(it)
            )
            savedStateHandle?.remove<String>(BankTransferResult.SELECTED_COOP_BRANCH)
        }
    }

    LaunchedEffect(Unit) {
        savedStateHandle?.getStateFlow<String?>(PaymentAuthResult.mPin, null)
            ?.collect { mPin ->
                mPin?.let {
                    viewModel.onMPinVerified(it)
                    savedStateHandle.remove<String>(PaymentAuthResult.mPin)
                }
            }
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect {
            when (it) {
                is SameBankTransferEffect.NavigateToConfirmation -> {
                    showConfirmation(it.confirmationData)
                }
            }
        }
    }

    state.accountValidationError?.let { error ->
        ErrorDialog(
            title = error.title,
            msg = error.message,
            onDismiss = {
                viewModel.dismissValidationError()
            }
        )
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
                        text = stringResource(SharedRes.Strings.sameBankTransfer),
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

        if (state.validatingAccount) {
            LoadingScreen()
        }

        Column(
            modifier = Modifier.padding(contentPadding)
                .padding(horizontal = MaterialTheme.dimens.small3)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1.0f)
                        .clickable {
                            viewModel.onTabSelected(TransferTab.ACCOUNT)
                        },
                    shape = RoundedCornerShape(1.dp),
                    border = BorderStroke(1.dp, MaterialTheme.appColors.primaryColor),
                    colors = if (state.selectedTab == TransferTab.ACCOUNT) {
                        CardDefaults.cardColors(Color.Red)
                    } else {
                        CardDefaults.cardColors(Color.White)
                    }
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth()
                            .padding(8.dp),
                        text = stringResource(SharedRes.Strings.usingReceiversAccountNumber),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = if (state.selectedTab == TransferTab.ACCOUNT) {
                                Color.White
                            } else {
                                MaterialTheme.appColors.primaryTextColor
                            }
                        )
                    )
                }

                OutlinedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1.0f)
                        .clickable {
                            viewModel.onTabSelected(TransferTab.MOBILE)
                        },
                    shape = RoundedCornerShape(1.dp),
                    border = BorderStroke(1.dp, MaterialTheme.appColors.primaryColor),
                    colors = if (state.selectedTab == TransferTab.MOBILE) {
                        CardDefaults.cardColors(Color.Red)
                    } else {
                        CardDefaults.cardColors(Color.White)
                    }
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth()
                            .padding(8.dp),
                        text = stringResource(SharedRes.Strings.usingReceiversMobileNumber),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = if (state.selectedTab == TransferTab.MOBILE) {
                                Color.White
                            } else {
                                MaterialTheme.appColors.primaryTextColor
                            }
                        )
                    )
                }

            }
            Spacer(modifier = Modifier.height(16.dp))

            AccountDetailView(
                state = accountState,
                onSelectAccount = {
                    accountSelectionViewModel.onAction(AccountSelectionAction.SelectAccount(it))
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column {
                if (state.selectedTab == TransferTab.ACCOUNT) {
                    //accountNumber
                    AppTextField(
                        text = state.accountNumber,
                        label = stringResource(SharedRes.Strings.accountNumber),
                        hint = "",
                        onValueChange = {
                            viewModel.onAction(SameBankTransferAction.OnAccountNumberChanged(it))
                        },
                        error = state.accountNumberError,
                        onErrorStateChange = {
                            viewModel.onAction(SameBankTransferAction.OnAccountNumberError(it))
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

                    //fullName
                    AppTextField(
                        modifier = Modifier.fillMaxWidth(),
                        label = stringResource(SharedRes.Strings.fullName),
                        hint = "",
                        text = state.fullName,
                        onValueChange = {
                            viewModel.onAction(SameBankTransferAction.OnFullNameChanged(it))
                        },
                        error = state.fullNameError,
                        onErrorStateChange = {
                            viewModel.onAction(SameBankTransferAction.OnFullNameError(it))
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

                    //branch
                    AppTextField(
                        text = state.branch?.name.orEmpty(),
                        label = stringResource(SharedRes.Strings.branch),
                        hint = "",
                        onValueChange = {
                            //onAction(SameBankTransferAction.OnBranchChanged(it))
                        },
                        error = state.branchError,
                        onErrorStateChange = {
                            viewModel.onAction(SameBankTransferAction.OnBranchError(it))
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
                            onSelectCoopBranchClicked()
                        }
                    )
                }


                if (state.selectedTab == TransferTab.MOBILE) {
                    Spacer(modifier = Modifier.height(8.dp))

                    //mobileNumber
                    MobileTextField(
                        modifier = Modifier.fillMaxWidth(),
                        label = stringResource(SharedRes.Strings.mobileNumber),
                        hint = "",
                        value = state.mobileNumber,
                        onValueChange = {
                            viewModel.onAction(SameBankTransferAction.OnMobileNumberChanged(it))
                        },
                        error = state.mobileNumberError,
                        onErrorStateChange = {
                            viewModel.onAction(SameBankTransferAction.OnMobileNumberError(it))
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
                }

                Spacer(modifier = Modifier.height(8.dp))

                //amount
                AmountTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = stringResource(SharedRes.Strings.amount),
                    hint = "",
                    value = state.amount,
                    onValueChange = {
                        viewModel.onAction(SameBankTransferAction.OnAmountChanged(it))
                    },
                    error = state.amountError,
                    onErrorStateChange = {
                        viewModel.onAction(SameBankTransferAction.OnAmountError(it))
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
                    text = state.remarks,
                    label = stringResource(SharedRes.Strings.remarks),
                    hint = "",
                    onValueChange = {
                        viewModel.onAction(SameBankTransferAction.OnRemarksChanged(it))

                    },
                    error = state.remarksError,
                    rules = FormValidate.requiredValidationRules,
                    onErrorStateChange = {
                        viewModel.onAction(SameBankTransferAction.OnRemarksError(it))
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

            Spacer(modifier = Modifier.height(16.dp))

            AppButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    viewModel.onAction(SameBankTransferAction.OnProceedClicked)
                },
                isLoading = state.isLoading,
                text = stringResource(SharedRes.Strings.proceed)
            )
        }

    }
}
