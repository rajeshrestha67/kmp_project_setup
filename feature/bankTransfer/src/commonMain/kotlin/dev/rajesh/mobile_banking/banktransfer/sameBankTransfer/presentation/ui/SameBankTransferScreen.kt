package dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.ui

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.components.TransferWithAccountForm
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.components.TransferWithMobileNumberForm
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.state.TransferTab
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.viewmodel.SameBankTransferViewModel
import dev.rajesh.mobile_banking.components.appColors
import dev.rajesh.mobile_banking.components.dimens
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SameBankTransferScreen(
    onBackClicked: () -> Unit
) {
    val viewModel: SameBankTransferViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

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
                        text = "Same Bank Transfer",
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
            modifier = Modifier.padding(contentPadding)
                .padding(horizontal = MaterialTheme.dimens.small3)
        ) {
            // Tab Selector
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
                TransferTab.ACCOUNT -> TransferWithAccountForm(state, viewModel)
                TransferTab.MOBILE -> TransferWithMobileNumberForm(state, viewModel)
            }
        }

    }
}
