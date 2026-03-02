package dev.rajesh.mobile_banking.user.data.mapper

import dev.rajesh.datastore.userData.model.AccountDetailLocal
import dev.rajesh.datastore.userData.model.QrLocal
import dev.rajesh.datastore.userData.model.UserDetailsLocal
import dev.rajesh.mobile_banking.database.models.UserDetailsEntity
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

fun UserDetails.toEntity(): UserDetailsEntity{
    return UserDetailsEntity(
        addressOne= addressOne,
        addressTwo= addressTwo,
        alertType= alertType,
        appVerification= appVerification,
        bank= bank,
        bankBranch= bankBranch,
        bankBranchCode= bankBranchCode,
        bankCode= bankCode,
        bankTransferOtp= bankTransferOtp,
        beneficiaryFlag= beneficiaryFlag,
        chatId= chatId,
        city= city,
        deviceToken= deviceToken,
        email= email,
        firebaseToken= firebaseToken,
        firstName= firstName,
        fullName= fullName,
        gender= gender,
        isEtellerEnabled= isEtellerEnabled,
        isNpsEnabled= isNpsEnabled,
        lastName= lastName,
        middleName= middleName,
        mobileBanking= mobileBanking,
        mobileNumber= mobileNumber,
        oauthTokenCount= oauthTokenCount,
        otpString= otpString,
        registered= registered,
        smsService= smsService,
        socketPrefix= socketPrefix,
        socketURl= socketURl,
        state= state,
        unseenNotificationCount= unseenNotificationCount
    )
}

//Entity (Room) -> Domain
fun UserDetailsEntity.toDomain(): UserDetails{
    return UserDetails(
        addressOne= this.addressOne,
        addressTwo= this.addressTwo,
        alertType= this.alertType,
        appVerification= this.appVerification,
        bank= this.bank,
        bankBranch= this.bankBranch,
        bankBranchCode= this.bankBranchCode,
        bankCode= this.bankCode,
        bankTransferOtp= this.bankTransferOtp,
        beneficiaryFlag= this.beneficiaryFlag,
        chatId= this.chatId,
        city= this.city,
        deviceToken= this.deviceToken,
        email= this.email,
        firebaseToken= this.firebaseToken,
        firstName= this.firstName,
        fullName= this.fullName,
        gender= this.gender,
        isEtellerEnabled=this. isEtellerEnabled,
        isNpsEnabled= this.isNpsEnabled,
        lastName= this.lastName,
        middleName= this.middleName,
        mobileBanking= this.mobileBanking,
        mobileNumber= this.mobileNumber,
        oauthTokenCount= this.oauthTokenCount,
        otpString= this.otpString,
        registered= this.registered,
        smsService= this.smsService,
        socketPrefix= this.socketPrefix,
        socketURl= this.socketURl,
        state= this.state,
        unseenNotificationCount= this.unseenNotificationCount
    )
}

