package dev.rajesh.mobile_banking.home.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class BankingServiceDTO(
    val status: String? = null,
    val code: String? = null,
    val message: String? = null,
    val details: List<BankingServiceDetailDTO>? = null,
//    val detail: Any,
//    val packages: Any
)

@Serializable
data class BankingServiceDetailDTO(
    val name: String? = null,
    val uniqueIdentifier: String? = null,
    val type: String? = null,
    val status: String? = null,
    val imageUrl: String? = null,
    val appOrder: Int? = null,
    val new: Boolean? = null
)
