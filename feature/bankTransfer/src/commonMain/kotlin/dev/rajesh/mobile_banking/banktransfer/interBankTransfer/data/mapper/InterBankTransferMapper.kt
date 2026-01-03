package dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.mapper

import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.dto.InterBankTransferResponseDTO
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.InterBankTransferDetail

fun InterBankTransferResponseDTO.toInterBankTransferDetail(): InterBankTransferDetail {
    return InterBankTransferDetail(
        transactionIdentifier = detail.transactionIdentifier,
        status = detail.status,
        message = message
    )
}