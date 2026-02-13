package dev.rajesh.mobile_banking.home.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class QuickServicesResponseDTO(
    val status: String? = null,
    val code: String? = null,
    val message: String? = null,
    val details: List<QuickServiceDetailDTO>? = null,
//    val detail: Any,
//    val packages: Any
)

@Serializable
data class QuickServiceDetailDTO(
    val id: Int? = null,
    val name: String? = null,
    val imageUrl: String? = null,
    val uniqueIdentifier: String? = null,
    val isNew: Boolean? = null,
    val appOrder: Int? = null,
    val services: List<ServiceDTO>? = null
)

@Serializable
data class ServiceDTO(
    val id: Int? = null,
    val url: String? = null,
    val uniqueIdentifier: String? = null,
    val service: String? = null,
    val status: String? = null,
    val labelName: String? = null,
    val labelSize: String? = null,
    val labelSample: String? = null,
    val labelPrefix: String? = null,
    val instructions: String? = null,
    val fixedlabelSize: Boolean? = null,
    val priceInput: Boolean? = null,
    val notificationUrl: String? = null,
    val minValue: Double? = null,
    val maxValue: Double? = null,
    val icon: String? = null,
    val categoryId: Int? = null,
    val serviceCategoryName: String? = null,
    val webView: Boolean? = null,
    val isNew: Boolean? = null,
    val cashBackView: String? = null,
    val appOrder: Int? = null,
    val isSmsMode: Boolean? = null,
    val ticketStatus: Boolean? = null,
    val labelMaxLength: String? = null,
    val labelMinLength: String? = null,
    val priceRange: String? = null
)