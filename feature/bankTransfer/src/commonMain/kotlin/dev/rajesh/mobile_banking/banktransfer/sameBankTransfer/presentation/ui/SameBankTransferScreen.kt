package dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.ui

import ErrorDialog
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import dev.rajesh.mobile_banking.banktransfer.navigation.BankTransferResult
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.components.TransferWithAccountForm
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.components.TransferWithMobileNumberForm
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.state.SameBankTransferAction
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.state.SameBankTransferEffect
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.state.TransferTab
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.viewmodel.SameBankTransferViewModel
import dev.rajesh.mobile_banking.components.appColors
import dev.rajesh.mobile_banking.components.button.AppButton
import dev.rajesh.mobile_banking.components.dimens
import dev.rajesh.mobile_banking.components.loadingScreen.LoadingScreen
import dev.rajesh.mobile_banking.confirmation.model.ConfirmationData
import dev.rajesh.mobile_banking.paymentAuthentication.PaymentAuthResult
import dev.rajesh.mobile_banking.res.SharedRes
import dev.rajesh.mobile_banking.useraccounts.presentation.AccountDetailView
import dev.rajesh.mobile_banking.useraccounts.presentation.state.AccountSelectionAction
import dev.rajesh.mobile_banking.useraccounts.presentation.viewmodel.AccountSelectionViewModel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

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
            AccountDetailView(
                state = accountState,
                onSelectAccount = {
                    accountSelectionViewModel.onAction(
                        AccountSelectionAction.SelectAccount(it)
                    )
                }
            )

            TabRow(
                selectedTabIndex = state.selectedTab.ordinal
            ) {
                TransferTab.entries.forEach { tab ->
                    Tab(
                        selected = state.selectedTab == tab,
                        onClick = { viewModel.onTabSelected(tab) },
                        text = { Text(tab.name.replace("_", " ")) }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            when (state.selectedTab) {
                TransferTab.ACCOUNT -> TransferWithAccountForm(
                    state,
                    viewModel::onAction,
                    onSelectCoopBranchClicked
                )

                TransferTab.MOBILE -> TransferWithMobileNumberForm(
                    state,
                    viewModel::onAction
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
