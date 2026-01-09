package dev.rajesh.mobile_banking.loadWallet.presentation.state

import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletDetail
import dev.rajesh.mobile_banking.model.ErrorData
import org.jetbrains.compose.resources.StringResource

data class LoadWalletScreenState(

    val walletId: String = "",
    val walletIdError: StringResource? = null,

    val amount: String = "",
    val amountError: StringResource? = null,

    val remarks : String = "",
    val remarksError: StringResource? = null,

    val isValidatingWallet: Boolean = false,
    val walletValidationError: ErrorData? = null,

    val charge: String? = null,

    val isLoadingWallet: Boolean = false,
    val loadingWalletError: ErrorData? = null,

    val walletDetail: WalletDetail? = null
)