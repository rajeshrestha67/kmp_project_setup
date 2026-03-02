package dev.rajesh.mobile_banking.user.data.mapper

import dev.rajesh.datastore.userData.model.AccountDetailLocal
import dev.rajesh.datastore.userData.model.QrLocal
import dev.rajesh.datastore.userData.model.UserDetailsLocal
import dev.rajesh.mobile_banking.user.domain.model.AccountDetail
import dev.rajesh.mobile_banking.user.domain.model.Qr
import dev.rajesh.mobile_banking.user.domain.model.UserDetails


fun UserDetails.toUserDetailsLocal() = UserDetailsLocal(
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
    accountDetail = accountDetail.map { it.toAccountDetailsLocal() },
    qr = qr.map { it.toQrLocal() },
)

fun AccountDetail.toAccountDetailsLocal() = AccountDetailLocal(
    interestRate = interestRate,
    accountType = accountType,
    branchName = branchName,
    accruedInterest = accruedInterest,
    accountNumber = accountNumber,
    accountHolderName = accountHolderName,
    availableBalance = availableBalance,
    branchCode = branchCode,
    mainCode = mainCode,
    minimumBalance = minimumBalance,
    clientCode = clientCode,
    actualBalance = actualBalance,
    mobileBanking = mobileBanking,
    sms = sms,
    currency = currency,
    id = id,
    primary = primary
)

fun Qr.toQrLocal() = QrLocal(
    active = active,
    code = code,
    imageUrl = imageUrl,
    label = label,
    sortOrder = sortOrder
)


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