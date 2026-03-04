package dev.rajesh.mobile_banking.database.models.coop

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CoopDetailEntity(
    @PrimaryKey(autoGenerate = false) val id: Int = 0,
    val clientId:String = "",
    val created: String = "",
    val lastModified: String = "",
    val version: Int = 0,
    val contactNumber: String = "",
    val facebookUrl: String = "",
    val registerUrl: String = "",
    val email: String = "",
    val web: String = "",
    val address: String = "",
    val splashScreenImageUrl: String = "",
    val appPrimaryColor: String = "",
    val showInterestRate: Boolean = false,
    val showChatSetting: Boolean = false,
    val showForceAppUpdate: Boolean = false,
    val fundTransferViaMobileNumber: Boolean = false,
    val showAnimationForDigitalDakshina: Boolean = false,
    val showSyncAllAccounts: String = "",
    val isRemitHubEnabled: Boolean = false,
    val isDynamicQrEnabled: Boolean = false,
    val bankId: String = "",
    val bankName: String = "",
    val ibankHost: String = "",
    val ibankingPrimaryColor: String = "",
    val ibankingSecondaryColor: String = "",
    val ibankingTertiaryColor: String = "",
    val new: Boolean
)