package dev.rajesh.mobile_banking.banktransfer.differentBankTransfer.presentation.state

import dev.rajesh.mobile_banking.banktransfer.differentBankTransfer.domain.model.BankDetail

data class SelectBankState(
    val banks: List<BankDetail> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)