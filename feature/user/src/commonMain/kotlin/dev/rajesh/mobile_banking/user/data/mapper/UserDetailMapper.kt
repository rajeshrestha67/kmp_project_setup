package dev.rajesh.mobile_banking.user.data.mapper

import dev.rajesh.datastore.userData.model.AccountDetailLocal
import dev.rajesh.datastore.userData.model.QrLocal
import dev.rajesh.datastore.userData.model.UserDetailsLocal
import dev.rajesh.mobile_banking.database.models.AccountDetailEntity
import dev.rajesh.mobile_banking.database.models.QrEntity
import dev.rajesh.mobile_banking.database.models.UserDetailsEntity
import dev.rajesh.mobile_banking.database.relations.UserWithAccounts
import dev.rajesh.mobile_banking.user.data.remote.dto.AccountDetailDTO
import dev.rajesh.mobile_banking.user.data.remote.dto.QrDTO
import dev.rajesh.mobile_banking.user.data.remote.dto.UserDetailsDTO
import dev.rajesh.mobile_banking.user.domain.model.AccountDetail
import dev.rajesh.mobile_banking.user.domain.model.Qr
import dev.rajesh.mobile_banking.user.domain.model.UserDetails

//fun UserDetailResponseDTO.toUserDetail(): UserDetail = UserDetail(
//    code = code.orEmpty(),
//    status = status.orEmpty(),
//    message = message.orEmpty(),
//    details = details?.toUserDetails()
//)

fun UserDetailsDTO.toUserDetails(): UserDetails {
    return UserDetails(
        accountDetail = accountDetail?.map { it.toAccountDetails() } ?: emptyList(),
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
        qr = qr?.map { it.toQr() } ?: emptyList(),
        registered = registered ?: false,
        smsService = smsService ?: false,
        socketPrefix = socketPrefix.orEmpty(),
        socketURl = socketURl.orEmpty(),
        state = state.orEmpty(),
        unseenNotificationCount = unseenNotificationCount ?: 0
    )
}

fun AccountDetailDTO.toAccountDetails(): AccountDetail {
    return AccountDetail(
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
}

fun QrDTO.toQr(): Qr {
    return Qr(
        active = active.orEmpty(),
        code = code.orEmpty(),
        imageUrl = imageUrl.orEmpty(),
        label = label.orEmpty(),
        sortOrder = sortOrder ?: 0
    )
}


//domain -> Entity (Room)

fun UserDetails.toEntity(): UserDetailsEntity {
    return UserDetailsEntity(
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
        unseenNotificationCount = unseenNotificationCount
    )
}

//Entity (Room) -> Domain
fun UserWithAccounts.toDomain(): UserDetails {
    return UserDetails(
        addressOne = this.user.addressOne,
        addressTwo = this.user.addressTwo,
        alertType = this.user.alertType,
        appVerification = this.user.appVerification,
        bank = this.user.bank,
        bankBranch = this.user.bankBranch,
        bankBranchCode = this.user.bankBranchCode,
        bankCode = this.user.bankCode,
        bankTransferOtp = this.user.bankTransferOtp,
        beneficiaryFlag = this.user.beneficiaryFlag,
        chatId = this.user.chatId,
        city = this.user.city,
        deviceToken = this.user.deviceToken,
        email = this.user.email,
        firebaseToken = this.user.firebaseToken,
        firstName = this.user.firstName,
        fullName = this.user.fullName,
        gender = this.user.gender,
        isEtellerEnabled = this.user.isEtellerEnabled,
        isNpsEnabled = this.user.isNpsEnabled,
        lastName = this.user.lastName,
        middleName = this.user.middleName,
        mobileBanking = this.user.mobileBanking,
        mobileNumber = this.user.mobileNumber,
        oauthTokenCount = this.user.oauthTokenCount,
        otpString = this.user.otpString,
        registered = this.user.registered,
        smsService = this.user.smsService,
        socketPrefix = this.user.socketPrefix,
        socketURl = this.user.socketURl,
        state = this.user.state,
        unseenNotificationCount = this.user.unseenNotificationCount,
        accountDetail = this.accountDetails.map { it.toDomain() },
        qr = this.qrList.map { it.toDomain() }
    )
}

fun AccountDetail.toEntity(mobileNumber: String): AccountDetailEntity {
    return AccountDetailEntity(
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
        primary = primary,
        mobileNumber = mobileNumber
    )
}

fun AccountDetailEntity.toDomain(): AccountDetail {
    return AccountDetail(
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
        primary = primary,
    )
}

fun Qr.toEntity (mobileNumber: String) : QrEntity {
    return QrEntity(
        mobileNumber = mobileNumber,
        active = this.active,
        code = this.code,
        imageUrl = this.imageUrl,
        label = this.label,
        sortOrder =this. sortOrder,
    )
}

fun QrEntity.toDomain() : Qr{
    return Qr(
        active = this.active,
        code = this.code,
        imageUrl = this.imageUrl,
        label = this.label,
        sortOrder =this. sortOrder,
    )
}



