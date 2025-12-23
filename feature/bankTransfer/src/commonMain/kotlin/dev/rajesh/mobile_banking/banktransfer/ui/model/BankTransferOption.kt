package dev.rajesh.mobile_banking.banktransfer.ui.model

import kotlinx.serialization.Serializable

@Serializable
data class BankTransferOption(
    val id: Int,
    val title: String,
    val description: String
)