package dev.rajesh.mobile_banking.home.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class BankingServiceDTO(
    val status: String,
    val code: String,
    val message: String,
    val details: List<BankingServiceDetailDTO>,
//    val detail: Any,
//    val packages: Any
)

@Serializable
data class BankingServiceDetailDTO(
    val name: String,
    val uniqueIdentifier: String,
    val type: String,
    val status: String,
    val imageUrl: String,
    val appOrder: Int,
    val new: Boolean
)
