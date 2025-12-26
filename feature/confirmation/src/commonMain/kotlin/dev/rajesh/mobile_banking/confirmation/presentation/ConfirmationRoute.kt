package dev.rajesh.mobile_banking.confirmation.presentation

import dev.rajesh.mobile_banking.confirmation.model.ConfirmationData
import kotlinx.serialization.Serializable

@Serializable
data class ConfirmationRoute(val data: ConfirmationData)