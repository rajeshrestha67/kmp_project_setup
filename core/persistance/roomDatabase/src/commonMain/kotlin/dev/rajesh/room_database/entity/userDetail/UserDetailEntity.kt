package dev.rajesh.room_database.entity.userDetail

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity //(tableName = "user_details")
data class UserDetailEntity(
    @PrimaryKey val mobileNumber: String,
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
    val oauthTokenCount: Int,
    val otpString: String,
    val registered: Boolean,
    val smsService: Boolean,
    val socketPrefix: String,
    val socketURl: String,
    val state: String,
    val unseenNotificationCount: Int,
    //    val accountDetail: List<AccountDetail>,
    //    val qr: List<Qr>,
)