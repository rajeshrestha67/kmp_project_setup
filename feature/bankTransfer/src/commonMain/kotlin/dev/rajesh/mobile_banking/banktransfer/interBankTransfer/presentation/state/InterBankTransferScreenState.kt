package dev.rajesh.mobile_banking.banktransfer.interBankTransfer.presentation.state

import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.BankDetail
import dev.rajesh.mobile_banking.model.ErrorData
import org.jetbrains.compose.resources.StringResource

data class InterBankTransferScreenState (
    val receiversAccountNumber: String = "00200501213504",
    val receiversFullName : String ="Rajesh Shrestha",
    val amount : String  = "1111",
    val remarks : String = "test",
    val selectedBank: BankDetail? = null,

    val receiversAccountNumberError: StringResource? = null,
    val receiversFullNameError: StringResource? = null,
    val amountError: StringResource? = null,
    val remarksError: StringResource? = null,

    val isLoading: Boolean = false,
    val isValidatingAccount: Boolean = false,
    val isTransferringFund: Boolean = false,

    val isError: Boolean = false,
    val errorData : ErrorData? = null,

    val charge: String? = null


)
