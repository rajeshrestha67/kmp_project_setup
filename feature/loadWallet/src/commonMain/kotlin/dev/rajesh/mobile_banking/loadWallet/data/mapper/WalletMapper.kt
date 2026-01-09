package dev.rajesh.mobile_banking.loadWallet.data.mapper

import dev.rajesh.mobile_banking.loadWallet.data.dto.WalletChargeResponseDTO
import dev.rajesh.mobile_banking.loadWallet.data.dto.WalletListResponseDTO
import dev.rajesh.mobile_banking.loadWallet.data.dto.WalletValidationDetailDTO
import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletChargeDetail
import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletDetail
import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletValidationDetail
import dev.rajesh.mobile_banking.networkhelper.Constants


fun WalletListResponseDTO.toWalletList(): List<WalletDetail> {
    return details.map {
        WalletDetail(
            id = it.id,
            name = it.name,
            descOneFieldName = it.descOneFieldName,
            descOneFieldType = it.descOneFieldType,
            descOneFixedLength = it.descOneFixedLength,
            descOneLength = it.descOneLength,
            descOneMinLength = it.descOneMinLength,
            descOneMaxLength = it.descOneMaxLength,
            descTwoFieldName = it.descTwoFieldName,
            descTwoFieldType = it.descTwoFieldType,
            descTwoFixedLength = it.descTwoFixedLength,
            descTwoLength = it.descTwoLength,
            descTwoMinLength = it.descTwoMinLength,
            descTwoMaxLength = it.descTwoMaxLength,
            icon = "${Constants.baseUrl}/mbank/serviceIcon/${it.icon}",
            accountHead = it.accountHead,
            accountNumber = it.accountNumber,
            minAmount = it.minAmount,
            maxAmount = it.maxAmount,
            status = it.status
        )
    }
}

fun WalletValidationDetailDTO.toWalletValidationDetail(): WalletValidationDetail {
    return WalletValidationDetail(
        status = status,
        message = message,
        customerName = customerName,
        customerProfileImageUrl = customerProfileImageUrl.orEmpty(),
        validationIdentifier = validationIdentifier.orEmpty(),
    )
}

fun WalletChargeResponseDTO.toWalletChargeDetail(): WalletChargeDetail {
    return WalletChargeDetail(
        code = code,
        details = details,
        message = message,
        status = status
    )
}
