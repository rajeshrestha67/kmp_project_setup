package dev.rajesh.mobile_banking.home.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class QuickServicesResponseDTO(
    val status: String,
    val code: String,
    val message: String,
    val details: List<QuickServiceDetailDTO>,
//    val detail: Any,
//    val packages: Any
)

@Serializable
data class QuickServiceDetailDTO(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val uniqueIdentifier: String,
    val isNew: Boolean,
    val appOrder: Int,
    val services: List<ServiceDTO>
)

@Serializable
data class ServiceDTO(
    val id: Int,
    val url: String,
    val uniqueIdentifier: String,
    val service: String,
    val status: String,
    val labelName: String,
    val labelSize: String?=null,
    val labelSample: String,
    val labelPrefix: String,
    val instructions: String,
    val fixedlabelSize: Boolean,
    val priceInput: Boolean,
    val notificationUrl: String,
    val minValue: Double,
    val maxValue: Double,
    val icon: String,
    val categoryId: Int,
    val serviceCategoryName: String,
    val webView: Boolean,
    val isNew: Boolean,
    val cashBackView : String?= null,
    val appOrder: Int,
    val isSmsMode: Boolean,
    val ticketStatus: Boolean,
    val labelMaxLength: String?= null,
    val labelMinLength: String?= null,
    val priceRange: String?= null
)