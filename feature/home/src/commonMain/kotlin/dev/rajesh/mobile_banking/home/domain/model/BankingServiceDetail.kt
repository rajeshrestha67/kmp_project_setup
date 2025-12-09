package dev.rajesh.mobile_banking.home.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class BankingServiceDetail(
    val name: String,
    val uniqueIdentifier: String,
    val type: String,
    val status: String,
    val imageUrl: String,
    val appOrder: Int,
    val new: Boolean
)