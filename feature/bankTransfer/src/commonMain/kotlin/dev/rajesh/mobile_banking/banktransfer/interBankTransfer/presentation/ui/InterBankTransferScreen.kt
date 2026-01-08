package dev.rajesh.mobile_banking.banktransfer.interBankTransfer.presentation.ui

import ErrorDialog
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.presentation.state.InterBankTransferEffect
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.presentation.state.InterBankTransferScreenAction
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.presentation.viewmodel.InterBankTransferViewModel
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.presentation.viewmodel.SelectBankViewModel
import dev.rajesh.mobile_banking.components.ItemSelector
import dev.rajesh.mobile_banking.components.appColors
import dev.rajesh.mobile_banking.components.button.AppButton
import dev.rajesh.mobile_banking.components.dimens
import dev.rajesh.mobile_banking.components.loadingScreen.LoadingScreen
import dev.rajesh.mobile_banking.components.textField.AppTextField
import dev.rajesh.mobile_banking.components.textField.FormValidate
import dev.rajesh.mobile_banking.confirmation.model.ConfirmationData
import dev.rajesh.mobile_banking.paymentAuthentication.PaymentAuthResult
import dev.rajesh.mobile_banking.res.SharedRes
import dev.rajesh.mobile_banking.transactionsuccess.model.TransactionData
import dev.rajesh.mobile_banking.useraccounts.presentation.AccountDetailView
import dev.rajesh.mobile_banking.useraccounts.presentation.state.AccountSelectionAction
import dev.rajesh.mobile_banking.useraccounts.presentation.viewmodel.AccountSelectionViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InterBankTransferScreen(
    navController: NavController,
    showConfirmation: (ConfirmationData) -> Unit,
    onTransactionSuccessful: (TransactionData) -> Unit,
    onBackClicked: () -> Unit,
) {

    val interBankTransferViewModel: InterBankTransferViewModel = koinViewModel()
    val state by interBankTransferViewModel.state.collectAsStateWithLifecycle()

    val accountSelectionViewModel: AccountSelectionViewModel = koinViewModel()
    val accountState by accountSelectionViewModel.state.collectAsStateWithLifecycle()

    val bankSelectionViewModel: SelectBankViewModel = koinViewModel()
    val bankState by bankSelectionViewModel.state.collectAsStateWithLifecycle()

    var shouldShowBankList by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle

    LaunchedEffect(Unit) {
        savedStateHandle?.getStateFlow<String?>(PaymentAuthResult.mPin, null)
            ?.collect { mPin ->
                mPin?.let {
                    interBankTransferViewModel.onMPinVerified(it)
                    savedStateHandle.remove<String>(PaymentAuthResult.mPin)
                }
            }
    }


    LaunchedEffect(Unit) {
        interBankTransferViewModel.effect.collect {
            when (it) {
                is InterBankTransferEffect.NavigateToConfirmation -> {
                    showConfirmation(it.confirmationData)
                }

                is InterBankTransferEffect.TransactionSuccessful -> {
                    onTransactionSuccessful(it.transactionData)
                }
            }
        }
    }

    state.errorData?.let { error ->
        ErrorDialog(
            title = "Error",
            msg = error.message.orEmpty(),
            onDismiss = {
                interBankTransferViewModel.dismissError()
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
                .fillMaxSize()
                .padding(contentPadding)
                .padding(horizontal = MaterialTheme.dimens.small3)
                .verticalScroll(scrollState)
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
                    interBankTransferViewModel.onAction(
                        InterBankTransferScreenAction.OnReceiversAccountNumberChanged(
                            it
                        )
                    )
                },
                imeAction = ImeAction.Next,
                error = state.receiversAccountNumberError,
                onErrorStateChange = {
                    interBankTransferViewModel.onAction(
                        InterBankTransferScreenAction.OnReceiversAccountNumberError(
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
                    interBankTransferViewModel.onAction(
                        InterBankTransferScreenAction.OnReceiversFullNameChanged(
                            it
                        )
                    )
                },
                error = state.receiversFullNameError,
                onErrorStateChange = {
                    interBankTransferViewModel.onAction(
                        InterBankTransferScreenAction.OnReceiversFullNameError(
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
                    interBankTransferViewModel.onAction(
                        InterBankTransferScreenAction.OnAmountChanged(
                            it
                        )
                    )
                },
                error = state.amountError,
                onErrorStateChange = {
                    interBankTransferViewModel.onAction(
                        InterBankTransferScreenAction.OnAmountError(
                            it
                        )
                    )
                },
                rules = FormValidate.requiredValidationRules,
                imeAction = ImeAction.Next,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
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
                    interBankTransferViewModel.onAction(
                        InterBankTransferScreenAction.OnRemarksChanged(
                            it
                        )
                    )
                },
                error = state.remarksError,
                onErrorStateChange = {
                    interBankTransferViewModel.onAction(
                        InterBankTransferScreenAction.OnRemarksError(
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
                    interBankTransferViewModel.onAction(InterBankTransferScreenAction.OnProceedClicked)
                },
                text = stringResource(SharedRes.Strings.proceed)
            )


        }

        if (state.isValidatingAccount || state.isTransferringFund) {
            LoadingScreen()
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
                interBankTransferViewModel.onAction(InterBankTransferScreenAction.OnBankSelected(it))
                shouldShowBankList = false
            }
        )
    }
}