package dev.rajesh.mobile_banking.domain.userDetail.model


//data class UserDetail(
//    val code: String,
//    val details: UserDetails?,
//    val message: String,
//    val status: String
//)


data class UserDetails(
    val accountDetail: List<AccountDetail>,
    val addressOne: String,
    val addressTwo: String,
    val alertType: Boolean,
    val appVerification: Boolean,
    val bank: String,
    val bankBranch: String,
    val bankBranchCode: String,
    val bankCode: String,
    val bankTransferOtp: Boolean,
    val beneficiaryFlag: Boolean,
    val chatId: String,
    val city: String,
    val deviceToken: String,
    val email: String,
    val firebaseToken: Boolean,
    val firstName: String,
    val fullName: String,
    val gender: String,
    val isEtellerEnabled: String,
    val isNpsEnabled: String,
    val lastName: String,
    val middleName: String,
    val mobileBanking: Boolean,
    val mobileNumber: String,
    val oauthTokenCount: Int,
    val otpString: String,
    val qr: List<Qr>,
    val registered: Boolean,
    val smsService: Boolean,
    val socketPrefix: String,
    val socketURl: String,
    val state: String,
    val unseenNotificationCount: Int
)

data class AccountDetail(
    val accountNumber: String,
    val accountType: String,
    val branchCode: String,
    val branchName: String,
    val id: String,
    val mainCode: String,
    val mobileBanking: String,
    val primary: String,
    val sms: String
)

data class Qr(
    val active: String,
    val code: String,
    val imageUrl: String,
    val label: String,
    val sortOrder: Int
)