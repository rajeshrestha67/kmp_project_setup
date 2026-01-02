package dev.rajesh.mobile_banking.banktransfer.differentBankTransfer.presentation.state

import org.jetbrains.compose.resources.StringResource

data class OtherBankTransferScreenState (
    val receiversAccountNumber: String = "",
    val receiversFullName : String ="",
    val amount : String  = "",
    val remarks : String = "",
//    val selectedBank: String = "",

    val receiversAccountNumberError: StringResource? = null,
    val receiversFullNameError: StringResource? = null,
    val amountError: StringResource? = null,
    val remarksError: StringResource? = null,
    val selectedBankError: StringResource? = null,

    val isLoading: Boolean = false,
    val isValidatingAccount: Boolean = false,
    val isTransferringFund: Boolean = false,

    val isError: Boolean = false,
    val errorMessage :String = ""


)
