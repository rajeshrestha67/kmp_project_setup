package dev.rajesh.mobile_banking.user.domain.model

import kotlinx.serialization.Serializable


//data class UserDetail(
//    val code: String,
//    val details: UserDetails?,
//    val message: String,
//    val status: String
//)

@Serializable
data class UserDetails(
    val accountDetail: List<AccountDetail> = emptyList(),
    val addressOne: String = "",
    val addressTwo: String = "",
    val alertType: Boolean = false,
    val appVerification: Boolean = false,
    val bank: String = "",
    val bankBranch: String = "",
    val bankBranchCode: String = "",
    val bankCode: String = "",
    val bankTransferOtp: Boolean = false,
    val beneficiaryFlag: Boolean = false,
    val chatId: String = "",
    val city: String = "",
    val deviceToken: String = "",
    val email: String = "",
    val firebaseToken: Boolean = false,
    val firstName: String = "",
    val fullName: String = "",
    val gender: String = "",
    val isEtellerEnabled: String = "",
    val isNpsEnabled: String = "",
    val lastName: String = "",
    val middleName: String = "",
    val mobileBanking: Boolean = false,
    val mobileNumber: String = "",
    val oauthTokenCount: Int = 0,
    val otpString: String = "",
    val qr: List<Qr> = emptyList(),
    val registered: Boolean = false,
    val smsService: Boolean = false,
    val socketPrefix: String = "",
    val socketURl: String = "",
    val state: String = "",
    val unseenNotificationCount: Int = 0
)

@Serializable
data class AccountDetail(
    val interestRate: String = "",
    val accountType: String = "",
    val branchName: String = "",
    val accruedInterest: String = "",
    val accountNumber: String = "",
    val accountHolderName: String = "",
    val availableBalance: String = "350000",
    val branchCode: String = "",
    val mainCode: String = "",
    val minimumBalance: String = "",
    val clientCode: String = "",
    val actualBalance: String = "",
    val mobileBanking: String = "",
    val sms: String = "",
    val currency: String = "",
    val id: String = "",
    val primary: String = ""
)

@Serializable
data class Qr(
    val active: String = "",
    val code: String = "",
    val imageUrl: String = "",
    val label: String = "",
    val sortOrder: Int = 0
)