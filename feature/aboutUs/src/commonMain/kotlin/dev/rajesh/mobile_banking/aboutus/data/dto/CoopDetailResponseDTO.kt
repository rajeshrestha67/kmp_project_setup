package dev.rajesh.mobile_banking.aboutus.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CoopDetailResponseDTO(
    val status: String,
    val code: String,
    val message: String,
    val detail: CoopDetailDTO,
)

@Serializable
data class CoopDetailDTO(
    val id: Int? = null,
    val created: String? = null,
    val lastModified: String? = null,
    val version: Int? = null,
    val contactNumber: String? = null,
    val facebookUrl: String? = null,
    val registerUrl: String? = null,
    val email: String? = null,
    val web: String? = null,
    val address: String? = null,
    val splashScreenImageUrl: String? = null,
    val appPrimaryColor: String? = null,
    val showInterestRate: Boolean? = null,
    val showChatSetting: Boolean? = null,
    val showForceAppUpdate: Boolean? = null,
    val fundTransferViaMobileNumber: Boolean? = null,
    val showAnimationForDigitalDakshina: Boolean? = null,
    val showSyncAllAccounts: String? = null,
    val isRemitHubEnabled: Boolean? = null,
    val isDynamicQrEnabled: Boolean? = null,
    val bankId: String? = null,
    val bankName: String? = null,
    val ibankHost: String? = null,
    val ibankingPrimaryColor: String? = null,
    val ibankingSecondaryColor: String? = null,
    val ibankingTertiaryColor: String? = null,
    val new: Boolean
)
