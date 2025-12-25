package dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.state

import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model.CoopBranchDetail

data class SelectBranchScreenState(
    val isLoading: Boolean = false,
    val coopBranches: List<CoopBranchDetail> = listOf()
)