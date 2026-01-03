package dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.mapper

import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.dto.SchemeChargeResponseDTO
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.SchemeCharge

fun SchemeChargeResponseDTO.toSchemeCharge(): SchemeCharge{
    return SchemeCharge(
        status = status,
        code = code,
        message = message,
        details = details
    )
}