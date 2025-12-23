package dev.rajesh.mobile_banking.banktransfer.presentation.model

import kotlinx.serialization.Serializable

@Serializable
data class BankTransferOption(
    val id: Int,
    val title: String,
    val description: String
)