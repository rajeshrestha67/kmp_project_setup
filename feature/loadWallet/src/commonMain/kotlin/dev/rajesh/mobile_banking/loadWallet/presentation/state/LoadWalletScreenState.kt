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

    val charge: String? = null,

    val isValidatingWallet: Boolean = false,
    val walletValidationError: ErrorData? = null,
    val validationIdentifier: String? = null,

    val isWalletTransferring: Boolean = false,
    val walletTransferError: ErrorData? = null,

    val walletDetail: WalletDetail? = null
)