package dev.rajesh.datastore.userData.model

import kotlinx.serialization.Serializable

@Serializable
data class UserDetailsLocal(
    val accountDetail: List<AccountDetailLocal> = emptyList(),
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
    val qr: List<QrLocal> = emptyList(),
    val registered: Boolean = false,
    val smsService: Boolean = false,
    val socketPrefix: String = "",
    val socketURl: String = "",
    val state: String = "",
    val unseenNotificationCount: Int = 0
)
@Serializable
data class AccountDetailLocal(
    val accountNumber: String = "",
    val accountType: String = "",
    val branchCode: String = "",
    val branchName: String = "",
    val id: String = "",
    val mainCode: String = "",
    val mobileBanking: String = "",
    val primary: String = "",
    val sms: String = ""
)
@Serializable
data class QrLocal(
    val active: String = "",
    val code: String = "",
    val imageUrl: String = "",
    val label: String = "",
    val sortOrder: Int = 0
)