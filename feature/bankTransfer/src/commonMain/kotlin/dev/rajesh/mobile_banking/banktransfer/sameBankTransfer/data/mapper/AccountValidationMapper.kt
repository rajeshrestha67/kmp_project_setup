package dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.mapper

import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.remote.dto.AccountValidationResponseDTO
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model.AccountValidationDetail


fun AccountValidationResponseDTO.toAccountValidation(): AccountValidationDetail {
    return AccountValidationDetail(
        status = detail.status,
        message = detail.message,
        matchPercentage = detail.matchPercentage,
        destinationAccountName = detail.destinationAccountName
    )
}