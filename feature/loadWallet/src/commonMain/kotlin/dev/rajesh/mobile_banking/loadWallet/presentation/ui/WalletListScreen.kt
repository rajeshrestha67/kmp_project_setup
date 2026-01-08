package dev.rajesh.mobile_banking.loadWallet.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import dev.rajesh.mobile_banking.components.appColors
import dev.rajesh.mobile_banking.components.dimens
import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletDetail
import dev.rajesh.mobile_banking.loadWallet.presentation.ui.components.WalletItem
import dev.rajesh.mobile_banking.loadWallet.presentation.viewmodel.WalletListViewModel
import dev.rajesh.mobile_banking.logger.AppLogger
import dev.rajesh.mobile_banking.res.SharedRes
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletListScreen(
    onBackClicked: () -> Unit,
    onWalletClicked: (WalletDetail) -> Unit
) {

    val viewModel: WalletListViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

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
                        text = "Wallet List",
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
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(horizontal = MaterialTheme.dimens.small3),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small2),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small3)
        ) {
            items(state.walletList) { walletDetail ->
                WalletItem(
                    walletDetail = walletDetail,
                    onClick = { wallet ->
                        AppLogger.d(tag = "WalletListScreen", "Wallet clicked: ${wallet.name}")
                        onWalletClicked(walletDetail)
                    })
            }
        }
    }
}