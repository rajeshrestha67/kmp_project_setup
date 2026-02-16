package dev.rajesh.mobile_banking.loadWallet.fakes

import dev.rajesh.mobile_banking.loadWallet.data.dto.WalletValidationDetailDTO
import dev.rajesh.mobile_banking.loadWallet.data.dto.WalletValidationResponseDTO
import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletDetail


fun fakeWalletList(): List<WalletDetail> = listOf(
    WalletDetail(
        id = 1,
        name = "eSewa",
        descOneFieldName = "Wallet ID",
        descOneFieldType = "String",
        descOneFixedLength = false,
        descOneLength = 0,
        descOneMinLength = 1,
        descOneMaxLength = 35,
        descTwoFieldName = "Remarks",
        descTwoFieldType = "String",
        descTwoFixedLength = false,
        descTwoLength = "null",
        descTwoMinLength = 1,
        descTwoMaxLength = 35,
        icon = "1542352527067.png",
        accountHead = "ESEWA",
        accountNumber = "ESEWAWALLET",
        minAmount = 100.00,
        maxAmount = 1000.00,
        status = "Active"
    ),
    WalletDetail(
        id = 2,
        name = "Khalti",
        descOneFieldName = "Wallet ID",
        descOneFieldType = "String",
        descOneFixedLength = false,
        descOneLength = 0,
        descOneMinLength = 1,
        descOneMaxLength = 35,
        descTwoFieldName = "Remarks",
        descTwoFieldType = "String",
        descTwoFixedLength = false,
        descTwoLength = "",
        descTwoMinLength = 1,
        descTwoMaxLength = 35,
        icon = "15423525270699999.png",
        accountHead = "Khalti",
        accountNumber = "KHALTIWALLET",
        minAmount = 100.00,
        maxAmount = 1000.00,
        status = "Active"
    )
)

fun fakeValidationResponse() = WalletValidationResponseDTO(
    status = "success",
    message = "Quick service fetch successfully",
    code = "M001",
    detail = WalletValidationDetailDTO(
        message = "Wallet validated successfully",
        status = "success",
        customerName = "John Doe",
        customerProfileImageUrl = "https://example.com/profile.jpg",
        validationIdentifier = "123456"

    )
)