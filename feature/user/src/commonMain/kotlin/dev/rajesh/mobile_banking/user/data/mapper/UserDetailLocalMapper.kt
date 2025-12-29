package dev.rajesh.mobile_banking.user.data.mapper

import dev.rajesh.datastore.userData.model.AccountDetailLocal
import dev.rajesh.datastore.userData.model.QrLocal
import dev.rajesh.datastore.userData.model.UserDetailsLocal
import dev.rajesh.mobile_banking.user.domain.model.AccountDetail
import dev.rajesh.mobile_banking.user.domain.model.Qr
import dev.rajesh.mobile_banking.user.domain.model.UserDetails

// ---------------------- DOMAIN → LOCAL ----------------------

fun UserDetails.toUserDetailsLocal() = UserDetailsLocal(
    addressOne = addressOne.orEmpty(),
    addressTwo = addressTwo.orEmpty(),
    alertType = alertType ?: false,
    appVerification = appVerification ?: false,
    bank = bank.orEmpty(),
    bankBranch = bankBranch.orEmpty(),
    bankBranchCode = bankBranchCode.orEmpty(),
    bankCode = bankCode.orEmpty(),
    bankTransferOtp = bankTransferOtp ?: false,
    beneficiaryFlag = beneficiaryFlag ?: false,
    chatId = chatId.orEmpty(),
    city = city.orEmpty(),
    deviceToken = deviceToken.orEmpty(),
    email = email.orEmpty(),
    firebaseToken = firebaseToken ?: false,
    firstName = firstName.orEmpty(),
    fullName = fullName.orEmpty(),
    gender = gender.orEmpty(),
    isEtellerEnabled = isEtellerEnabled.orEmpty(),
    isNpsEnabled = isNpsEnabled.orEmpty(),
    lastName = lastName.orEmpty(),
    middleName = middleName.orEmpty(),
    mobileBanking = mobileBanking ?: false,
    mobileNumber = mobileNumber.orEmpty(),
    oauthTokenCount = oauthTokenCount ?: 0,
    otpString = otpString.orEmpty(),
    registered = registered ?: false,
    smsService = smsService ?: false,
    socketPrefix = socketPrefix.orEmpty(),
    socketURl = socketURl.orEmpty(),
    state = state.orEmpty(),
    unseenNotificationCount = unseenNotificationCount ?: 0,
    accountDetail = accountDetail.map { it.toAccountDetailsLocal() } ?: emptyList(),
    qr = qr.map { it.toQrLocal() } ?: emptyList(),
)

fun AccountDetail.toAccountDetailsLocal() = AccountDetailLocal(
    interestRate = interestRate.orEmpty(),
    accountType = accountType.orEmpty(),
    branchName = branchName.orEmpty(),
    accruedInterest = accruedInterest.orEmpty(),
    accountNumber = accountNumber.orEmpty(),
    accountHolderName = accountHolderName.orEmpty(),
    availableBalance = availableBalance.orEmpty(),
    branchCode = branchCode.orEmpty(),
    mainCode = mainCode.orEmpty(),
    minimumBalance = minimumBalance.orEmpty(),
    clientCode = clientCode.orEmpty(),
    actualBalance = actualBalance.orEmpty(),
    mobileBanking = mobileBanking.orEmpty(),
    sms = sms.orEmpty(),
    currency = currency.orEmpty(),
    id = id.orEmpty(),
    primary = primary.orEmpty()
)

fun Qr.toQrLocal() = QrLocal(
    active = active.orEmpty(),
    code = code.orEmpty(),
    imageUrl = imageUrl.orEmpty(),
    label = label.orEmpty(),
    sortOrder = sortOrder ?: 0
)


// ---------------------- LOCAL → DOMAIN ----------------------

fun UserDetailsLocal.toDomain(): UserDetails {
    return UserDetails(
        addressOne = addressOne,
        addressTwo = addressTwo,
        alertType = alertType,
        appVerification = appVerification,
        bank = bank,
        bankBranch = bankBranch,
        bankBranchCode = bankBranchCode,
        bankCode = bankCode,
        bankTransferOtp = bankTransferOtp,
        beneficiaryFlag = beneficiaryFlag,
        chatId = chatId,
        city = city,
        deviceToken = deviceToken,
        email = email,
        firebaseToken = firebaseToken,
        firstName = firstName,
        fullName = fullName,
        gender = gender,
        isEtellerEnabled = isEtellerEnabled,
        isNpsEnabled = isNpsEnabled,
        lastName = lastName,
        middleName = middleName,
        mobileBanking = mobileBanking,
        mobileNumber = mobileNumber,
        oauthTokenCount = oauthTokenCount,
        otpString = otpString,
        registered = registered,
        smsService = smsService,
        socketPrefix = socketPrefix,
        socketURl = socketURl,
        state = state,
        unseenNotificationCount = unseenNotificationCount,
        accountDetail = accountDetail.map { it.toDomain() },
        qr = qr.map { it.toDomain() }
    )
}

fun AccountDetailLocal.toDomain(): AccountDetail {
    return AccountDetail(
        accountNumber = accountNumber,
        accountType = accountType,
        branchCode = branchCode,
        branchName = branchName,
        id = id,
        mainCode = mainCode,
        mobileBanking = mobileBanking,
        primary = primary,
        sms = sms
    )
}

fun QrLocal.toDomain(): Qr {
    return Qr(
        active = active,
        code = code,
        imageUrl = imageUrl,
        label = label,
        sortOrder = sortOrder
    )
}

