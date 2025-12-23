package dev.rajesh.mobile_banking.banktransfer.presentation.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.rajesh.mobile_banking.banktransfer.presentation.viewmodel.BankTransferScreenViewModel
import dev.rajesh.mobile_banking.banktransfer.presentation.component.BankTransferOptionItem
import dev.rajesh.mobile_banking.banktransfer.presentation.model.BankTransferOption
import dev.rajesh.mobile_banking.components.appColors
import dev.rajesh.mobile_banking.components.dimens
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun BankTransferScreen(
    onOptionSelected: (BankTransferOption) -> Unit,
    onBackClicked: () -> Unit
) {
    val viewModel: BankTransferScreenViewModel = koinViewModel()
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
                        "Bank Transfer",
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
        LazyColumn(
            modifier = Modifier.fillMaxSize()
                .padding(contentPadding)
                .fillMaxSize()
                .padding(horizontal = MaterialTheme.dimens.small3)
        ) {
            items(state.bankTransferOptionList) { option ->
                BankTransferOptionItem(option, onOptionClicked = { bankTransferOption ->
                    onOptionSelected(bankTransferOption)
                })
            }
            item { Spacer(modifier = Modifier.fillMaxWidth().height(MaterialTheme.dimens.small3)) }

            item { Text("Favourite Accounts", style = MaterialTheme.typography.titleLarge) }
        }

    }
}


