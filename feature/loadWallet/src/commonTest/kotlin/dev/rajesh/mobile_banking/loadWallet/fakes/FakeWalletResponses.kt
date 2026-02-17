package dev.rajesh.mobile_banking.loadWallet.fakes

import dev.rajesh.mobile_banking.loadWallet.data.dto.WalletChargeResponseDTO
import dev.rajesh.mobile_banking.loadWallet.data.dto.WalletDetailDTO
import dev.rajesh.mobile_banking.loadWallet.data.dto.WalletLoadDetailsDTO
import dev.rajesh.mobile_banking.loadWallet.data.dto.WalletLoadResponseDTO
import dev.rajesh.mobile_banking.loadWallet.data.dto.WalletValidationDetailDTO
import dev.rajesh.mobile_banking.loadWallet.data.dto.WalletValidationResponseDTO
import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletChargeDetail
import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletDetail
import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletLoadDetails
import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletValidationDetail


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

fun fakeWalletListResponse(): List<WalletDetailDTO> = listOf(
    WalletDetailDTO(
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
    WalletDetailDTO(
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
    message = "Wallet validated successfully",
    code = "M001",
    detail = WalletValidationDetailDTO(
        message = "Wallet validated successfully",
        status = "success",
        customerName = "John Doe",
        customerProfileImageUrl = "https://example.com/profile.jpg",
        validationIdentifier = "123456"

    )
)

fun fakeWalletCharge() = WalletChargeResponseDTO(
    code = "",
    details = 10.0,
    message = "Charge fetched successfully",
    status = "success"
)

fun fakeWalletChargeDetail() = WalletChargeDetail(
    code = "M0000",
    details = 10.0,
    message = "Charge fetched successfully",
    status = "success"
)

fun fakeLoadWalletSuccessResponse() = WalletLoadResponseDTO(
    status = "success",
    message = "Successfully transferred to wallet",
    code = "M001",
    details = WalletLoadDetailsDTO(
        descOneFieldName = "Wallet ID",
        amount = "1111",
        walletIcon = "1542352527067.png",
        walletName = " eSewa ",
        descTwoFieldValue = " test remarks",
        descTwoFieldName = " Remarks ",
        transactionIdentifier = "903941143070366",
        descOneFieldValue = "9840173991",
        accountNumber = "00155555"
    )
)

fun fakeWalletValidationDetail() = WalletValidationDetail(
    message = "Wallet validated successfully",
    status = "success",
    customerName = "John Doe",
    customerProfileImageUrl = "https://example.com/profile.jpg",
    validationIdentifier = "123456"
)

fun fakeWalletLoadSuccessDetails() = WalletLoadDetails(
    descOneFieldName = "Wallet ID",
    amount = "1111",
    walletIcon = "1542352527067.png",
    walletName = " eSewa ",
    descTwoFieldValue = " test remarks",
    descTwoFieldName = " Remarks ",
    transactionIdentifier = "903941143070366",
    descOneFieldValue = "9840173991",
    accountNumber = "00155555",
    message = "Successfully transferred to wallet"
)