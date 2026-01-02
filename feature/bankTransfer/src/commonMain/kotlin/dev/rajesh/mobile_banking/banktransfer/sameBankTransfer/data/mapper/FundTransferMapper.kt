package dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.mapper

import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.remote.dto.FundTransferResponseDTO
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model.FundTransferDetail

fun FundTransferResponseDTO.toFundTransferDetail(): FundTransferDetail {
    return FundTransferDetail(
        status = status,
        transactionIdentifier = detail.transactionIdentifier,
        message = message
    )
}