package dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.mapper

import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.dto.BankAccountValidationResponseDTO
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.BankAccountValidationDetail


fun BankAccountValidationResponseDTO.toAccountValidation(): BankAccountValidationDetail {
    return BankAccountValidationDetail(
        status = detail.status,
        message = detail.message,
        matchPercentage = detail.matchPercentage,
        destinationAccountName = detail.destinationAccountName.orEmpty()
    )
}