package dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.mapper

import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.dto.BankListResponseDTO
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.BankDetail


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