package dev.rajesh.mobile_banking.database.models.userDetail

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserDetailsEntity(
//    val accountDetail: List<AccountDetail> = emptyList(),
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
    @PrimaryKey(autoGenerate = false) val mobileNumber: String = "",
    val oauthTokenCount: Int,
    val otpString: String,
    //val qr: List<Qr> = emptyList(),
    val registered: Boolean,
    val smsService: Boolean,
    val socketPrefix: String,
    val socketURl: String,
    val state: String,
    val unseenNotificationCount: Int
)
