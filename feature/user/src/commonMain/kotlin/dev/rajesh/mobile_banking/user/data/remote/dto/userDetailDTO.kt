package dev.rajesh.mobile_banking.user.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDetailResponseDTO(
    val code: String,
    val details: UserDetailsDTO,
    val message: String,
    val status: String
)

@Serializable
data class UserDetailsDTO(
    val accountDetail: List<AccountDetailDTO>? = null,
    val addressOne: String? = null,
    val addressTwo: String? = null,
    val alertType: Boolean? = null,
    val appVerification: Boolean? = null,
    val bank: String? = null,
    val bankBranch: String? = null,
    val bankBranchCode: String? = null,
    val bankCode: String? = null,
    val bankTransferOtp: Boolean? = null,
    val beneficiaryFlag: Boolean? = null,
    val chatId: String? = null,
    val city: String? = null,
    val deviceToken: String? = null,
    val email: String? = null,
    val firebaseToken: Boolean? = null,
    val firstName: String? = null,
    val fullName: String? = null,
    val gender: String? = null,
    val isEtellerEnabled: String? = null,
    val isNpsEnabled: String? = null,
    val lastName: String? = null,
    val middleName: String? = null,
    val mobileBanking: Boolean? = null,
    val mobileNumber: String? = null,
    val oauthTokenCount: Int? = null,
    val otpString: String? = null,
    val qr: List<QrDTO>? = null,
    val registered: Boolean? = null,
    val smsService: Boolean? = null,
    val socketPrefix: String? = null,
    val socketURl: String? = null,
    val state: String? = null,
    val unseenNotificationCount: Int? = null
)

@Serializable
data class AccountDetailDTO(
    val interestRate: String? = null,
    val accountType: String? = null,
    val branchName: String? = null,
    val accruedInterest: String? = null,
    val accountNumber: String? = null,
    val accountHolderName: String? = null,
    val availableBalance: String? = null,
    val branchCode: String? = null,
    val mainCode: String? = null,
    val minimumBalance: String? = null,
    val clientCode: String? = null,
    val actualBalance: String? = null,
    val mobileBanking: String? = null,
    val sms: String? = null,
    val currency: String? = null,
    val id: String? = null,
    val primary: String? = null
)

@Serializable
data class QrDTO(
    val active: String? = null,
    val code: String? = null,
    val imageUrl: String? = null,
    val label: String? = null,
    val sortOrder: Int? = null
)