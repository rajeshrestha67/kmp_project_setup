package dev.rajesh.mobile_banking.qrscanner.data.mapper

import dev.rajesh.mobile_banking.qrscanner.data.dto.QPayMerchantDetailDTO
import dev.rajesh.mobile_banking.qrscanner.data.dto.QPayMerchantDetailResponseDTO
import dev.rajesh.mobile_banking.qrscanner.domain.model.AccountDetails
import dev.rajesh.mobile_banking.qrscanner.domain.model.QPayMerchantDetail


fun QPayMerchantDetailResponseDTO.toQPayMerchantDetail(): QPayMerchantDetail {
    return QPayMerchantDetail(
        status = details.status,
        bankTransfer = details.bankTransfer ?: false,
        internalFundTransfer = details.internalFundTransfer ?: false,
        accountDetails = details.toAccountDetails(),
        walletId = details.walletId.orEmpty(),
        walletType = details.walletType.orEmpty(),
        loadWallet = details.loadWallet ?: false,
        name = details.name.orEmpty()
    )
}

fun QPayMerchantDetailDTO.toAccountDetails(): AccountDetails {
    return AccountDetails(
        accountNumber = accountDetails?.accountNumber.orEmpty(),
        accountName = accountDetails?.accountName.orEmpty(),
        bankCode = accountDetails?.bankCode.orEmpty(),
        branchCode = accountDetails?.branchCode.orEmpty(),
        bankName = accountDetails?.bankName.orEmpty()
    )
}
