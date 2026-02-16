package dev.rajesh.mobile_banking.loadWallet.data.mapper

import dev.rajesh.mobile_banking.loadWallet.data.dto.WalletChargeResponseDTO
import dev.rajesh.mobile_banking.loadWallet.data.dto.WalletListResponseDTO
import dev.rajesh.mobile_banking.loadWallet.data.dto.WalletLoadResponseDTO
import dev.rajesh.mobile_banking.loadWallet.data.dto.WalletValidationDetailDTO
import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletChargeDetail
import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletDetail
import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletLoadDetails
import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletValidationDetail
import dev.rajesh.mobile_banking.networkhelper.Constants


fun WalletListResponseDTO.toWalletList(): List<WalletDetail> {
    return details?.map {
        WalletDetail(
            id = it.id ?: 0,
            name = it.name.orEmpty(),
            descOneFieldName = it.descOneFieldName.orEmpty(),
            descOneFieldType = it.descOneFieldType.orEmpty(),
            descOneFixedLength = it.descOneFixedLength ?: false,
            descOneLength = it.descOneLength ?: 0,
            descOneMinLength = it.descOneMinLength ?: 0,
            descOneMaxLength = it.descOneMaxLength ?: 0,
            descTwoFieldName = it.descTwoFieldName.orEmpty(),
            descTwoFieldType = it.descTwoFieldType.orEmpty(),
            descTwoFixedLength = it.descTwoFixedLength ?: false,
            descTwoLength = it.descTwoLength.orEmpty(),
            descTwoMinLength = it.descTwoMinLength ?: 0,
            descTwoMaxLength = it.descTwoMaxLength ?: 0,
            icon = "${Constants.baseUrl}/mbank/serviceIcon/${it.icon}",
            accountHead = it.accountHead.orEmpty(),
            accountNumber = it.accountNumber.orEmpty(),
            minAmount = it.minAmount ?: 0.0,
            maxAmount = it.maxAmount ?: 0.0,
            status = it.status.orEmpty()
        )
    } ?: emptyList()
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

fun WalletLoadResponseDTO.toWalletLoadDetail(): WalletLoadDetails {
    return WalletLoadDetails(
        message = message,
        descOneFieldName = details.descOneFieldName,
        amount = details.amount,
        walletIcon = details.walletIcon,
        walletName = details.walletName,
        descTwoFieldValue = details.descTwoFieldValue,
        descTwoFieldName = details.descTwoFieldName,
        transactionIdentifier = details.transactionIdentifier,
        descOneFieldValue = details.descOneFieldValue,
        accountNumber = details.accountNumber
    )
}
