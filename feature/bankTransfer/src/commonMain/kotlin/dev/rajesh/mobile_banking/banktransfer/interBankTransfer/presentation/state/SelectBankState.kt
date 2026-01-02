package dev.rajesh.mobile_banking.banktransfer.interBankTransfer.presentation.state

import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.BankDetail

data class SelectBankState(
    val searchText: String = "",
    var banks: List<BankDetail> = emptyList(),
    val allBanks: List<BankDetail> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)