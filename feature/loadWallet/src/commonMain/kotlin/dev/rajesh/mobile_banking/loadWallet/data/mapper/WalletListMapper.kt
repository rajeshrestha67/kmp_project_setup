package dev.rajesh.mobile_banking.loadWallet.data.mapper

import dev.rajesh.mobile_banking.loadWallet.data.dto.WalletListResponseDTO
import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletDetail
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
