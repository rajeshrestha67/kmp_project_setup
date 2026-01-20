package dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.mapper

import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.dto.BankAccountValidationResponseDTO
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.dto.BankListResponseDTO
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.dto.InterBankTransferResponseDTO
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.dto.SchemeChargeResponseDTO
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.BankAccountValidationDetail
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.BankDetail
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.InterBankTransferDetail
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.SchemeCharge

fun InterBankTransferResponseDTO.toInterBankTransferDetail(): InterBankTransferDetail {
    return InterBankTransferDetail(
        transactionIdentifier = detail.transactionIdentifier,
        status = detail.status,
        message = message
    )
}

fun BankAccountValidationResponseDTO.toAccountValidation(): BankAccountValidationDetail {
    return BankAccountValidationDetail(
        status = detail.status,
        message = detail.message,
        matchPercentage = detail.matchPercentage,
        destinationAccountName = detail.destinationAccountName.orEmpty()
    )
}

fun SchemeChargeResponseDTO.toSchemeCharge(): SchemeCharge{
    return SchemeCharge(
        status = status,
        code = code,
        message = message,
        details = details
    )
}


fun BankListResponseDTO.toBankList(): List<BankDetail> {
    return details.map {
        BankDetail(
            bankId = it.bankId,
            refBankId = it.refBankId.orEmpty(),
            bankName = it.bankName,
            enabled = it.enabled.equals("Y", ignoreCase = true),
            lastModifiedOn = it.lastModifiedOn.orEmpty(),
            swiftCode = it.swiftCode.orEmpty(),
            iconUrl = it.iconUrl.orEmpty()
        )
    }
}