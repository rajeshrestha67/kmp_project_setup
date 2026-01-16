package dev.rajesh.mobile_banking.qrscanner.data.mapper

import dev.rajesh.mobile_banking.qrscanner.data.dto.QPayMerchantDetailDTO
import dev.rajesh.mobile_banking.qrscanner.data.dto.QPayMerchantDetailResponseDTO
import dev.rajesh.mobile_banking.qrscanner.domain.model.AccountDetails
import dev.rajesh.mobile_banking.qrscanner.domain.model.QPayMerchantDetail


fun QPayMerchantDetailResponseDTO.toQPayMerchantDetail(): QPayMerchantDetail {
    return QPayMerchantDetail(
        bankTransfer = details.bankTransfer,
        accountDetails = details.toAccountDetails(),
        status = details.status
    )
}

fun QPayMerchantDetailDTO.toAccountDetails(): AccountDetails {
    return AccountDetails(
        accountNumber = accountDetails.accountNumber,
        accountName = accountDetails.accountName,
        bankCode = accountDetails.bankCode,
        branchCode = accountDetails.branchCode,
        bankName = accountDetails.bankName
    )
}
