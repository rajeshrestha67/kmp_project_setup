package dev.rajesh.mobile_banking.loadWallet.presentation.state

import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletDetail

data class WalletListScreenState(
    val isLoading: Boolean = false,
    val walletList: List<WalletDetail> = emptyList(),
    val errorMessage: String? =null
)
