package dev.rajesh.mobile_banking.loadWallet.presentation.state

import dev.rajesh.mobile_banking.model.ErrorData
import org.jetbrains.compose.resources.StringResource

data class LoadWalletScreenState(
    val accountNumber: String = "",
    val accountNumberError: StringResource? = null,

    val walletId: String = "",
    val walletIdError: StringResource? = null,

    val amount: String = "",
    val amountError: StringResource? = null,

    val remarks : String = "",
    val remarksError: StringResource? = null,

    val validatingWallet: Boolean = false,
    val walletValidationError: ErrorData? = null,

    val loadingWallet: Boolean = false,
    val loadingWalletError: ErrorData? = null,
)